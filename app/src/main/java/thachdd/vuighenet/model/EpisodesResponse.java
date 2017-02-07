package thachdd.vuighenet.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cpu60011-local on 07/02/2017.
 */

public class EpisodesResponse {
    @SerializedName("data")
    private List<EpisodeDetail> episodesDetail;

    public List<EpisodeDetail> getEpisodesDetail() {
        return episodesDetail;
    }

    public void setEpisodesDetail(List<EpisodeDetail> episodesDetail) {
        this.episodesDetail = episodesDetail;
    }
}
