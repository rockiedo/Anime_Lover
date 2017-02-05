package thachdd.vuighenet.api_client;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thachdd.vuighenet.activity.IntroActivity;
import thachdd.vuighenet.model.SeasonsResponse;

/**
 * Created by thachdd on 05/02/2017.
 */

public class SeasonsCallback implements Callback<SeasonsResponse> {
    private final WeakReference<IntroActivity> activityWeakReference;

    public SeasonsCallback(IntroActivity activity) {
        activityWeakReference = new WeakReference<IntroActivity>(activity);
    }

    @Override
    public void onResponse(Call<SeasonsResponse> call, Response<SeasonsResponse> response) {
        IntroActivity activity = activityWeakReference.get();
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            activity.showResult(response.body());
        }
    }

    @Override
    public void onFailure(Call<SeasonsResponse> call, Throwable t) {

    }
}
