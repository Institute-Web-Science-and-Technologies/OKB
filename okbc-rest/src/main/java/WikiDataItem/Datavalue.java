package WikiDataItem;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Datavalue")
public class Datavalue {
    @DatabaseField(generatedId = true)
    private int pid;

    @DatabaseField
    private Value value;

    @DatabaseField
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