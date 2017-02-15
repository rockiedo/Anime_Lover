package thachdd.vuighenet.api_client;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import thachdd.vuighenet.model.PlayerResponse;

/**
 * Created by thachdd on 14/02/2017.
 */

public interface HerokuInterface {
    @GET("player")
    Call<PlayerResponse> getPlayerDetail(@Query("id") int id);
}
