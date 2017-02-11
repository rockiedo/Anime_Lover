package thachdd.vuighenet.async;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import thachdd.vuighenet.activity.PlayerActivity;

/**
 * Created by thachdd on 11/02/2017.
 */

public class RedirectAsync extends AsyncTask<String,Void,Uri> {
    private WeakReference<Activity> weakReference;

    public RedirectAsync(Activity activity) {
        weakReference = new WeakReference<Activity>(activity);
    }

    @Override
    protected Uri doInBackground(String... params) {
        Uri uri = Uri.parse(params[0]);

        try {
            URL url = new URL(uri.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String redirected = conn.getHeaderField("Location");

            Log.d("mylog", redirected);

            return Uri.parse(redirected);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Uri uri) {
        super.onPostExecute(uri);

        PlayerActivity activity = (PlayerActivity) weakReference.get();
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            if (uri == null) {
                activity.onRedirectFailed();
            }
            else {
                activity.onRedirectSuccessfully(uri);
            }
        }
    }
}
