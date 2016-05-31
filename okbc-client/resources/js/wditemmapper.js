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
            var claim = mapJsonToWikidataClaim(jsonItem.claims[pid][i], pid);
            // Add the created claim to the item.
            item.claims.push(claim);
        }
    }
    requestMissingIdLabelPairs(item);

    return item;
}

// TODO: doc
function mapJsonToWikidataClaim(data, pid) {
    var claim = new WikidataClaim();
    claim.mainsnak.propertyId = pid;
    claim.mainsnak.snaktype = data.mainsnak.snaktype;
    claim.mainsnak.datatype = data.mainsnak.datatype;
    if (claim.mainsnak.snaktype == "value") {
        claim.mainsnak.datavalue = mapJsonToDatavalue(data.mainsnak.datavalue, claim.mainsnak.datatype);
    }
    if (data.hasOwnProperty("references")) {
        // Iterate over the references of the specific claim.            
        for (var j = 0; j < data.references.length; j++) {
            var reference = mapJsonToWikidataReference(data.references[j]);
            // Add the created reference to the claim.
            claim.references.push(reference);
        }
    }
    // Iterate over the qualifiers of the specific claim.
    for (var p in data.qualifiers) {
        var qualifier = mapJsonToWikidataQualifier(data.qualifiers[p][0]);
        // Add the created qualifier to the claim.
        claim.qualifiers.push(qualifier);
    }
    return claim;
}

// TODO: doc
function mapJsonToWikidataReference(data) {
    var reference = new WikidataReference();
    reference.snaksOrder = data["snaks-order"];
    for (var k = 0; k < reference.snaksOrder.length; k++) {
        var snak = mapJsonToWikidataSnak(data.snaks[reference.snaksOrder[k]][0]);
        // Add the created snak to the reference..
        reference.snaks.push(snak);
    }
    return reference;
}

// TODO: doc
function mapJsonToWikidataQualifier(data) {
    var qualifier = new WikidataQualifier();
    qualifier.snak = mapJsonToWikidataSnak(data);
    return qualifier;
}

// TODO: doc
function mapJsonToWikidataSnak(data) {
    var snak = new WikidataSnak();
    snak.propertyId = data.property;
    snak.snaktype = data.snaktype;
    snak.datatype = data.datatype;
    if (snak.snaktype == "value") {
        snak.datavalue = mapJsonToDatavalue(data.datavalue, snak.datatype);
    }
    return snak;
}

// TODO: doc
function mapJsonToDatavalue(data, datatype) {
    if (datatype == "wikibase-item") {
        return new WikibaseItem(data.value["numeric-id"]);
    } else if (datatype == "wikibase-property") {
        return new WikibaseProperty(data.value["numeric-id"]);
    } else if (datatype == "globe-coordinate") {
        return new GlobeCoordinate(data.value.latitude, data.value.longitude);
    } else if (datatype == "time") {
        return new WikidataTime(data.value.time);
    } else if (datatype == "quantity") {
        return new Quantity(data.value.amount);
    } else if (datatype == "string") {
        return new WikidataString(data.value);
    } else if (datatype == "external-id") {
        return new ExternalId(data.value);
    } else if (datatype == "monolingualtext") {
        return new MonolingualText(data.value.text, data.value.language);
    } else if (datatype == "commonsMedia") {
        return new CommonsMedia(data.value);
    } else if (datatype == "url") {
        return new Url(data.value);
    }
    throw datatype + ' is not implemented';
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
WikidataSnak.prototype.toString = function() {
    var label;
    try {
        label = getLabelOfId(this.propertyId);
    } catch (err) {
        label = this.propertyId;
    }
    if (this.snaktype == "value") {
        return label + ': ' + this.datavalue;
    } else {
        return  label + ' is of snaktype ' + this.snaktype;
    }
};

// TODO: doc
function WikibaseItem(numericid) {
    this.numericid = numericid;
}
WikibaseItem.prototype.toString = function() {
    var label;
    try {
        label = getLabelOfId('Q' + this.numericid);
    } catch (err) {
        label = "Q" + this.numericid;
    }
    return label;
};

// TODO: doc
function WikibaseProperty(numericid) {
    this.numericid = numericid;
}
WikibaseProperty.prototype.toString = function() {
    var label;
    try {
        label = getLabelOfId('P' + this.numericid);
    } catch (err) {
        label = "P" + this.numericid;
    }
    return label;
};

// TODO: doc
function GlobeCoordinate(latitude, longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
}
GlobeCoordinate.prototype.toString = function() {
    return this.latitude + 'N,' + this.longitude + 'E';
};

// TODO: doc
function CommonsMedia(value) {
    this.value = value;
}
CommonsMedia.prototype.toString = function() {
    return this.value;
};

// TODO: doc
function WikidataTime(time) {
    this.time = time;
}
WikidataTime.prototype.toString = function() {
    return this.time;
}

// TODO: doc
function Quantity(amount) {
    this.amount = amount;
}
Quantity.prototype.toString = function() {
    return this.amount;
}

// TODO: doc
function WikidataString(value) {
    this.value = value;
}
WikidataString.prototype.toString = function() {
    return this.value;
};

// TODO: doc
function ExternalId(value) {
    this.value = value;
}
ExternalId.prototype.toString = function() {
   return this.value; 
}

// TODO: doc
function Url(url) {
    this.url = url;
}
Url.prototype.toString = function() {
    return this.url;
}

// TODO: doc
function MonolingualText(text, language) {
    this.text = text;
    this.language = language;
}
MonolingualText.prototype.toString = function() {
    return this.text;
}