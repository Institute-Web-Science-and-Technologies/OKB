describe("idlabelstore.js contains functions related to gathering and storing Wikidata entity ids"
    + " and their labels", function() {
    describe("getAllIdsFrom iterates over an object of type WikidataItem"
        + "and returns a set of entity IDs, which appear in the item", function() {
    
        it("test for item with no claims", function() {
            var claimlessItem = new WikidataItem();
            claimlessItem.id = "Q111";

            var result = getAllIdsFrom(claimlessItem);
        
            expect(result.size).toEqual(1);
            expect(result.has("Q111")).toEqual(true);
        });

        it("test for item with claims with different IDs", function() {
            var item = new WikidataItem();
            item.id = "Q1";
            var claim1 = new WikidataClaim();
            var mainsnak1 = new WikidataSnak();
            mainsnak1.propertyId = "P1";
            claim1.mainsnak = mainsnak1;
            var claim2 = new WikidataClaim();
            var mainsnak2 = new WikidataSnak();
            mainsnak2.propertyId = "P2";
            claim2.mainsnak = mainsnak2;
            item.claims.push(claim1);
            item.claims.push(claim2);

            var result = getAllIdsFrom(item);

            expect(result.size).toEqual(3);
            expect(result.has("Q1")).toEqual(true);
            expect(result.has("P1")).toEqual(true);
            expect(result.has("P2")).toEqual(true);
        });

        it("test for item with claims with same IDs", function() {
            var item = new WikidataItem();
            item.id = "Q1";
            var claim1 = new WikidataClaim();
            var mainsnak1 = new WikidataSnak();
            mainsnak1.propertyId = "P1";
            claim1.mainsnak = mainsnak1;
            var claim2 = new WikidataClaim();
            var mainsnak2 = new WikidataSnak();
            mainsnak2.propertyId = "P1";
            claim2.mainsnak = mainsnak2;
            item.claims.push(claim1);
            item.claims.push(claim2);

            var result = getAllIdsFrom(item);

            expect(result.size).toEqual(2);
            expect(result.has("Q1")).toEqual(true);
            expect(result.has("P1")).toEqual(true);
        });

        it("test for item with claim with references and qualifiers", function() {
            var item = new WikidataItem();
            item.id = "Q1";
            var claim = new WikidataClaim();
            var mainsnak = new WikidataSnak();
            mainsnak.propertyId = "P1";
            claim.mainsnak = mainsnak;
            var ref = new WikidataReference();
            var refsnak = new WikidataSnak();
            refsnak.propertyId = "P2";
            ref.snaks.push(refsnak);
            ref.snaksOrder.push("P2");
            claim.references.push(ref);
            var qualifier = new WikidataQualifier();
            var qualsnak = new WikidataSnak();
            qualsnak.propertyId = "P3";
            qualifier.snak = qualsnak;
            claim.qualifiers.push(qualifier);
            item.claims.push(claim);

            var result = getAllIdsFrom(item);

            expect(result.size).toEqual(4);
            expect(result.has("Q1")).toEqual(true);
            expect(result.has("P1")).toEqual(true);
            expect(result.has("P2")).toEqual(true);
            expect(result.has("P3")).toEqual(true);

        });
    });

    describe("Set.prototype.addIdsFromSnak adds all entity IDs appearing in a Snak to the set",
        function() {
        
        it("test for snak, which has neither wikibase-item nor wikibase-property data type",
            function() {
            var s = new Set();
            var snak = new WikidataSnak();
            snak.propertyId = "P1";
            s.addIdsFromSnak(snak);
            
            expect(s.size).toEqual(1);
            expect(s.has("P1")).toEqual(true);
            expect(s.has("P2")).toEqual(false);
        });
        // TODO: other tests, note for this there have to be actual classes for data values.
    });
});