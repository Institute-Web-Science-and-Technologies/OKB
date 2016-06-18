package WikiDataItem;

import java.time.LocalDateTime;

/**
 * Created by wkoop on 13.06.2016.
 */
public class Reference {
    private String url;
    private String title;
    private LocalDateTime publicationDate;
    private LocalDateTime retrievalDate;
    private String authors;
    private String articleType;
    private float trustRating;
    private float neutralityRating;

    public Reference() {
    }

    public Reference(String url, String title, LocalDateTime publicationDate, LocalDateTime retrievalDate, String authors, String articleType, float trustRating, float neutralityRating) {
        this.url = url;
        this.title = title;
        this.publicationDate = publicationDate;
        this.retrievalDate = retrievalDate;
        this.authors = authors;
        this.articleType = articleType;
        this.trustRating = trustRating;
        this.neutralityRating = neutralityRating;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public LocalDateTime getRetrievalDate() {
        return retrievalDate;
    }

    public void setRetrievalDate(LocalDateTime retrievalDate) {
        this.retrievalDate = retrievalDate;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public float getTrustRating() {
        return trustRating;
    }

    public void setTrustRating(float trustRating) {
        this.trustRating = trustRating;
    }

    public float getNeutralityRating() {
        return neutralityRating;
    }

    public void setNeutralityRating(float neutralityRating) {
        this.neutralityRating = neutralityRating;
    }
}
