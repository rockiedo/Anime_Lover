package thachdd.vuighenet.api_client;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import thachdd.vuighenet.model.EpisodesResponse;
import thachdd.vuighenet.model.PlayerResponse;
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

    @Headers({
            "Referer: http://vuighe.net/vua-hai-tac",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET("seasons/{id}/episodes?sort=name")
    Call<EpisodesResponse> getEpisodes(@Path("id") int id);

    @Headers({
            "Referer: http://vuighe.net/vua-hai-tac",
            "X-Requested-With: XMLHttpRequest"
    })
    @GET("films/656/episodes/{id}")
    Call<PlayerResponse> getPlayer(@Path("id") int id);
}
