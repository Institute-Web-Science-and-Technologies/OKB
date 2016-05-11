package test_wiki.edit_wiki_data;

/**
 * OKB-R Project
 * Created by OKBR on 26/04/16.
 */
import java.io.*;
        import java.net.*;
        import java.util.*;

import org.json.JSONObject;
import org.json.JSONStringer;


/**
 * Class Test for add, edit statements to wiki data
 * @author OKB-R Team
 *
 */

public class Test {

	/**
	 * Sets Qualifier to a statement
	 * @param claimId
	 * @param property
	 * @param data
	 * @return JSON string of whole statement with success or failure
	 * @throws IOException
	 */
		public String setQualifier (String claimId, String property, String data) throws IOException
		{
			
			  URL url = new URL("https://www.wikidata.org/w/api.php");
		        Map<String,Object> params = new LinkedHashMap<>();
		        params.put("action", "wbsetqualifier");
		        params.put("claim", claimId);
		        params.put("property", property);        
		        params.put("snaktype", "value");
		        params.put("format", "json");
		        params.put("value", data);
		        params.put("token", "+\\");

		        StringBuilder postData = new StringBuilder();
		        for (Map.Entry<String,Object> param : params.entrySet()) {
		            if (postData.length() != 0) postData.append('&');
		            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
		            postData.append('=');
		            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		        }
		        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

		        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		        conn.setRequestMethod("POST");
		        conn.setRequestProperty("x-accept-version","2.0.0");
		        conn.setRequestProperty("cache-control","no-cache");
		        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
		        conn.setDoOutput(true);
		        conn.getOutputStream().write(postDataBytes);

		        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		        String retJson="";
		        for (int c; (c = in.read()) >= 0;)
		             retJson+= (char)c;
		        
		        JSONObject json = (JSONObject)new JSONObject(retJson);
		        
		        return json.toString();
		        
		}
		/**
		 * Sets reference to a particular statement
		 * @param claimId
		 * @param snaks
		 * @return JSON string of whole statement with success or failure
		 * @throws IOException
		 */
		public String setReference (String claimId, String snaks) throws IOException
		{
			
			  URL url = new URL("https://www.wikidata.org/w/api.php");
		        Map<String,Object> params = new LinkedHashMap<>();
		        params.put("action", "wbsetreference");
		        params.put("statement", claimId);
		       // params.put("property", property);        
		        params.put("snaks", snaks);
		        params.put("format", "json");
		       // params.put("value", data);
		        params.put("token", "+\\");

		        StringBuilder postData = new StringBuilder();
		        for (Map.Entry<String,Object> param : params.entrySet()) {
		            if (postData.length() != 0) postData.append('&');
		            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
		            postData.append('=');
		            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		        }
		        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

		        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		        conn.setRequestMethod("POST");
		        conn.setRequestProperty("x-accept-version","2.0.0");
		        conn.setRequestProperty("cache-control","no-cache");
		        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
		        conn.setDoOutput(true);
		        conn.getOutputStream().write(postDataBytes);

		        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		        String retJson="";
		        for (int c; (c = in.read()) >= 0;)
		             retJson+= (char)c;
		        
		        JSONObject json = (JSONObject)new JSONObject(retJson);
		        
		        return json.toString();
		        
		}

