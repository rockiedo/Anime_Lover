package thachdd.vuighenet.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cpu60011-local on 07/02/2017.
 */

public class PlayerWrapper {
    @SerializedName("data")
    private List<PlayerDetail> data;

    public List<PlayerDetail> getData() {
        return data;
    }

    public void setData(List<PlayerDetail> data) {
        this.data = data;
    }
}
