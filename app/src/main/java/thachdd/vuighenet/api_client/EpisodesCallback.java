package thachdd.vuighenet.api_client;

import android.app.Activity;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thachdd.vuighenet.activity.MainActivity;
import thachdd.vuighenet.model.EpisodesResponse;

/**
 * Created by cpu60011-local on 07/02/2017.
 */

public class EpisodesCallback implements Callback<EpisodesResponse> {
    private final WeakReference<Activity> weakReference;

    public EpisodesCallback(Activity activity) {
        weakReference = new WeakReference<Activity>(activity);
    }

    @Override
    public void onResponse(Call<EpisodesResponse> call, Response<EpisodesResponse> response) {
        MainActivity activity = (MainActivity) weakReference.get();
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            activity.onEpisodesLoadedSuccessfully(response.body());
        }
    }

    @Override
    public void onFailure(Call<EpisodesResponse> call, Throwable t) {
        MainActivity activity = (MainActivity) weakReference.get();
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            activity.onEpisodesLoadedFailed();
        }
    }
}
