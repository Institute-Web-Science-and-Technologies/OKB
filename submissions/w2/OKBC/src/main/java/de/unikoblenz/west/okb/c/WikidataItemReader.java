package de.unikoblenz.west.okb.c;



import com.cedarsoftware.util.io.JsonWriter;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class WikidataItemReader {

	public static String itemReader(String id){
		String wdlink = "https://www.wikidata.org/w/api.php?action=wbgetentities&ids="+id+"&format=json";
		String output1 = null, output2=null;

		try {
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
			output2 = JsonWriter.formatJson(output1.toString());

		} catch (Exception e) {

			e.printStackTrace();

		}
		return output2;
	}



}