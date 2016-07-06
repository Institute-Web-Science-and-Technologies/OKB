package WikiDataItem;

import java.util.Enumeration;
import java.util.List;

/**
 * Created by wkoop on 13.06.2016.
 */
public class Claim {

    private String value;
    private Snaktype snaktype;
    private String userid;
    private Rank ranking;
    private List<Qualifier> qualifier;
    private List<Reference> references;

    public Claim() {
    }


    public Claim(String value, Snaktype snaktype, String userid, Rank ranking, List<Qualifier> qualifier, List<Reference> references) {
        this.value = value;
        this.snaktype = snaktype;
        this.userid = userid;
        this.ranking = ranking;
        this.qualifier = qualifier;
        this.references = references;
    }

    public String toString(){
        String result = "{\"value\": \""+value + "\", \"snaktype\": \""+snaktype+
                "\", \"ranking\": \""+ranking+"\", " +
                "\"qualifier\": [";
        if(qualifier==null) ;
        else {
            for (int i = 0; i < qualifier.size(); i++) {
                if (i == qualifier.size() - 1) {
                    result += qualifier.get(i).toString();
                    break;
                }
                result += qualifier.get(i).toString() + ", ";
            }
        }
        result +="], \"sources\": [";
        if(references==null) ;
        else {
            for (int i = 0; i < references.size(); i++) {
                if (i == references.size() - 1) {
                    result += references.get(i).toString();
                    break;
                }
                result += references.get(i).toString() + ", ";
            }
        }
        result+= "]}";

        return result;
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

    public Snaktype getSnaktype() {
        return snaktype;
    }

    public void setSnaktype(Snaktype snaktype) {
        this.snaktype = snaktype;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Rank getRanking() {
        return ranking;
    }

    public void setRanking(Rank ranking) {
        this.ranking = ranking;
    }

    public List<Qualifier> getQualifier() {
        return qualifier;
    }

    public void setQualifier(List<Qualifier> qualifier) {
        this.qualifier = qualifier;
    }
}
