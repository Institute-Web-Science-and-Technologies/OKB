package WikiDataItem;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Claims")
public class Claims {
    @DatabaseField(generatedId = true)
    private int pid;

    @DatabaseField
    private String claim;

    @DatabaseField
    private Mainsnak mainsnak;

    @DatabaseField
    private String type;

    @DatabaseField
    private String id;

    @DatabaseField
    private String rank;

    // @DatabaseField
//    private Reference reference;

    public Claims() {
    }

    public Claims(String claim, Mainsnak mainsnak, String type, String id, String rank, Reference reference) {
        this.claim = claim;
        this.mainsnak = mainsnak;
        this.type = type;
        this.id = id;
        this.rank = rank;
        //this.reference = reference;
    }

    public String toString() {
        String a = "Claims: {" + claim + ", ";

//        a += mainsnak.toString() + ", ";
        a += type + ", " + id + ", " + rank + ", ";
        //a+=reference.toString();
        a += "}";
        return a;
    }

    public String getClaim() {
        return claim;
    }

    public void setClaim(String claim) {
        this.claim = claim;
    }

    public Mainsnak getMainsnak() {
        return mainsnak;
    }

    public void setMainsnak(Mainsnak mainsnak) {
        this.mainsnak = mainsnak;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

 /*   public Reference getReference() {
        return reference;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }
    */
}
