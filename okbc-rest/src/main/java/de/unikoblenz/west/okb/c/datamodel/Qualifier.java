package de.unikoblenz.west.okb.c.datamodel;

/**
 * Created by wkoop on 13.06.2016.
 */
public class Qualifier {
    private int propertyId;
    private String label;
    private Datatype datatype;
    private String value;

    public String toString(){
        return "{\"propertyid\": \"P"+propertyId+"\", \"label\": \""+label
                +"\", \"datatype\": \""+datatype+"\", \"value\": \""+value+"\"}";
    }

    public Qualifier() {
    }

    public Qualifier(int propertyId, String label, Datatype datatype, String value) {
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

    public Datatype getDatatype() {
        return datatype;
    }

    public void setDatatype(Datatype datatype) {
        this.datatype = datatype;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