	/**
	 *  Set a new claim/statement in wiki data
	 * @param entity
	 * @param property
	 * @param data
	 * @return JSON string of whole statement with success or failure
	 * @throws IOException
	 */
	public String setStatement (String entity, String property, String data) throws IOException
	{
		
		  URL url = new URL("https://www.wikidata.org/w/api.php");
	        Map<String,Object> params = new LinkedHashMap<>();
	        params.put("action", "wbcreateclaim");
	        params.put("entity", entity);
	        params.put("property", property);        
	        params.put("snaktype", "value");
	        params.put("format", "json");
	        params.put("value", data);
	        params.put("token", "+\\");

	        StringBuilder postData = new StringBuilder();
	        for (Map.Entry<String,Object> param : params.entrySet()) {
	            if (postData.length() != 0) postData.append('&');
	            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
	            postData.append('=');
	            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
	        }
	        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("x-accept-version","2.0.0");
	        conn.setRequestProperty("cache-control","no-cache");
	        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
	        conn.setDoOutput(true);
	        conn.getOutputStream().write(postDataBytes);

	        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	        String retJson="";
	        for (int c; (c = in.read()) >= 0;)
	             retJson+= (char)c;
	        
	        JSONObject json = (JSONObject)new JSONObject(retJson);
	        
	        return json.getJSONObject("claim").getString("id");
	        
	}
	
	/**
	 * Updates rank for particular claim/statement in wikidata
	 * @param claimId
	 * @param snaks
	 * @return JSON string of whole statement with success or failure
	 * @throws IOException
	 */
	public String updateRank (String claimId, String snaks) throws IOException
	{
		
		  URL url = new URL("https://www.wikidata.org/w/api.php");
	        Map<String,Object> params = new LinkedHashMap<>();
	        params.put("action", "wbsetclaim");
	        params.put("claim", snaks);
	       // params.put("property", property);        
	       // params.put("snaks", snaks);
	        params.put("format", "json");
	       // params.put("value", data);
	        params.put("token", "+\\");

	        StringBuilder postData = new StringBuilder();
	        for (Map.Entry<String,Object> param : params.entrySet()) {
	            if (postData.length() != 0) postData.append('&');
	            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
	            postData.append('=');
	            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
	        }
	        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("x-accept-version","2.0.0");
	        conn.setRequestProperty("cache-control","no-cache");
	        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
	        conn.setDoOutput(true);
	        conn.getOutputStream().write(postDataBytes);

	        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	        String retJson="";
	        for (int c; (c = in.read()) >= 0;)
	             retJson+= (char)c;
	        
	        JSONObject json = (JSONObject)new JSONObject(retJson);
	        
	        return json.toString();
	        
	}

	
    public static void main(String[] args) throws Exception {
      
    	String entity = "Q4115189";
    	String property = "P1082";
    	String qualifierProperty = "P585";
    	String data = "{\"amount\":\"+27553287\",\"unit\":\"1\",\"upperBound\":\"+27553287\",\"lowerBound\":\"+27553287\"}";
    	String qualiferData = "{\"time\": \"+2009-00-00T00:00:00Z\",\"timezone\": 0,\"before\": 0,\"after\": 0,\"precision\": 9,\"calendarmodel\": \"http:\\/\\/www.wikidata.org\\/entity\\/Q1985727\"}";
    	String referencesnaks = "{\"P248\":[{\"snaktype\":\"value\",\"property\":\"P248\",\"datavalue\":{\"value\":{\"entity-type\":\"item\",\"numeric-id\":21540096},\"type\":\"wikibase-entityid\"},\"datatype\":\"wikibase-item\"}]}";
    	
    	Test statement = new Test();
    	String stId = statement.setStatement(entity, property, data);
    	String rankData = "{\"mainsnak\": {\"snaktype\": \"value\",   \"property\": \"P1082\",\"datavalue\": {\"value\": "+data+", \"type\": \"quantity\"},\"datatype\": \"quantity\"},\"type\": \"statement\", \"id\": \""+stId+"\", \"rank\": \"preferred\"}";

    	String updateRank = statement.updateRank(stId,rankData); // adding rank while creting claim
    	String reference = statement.setReference(stId, referencesnaks); //setting reference while creting claim
    	String qualiferResult = statement.setQualifier(stId, qualifierProperty, qualiferData); //setting qualifier while creating claim
        System.out.println(stId);
        System.out.println(reference);
        
        System.out.println(updateRank );
        
        System.out.println(qualiferResult);

        
    }
}