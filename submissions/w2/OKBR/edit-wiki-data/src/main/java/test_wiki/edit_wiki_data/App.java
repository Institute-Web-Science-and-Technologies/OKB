package test_wiki.edit_wiki_data;

import java.io.IOException;
import java.net.URISyntaxException;

import org.json.JSONException;

/**
 * Hello world!
 *
 */
public class App 
{
	
    public static void main( String[] args ) throws Exception
    {
    	WikiApiHelper edit = new WikiApiHelper();
    	String test = edit.edit("Q4115189");
    	//String test = edit.sendPost();
    	System.out.println( test );
    }
}
