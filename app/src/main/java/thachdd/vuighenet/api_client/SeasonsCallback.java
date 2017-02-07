package thachdd.vuighenet.api_client;

import android.app.Activity;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thachdd.vuighenet.activity.MainActivity;
import thachdd.vuighenet.model.SeasonsResponse;

/**
 * Created by thachdd on 05/02/2017.
 */

public class SeasonsCallback implements Callback<SeasonsResponse> {
    private final WeakReference<Activity> activityWeakReference;

    public SeasonsCallback(Activity activity) {
        activityWeakReference = new WeakReference<Activity>(activity);
    }

    @Override
    public void onResponse(Call<SeasonsResponse> call, Response<SeasonsResponse> response) {
        MainActivity activity = (MainActivity) activityWeakReference.get();
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            activity.onSeasonsLoadedSuccessfully(response.body());
        }
    }

    @Override
    public void onFailure(Call<SeasonsResponse> call, Throwable t) {
        MainActivity activity = (MainActivity) activityWeakReference.get();
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            activity.onSeasonsLoadedFailed();
        }
    }
}
