package WikiDataItem;

import java.util.Enumeration;
import java.util.List;

/**
 * Created by wkoop on 13.06.2016.
 */
public class Claim {

    private String value;
    private Enumeration<Snaktype> snaktype;
    private String userid;
    private Enumeration<Rank> ranking;
    private List<Qualifier> qualifier;
    private List<Reference> references;

    public Claim() {
    }


    public Claim(String value, Enumeration<Snaktype> snaktype, String userid, Enumeration<Rank> ranking, List<Qualifier> qualifier, List<Reference> references) {
        this.value = value;
        this.snaktype = snaktype;
        this.userid = userid;
        this.ranking = ranking;
        this.qualifier = qualifier;
        this.references = references;
    }

    public List<Reference> getReferences() {
        return references;
    }

    public void setReferences(List<Reference> references) {
        this.references = references;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Enumeration<Snaktype> getSnaktype() {
        return snaktype;
    }

    public void setSnaktype(Enumeration<Snaktype> snaktype) {
        this.snaktype = snaktype;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Enumeration<Rank> getRanking() {
        return ranking;
    }

    public void setRanking(Enumeration<Rank> ranking) {
        this.ranking = ranking;
    }

    public List<Qualifier> getQualifier() {
        return qualifier;
    }

    public void setQualifier(List<Qualifier> qualifier) {
        this.qualifier = qualifier;
    }
}
