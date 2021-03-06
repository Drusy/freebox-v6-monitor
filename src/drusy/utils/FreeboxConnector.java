package drusy.utils;

import aurelienribon.utils.DialogUtils;
import aurelienribon.utils.HttpUtils;
import drusy.ui.dialogs.WaitingGrantDialog;
import org.json.JSONObject;

import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class FreeboxConnector {
    public static String SessionToken = "";

    public static void Connect(JFrame frame) {
        Properties properties = Config.ReadProperties();

        if (properties.getProperty("app_token") == null || properties.getProperty("track_id") == null) {
            FreeboxConnector.TokenRequest(frame);
        } else {
            TrackAuthorizationProgress(Integer.parseInt(properties.getProperty("track_id")), frame);
        }
    }

    public static void TrackAuthorizationProgress(final int track_id, final JFrame frame) {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        HttpUtils.DownloadTask task = HttpUtils.downloadGetAsync(Config.FREEBOX_API_AUTHORIZE + "/" + String.valueOf(track_id), output, "Track authorization progress", true);

        task.addListener(new HttpUtils.DownloadListener() {
            @Override public void onComplete() {
                JSONObject obj = new JSONObject(output.toString());
                boolean success = obj.getBoolean("success");
                JSONObject result = obj.getJSONObject("result");
                String status = result.getString("status");

                Log.Debug("Freebox Connector - TrackAuthorizationProgress", output.toString());

                if (success == true) {
                    if (status.equals("timeout") || status.equals("unknown")) {
                        Config.ClearConfigFile();
                        Connect(frame);
                    } else if (!status.equals("granted")) {
                        final WaitingGrantDialog dialog = new WaitingGrantDialog(frame);;
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                DialogUtils.fadeIn(dialog);
                            }
                        });

                        WaitForUserGrant(track_id, dialog, frame);
                    } else {
                        RetrieveChallenge();
                    }
                }
            }
        });

        task.addListener(new HttpUtils.DownloadListener() {
            @Override
            public void onError(IOException ex) {
                Log.Debug("Freebox Connector - TrackAuthorizationProgress", ex.getMessage());
            }
        });
    }

    public static void WaitForUserGrant(final int track_id, final JDialog dialog, final JFrame frame) {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        HttpUtils.DownloadTask task = HttpUtils.downloadGetAsync(Config.FREEBOX_API_AUTHORIZE + "/" + String.valueOf(track_id), output, "Track authorization progress", true);

        task.addListener(new HttpUtils.DownloadListener() {
            @Override public void onComplete() {
                JSONObject obj = new JSONObject(output.toString());
                boolean success = obj.getBoolean("success");
                JSONObject result = obj.getJSONObject("result");
                String status = result.getString("status");

                Log.Debug("Freebox Connector - WaitForUserGrant", output.toString());

                if (success == true) {
                    // Timeout = reconnect
                    if (status.equals("timeout") || status.equals("unknown")) {
                        Config.ClearConfigFile();
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                DialogUtils.fadeOut(dialog);
                            }
                        });
                        Connect(frame);
                    }
                    // Granted = ok
                    else if  (status.equals("granted")) {
                        if (dialog.isShowing()) {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    DialogUtils.fadeOut(dialog);
                                }
                            });
                        }
                        RetrieveChallenge();
                    }
                    // Else, wait
                    else {
                        try {
                            Thread.sleep(2000);
                            WaitForUserGrant(track_id, dialog, frame);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        task.addListener(new HttpUtils.DownloadListener() {
            @Override
            public void onError(IOException ex) {
                Log.Debug("Freebox Connector - WaitForUserGrant", ex.getMessage());
            }
        });
    }


    public static void TokenRequest(final JFrame frame) {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        JSONObject authorize = new JSONObject();

        authorize.put("app_id", Config.APP_ID);
        authorize.put("app_name", Config.APP_NAME);
        authorize.put("app_version", String.valueOf(Config.CURRENT_VERSION));
        try {
            authorize.put("device_name", InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        HttpUtils.DownloadPostTask task = HttpUtils.downloadPostAsync(Config.FREEBOX_API_AUTHORIZE, output, "Request authorization", authorize);

        task.addListener(new HttpUtils.DownloadListener() {
            @Override public void onComplete() {
                JSONObject obj = new JSONObject(output.toString());
                boolean success = obj.getBoolean("success");
                JSONObject result = obj.getJSONObject("result");
                String app_token = result.getString("app_token");
                int track_id = result.getInt("track_id");

                Properties properties = new Properties();
                properties.setProperty("app_token", app_token);
                properties.setProperty("track_id", String.valueOf(track_id));
                Config.WriteProperties(properties);

                Log.Debug("Freebox Connector - TokenRequest", output.toString());

                if (success == true)  {
                    TrackAuthorizationProgress(track_id, frame);
                }
            }
        });

        task.addListener(new HttpUtils.DownloadListener() {
            @Override
            public void onError(IOException ex) {
                Log.Debug("Freebox Connector - TokenRequest", ex.getMessage());
            }
        });
    }

    public static void RetrieveChallenge() {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        HttpUtils.DownloadGetTask task = HttpUtils.downloadGetAsync(Config.FREEBOX_API_CHECK_URL, output, "Getting Challenge", true);

        task.addListener(new HttpUtils.DownloadListener() {
            @Override public void onComplete() {
                JSONObject obj = new JSONObject(output.toString());
                boolean success = obj.getBoolean("success");
                JSONObject result = obj.getJSONObject("result");
                String challenge = result.getString("challenge");

                Properties properties = Config.ReadProperties();

                if (success == true) {
                    Log.Debug("Freebox Connector - RetrieveChallenge", output.toString());

                    String password = Crypto.HmacSha1(properties.getProperty("app_token"), challenge);
                    RetrieveSessionToken(password);
                }
            }
        });

        task.addListener(new HttpUtils.DownloadListener() {
            @Override
            public void onError(IOException ex) {
            }
        });
    }

    public static void RetrieveSessionToken(String password) {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        JSONObject authorize = new JSONObject();

        authorize.put("app_id", Config.APP_ID);
        authorize.put("password", password);

        HttpUtils.DownloadPostTask task = HttpUtils.downloadPostAsync(Config.FREEBOX_API_SESSION_TOKEN, output, "Request authorization", authorize);

        task.addListener(new HttpUtils.DownloadListener() {
            @Override public void onComplete() {
                JSONObject obj = new JSONObject(output.toString());
                boolean success = obj.getBoolean("success");
                JSONObject result = obj.getJSONObject("result");

                Log.Debug("Freebox Connector - RetrieveSessionToken", output.toString());

                if (success == true)  {
                    SessionToken = result.getString("session_token");
                } else {
                    Log.Debug("Freebox Connector - RetrieveSessionToken", "Bad Password");
                }
            }
        });

        task.addListener(new HttpUtils.DownloadListener() {
            @Override
            public void onError(IOException ex) {
                Log.Debug("Freebox Connector - RetrieveSessionToken", ex.getMessage());
            }
        });
    }
}
