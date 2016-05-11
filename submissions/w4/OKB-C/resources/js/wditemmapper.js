// J2O MAPPING
// TODO: doc, subdivide
// TODO: Replace error string with error object.
/*
* This function takes JSON data, which was obtained from a Wikidata wbgetentities API call
* and maps the first entity in this data to a WikidataItem object.
* If the API call was bad (e.g. an error is returned) or the first entitity is missing,
* an exception is raised based on the specific error cause.
*
* @param data - object parsed from JSON response to Wikidata wbgetentities call.
* @return WikidataItem object representing the first entity appearing in the JSON data.
* @throws 
* @throws
*/
function mapJsonToWikidataItem(data) {
    console.log("map data to item");
    console.log(data);

    if (data.hasOwnProperty("error")) {
        throw data.error.code + ": " + data.error.info;
    }

    // Get the first item id, which was returned.
    var itemId = Object.keys(data.entities)[0];
    var jsonItem = data.entities[itemId];
    // Check if the first item id has a corresponding Wikidata item.
    if (jsonItem.hasOwnProperty("missing")) {
        throw "There is no item with the ID '" + itemId + "'.";
    }
    var item = new WikidataItem();
    item.id = jsonItem.id;
    item.label = jsonItem.labels.en.value;
    item.description = jsonItem.descriptions.en.value;
    if (jsonItem.aliases.hasOwnProperty("en")) {
        // Iterate over each alias of the item.
        for (var i = 0; i < jsonItem.aliases.en.length; i++) {
            item.aliases.push(jsonItem.aliases.en[i].value);
        }
    }
    // Iterate over each property of the item.
    for (var pid in jsonItem.claims) {
        // Iterate over each claim in the specific property.
        for (var i = 0; i < jsonItem.claims[pid].length; i++) {
            var jsonClaim = jsonItem.claims[pid][i];
            var claim = new WikidataClaim();
            claim.mainsnak.propertyId = pid;
            claim.mainsnak.snaktype = jsonClaim.mainsnak.snaktype;
            claim.mainsnak.datatype = jsonClaim.mainsnak.datatype;
            if (claim.mainsnak.snaktype == "value") {
                claim.mainsnak.datavalue = jsonClaim.mainsnak.datavalue;
            }
            if (jsonClaim.hasOwnProperty("references")) {
                // Iterate over the references of the specific claim.            
                for (var j = 0; j < jsonClaim.references.length; j++) {
                    var jsonReference = jsonClaim.references[j];
                    var reference = new WikidataReference();
                    reference.snaksOrder = jsonReference["snaks-order"];
                    for (var k = 0; k < reference.snaksOrder.length; k++) {
                        var jsonSnak = jsonReference.snaks[reference.snaksOrder[k]][0];
                        var snak = new WikidataSnak();
                        snak.propertyId = jsonSnak.property;
                        snak.snaktype = jsonSnak.snaktype;
                        snak.datatype = jsonSnak.datatype;
                        if (snak.snaktype == "value") {
                            snak.datavalue = jsonSnak.datavalue;
                        }
                        // Add the created snak to the reference..
                        reference.snaks.push(snak);
                    }
                    // Add the created reference to the claim.
                    claim.references.push(reference);
                }
            }
            // Iterate over the qualifiers of the specific claim.
            for (var p in jsonClaim.qualifiers) {
                var jsonQualifier = jsonClaim.qualifiers[p][0];
                var qualifier = new WikidataQualifier();
                qualifier.snak.propertyId = jsonQualifier.property;
                qualifier.snak.snaktype = jsonQualifier.snaktype;
                qualifier.snak.datatype = jsonQualifier.datatype;
                if (qualifier.snak.snaktype == "value") {
                    qualifier.snak.datavalue = jsonQualifier.datavalue;
                }
                // Add the created qualifier to the claim.
                claim.qualifiers.push(qualifier);
            }
            // Add the created claim to the item.
            item.claims.push(claim);
        }
    }
    requestMissingIdLabelPairs(item);

    return item;
}

// Wikidata classes
// TODO: doc
function WikidataItem() {
    this.id;
    this.label;
    this.description;
    this.aliases = [];
    this.claims = [];
}

// TODO: doc
function WikidataClaim() {
    this.mainsnak = new WikidataSnak();
    this.references = [];
    this.qualifiers = [];
}

// TODO: doc
function WikidataReference() {
    // The snaks are already ordered, after the given snaksOrder.
    this.snaks = [];
    this.snaksOrder = [];
}

// TODO: doc
function WikidataQualifier() {
    this.snak = new WikidataSnak();
}

// TODO: doc
function WikidataSnak() {
    this.propertyId;
    this.snaktype;
    this.datatype;
    this.datavalue;
}
// TODO: doc
WikidataSnak.prototype.toString = function snakToString() {
    return JSON.stringify(this);
};

// TODO: add classes for data values.