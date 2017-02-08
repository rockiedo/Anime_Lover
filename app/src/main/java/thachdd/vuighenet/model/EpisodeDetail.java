package thachdd.vuighenet.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cpu60011-local on 07/02/2017.
 */

public class EpisodeDetail {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private int name;
    @SerializedName("full_name")
    private String fullName;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("views")
    private long views;
    @SerializedName("link")
    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
