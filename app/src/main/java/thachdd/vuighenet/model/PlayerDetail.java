package thachdd.vuighenet.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cpu60011-local on 07/02/2017.
 */

public class PlayerDetail {
    @SerializedName("url")
    private String url;
    @SerializedName("resolution")
    private int resolution;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }
}
