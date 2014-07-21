package drusy.utils;

import aurelienribon.utils.DialogUtils;
import aurelienribon.utils.HttpUtils;
import drusy.ui.ErrorDialog;
import org.json.JSONObject;

import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Checker {
    public static void CheckVersion() {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        HttpUtils.DownloadGetTask task = HttpUtils.downloadGetAsync(Config.VERSION_DISTANT_FILE, output, "Checking version");

        task.addListener(new HttpUtils.DownloadListener() {
            @Override public void onComplete() {
                float lastVersion = Float.parseFloat(output.toString());

                if (lastVersion < Config.CURRENT_VERSION) {
                    Log.Debug("Version Checker", "Last version is " + lastVersion + " (current " + Config.CURRENT_VERSION + ")");
                } else {
                    Log.Debug("Version Checker", "Last version installed");
                }
            }
        });
    }

    public static void CheckFreeboxUp(final JFrame frame) {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        HttpUtils.DownloadGetTask task = HttpUtils.downloadGetAsync(Config.FREEBOX_API_CHECK_URL, output, "Checking freebox");

        task.addListener(new HttpUtils.DownloadListener() {
            @Override public void onComplete() {
                String json = output.toString();
                JSONObject obj = new JSONObject(json);
                boolean success = obj.getBoolean("success");
                JSONObject result = obj.getJSONObject("result");
                boolean loggedIn = result.getBoolean("logged_in");

                if (success == true) {
                    Log.Debug("Freebox Checker", "Freebox is UP (logged_in : " + loggedIn + ")");
                    FreeboxConnector.TokenRequest();
                }
            }
        });

        task.addListener(new HttpUtils.DownloadListener() {
            @Override
            public void onError(IOException ex) {
                ErrorDialog dialog = new ErrorDialog(frame);
                DialogUtils.fadeIn(dialog);
            }
        });
    }
}
