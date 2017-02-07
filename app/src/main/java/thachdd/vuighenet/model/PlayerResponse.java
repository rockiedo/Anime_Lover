package thachdd.vuighenet.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cpu60011-local on 07/02/2017.
 */

public class PlayerResponse {
    @SerializedName("sources")
    private PlayerWrapper sources;

    public PlayerWrapper getSources() {
        return sources;
    }

    public void setSources(PlayerWrapper sources) {
        this.sources = sources;
    }
}
