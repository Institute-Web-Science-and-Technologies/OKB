package de.unikoblenz.west.okb.c.Item_Handling;

import de.unikoblenz.west.okb.c.WikiDataItem.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.util.ArrayList;


/**
 * Created by wkoop on 27.06.2016.
 */

public class ResultSetToJson {

    public static String ResultSetoutput(ResultSet rs) {
        ArrayList<de.unikoblenz.west.okb.c.WikiDataItem.Event> event = new ArrayList<de.unikoblenz.west.okb.c.WikiDataItem.Event>();
        String result = "";
        try {
            while (rs.next()) {
                event.add(parseEvents(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(event.size()>1)
            result+="{";
        String separator="";
        for(de.unikoblenz.west.okb.c.WikiDataItem.Event ev : event) {
            result += separator + ev.toString() + "\n";
            separator=",";
        }
        if(event.size()>1)
            result+="}";
        return result;
    }

    //parses ResultSet containing Event(s) to Json valid String
    public static de.unikoblenz.west.okb.c.WikiDataItem.Event parseEvents(ResultSet rs){
        de.unikoblenz.west.okb.c.WikiDataItem.Event event = new de.unikoblenz.west.okb.c.WikiDataItem.Event();
        event.setLabel("null");
        event.setLocation("null");
        ArrayList<String> empty = new ArrayList<String>();
        empty.add("null");
        event.setCategories(empty);
        event.setEventid("null");
        ArrayList<Statement> statements = new ArrayList<Statement>();
        event.setStatements(statements);
        int statementcounter=-1, claimcounter=-1;
        try {
            int size = rs.getMetaData().getColumnCount();
            for (int i = 1; i < size; i++) {
                String name = rs.getMetaData().getColumnLabel(i).toLowerCase();
                String value = "";
                if (rs.getObject(i) == null)
                    value = "null";
                else
                    value = rs.getObject(i).toString();
                //Event-parser
                if(name.contains("eventid"))
                    event.setEventid(value);
                if(name.contains("evlabel"))
                    event.setLabel(value);
                if(name.contains("location"))
                    event.setLocation(value);
                if(name.contains("category")) {
                    if(value.contains(",")){
                        String[] cat = value.split(",");
                        ArrayList<String> categories=new ArrayList<String>();
                        for(String c : cat)
                            categories.add(c);
                        event.setCategories(categories);
                    }else
                        event.setLocation(value);
                }
                if(name.contains("statements")) {
                    statements.add(StatementParser(value));
                    statementcounter++;
                }
                if(name.contains("claim")) {
                    statements.get(statementcounter).getClaims().add(ClaimParser(value));
                    claimcounter++;
                    ArrayList<Qualifier> qualifier = new ArrayList<Qualifier>();
                    statements.get(statementcounter).getClaims().get(claimcounter).setQualifier(qualifier);
                    ArrayList<Reference> references = new ArrayList<Reference>();
                    statements.get(statementcounter).getClaims().get(claimcounter).setReferences(references);

                }
                if(name.contains("qualifier")){
                    statements.get(statementcounter).getClaims().get(claimcounter).getQualifier().add(QualifierParser(value));
                }

                if(name.contains("references") || name.contains("sources")){
                    statements.get(statementcounter).getClaims().get(claimcounter).getReferences().add(ReferenceParser(value));
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return event;
    }

    //input is a string with statement input (propertyid, label and datatype)
    //output is a statement object
    public static Statement StatementParser(String in){
        Statement st = new Statement();
        ArrayList<Claim> claims = new ArrayList<Claim>();
        st.setClaims(claims);
        String save="";

        //parse propertyid
        for(int i=in.indexOf("propertyid")+14; i<in.length(); i++){
            if(in.charAt(i)==',')
                break;
            save+=in.charAt(i);
        }
        st.setPropertyId(save);
        save="";

        //parse label
        for(int i=in.indexOf("label")+7; i<in.length(); i++){
            if(in.charAt(i)==',')
                break;
            save+=in.charAt(i);
        }
        st.setLabel(save);
        save="";

        //parse Datatype
        for(int i=in.indexOf("datatype")+10; i<in.length(); i++){
            if(in.charAt(i)=='"')
                break;
            save+=in.charAt(i);
        }
        save=save.toLowerCase();
        try{
        switch(save) {
            case "":
                st.setDatatype(null);
                break;
            case "quantity":
                st.setDatatype(Datatype.Quantity);
                break;
            case "item":
                st.setDatatype(Datatype.Item);
                break;
            case "property":
                st.setDatatype(Datatype.Property);
                break;
            case "url":
                st.setDatatype(Datatype.Url);
                break;
            case "time":
                st.setDatatype(Datatype.Time);
                break;
            case "monolingualtext" :
                st.setDatatype(Datatype.MonolingualText);
                break;
            default:
                throw new IllegalArgumentException("Invalid Argument (Datatype from Statement:\"" + save + "\"! Valid Arguments: Quantity, Item, Property, Url, Time, MonolingualText.");
        }
        }catch (Exception e){ e.printStackTrace();}
        return st;
    }

    public static Claim ClaimParser(String in){
        Claim claim = new Claim();
        String save = "";
        for(int i=in.indexOf("snaktype")+10; i<in.length(); i++){
            if(in.charAt(i)==',')
                break;
            save+=in.charAt(i);
        }
        save=save.toLowerCase();
        try{
            switch(save) {
                case "":
                    claim.setSnaktype(null);
                    break;
                case "value":
                    claim.setSnaktype(Snaktype.Value);
                    break;
                case "novalue":
                    claim.setSnaktype(Snaktype.NoValue);
                    break;
                case "missingvalue":
                    claim.setSnaktype(Snaktype.MissingValue);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid Argument (Datatype from Statement: " + save
                            + "! Valid Arguments: Value, NoValue and MissingValue.");
            }
        }catch (Exception e){ e.printStackTrace();}
        save="";

        for(int i=in.indexOf("value")+8; i<in.length(); i++){
            if(in.charAt(i)==',')
                break;
            save+=in.charAt(i);
        }
        claim.setValue(save);
        save="";

        for(int i=in.indexOf("ranking")+9; i<in.length(); i++){
            if(in.charAt(i)==',')
                break;
            save+=in.charAt(i);
        }
        save=save.toLowerCase();
        try{
            switch(save) {
                case "":
                    claim.setRanking(null);
                    break;
                case "deprecated":
                    claim.setRanking(Rank.Deprecated);
                    break;
                case "normal":
                    claim.setRanking(Rank.Normal);
                    break;
                case "Preferred":
                    claim.setRanking(Rank.Preferred);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid Argument (Datatype from Statement: \"" + save
                            + "\"! Valid Arguments:  Deprecated, Normal and Preferred;.");
            }
        }catch (Exception e){ e.printStackTrace();}
        save="";


        for(int i=in.indexOf(""); i<in.length(); i++){
            if(in.charAt(i)==',')
                break;
            save+=in.charAt(i);
        }
        save="";

        return claim;
    }

    public static Qualifier QualifierParser(String in){
        //if(in.length()<2) return new Qualifier(-1,"null",null,"null");
        Qualifier q = new Qualifier();
        String save="";
        for(int i=in.indexOf("propertyid")+12; i<in.length(); i++){
            if(in.charAt(i)==',')
                break;
            save+=in.charAt(i);
        }
        if(save=="")
            q.setPropertyId(0);
        else
            q.setPropertyId(Integer.parseInt(save));

        save="";
        for(int i=in.indexOf("label")+8; i<in.length(); i++){
            if(in.charAt(i)==',')
                break;
            save+=in.charAt(i);
        }
        q.setLabel(save);
        save="";

        for(int i=in.indexOf("datatype")+10; i<in.length(); i++){
            if(in.charAt(i)==',')
                break;
            save+=in.charAt(i);
        }
        save=save.toLowerCase();
        if(save=="null" || save==null || save=="")
            q.setDatatype(null);
        else
            try{
            switch(save) {
                case "quanitiy":
                    q.setDatatype(Datatype.Quantity);
                    break;
                case "item":
                    q.setDatatype(Datatype.Item);
                    break;
                case "property":
                    q.setDatatype(Datatype.Property);
                    break;
                case "url":
                    q.setDatatype(Datatype.Url);
                    break;
                case "time":
                    q.setDatatype(Datatype.Time);
                    break;
                case "monolingualtext" :
                    q.setDatatype(Datatype.MonolingualText);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid Argument: \"" + in + "\"! Valid Arguments: Quantity, Item, Property, Url, Time, MonolingualText.");
            }
        }catch (Exception e){ e.printStackTrace();}

        save="";
        for(int i=in.indexOf("value")+8; i<in.length(); i++){
            if(in.charAt(i)==',') break;
            save+=in.charAt(i);
        }
        q.setValue(save);
        return q;
    }

    public static Reference ReferenceParser(String in){
        Reference ref = new Reference();
        String save="";
        for(int i=in.indexOf("url")+5; i<in.length(); i++){
            if(in.charAt(i)==',')
                break;
            save+=in.charAt(i);
        }
        ref.setUrl(save);

        save="";
        for(int i=in.indexOf("title")+7; i<in.length(); i++){
            if(in.charAt(i)==',')
                break;
            save+=in.charAt(i);
        }
        ref.setTitle(save);

        save="";
        for(int i=in.indexOf("publicationdate")+17; i<in.length(); i++){
            if(in.charAt(i)==',')
                break;
            save+=in.charAt(i);
        }
        ref.setPublicationDate(save);

        save="";
        for(int i=in.indexOf("retrievaldate")+15; i<in.length(); i++){
            if(in.charAt(i)==',')
                break;
            save+=in.charAt(i);
        }
        ref.setRetrievalDate(save);

        save="";
        for(int i=in.indexOf("articleType")+13; i<in.length(); i++){
            if(in.charAt(i)==',')
                break;
            save+=in.charAt(i);
        }
        ref.setArticleType(save);

        save="";
        for(int i=in.indexOf("trustrating")+13; i<in.length(); i++){
            if(in.charAt(i)==',')
                break;
            save+=in.charAt(i);
        }
        if(save=="")
            ref.setTrustRating(-1);
        else
            ref.setTrustRating(Float.parseFloat(save));

        save="";
        for(int i=in.indexOf("neutralityrating")+17; i<in.length(); i++){
            if(in.charAt(i)==',')
                break;
            save+=in.charAt(i);
        }
        if(save=="")
            ref.setNeutralityRating(-1);
        else
            ref.setNeutralityRating(Float.parseFloat(save));


        //private ArrayList<String> authors;
        return ref;
    }

    //parses result from MySQLconnector DB query to Json
    public static String ResultSetoutput2(ResultSet rs) {
        String ret="";
        int idx=0;
        try{
            while(rs.next()) {
                ret+=++idx + ": {";
                for(int i = 1; i<rs.getMetaData().getColumnCount(); i++){
                    String name = rs.getMetaData().getColumnLabel(i).toLowerCase();
                    Object val = rs.getObject(i);
                    ret+=name+": "+val.toString();
                }
                ret += "}\n";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }



    public static String splitandaddtoJson(String s1, String s2){
        if (s1=="null" || s1 == "" || s2 == "")
            return "";
        String [] s = s1.split("(?="+s2+")");
        String ret="";
        for(int i=0; i< s.length; i++){
            if (i==s.length-1){
                s[i]= "{" + s[i];
                ret +=s[i];
            }else{
                s[i]= "{" + s[i];
                s[i] = s[i].substring(0, s[i].length()-1);
                ret +=s[i];
            }
        }
        return ret;
    }

    public static int countStringinString(String s1, String s2){
        if (s1=="null" || s1 == "" || s2 == "")
            return 0;
        int s2length = s2.length(), count=0;
        for(int i=0; i<s1.length()-s2length; i++){
            if(s1.indexOf(s2,i) != -1) {
                count++;
                i= s1.indexOf(s2,i)+1;
            }
        }
        return count;
    }


    public static String convertResultSetIntoJSON(ResultSet resultSet) throws Exception {
        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            JSONObject obj = new JSONObject();
            for (int i = 0; i < total_rows; i++) {
                String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
                Object columnValue = resultSet.getObject(i + 1);
                // if value in DB is null, then we set it to default value
                if (columnValue == null){
                    columnValue = "null";
                }
                /*
                Next if block is a hack. In case when in db we have values like price and price1 there's a bug in jdbc -
                both this names are getting stored as price in ResulSet. Therefore when we store second column value,
                we overwrite original value of price. To avoid that, i simply add 1 to be consistent with DB.
                 */
                if (obj.has(columnName)){
                    columnName += "1";
                }
                obj.put(columnName, columnValue);
            }
            jsonArray.put(obj);
        }
        return jsonArray.toString();
    }
}
