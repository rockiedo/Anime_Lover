package thachdd.vuighenet.api_client;

import android.app.Activity;
import android.util.Log;

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
            List<PlayerDetail> players = response.body().getData();

            if (players.size() > 0) {
                int id360 = -1, id480 = -1;

                for (int i = 0; i < players.size(); i++) {
                    if (players.get(i).getResolution() == 360) {
                        id360 = i;
                    }

                    if (players.get(i).getResolution() == 480) {
                        id480 = i;
                        break;
                    }
                }

                if (id480 != -1) {
                    activity.onPlayerLoadedSuccessfully(players.get(id480).getUrl());
                }
                else if (id360 != -1) {
                    activity.onPlayerLoadedSuccessfully(players.get(id360).getUrl());
                }
                else {
                    activity.onPlayerLoadedFailed();
                }
            }
            else {
                activity.onPlayerLoadedFailed();
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
