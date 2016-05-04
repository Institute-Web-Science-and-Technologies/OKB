package test_wiki.edit_wiki_data;

/**
 * Created by OKBR on 26/04/16.
 */
import java.io.*;
        import java.net.*;
        import java.util.*;

import org.json.JSONObject;
import org.json.JSONStringer;




class Test {

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
	
    public static void main(String[] args) throws Exception {
      
    	String entity = "Q4115189";
    	String property = "P1082";
    	String data = "{\"amount\":\"+27553282\",\"unit\":\"1\",\"upperBound\":\"+27553282\",\"lowerBound\":\"+27553282\"}";
    	String qualiferData = "{\"P1082\":[{\"snaktype\":\"value\",\"property\":\"P1082\",\"hash\":\"48517f9080dabfa101fa5f13a4c11866852488b6\",\"datavalue\":{\"value\":{\"amount\":\"+837442\",\"unit\":\"1\",\"upperBound\":\"+837442\",\"lowerBound\":\"+837442\"}";
    	String referencesnaks = "{\"P248\":[{\"snaktype\":\"value\",\"property\":\"P248\",\"datavalue\":{\"value\":{\"entity-type\":\"item\",\"numeric-id\":21540096},\"type\":\"wikibase-entityid\"},\"datatype\":\"wikibase-item\"}]}";
    	
    	Test statement = new Test();
    	String stId = statement.setStatement(entity, property, data);
    	String reference = statement.setReference(stId, referencesnaks);
    	String qualiferResult = statement.setQualifier(stId, property, qualiferData);
        System.out.println(stId);
        System.out.println(reference);
        System.out.println(qualiferResult);
        
    }
}