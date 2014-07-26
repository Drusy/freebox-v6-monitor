package aurelienribon.utils;

import drusy.utils.FreeboxConnector;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Utility class used to quickly download files on distant servers.
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class HttpUtils {
	private static final List<Listener> listeners = new CopyOnWriteArrayList<Listener>();
	/**
	 * Asynchronously downloads the file located at the given url. Content is
	 * written to the given stream. If the url is malformed, the method returns
	 * null. Else, a {@link aurelienribon.utils.HttpUtils.DownloadGetTask} is returned. Use it if you need to
	 * cancel the download at any time.
	 * <p/>
	 * The returned object also lets you add event listeners to warn you
	 * when the download is complete, if an error happens (such as a connection
	 * loss). The listeners also let you be notified of the download progress.
	 */
	public static DownloadGetTask downloadAsync(String url, OutputStream output) {
		return downloadGetAsync(url, output, null, true);
	}

	/**
	 * Asynchronously downloads the file located at the given url. Content is
	 * written to the given stream. If the url is malformed, the method returns
	 * null. Else, a {@link aurelienribon.utils.HttpUtils.DownloadGetTask} is returned. Use it if you need to
	 * cancel the download at any time.
	 * <p/>
	 * The returned object also lets you add event listeners to warn you
	 * when the download is complete, if an error happens (such as a connection
	 * loss). The listeners also let you be notified of the download progress.
	 * <p/>
	 * You can also assign a custom tag to the download, to pass information
	 * to the listeners for instance.
	 */
	public static DownloadGetTask downloadGetAsync(String url, OutputStream output, String tag, boolean taskPanelTile) {
		URL input;

		try {
			input = new URL(url);
		} catch (MalformedURLException ex) {
			return null;
		}

		final DownloadGetTask task = new DownloadGetTask(input, output, tag, taskPanelTile);
		for (Listener lst : listeners)  {
            lst.newDownload(task);
        }

		task.start();
		return task;
	}

    public static DownloadPostTask downloadPostAsync(String url, OutputStream output, String tag, JSONObject json) {
        URL input;

        try {
            input = new URL(url);
        } catch (MalformedURLException ex) {
            return null;
        }

        final DownloadPostTask task = new DownloadPostTask(input, output, tag, json);
        for (Listener lst : listeners) lst.newDownload(task);

        task.start();
        return task;
    }

	/**
	 * Adds a new listener to catch the start of new downloads.
	 */
	public static void addListener(Listener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes the given listener.
	 */
	public static void removeListener(Listener listener) {
		listeners.remove(listener);
	}

	// -------------------------------------------------------------------------
	// Classes
	// -------------------------------------------------------------------------

	/**
	 * Listener for start of new downloads.
	 */
	public static interface Listener {
		public void newDownload(DownloadTask task);
	}

	/**
	 * Listener for a {@link aurelienribon.utils.HttpUtils.DownloadGetTask}. Used to get notified about all the
	 * download events: completion, errors and progress.
	 */
	public static class DownloadListener {
		public void onComplete() {}
		public void onCancel() {}
		public void onError(IOException ex) {}
		public void onUpdate(int length, int totalLength) {}
	}


    public static interface DownloadTask {
        public String getTag();
        public void stop();
        public void addListener(DownloadListener listener);
        public boolean hasTaskPanelTile();
    }

	/**
	 * A download task lets you cancel the current download in progress. You
	 * can also access its parameters, such as the input and output streams.
	 */
	public static class DownloadGetTask implements DownloadTask {
		private final URL input;
		private final OutputStream output;
		private final String tag;
		private final List<DownloadListener> listeners = new CopyOnWriteArrayList<DownloadListener>();
		private boolean run = true;
        private boolean taskPanelTile;

		public DownloadGetTask(URL input, OutputStream output, String tag, boolean taskPanelTile) {
			this.input = input;
			this.output = output;
			this.tag = tag;
            this.taskPanelTile = taskPanelTile;
		}

        public boolean hasTaskPanelTile() {
            return taskPanelTile;
        }

		/**
		 * Adds a new listener to listen for the task events.
		 */
		public void addListener(DownloadListener listener) {
			listeners.add(listener);
		}

		/**
		 * Cancels the download. If a callback is associated to the download
		 * task, its onCancel() method will be raised instead of the
		 * onComplete() one.
		 */
		public void stop() {
			if (run == false) for (DownloadListener lst : listeners) lst.onCancel();
			run = false;
		}

		public URL getInput() {return input;}
		public OutputStream getOutput() {return output;}
		public String getTag() {return tag;}

		private void start() {
			new Thread(new Runnable() {@Override public void run() {
				OutputStream os = null;
				InputStream is = null;
				IOException ex = null;

				try {
					HttpURLConnection connection = (HttpURLConnection) input.openConnection();
					connection.setDoInput(true);
					connection.setDoOutput(false);
					connection.setUseCaches(true);
                    connection.setRequestProperty("X-Fbx-App-Auth", FreeboxConnector.SessionToken);
					connection.setConnectTimeout(5000);
					connection.connect();

					is = new BufferedInputStream(connection.getInputStream(), 4096);
					os = output;

					byte[] data = new byte[4096];
					int length = connection.getContentLength();
					int total = 0;

					int count;
					while (run && (count = is.read(data)) != -1) {
						total += count;
						os.write(data, 0, count);
						for (DownloadListener l : listeners) l.onUpdate(total, length);
					}

				} catch (IOException ex1) {
					ex = ex1;

				} finally {
					if (os != null) try {os.flush(); os.close();} catch (IOException ex1) {}
					if (is != null) try {is.close();} catch (IOException ex1) {}

					if (ex != null) for (DownloadListener l : listeners) l.onError(ex);
					else if (run == true) for (DownloadListener l : listeners) l.onComplete();
					else for (DownloadListener l : listeners) l.onCancel();

					run = false;
				}

			}}).start();
		}
	}

    /**
     * A download task lets you cancel the current download in progress. You
     * can also access its parameters, such as the input and output streams.
     */
    public static class DownloadPostTask implements DownloadTask{
        private final URL input;
        private final OutputStream output;
        private final String tag;
        private final List<DownloadListener> listeners = new CopyOnWriteArrayList<DownloadListener>();
        private boolean run = true;
        JSONObject jsonOutput;

        public DownloadPostTask(URL input, OutputStream output, String tag, JSONObject jsonOutput) {
            this.input = input;
            this.output = output;
            this.tag = tag;
            this.jsonOutput = jsonOutput;
        }

        /**
         * Adds a new listener to listen for the task events.
         */
        public void addListener(DownloadListener listener) {
            listeners.add(listener);
        }

        @Override
        public boolean hasTaskPanelTile() {
            return true;
        }

        /**
         * Cancels the download. If a callback is associated to the download
         * task, its onCancel() method will be raised instead of the
         * onComplete() one.
         */
        public void stop() {
            if (run == false) for (DownloadListener lst : listeners) lst.onCancel();
            run = false;
        }

        public URL getInput() {return input;}
        public OutputStream getOutput() {return output;}
        public String getTag() {return tag;}

        private void start() {
            new Thread(new Runnable() {@Override public void run() {
                OutputStream os = null;
                InputStream is = null;
                IOException ex = null;

                try {
                    HttpURLConnection connection = (HttpURLConnection) input.openConnection();
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(true);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    connection.setRequestProperty("charset", "utf-8");
                    connection.setRequestProperty("Content-Length", "" + Integer.toString(jsonOutput.toString().getBytes("UTF-8").length));
                    connection.setConnectTimeout(5000);
                    connection.connect();

                    DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                    wr.write(jsonOutput.toString().getBytes("UTF-8"));
                    wr.flush();
                    wr.close();

                    is = new BufferedInputStream(connection.getInputStream(), 4096);
                    os = output;

                    byte[] data = new byte[4096];
                    int length = connection.getContentLength();
                    int total = 0;

                    int count;
                    while (run && (count = is.read(data)) != -1) {
                        total += count;
                        os.write(data, 0, count);
                        for (DownloadListener l : listeners) l.onUpdate(total, length);
                    }

                } catch (IOException ex1) {
                    ex = ex1;

                } finally {
                    if (os != null) try {os.flush(); os.close();} catch (IOException ex1) {}
                    if (is != null) try {is.close();} catch (IOException ex1) {}

                    if (ex != null) for (DownloadListener l : listeners) l.onError(ex);
                    else if (run == true) for (DownloadListener l : listeners) l.onComplete();
                    else for (DownloadListener l : listeners) l.onCancel();

                    run = false;
                }

            }}).start();
        }
    }
}
