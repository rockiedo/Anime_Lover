package thachdd.vuighenet.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by thachdd on 05/02/2017.
 */

public class SeasonDetail {
    @SerializedName("id")
    private int id;
    @SerializedName("begin")
    private int begin;
    @SerializedName("end")
    private int end;
    @SerializedName("detail_name")
    private String detailName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }
}
