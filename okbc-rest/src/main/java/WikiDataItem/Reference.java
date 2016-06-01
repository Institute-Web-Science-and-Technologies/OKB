package WikiDataItem;

/**
 * Created by wkoop on 16.05.16.
 */
public class Reference {

    private String hash;
    private Snaks snaks;
    private String snaks_order;

    public Reference() {
    }

    public Reference(String hash, Snaks snaks, String snaks_order) {
        this.hash = hash;
        this.snaks = snaks;
        this.snaks_order = snaks_order;
    }

    public String toString() {
        String a = "Reference: {";
        a += hash + ", ";
        a += snaks.toString() + ", ";
        a += snaks_order + "}";

        return a;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Snaks getSnaks() {
        return snaks;
    }

    public void setSnaks(Snaks snaks) {
        this.snaks = snaks;
    }

    public String getSnaks_order() {
        return snaks_order;
    }

    public void setSnaks_order(String snaks_order) {
        this.snaks_order = snaks_order;
    }

}
