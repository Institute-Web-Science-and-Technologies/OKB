package de.unikoblenz.west.okb.c.datamodel;

import java.util.List;

/**
 * Created by wkoop on 08.06.2016.
 */
public class Event {
    private String eventid;
    private String label;
    private List<String> categories;
    private String location;
    private List<Statement> statements;


    public String toString(){
        String result = "";
        result+="{\"eventid\": \""+eventid+"\", " +
                "\"label\": \""+label+"\", " +
                "\"categories\": [";
        for(int i=0; i<categories.size(); i++){
            if(i==categories.size()-1)
                result+="\""+categories.get(i)+"\"";
            else
                result +="\""+categories.get(i)+"\""+(", ");
        }
        result+="], \"location\": \""+location+"\", \n";
        result+="\"statements\": [";
        for(Statement st : statements)
            result+=st.toString();
        result+="}";
        return result;
    }

    public Event() {
    }

    public Event(String eventid, String label, List<String> categories, String location, List<Statement> statements) {
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

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
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
