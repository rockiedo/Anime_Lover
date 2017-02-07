package thachdd.vuighenet.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cpu60011-local on 07/02/2017.
 */

public class PlayerDetail {
    @SerializedName("src")
    private String link;
    @SerializedName("type")
    private String type;
    @SerializedName("quality")
    private String quality;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
