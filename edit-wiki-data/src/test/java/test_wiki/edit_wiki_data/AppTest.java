package test_wiki.edit_wiki_data;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.regex.Pattern;

import test_wiki.edit_wiki_data.EditWikiData;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    
    public void testStatementEntered() throws IOException {
        EditWikiData statement = new EditWikiData();
        String stId = statement.setStatement("Q4115189", "P1082", "{\"amount\":\"+27553287\",\"unit\":\"1\",\"upperBound\":\"+27553287\",\"lowerBound\":\"+27553287\"}");
        
        String re1="(Q)";	// Any Single Character 1
        String re2="(\\d+)";	// Integer Number 1
        String re3="(\\$)";	// Any Single Character 2
        String re4="([A-Z0-9]{8}-[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{12})";	// SQL GUID 1

        Pattern p = Pattern.compile(re1+re2+re3+re4,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        assertTrue(stId.matches(p.pattern()));
                
        //assertEquals(expected, stId);
    }
}
