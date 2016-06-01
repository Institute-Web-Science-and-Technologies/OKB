package de.unikoblenz.west.okb.c;

import WikiDataItem.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wkoop on 14.05.16.
 */
public class WikidataJsonParser {

    //Parses WikiData Json to Java Object
    public static Entities parser(String in) throws Exception {
        if (in.contains("error") || in.contains("missing"))
            throw new Exception("wrong input");

        Entities e = new Entities();
        ID id = new ID();
        e.setId(id);

        String[] arr = in.split("descriptions");
        String entities = arr[0];

        //Search and write ID to variable
        id.setId(searcher(entities, "entities", 12, '"'));
        //Search and write pageid to variable
        id.setPageid(Integer.parseInt(searcher(entities, "pageid", 8, ',')));
        //Search and write ns to variable
        id.setNs(Integer.parseInt(searcher(entities, "ns", 4, ',')));
        //Search and write title to variable
        id.setTitle(searcher(entities, "title", 8, '"'));
        //Search and write lastrevid to variable
        id.setLastrevid(Integer.parseInt(searcher(entities, "lastrevid", 11, ',')));
        //Search and write modified to variable
        //maybe parse to Java Date instead of String???
        id.setModified(searcher(entities, "modified", 11, '"'));
        //Search and write type to variable
        id.setType(searcher(entities, "type", 7, '"'));
        //Search and write lables
        id.setLabels(searchLangVals(entities, "labels\":{", "descriptions", "],", 1, '"'));

        entities = in;
        //search and add descriptions
        e.setDescriptions(searchLangVals(entities, "descriptions\":\\{", "aliases", "\\},", 1, '"'));

        e.setAliases(searchAliases(entities, "aliases\":\\{", "\"claims\"", '"'));
        //arr = arr[1].split("aliases");
        e.setClaims(searchClaims(entities));
        //arr = arr[1].split("claims");

        return e;
    }

    //Searches for String and returns related Value
    public static String searcher(String ent, String start, int ln, char stop) {
        String ret = "";
        for (int i = ent.indexOf(start) + ln; i < ent.length(); i++) {
            if (ent.charAt(i) == stop) {
                break;
            }
            ret += ent.charAt(i);
        }
        return ret;
    }


    //Cuts unneccessary data away, so that we can iterate and return values
    public static List<LangVal> searchLangVals(String ent, String split1, String split2, String split3,
                                               int ln, char stop) {
        List<LangVal> ret = new ArrayList<LangVal>();
        String[] arr = ent.split(split1);
        arr = arr[1].split(split2);

        arr = arr[0].split(split3);
        for (int i = 0; i < arr.length - 1; i++) {
            String a = arr[i];
            String st1 = "", st2 = "";
            for (int j = 1; j < a.length(); j++) {
                if (a.charAt(j) == stop)
                    break;
                st1 += a.charAt(j);
            }
            for (int j = a.indexOf("value") + 8; j < ent.length(); j++) {
                if (a.charAt(j) == stop)
                    break;
                st2 += a.charAt(j);
            }
            LangVal lv = new LangVal(st1, st1, st2);
            ret.add(lv);
        }
        return ret;
    }

    //returns LangVal object taken from String
    public static LangVal retLangVal(String ent, String shortcut) {
        String lang = "", val = "";
        int l = ent.indexOf("language");
        int v = ent.indexOf("value");
        for (int i = l + 11; i < ent.length(); i++) {
            if (ent.charAt(i) == '"')
                break;
            lang += ent.charAt(i);
        }
        for (int i = v + 8; i < ent.length(); i++) {
            if (ent.charAt(i) == '"')
                break;
            val += ent.charAt(i);
        }
        return new LangVal(shortcut, lang, val);
    }

    //return List of Aliases Objects from String
    public static List<Aliases> searchAliases(String ent, String split1, String split2, char stop) {
        List<Aliases> ret = new ArrayList<Aliases>();
        String[] arr = ent.split(split1);
        arr = arr[1].split(split2);
        arr = arr[0].split("],");
        String st1 = "";
        for (int i = 0; i < arr.length; i++) {
            st1 = "";
            Aliases a = new Aliases();
            List<LangVal> lv = new ArrayList<LangVal>();
            for (int j = 1; j < arr[i].length(); j++) {
                if (arr[i].charAt(j) == '"')
                    break;
                st1 += arr[i].charAt(j);
            }
            a.setAlias(st1);
            a.setLangval(lv);
            String[] arr2 = arr[i].split("\\},");
            for (String j : arr2)
                lv.add(retLangVal(j, st1));
            ret.add(a);
        }
        return ret;
    }

