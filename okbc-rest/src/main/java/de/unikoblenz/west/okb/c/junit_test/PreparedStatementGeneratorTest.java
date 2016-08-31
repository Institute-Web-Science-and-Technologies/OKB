package de.unikoblenz.west.okb.c.junit_test;

import de.unikoblenz.west.okb.c.restapi.PreparedStatementGenerator;
import org.junit.Test;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;

public class PreparedStatementGeneratorTest {

    @Test
    public void getLatestEditedEventsArgumentTest(){
        int limit = 5;
        PreparedStatement l = null;
        try {
          l = PreparedStatementGenerator.getLatestEditedEvents(limit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(l.toString().contains("LIMIT "+limit));
    }

    @Test
    public void getLatestEditedEventsTest(){
        int limit = 5;
        PreparedStatement l = null;
        try {
          l = PreparedStatementGenerator.getLatestEditedEvents(limit);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertTrue(l.toString().contains("LIMIT "+limit));
    }


    @Test
    public void getEventByIdArgumentTest(){
        int id = 532;
        PreparedStatement l = null;
        try {
            l = PreparedStatementGenerator.getEventById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(l.toString().contains("eventid = "+id));
    }

    @Test
    public void getEventsByLabelArgumentTest(){
        String label = "a";
        PreparedStatement l = null;
        try {
            l = PreparedStatementGenerator.getEventsByLabel(label);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(l.toString().contains("LOWER(label) LIKE LOWER(CONCAT('%', '"+label));
    }

    @Test
    public void getEventsByCategoryArgumentTest(){
        String category = "a";
        PreparedStatement l = null;
        try {
            l = PreparedStatementGenerator.getEventsByCategory(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(l.toString().contains("category = '"+category));
    }

    @Test
    public void getCategoriesByEventIdArgumentTest(){
        int eventid = 5;
        PreparedStatement l = null;
        try {
            l = PreparedStatementGenerator.getCategoriesByEventId(eventid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(l.toString().contains("eventid = "+eventid));
    }

    @Test
    public void getStatementsByEventIdArgumentTest(){
        int eventid = 5;
        PreparedStatement l = null;
        try {
            l = PreparedStatementGenerator.getStatementsByEventId(eventid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(l.toString().contains("eventid="+eventid));
    }

    @Test
    public void getStatementByEventIdAndPropertyIdArgumentTest(){
        int eventid = 5, pid = 7;
        PreparedStatement l = null;
        try {
            l = PreparedStatementGenerator.getStatementByEventIdAndPropertyId(eventid, pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(l.toString().contains("eventid="+eventid+" AND propertyid="+pid));
    }

    @Test
    public void getClaimsByStatementIdArgumentTest(){
        int sid=5;
        PreparedStatement l = null;
        try {
            l = PreparedStatementGenerator.getClaimsByStatementId(sid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(l.toString().contains("statementid="+sid));
    }

    @Test
    public void getClaimsByEventIdArgumentTest(){
        int eid=5;
        PreparedStatement l = null;
        try {
            l = PreparedStatementGenerator.getClaimsByEventId(eid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(l.toString().contains("eventid = "+eid));
    }

    @Test
    public void getQualifiersByClaimIdArgumentTest(){
        int id=5;
        PreparedStatement l = null;
        try {
            l = PreparedStatementGenerator.getQualifiersByClaimId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(l.toString().contains("claimid = "+id));
    }

    @Test
    public void getReferencesByClaimIdArgumentTest(){
        int id=5;
        PreparedStatement l = null;
        try {
            l = PreparedStatementGenerator.getReferencesByClaimId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(l.toString().contains("claimid = "+id));
    }

    @Test
    public void getUserByIdArgumentTest(){
        int id=5;
        PreparedStatement l = null;
        try {
            l = PreparedStatementGenerator.getUserById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(l.toString().contains("userid = "+id));
    }

    @Test
    public void getUserByNameArgumentTest(){
        String name = "a";
        PreparedStatement l = null;
        try {
            l = PreparedStatementGenerator.getUserByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(l.toString().contains("username = '"+name));
    }

    @Test
    public void updateRankOfClaimArgumentTest(){
        int id=5; String ranking = "a";
        PreparedStatement l = null;
        try {
            l = PreparedStatementGenerator.updateRankOfClaim(id, ranking);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(l.toString().contains("ranking='"+ranking+"' WHERE claimid="+id));
    }

    @Test
    public void createEventArgumentTest(){
        int year = 2000, month=4, day=3;
        int id=5; String label = "a";
        PreparedStatement l = null;
        try {
            l = PreparedStatementGenerator.createEvent(id, label, new Date(year,month,day));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(l.toString().contains("VALUES ("+id+",'"+label+"','"+year+"-"+month+"-"+day+"')"));
    }

    @Test
    public void createStatementArgumentTest(){
        int eid=5, pid=4; String label = "a", datatype="b";
        PreparedStatement l = null;
        try {
            l = PreparedStatementGenerator.createStatement(pid,label,datatype,eid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(l.toString().contains("VALUES ("+pid+",'"+label+"','"+datatype+"'"+","+eid+")"));
    }

    @Test
    public void createClaimArgumentTest(){
        String st="a", value="b"; int uid=1, sid=2;
        PreparedStatement l = null;
        try {
            l = PreparedStatementGenerator.createClaim(st,value,uid,sid, "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(l.toString().contains("VALUES ('"+st+"','"+value+"',"+uid+","+sid+")"));
    }

    @Test
    public void createClaim2ArgumentTest(){
        String st="a", value="b"; int sid=2;
        PreparedStatement l = null;
        try {
            l = PreparedStatementGenerator.createClaim(st,value,sid, "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(l.toString().contains("VALUES ('"+st+"','"+value+"',"+sid+")"));
    }

    @Test
    public void createQualifierArgumentTest(){
        int pid=1; String value="a"; int cid=2;
        PreparedStatement l = null;
        try {
            l = PreparedStatementGenerator.createQualifier(pid, value, cid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(l.toString().contains("VALUES ("+pid+",'"+value+"',"+cid+")"));
    }

    @Test
    public void createReferenceArgumentTest(){
        String url="a", publicationDate="2001-01-01", retrievalDate="2001-01-01";
        double reliabilityRating=1.0, neutralityRating=2.0;
        int claimId=3;
        PreparedStatement l = null;
        try {
            l = PreparedStatementGenerator.createReference(url, publicationDate, retrievalDate,
                    reliabilityRating, neutralityRating, claimId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(l.toString().contains("VALUES ('"+url+"','"+publicationDate+"','"+retrievalDate+"',"+
                reliabilityRating+","+neutralityRating+","+claimId+")"));
    }

    @Test
    public void createAuthorArgumentTest(){
        String author="a"; int referenceid=1;
        PreparedStatement l = null;
        try {
            l = PreparedStatementGenerator.createAuthor(author, referenceid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(l.toString().contains("VALUES ('"+author+"',"+referenceid+")"));
    }

    @Test
    public void createUserArgumentTest(){
        String userName="a"; double reputation=2.0;
        PreparedStatement l = null;
        try {
            l = PreparedStatementGenerator.createUser(userName, reputation);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(l.toString().contains("VALUES ('"+userName+"',"+reputation+")"));
    }









}
