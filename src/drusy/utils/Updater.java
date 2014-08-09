package drusy.utils;

public interface Updater {
    public void updated();

    public class FakeUpdater implements Updater {

        @Override
        public void updated() {

        }
    }
}