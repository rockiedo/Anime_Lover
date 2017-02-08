package thachdd.vuighenet.api_client;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thachdd.vuighenet.activity.PlayerActivity;
import thachdd.vuighenet.model.PlayerDetail;
import thachdd.vuighenet.model.PlayerResponse;

/**
 * Created by cpu60011-local on 07/02/2017.
 */

public class PlayerCallback implements Callback<PlayerResponse> {
    private final WeakReference<Activity> weakReference;

    public PlayerCallback(Activity activity) {
        weakReference = new WeakReference<Activity>(activity);
    }

    @Override
    public void onResponse(Call<PlayerResponse> call, Response<PlayerResponse> response) {
        PlayerActivity activity = (PlayerActivity) weakReference.get();
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            List<PlayerDetail> players = response.body().getSources().getData();

            if (players.size() > 0) {
                activity.onPlayerLoadedSuccessfully(players.get(1).getLink());
            }
        }
    }

    @Override
    public void onFailure(Call<PlayerResponse> call, Throwable t) {
        PlayerActivity activity = (PlayerActivity) weakReference.get();
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            activity.onPlayerLoadedFailed();
        }
    }
}
