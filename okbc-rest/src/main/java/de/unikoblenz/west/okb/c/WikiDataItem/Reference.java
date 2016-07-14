package de.unikoblenz.west.okb.c.WikiDataItem;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by wkoop on 13.06.2016.
 */
public class Reference {
    private String url;
    private String title;
    private String publicationDate;
    private String retrievalDate;
    private ArrayList<String> authors;
    private String articleType;
    private float trustRating;
    private float neutralityRating;

    public String toString(){
        String out="{\"url\": \""+url+"\", \"title\": \""+title+
                "\", \"publicationDate\": \""+ publicationDate+"\", \"retrievalDate\": \""
                +retrievalDate+ "\", \"authors\": [";
       /* if(authors.size()>0) {
            for (int i = 0; i < authors.size(); i++) {
                if (i == authors.size() - 1)
                    out += "\"" + authors.get(i) + "\"";
                else
                    out += "\"" + authors.get(i) + "\", ";
            }
        }
        */
        out+="], \"articleType\": \""+articleType+"\", \"trustRating\": \""
                +trustRating+"\", \"neutralityRating\": \""+neutralityRating+"\"}";
        return out;
    }

    public Reference() {
    }

    public Reference(String url, String title, String publicationDate, String retrievalDate, ArrayList<String> authors, String articleType, float trustRating, float neutralityRating) {
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

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getRetrievalDate() {
        return retrievalDate;
    }

    public void setRetrievalDate(String retrievalDate) {
        this.retrievalDate = retrievalDate;
    }

    public ArrayList<String>  getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String>  authors) {
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
