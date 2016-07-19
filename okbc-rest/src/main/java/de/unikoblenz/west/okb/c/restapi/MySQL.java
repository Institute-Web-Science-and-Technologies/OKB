package de.unikoblenz.west.okb.c.restapi;

/**
 * Created by wkoop on 14.07.2016.
 */
public class MySQL {
    private final static String sqlgetrequest =
            "SELECT " +
                    "GROUP_CONCAT(DISTINCT \"Q\", events.eventid) as 'eventid', \n" +
                    "GROUP_CONCAT(DISTINCT events.label) as 'label', \n" +
                    "GROUP_CONCAT(DISTINCT events.location) as 'location', \n" +
                    "GROUP_CONCAT(DISTINCT categories.category) as 'category', \n" +
                    "GROUP_CONCAT(DISTINCT \"propertyid: P\", okbstatement.propertyid,\", label: \", okbstatement.label, \", datatype: \", " +
                    "okbstatement.datatype) as 'statements', \n" +
                    "GROUP_CONCAT(DISTINCT \"snaktype: \", claim.snaktype,\", value: \",claim.clvalue,\", ranking: \", " +
                    "claim.ranking) as 'claims', \n" +
                    "GROUP_CONCAT(DISTINCT \"propertyid: \", qualifier.propertyid,\", label: \", qualifier.label, \", datatype: \", " +
                    "qualifier.datatype,\", value: \", qualifier.qvalue) as 'qualifiers', \n" +
                    "GROUP_CONCAT(DISTINCT \"url: \", reference.url,\", publicationdate: \", reference.publicationdate,\", retrievaldate: \", \n" +
                    "reference.retrievaldate,\", trustrating: \", reference.trustrating,\", articletype: \", reference.articletype,\", title: \", \n" +
                    "reference.title,\", neutralityrating: \", reference.neutralityrating) as 'sources: ', \n" +
                    "GROUP_CONCAT(DISTINCT authors.author) as 'authors: ' \n" +
                    "from OKBCDB.events\n" +
                    "LEFT JOIN OKBCDB.categories ON events.eventid = categories.eventid\n" +
                    "LEFT JOIN OKBCDB.okbstatement ON events.eventid = okbstatement.eventid\n" +
                    "LEFT JOIN OKBCDB.claim ON okbstatement.propertyid = claim.propertyid\n" +
                    "LEFT JOIN OKBCDB.reference ON reference.claimid = claim.clid\n" +
                    "LEFT JOIN OKBCDB.qualifier ON qualifier.claimid = claim.clid\n" +
                    "LEFT JOIN OKBCDB.authors ON authors.refid = reference.refid\n";

    public static String getSqlgetrequest() {
        return sqlgetrequest;
    }
}
