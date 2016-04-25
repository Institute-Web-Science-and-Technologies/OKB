package de.unikoblenz.west.okb.c;



import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class WikidataItemReader {

	
	
	public static void main(String[] args) {
		String u = "https://www.wikidata.org/w/api.php?action=wbgetentities&ids=Q2&format=json";
		
		System.out.println( u );
		
		
		
		
		try {

			Client client = Client.create();

			WebResource webResource = client
			   .resource(u);

			ClientResponse response = webResource.accept("application/json")
	                   .get(ClientResponse.class);

			if (response.getStatus() != 200) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}

			String output = response.getEntity(String.class);

			System.out.println("Output from Server .... \n");
			System.out.println(output);

		  } catch (Exception e) {

			e.printStackTrace();

		  }

		
	}

}
