package thachdd.vuighenet.api_client;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import thachdd.vuighenet.model.SeasonsResponse;

/**
 * Created by thachdd on 05/02/2017.
 */

public interface ApiInterface {
    @Headers({
            "Referer: http://vuighe.net/vua-hai-tac",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET("films/656/seasons")
    Call<SeasonsResponse> getSeasons();
}
