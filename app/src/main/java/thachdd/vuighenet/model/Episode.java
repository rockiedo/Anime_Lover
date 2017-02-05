package thachdd.vuighenet.model;

/**
 * Created by thachdd on 05/02/2017.
 */

public class Episode {
    private String mTitle;
    private String mLink;
    private String mImageUrl;
    private String mViews;

    public Episode() {
        mTitle = null;
        mLink = null;
        mImageUrl = null;
        mViews = null;
    }

    public Episode(String title, String link, String imageUrl, String views) {
        mTitle = title;
        mLink = link;
        mImageUrl = imageUrl;
        mViews = views;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getViews() {
        return mViews;
    }

    public void setViews(String views) {
        mViews = views;
    }
}
