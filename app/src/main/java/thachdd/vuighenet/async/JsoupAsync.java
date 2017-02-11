package thachdd.vuighenet.async;

import android.app.Activity;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.lang.ref.WeakReference;

import thachdd.vuighenet.activity.PlayerActivity;

/**
 * Created by thachdd on 08/02/2017.
 */

public class JsoupAsync extends AsyncTask<String, Void, String> {
    private final String HOST = "http://vuighe.net";

    private WeakReference<Activity> weakReference = null;

    public JsoupAsync(Activity activity) {
        weakReference = new WeakReference<Activity>(activity);
    }

    @Override
    protected String doInBackground(String... params) {
        String link = "/tap-0-gioi-thieu-phim-vua-hai-tac";

        if (params != null && params.length > 0) {
            link = params[0];
        }

        String url = params[0];
        try {
            Document doc = Jsoup.connect(url).get();
            Element element = doc.getElementById("videoPlayer");
            Node html = doc.childNode(1);
            Node body = html.childNode(2);
            Node filmPage = body.childNode(3);
            Node playerWrapper = filmPage.childNode(3);
            Node player = playerWrapper.childNode(1);
            Node playerContent = player.childNode(1);


            String res = element.attr("src");

            return res;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        PlayerActivity activity = (PlayerActivity) weakReference.get();
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            activity.onPlayerLoadedSuccessfully(s);
        }
    }
}
