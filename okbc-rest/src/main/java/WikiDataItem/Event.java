package WikiDataItem;

import java.util.List;

/**
 * Created by wkoop on 08.06.2016.
 */
public class Event {
    private int eventid;
    private String label;
    private List<String> categories;
    private String location;
    private List<Statement> statements;

    public Event() {
    }

    public Event(int eventid, String label, List<String> categories, String location, List<Statement> statements) {
        this.eventid = eventid;
        this.label = label;
        this.categories = categories;
        this.location = location;
        this.statements = statements;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

    public int getEventid() {
        return eventid;
    }

    public void setEventid(int eventid) {
        this.eventid = eventid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
