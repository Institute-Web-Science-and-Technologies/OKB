package test_wiki.edit_wiki_data;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A helper class that wraps the MediaWiki API of the
 * <a href="http://en.wikipedia.org">English Wikipedia</a>.
 *
 * See the <a href="https://www.mediawiki.org/wiki/API:Main_page">MediaWiki API
 * Documentation</a>.
 */
public class WikiApiHelper {

    /**
     * Instance of the Apache HTTPClient library. Used for performing HTTP
     * requests to the Wikipedia API.
     */
    private CloseableHttpClient http_client;

    /**
     * Constructs a new WikipediaApiHelper.
     */
    public WikiApiHelper() {
        // Build and configure Apache HTTPClient library object.
        HttpClientBuilder http_client_builder = HttpClientBuilder.create();
        // One should always set a sensible user agent when using Web-APIs.
        http_client_builder.setUserAgent("WebInforamtionRetrieval_Assignment_Bot/1.0");
        // Disable cookie management to circumvent warning about illegal cookies
        // from Wikipedia. We don't need cookies for a REST-API anyway.
        http_client_builder.disableCookieManagement();
        http_client = http_client_builder.build();
    }

    /**
     * Searches the English Wikipedia for a given keyword {@code query}.
     *
     * @param query
     *            The search query as a string. Must not be null.
     * @return A list of search results as a normal java list. The list will be
     *         at most 10 items long.
     * @throws URISyntaxException
     *             If an error occurred during creating the request URI to
     *             Wikipedia.
     * @throws JSONException
     *             If the response from Wikipedia could not be parsed as the
     *             expected JSON.
     * @throws IOException
     *             If a general error happend while performing the HTTP request.
     */
    public String edit(String id) throws URISyntaxException, JSONException, IOException {
        Objects.requireNonNull(id);

        // Construct request URI
        // Documentation of parameters at https://www.mediawiki.org/wiki/API:Search
        URI request_uri = new URIBuilder().setScheme("https").setHost("www.wikidata.org/").setPath("w/api.php")
                .addParameter("action", "wbeditentity").addParameter("title", "Wikidata Sandbox Page").addParameter("id", id)
                .addParameter("data", "{\"descriptions\":{\"en-ca\":{\"language\":\"en-ca\",\"value\": \"This serves as a sandbox for live testing features. Please be gentle with it. Feel free to edit anything on this page, though! For testing links, try adding ones to userpages124.\"}}}").addParameter("token", "e052eaa5ad8a9d49d1694efa80069d67571fcc7c+\\").build();

        // Perform the HTTP request and obtain response as a string.
        String response = http_client.execute(new HttpPost(request_uri), new BasicResponseHandler());

        // Parse the string response as JSON.
        //JSONObject json_response = new JSONObject(response);
        
        return response.toString();
        // Get the search results part of the JSON response.
        //JSONArray json_search_results = json_respose.getJSONObject("query").getJSONArray("search");


    }

    
    public String sendPost() throws Exception {

		String url = "https://www.wikidata.org/w/api.php?";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla 5.0.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", "applicaion/x-www-form-urlencoded");
		con.setRequestProperty("x-accept-version", "2.0.0");

		
		String urlParameters = "action=wbeditentity&id=Q4115189&title=Wikidata Sandbox Page&data={\"descriptions\":{\"en-ca\":{\"language\":\"en-ca\",\"value\": \"12This serves as a sandbox for live testing features. Please be gentle with it. Feel free to edit anything on this page, though! For testing links, try adding ones to userpages124.\"}}}&token=74fc7b209d2986cf56228b06cca8a060571fe83b+\\";
		urlParameters = URLEncoder.encode(urlParameters, "UTF-8");
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		return response.toString();

	}


}
