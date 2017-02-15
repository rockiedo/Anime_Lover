package thachdd.vuighenet.api_client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by thachdd on 05/02/2017.
 */

public class ApiClient {
    public static final String BASE_URL = "http://vuighe.net/api/v2/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        }

        return retrofit;
    }

    public static final String HEROKU_URL = "https://anime-lover.herokuapp.com/api/";
    private static Retrofit heroku = null;

    public static Retrofit getHeroku() {
        if (heroku == null) {
            heroku = new Retrofit.Builder()
                        .baseUrl(HEROKU_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        }

        return heroku;
    }
}
