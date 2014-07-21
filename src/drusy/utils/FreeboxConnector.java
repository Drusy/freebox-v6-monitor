package drusy.utils;

import aurelienribon.utils.HttpUtils;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class FreeboxConnector {

    public static void TrackAuthorizationProgress(String app_token, int track_id) {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        HttpUtils.DownloadTask task = HttpUtils.downloadGetAsync(Config.FREEBOX_API_AUTHORIZE + "/" + String.valueOf(track_id), output, "Track authorization progress");

        task.addListener(new HttpUtils.DownloadListener() {
            @Override public void onComplete() {
                System.out.println(output.toString());
            }
        });

        task.addListener(new HttpUtils.DownloadListener() {
            @Override
            public void onError(IOException ex) {

            }
        });
    }

    public static void TokenRequest() {
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
                String json = output.toString();
                JSONObject obj = new JSONObject(json);
                boolean success = obj.getBoolean("success");
                JSONObject result = obj.getJSONObject("result");
                String app_token = result.getString("app_token");
                int track_id = result.getInt("track_id");

                System.out.println(json);

                if (success == true)  {
                    TrackAuthorizationProgress(app_token, track_id);
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
}
