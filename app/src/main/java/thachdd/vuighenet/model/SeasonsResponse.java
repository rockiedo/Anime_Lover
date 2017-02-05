package thachdd.vuighenet.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by thachdd on 05/02/2017.
 */

public class SeasonsResponse {
    @SerializedName("data")
    private List<SeasonDetail> seasonsDetail;

    public List<SeasonDetail> getSeasonsDetail() {
        return seasonsDetail;
    }

    public void setSeasonsDetail(List<SeasonDetail> seasonsDetail) {
        this.seasonsDetail = seasonsDetail;
    }
}
