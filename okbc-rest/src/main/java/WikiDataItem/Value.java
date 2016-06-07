package WikiDataItem;

public class Value {
    private int vid;
    private String entity_type;
    private String numeric_id;
    private String value;

    public Value() {
    }

    public Value(String entity_type, String numeric_id, String value) {
        this.entity_type = entity_type;
        this.numeric_id = numeric_id;
        this.value = value;
    }

    public String toString() {
        String a = "Value: {";
        a += entity_type + ", ";
        a += numeric_id;
        a += ", " + value + "}";
        return a;
    }

    public String getEntity_type() {
        return entity_type;
    }

    public void setEntity_type(String entity_type) {
        this.entity_type = entity_type;
    }

    public String getNumeric_id() {
        return numeric_id;
    }

    public void setNumeric_id(String numeric_id) {
        this.numeric_id = numeric_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
