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
    @SerializedName("name")
    private int name;

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("Season ");
        buffer.append(name);
        buffer.append(" (");
        buffer.append(begin);
        buffer.append("-");
        buffer.append(end);
        buffer.append("): ");
        buffer.append(detailName);

        String res = buffer.toString();
        buffer.delete(0, buffer.length());

        return res;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

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
