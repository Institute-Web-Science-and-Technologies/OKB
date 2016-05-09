package de.unikoblenz.west.okb.c;

public class Main {
	
	public static void main(String[] args) {
		String id = "Q3";
		String output = WikidataItemReader.itemReader(id);
		String output1 = "["+output+"]";
		
		System.out.println(output1);

		String s=output1;
		
		
	
	}
 
}
