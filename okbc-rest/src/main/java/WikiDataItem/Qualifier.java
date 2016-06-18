package WikiDataItem;

import java.util.Enumeration;

/**
 * Created by wkoop on 13.06.2016.
 */
public class Qualifier {
    private int propertyId;
    private String label;
    private Enumeration<Datatype> datatype;
    private String value;

    public Qualifier() {
    }

    public Qualifier(int propertyId, String label, Enumeration<Datatype> datatype, String value) {
        this.propertyId = propertyId;
        this.label = label;
        this.datatype = datatype;
        this.value = value;
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

    public Enumeration<Datatype> getDatatype() {
        return datatype;
    }

    public void setDatatype(Enumeration<Datatype> datatype) {
        this.datatype = datatype;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
