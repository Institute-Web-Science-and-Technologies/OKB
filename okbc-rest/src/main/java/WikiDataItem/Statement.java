package WikiDataItem;

import java.util.Enumeration;
import java.util.List;

/**
 * Created by wkoop on 13.06.2016.
 */
public class Statement {
    private String propertyId;
    private String label;
    private Datatype datatype;
    private List<Claim> claims;

    public String toString(){
        String result = "{"+"\"propertyid\": \""+propertyId+"\", ";
        result += "\""+"label\": \""+label+"\", \"datatype\": \"" + datatype+"\"";
        result += ", \"claims\": [";
        if(claims.size()>0) {
            for (int i = 0; i < claims.size(); i++) {
                if (i == claims.size() - 1) {
                    result += claims.get(i).toString();
                    break;
                }
                result += claims.get(i).toString() + ", ";
            }
        }
        result+= "]}]";

        return result;
    }

    public Statement() {
    }


    public Statement(String propertyId, String label, Datatype datatype, List<Claim> claims) {
        this.propertyId = propertyId;
        this.label = label;
        this.datatype = datatype;
        this.claims = claims;
    }

    public Datatype getDatatype() {
        return datatype;
    }

    public void setDatatype(Datatype datatype) {
        this.datatype = datatype;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Claim> getClaims() {
        return claims;
    }

    public void setClaims(List<Claim> claims) {
        this.claims = claims;
    }
}
