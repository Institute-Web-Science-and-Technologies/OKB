package de.unikoblenz.west.okb.c.restapi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * Created by Alex on 12.07.2016.
 */
public class WikidataSparqlAccessor {
    private final String SPARQL_URL = "https://query.wikidata.org/sparql";
    private final String CHARSET = "UTF-8";
    private final String INSTANCE_OF_QUERY = "SELECT ?item ?itemLabel\n" +
            "WHERE\n" +
            "{\n" +
            "?item wdt:P31 wd:%s .\n" +
            "?item wdt:P585 ?date .\n" +
            "SERVICE wikibase:label { bd:serviceParam wikibase:language \"en\" }\n" +
            "} ORDER BY DESC(?date)";

    /**
     *
     * @param id is a Wikidata item ID.
     * @return a JSONObject, which contains an array of "events", where each event contains an "id" and a "label".
     * @throws IOException if the connection to Wikidata SPARQL service failed somehow.
     * @throws IllegalArgumentException if the parameter id is not a valid Wikidata item ID.
     */
    public JSONObject getItemsByInstanceOfId(String id) throws IOException, IllegalArgumentException, org.json.JSONException {
        if (!this.isValidWikidataItemId(id)) {
            throw new IllegalArgumentException(String.format("supplied id=\"%s\" is not a valid Wikidata item ID", id));
        }
        // Create query and generate the query URL.
        String query = String.format(INSTANCE_OF_QUERY, id);
        String queryUrl = createWikidataSparqlQueryUrl(query);

        // Execute request.
        URLConnection connection = new URL(queryUrl).openConnection();
        connection.setRequestProperty("Accept-Charset", CHARSET);
        InputStream response = connection.getInputStream();

        // Get response of the SPARQL query and dump it into a String.
        String responseBody;
        Scanner scanner = new Scanner(response);
        responseBody = scanner.useDelimiter("\\A").next();
        scanner.close();

        JSONObject result = this.mapItemByInstanceOfResponse(new JSONObject(responseBody));

        return result;
    }

    /**
     *
     * @param query a SPARQL query.
     * @return a String containing an URL for a query to the Wikidata SPARQL service.
     */
    private String createWikidataSparqlQueryUrl(String query) {
        try {
            return SPARQL_URL + "?" + String.format("query=%s&format=%s", URLEncoder.encode(query, CHARSET), "json");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(CHARSET + " is unknown.");
        }
    }

    /**
     *
     * @param response a JSON response of the Wikidata SPARQL service.
     * @return a JSONObject, which contains an array of "events", where each event contains an "id" and a "label".
     */

    private JSONObject mapItemByInstanceOfResponse(JSONObject response) throws org.json.JSONException{
        JSONArray bindings = response.getJSONObject("results").getJSONArray("bindings");

        JSONObject result = new JSONObject();
        result.put("events", new JSONArray());
        /*
        for (Object binding : bindings) {
            JSONObject eventData = (JSONObject) binding;
            String eventId = eventData.getJSONObject("item").getString("value").replace("http://www.wikidata.org/entity/", "");
            String eventLabel = eventData.getJSONObject("itemLabel").getString("value");
            JSONObject event = new JSONObject();
            event.put("id", eventId);
            event.put("label", eventLabel);
            result.append("events", event);
        }
        */
        return result;
    }

    /**
     *
     * @param id any String
     * @return true if the parameter id is a valid Wikidata item ID (e.g. "Q1234").
     */
    private boolean isValidWikidataItemId(String id) {
        return id.matches("^Q\\d+");
    }
}
