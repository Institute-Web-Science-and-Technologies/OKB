package de.unikoblenz.west.okb.c;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


public class WikidataItemReader {

    //function who takes an Wikidata id. The output is the Wikidata Item in Json Format
    public static String itemReader(String id) {

        //wikidata Link with varibel id using the Wikidata api wbgetentities
        //https://www.wikidata.org/w/api.php?action=help&modules=wbgetentities
        String wdlink = "https://www.wikidata.org/w/api.php?action=wbgetentities&ids="
                + id + "&format=json";

        String output1 = null, output2 = null;
        try {
            //Jersey client to connect to API
            Client client = Client.create();
            WebResource webResource = client
                    .resource(wdlink);
            ClientResponse response = webResource.accept("application/json")
                    .get(ClientResponse.class);
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }
            output1 = response.getEntity(String.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return output1;
    }


}