/**
 * Created by nishantisme on 27/04/16.
 */
import java.io.*;
        import java.net.*;
        import java.util.*;

class Test {
    public static void main(String[] args) throws Exception {
        URL url = new URL("https://www.wikidata.org/w/api.php");
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("action", "wbeditentity");
        params.put("id", "Q4115189");
        params.put("title", "Wikidata Sandbox Page");
        params.put("data", "{\"descriptions\":{\"en-ca\":{\"language\":\"en-ca\",\"value\": \"1234This serves as a sandbox for live testing features. Please be gentle with it. Feel free to edit anything on this page, though! For testing links, try adding ones to userpages.\"}}}");
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

        for (int c; (c = in.read()) >= 0;)
            System.out.print((char)c);
    }
}