    //return List of Claims Objects from String - NOT TESTED!!!
    public static List<Claims> searchClaims(String ent) {
        List<Claims> ret = new ArrayList<Claims>();
        String[] arr = ent.split("claims\":\\{");
        arr = arr[1].split("\"sitelinks");
        arr = arr[0].split("],");

        for (String i : arr) {
            Claims cl = new Claims();
            ret.add(cl);
            String st1 = "";

            for (int j = 1; j < i.length(); j++) {
                if (i.charAt(j) == '"')
                    break;
                st1 += i.charAt(j);
            }
            cl.setClaim(st1);
            st1 = "";


            //Serch of "\\},\"type" or "\\}, "type""
            for (int j = i.indexOf("\\},\"type\"", i.indexOf("\"id\"") - 30) + cl.getClaim().length() + 7; j < i.length(); j++) {
                if (i.charAt(j) == '"')
                    break;
                st1 += i.charAt(j);
            }
            cl.setType(st1);

            st1 = "";
            for (int j = i.indexOf("\"id\"") + 6; j < i.length(); j++) {
                if (i.charAt(j) == '"')
                    break;
                st1 += i.charAt(j);
            }
            cl.setId(st1);

            st1 = "";
            for (int j = i.indexOf("\"rank\"") + 8; j < i.length(); j++) {
                if (i.charAt(j) == '"')
                    break;
                st1 += i.charAt(j);
            }
            cl.setRank(st1);
            st1 = "";

            //Mainsnak:
            Mainsnak ms = new Mainsnak();
            cl.setMainsnak(ms);
            for (int j = i.indexOf("mainsnak") + 23; j < i.length(); j++) {
                if (i.charAt(j) == '"')
                    break;
                st1 += i.charAt(j);
            }
            ms.setSnaktype(st1);

            st1 = "";
            for (int j = i.indexOf("property") + 11; j < i.length(); j++) {
                if (i.charAt(j) == '"')
                    break;
                st1 += i.charAt(j);
            }
            ms.setProperty(st1);

            st1 = "";
            for (int j = i.indexOf("datatype") + 11; j < i.length(); j++) {
                if (i.charAt(j) == '"')
                    break;
                st1 += i.charAt(j);
            }
            ms.setDatatype(st1);
            st1 = "";

            Datavalue dv = new Datavalue();
            ms.setDatavalue(dv);
            for (int j = i.indexOf("\"type\"") + 8; j < i.length(); j++) {
                if (i.charAt(j) == '"')
                    break;
                st1 += i.charAt(j);
            }
            dv.setType(st1);
            st1 = "";
            if (i.contains("\"value\":{")) {
                Value v = new Value();
                dv.setValue(v);
                for (int j = i.indexOf("entity-type") + 14; j < i.length(); j++) {
                    if (i.charAt(j) == '"')
                        break;
                    st1 += i.charAt(j);
                }
                v.setEntity_type(st1);
                st1 = "";
                for (int j = i.indexOf("numeric-id") + 12; j < i.length(); j++) {
                    if (i.charAt(j) == '}')
                        break;
                    st1 += i.charAt(j);
                }
                v.setNumeric_id(st1);
                st1 = "";
            } else {
                Value v = new Value();
                dv.setValue(v);
                for (int j = i.indexOf("\"value\":") + 8; j < i.length(); j++) {
                    if (i.charAt(j) == '"')
                        break;
                    st1 += i.charAt(j);
                }
                v.setValue(st1);
                st1 = "";
                for (int j = i.indexOf("\"type\"") + 7; j < i.length(); j++) {
                    if (i.charAt(j) == '"')
                        break;
                    st1 += i.charAt(j);
                }
                dv.setType(st1);
                st1 = "";
            }

        }

        return ret;
    }

    //badges are missing???
    public static List<Sitelinks> searchSitelinks(String ent) {
        String[] arr = ent.split("\"sitelinks\"");
        arr = arr[1].split("}}");
        List<Sitelinks> sl = new ArrayList<Sitelinks>();
        for (String i : arr) {
            Sitelinks s = new Sitelinks();
            sl.add(s);
            String st1 = "";
            for (int j = 1; j < i.length(); j++) {
                if (i.charAt(j) == '"')
                    break;
                st1 += i.charAt(j);
            }
            s.setSitelink(st1);
            st1 = "";
            for (int j = i.indexOf("\"site\"") + 8; j < i.length(); j++) {
                if (i.charAt(j) == '"')
                    break;
                st1 += i.charAt(j);
            }
            s.setSite(st1);
            st1 = "";
            for (int j = i.indexOf("\"title\"") + 9; j < i.length(); j++) {
                if (i.charAt(j) == '"')
                    break;
                st1 += i.charAt(j);
            }
            s.setTitle(st1);
            st1 = "";
            /*
            if (i.contains("\"badges\":[]")) {
                ;
            } else {

            }
            */

        }
        return sl;
    }


}
