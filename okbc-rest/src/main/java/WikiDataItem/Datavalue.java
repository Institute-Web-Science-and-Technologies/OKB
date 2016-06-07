package WikiDataItem;


public class Datavalue {
    private int did;
    private Value value;
    private String type;

    public Datavalue() {
    }

    public Datavalue(Value value, String type) {
        this.value = value;
        this.type = type;
    }

    public String toString() {
        String a = "Datavalue: {";
        a += value.toString();
        a += ", type: " + type + "}";
        return a;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}