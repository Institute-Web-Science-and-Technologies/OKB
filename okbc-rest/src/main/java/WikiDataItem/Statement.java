package WikiDataItem;

import java.util.Enumeration;
import java.util.List;

/**
 * Created by wkoop on 13.06.2016.
 */
public class Statement {
    private int propertyId;
    private String label;
    private Enumeration<Datatype> datatype;
    private List<Claim> claims;

    public Statement() {
    }


    public Statement(int propertyId, String label, Enumeration<Datatype> datatype, List<Claim> claims) {
        this.propertyId = propertyId;
        this.label = label;
        this.datatype = datatype;
        this.claims = claims;
    }

    public Enumeration<Datatype> getDatatype() {
        return datatype;
    }

    public void setDatatype(Enumeration<Datatype> datatype) {
        this.datatype = datatype;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
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
