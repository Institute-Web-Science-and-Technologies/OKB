package WikiDataItem;

import java.util.List;


public class ID {

    private int iid;
    private int pageid;
    private int ns;
    private String title;
    private int lastrevid;
    private String modified;
    private String type;
    private String id;
    private List<LangVal> labels;
    private String response;

    public ID(int pageid, int ns, String title, int lastrevid, String modified, String type, String id, List<LangVal> labels, String response) {
        this.pageid = pageid;
        this.ns = ns;
        this.title = title;
        this.lastrevid = lastrevid;
        this.modified = modified;
        this.type = type;
        this.id = id;
        this.labels = labels;
        this.response = response;
    }

    public ID() {
    }

    public String toString() {
        String a = "ID: {pageid: " + getPageid();
        a += ", ns: " + getNs();
        a += ", title: " + getTitle();
        a += ", lastrevid: " + getLastrevid();
        a += ", modified: " + getModified();
        a += ", type: " + getType();
        a += ", id: " + getId();
        a += " labels: ";
        for (LangVal i : labels)
            a += i.getShortcut() + ", " + i.getLanguage() + ", " + i.getValue() + ", ";
        //a+=", response: "+getResponse();

        return a + "}";
    }

    public int getPageid() {
        return pageid;
    }

    public void setPageid(int pageid) {
        this.pageid = pageid;
    }

    public int getNs() {
        return ns;
    }

    public void setNs(int ns) {
        this.ns = ns;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLastrevid() {
        return lastrevid;
    }

    public void setLastrevid(int lastrevid) {
        this.lastrevid = lastrevid;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
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

    public List<LangVal> getLabels() {
        return labels;
    }

    public void setLabels(List<LangVal> labels) {
        this.labels = labels;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
