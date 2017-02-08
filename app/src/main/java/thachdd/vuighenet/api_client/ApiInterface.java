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
            "X-Requested-With: XMLHttpRequest",
            "Accept: */*",
            "Accept-Language: en-US,en;q=0.8,vi;q=0.6",
            "Connection: keep-alive",
            "Content-Type: application/json",
            "Host: vuighe.net",
            "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.76 Safari/537.36"
    })
    @GET("films/656/episodes/{id}")
    Call<PlayerResponse> getPlayer(@Path("id") int id);
}
