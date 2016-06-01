package Server_files;


import WikiDataItem.Claims;
import WikiDataItem.Entities;
import de.unikoblenz.west.okb.c.WikidataItemReader;
import de.unikoblenz.west.okb.c.WikidataJsonParser;

import java.util.ArrayList;
import java.util.List;


public class ClaimService {

    public static List<Claims> getAllClaims() {
        List<Claims> claims = new ArrayList<Claims>();
        Claims c = new Claims();
        c.setId("Q12345");
        c.setType("wikidata-item");
        claims.add(c);
        return claims;
    }

    public static List<Claims> getClaimbyid(String id) {
        String cl = WikidataItemReader.itemReader(id);
        Entities e = null;
        try {
            e = WikidataJsonParser.parser(cl);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        List<Claims> claimses = new ArrayList<Claims>();
        claimses = e.getClaims();
        return claimses;

    }

    public static Claims createClaims(String id, String type) {
        Claims c = new Claims();
        c.setId(id);
        c.setType(type);
        return c;
    }
/*
    public static User updateUser(String id, String name, String email){
        List<User> us=getAllUsers();
        for(User i : us)
            if(id==i.getId()){
                i.setUsername(name);
                i.setEmail(email);
                return i;
            }
        User u = new User();
        return u;
    }
    */


}
