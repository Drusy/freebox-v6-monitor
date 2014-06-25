package drusy.utils;

import aurelienribon.utils.HttpUtils;

import java.io.ByteArrayOutputStream;

public class Checker {
    public static void CheckVersion() {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        HttpUtils.DownloadTask task = HttpUtils.downloadAsync(Config.VERSION_DISTANT_FILE, output, "Checking version");

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

    public static void CheckFreeboxUp() {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        HttpUtils.DownloadTask task = HttpUtils.downloadAsync(Config.FREEBOX_API_VERSION_URL, output, "Checking freebox");

        task.addListener(new HttpUtils.DownloadListener() {
            @Override public void onComplete() {
                Log.Debug("Freebox Checker", "Freebox is UP and API version is : " + output.toString());
            }
        });
    }
}
