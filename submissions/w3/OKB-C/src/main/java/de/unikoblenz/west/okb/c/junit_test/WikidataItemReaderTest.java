package de.unikoblenz.west.okb.c.junit_test;
import static org.junit.Assert.*;

import org.junit.Test;

public class WikidataItemReaderTest {

	@Test
	public void Existing_Id_test() {
		//output should contain "success":1
		String output = de.unikoblenz.west.okb.c.WikidataItemReader.itemReader("Q2324");
		assertTrue(output.contains("\"success\":1"));
	}
	
	@Test
	public void No_ID_test(){
		//output should contain "success":1
		String output = de.unikoblenz.west.okb.c.WikidataItemReader.itemReader("");
		assertTrue(output.contains("\"success\":1"));
	}
	
	@Test
	public void Id_Missing_test(){
		//output should contain "success":1 and "missing" (Id is missing)
		String output = de.unikoblenz.west.okb.c.WikidataItemReader.itemReader("P1");
		assertTrue(output.contains("\"success\":1") && output.contains("missing"));
		
		output = de.unikoblenz.west.okb.c.WikidataItemReader.itemReader("Q1241241254151");
		assertTrue(output.contains("\"success\":1") && output.contains("missing"));
	}
	
	@Test
	public void Failure_No_Such_Entity_test(){
		//output should contain "error" and "no-such-entity"
		String output = de.unikoblenz.west.okb.c.WikidataItemReader.itemReader("Badass");
		assertTrue(output.contains("error") && output.contains("no-such-entity"));
	}

}
