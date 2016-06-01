package WikiDataItem;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Mainsnak")
public class Mainsnak {
    @DatabaseField(generatedId = true)
    private int pid;

    @DatabaseField
    private String snaktype;

    @DatabaseField
    private String property;

    @DatabaseField
    private Datavalue datavalue;

    @DatabaseField
    private String datatype;

    public Mainsnak() {

    }

    public Mainsnak(String snak, String snaktype, String property, Datavalue datavalue, String datatype) {
        this.snaktype = snaktype;
        this.property = property;
        this.datavalue = datavalue;
        this.datatype = datatype;
    }

    public String toString() {
        String a = "mainsnak: {" + snaktype + ", " + property + ", ";
        a += datavalue.toString() + ", ";
        a += datatype + "}";
        return a;
    }

    public String getSnaktype() {
        return snaktype;
    }

    public void setSnaktype(String snaktype) {
        this.snaktype = snaktype;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Datavalue getDatavalue() {
        return datavalue;
    }

    public void setDatavalue(Datavalue datavalue) {
        this.datavalue = datavalue;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

}