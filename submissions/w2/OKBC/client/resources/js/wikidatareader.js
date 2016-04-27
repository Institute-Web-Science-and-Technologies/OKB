// UI EVENT FUNCTIONS
// TODO: separate functions into different files depending on functionality.

// TODO: doc
function executeSearchRequest(form) {
    var request = createWikidataItemRequest(form.search_query.value, printItem);
    form.search_query.value = "";
    executeApiRequest(request);
}

// OUTPUT FUNCTIONS
// TODO: doc
function printItem(data) {
    var item;
    try {
        item = extractItemFromJson(data);
    } catch(err) {
        console.log(err);
        window.alert(err);
        return;
    }
    console.log(item);

    // Generate HTML string for pretty printing of item.
    var itemString = "";
    itemString += createTitleOf(item);
    itemString += createDescriptionOf(item);
    itemString += createAliasListOf(item);
    itemString += createClaimListOf(item);
    // Write the itemString into the item-div.
    var statementsDiv = document.getElementById("item");
    statementsDiv.innerHTML = itemString;
}

// HELPER: HTML GENERATORS
// TODO: doc, function renaming
function createTitleOf(item) {
    return "<h3>" + item.labels.en.value + " (" + item.id + ")" + "</h3>";
}

// TODO: doc, function renaming
function createDescriptionOf(item) {
    return "<p>" + item.descriptions.en.value + "</p>";
}

// TODO: doc, function renaming
function createAliasListOf(item) {
    if (!item.aliases.hasOwnProperty("en")) {
        return "";
    }
    var result = "<h4>Aliases:</h4><ul>";
    for (var i = 0; i <  item.aliases.en.length; i++) {
        result += "<li>" + item.aliases.en[i].value + "</li>";
    }
    result += "</ul>";
    return result;
}

// TODO: doc, function renaming
function createClaimListOf(item) {
    var claims = item.claims;
    var result = "<h4>Claims:</h4><ul>"
    for (var pid in claims) {
        result += "<li>" + createStatementsOf(pid, claims[pid]) + "</li>";
    }
    result += "</ul>";
    return result;
}

// TODO: docs, function renaming
function createStatementsOf(propertyId, statement) {
    var result = createWikidataLinkToProperty(propertyId) + ": <ul>";
    for (var i = 0; i < statement.length; i++) {
        result += "<li>" + createValueOf(statement[i]);
        result += "<ul>";
        result += createQualifiersOf(statement[i]);
        result += createReferencesOf(statement[i]);
        result += "</ul></li>";
    }
    result += "</ul>";
    return result;
}

// TODO: docs, function renaming
function createValueOf(claim) {
    var result = "";
    var datatype = claim.mainsnak.datatype;
    var datavalue = claim.mainsnak.datavalue;
    var snaktype = claim.mainsnak.snaktype;
    if (snaktype == "value") {
        result = createValueRepresentationOf(datatype, datavalue);
    } else {
        result = datatype + ": " + snaktype;
    }
    return result;
}

// TODO: docs, function renaming
function createValueRepresentationOf(datatype, datavalue) {
    var result = datatype + ": ";
    // Check cases for different data types with snaktype=value.
    if (datatype == "commonsMedia") {
        result += '"' + datavalue.value + '"';
    } else if (datatype == "wikibase-item") {
        // TODO: this may also catch properties and not only items.
        var numId = "Q" + datavalue.value["numeric-id"];
        result += createWikidataLinkToItem(numId);
    } else if (datatype == "time") {
        result += datavalue.value.time;
    } else if (datatype == "globe-coordinate") {
        result += "lat=" + datavalue.value.latitude;
        result += ", long=" + datavalue.value.longitude;
        result += ", alt=" + datavalue.value.altitude;
        result += ", pre=" + datavalue.value.precision;
    } else if (datatype == "quantity") {
        result += datavalue.value.lowerBound + " to " + datavalue.value.upperBound; 
    } else if (datatype == "string") {
        result += datavalue.value;
    } else if (datatype == "external-id") {
        result += datavalue.value;
    } else if (datatype == "url") {
        result += "<a href=" + datavalue.value + ">link</a>";
    } // TODO: handle all missing cases, if there are any.
    return result;
}

//TODO: docs, function renaming
function createQualifiersOf(claim) {
    if (!claim.hasOwnProperty("qualifiers")) {
        return "";
    }
    var result = "<li>Qualifiers:<ul>"
    for (var pid in claim.qualifiers) {
        var datatype = claim.qualifiers[pid][0].datatype;
        var datavalue = claim.qualifiers[pid][0].datavalue;
        result += "<li>" + createWikidataLinkToProperty(pid) + ": " 
        result += createValueRepresentationOf(datatype, datavalue) + "</li>";
    }
    result += "</ul></li>";
    return result;
}

// TODO: docs, function renaming
function createReferencesOf(claim) {
    if (!claim.hasOwnProperty("references")) {
        return "";
    }
    var result = "<li>References:<ul>"
    for (var i = 0; i < claim.references.length; i++) {
        var ref = claim.references[i];
        result += "<li>Ref " + i + ":<ul>";
        for (pid in ref.snaks) {
            var datatype = ref.snaks[pid][0].datatype;
            var datavalue = ref.snaks[pid][0].datavalue;
            result += "<li>" + createWikidataLinkToProperty(pid) + ": " 
            result += createValueRepresentationOf(datatype, datavalue) + "</li>";
        }
        result += "</ul></li>";
    }
    result += "</ul></li>";
    return result;
}

// TODO: docs, function renaming
function createWikidataLinkToItem(itemId) {
    return "<a href=https://www.wikidata.org/wiki/" + itemId + ">" + itemId + "</a>"
}

// TODO: docs, function renaming
function createWikidataLinkToProperty(propertyId) {
    return "<a href=https://www.wikidata.org/wiki/Property:" + propertyId + ">" + propertyId + "</a>"
}

// API REQUEST FUNCTIONS
// TODO: doc
function executeApiRequest(request) {
    // Create the script element.
    var scriptTag = document.createElement('script');
    // Add the API request as source to the script element.
    scriptTag.src = request;
    // Get the head element of the document.
    var headTag = document.getElementsByTagName('head')[0];
    // Add the script element to the head of the current document.
    // This executes the callback function, which is part of the request.
    headTag.appendChild(scriptTag);
    // Remove the script-tag after execution of the callback function.
    headTag.removeChild(scriptTag);
}

// TODO: doc
function createWikidataRequest(params, callbackFunc) {
    var wikidataApiUrl = "https://www.wikidata.org/w/api.php";
    var request = wikidataApiUrl + "?callback=" + callbackFunc.name;
    for (key in params) {
        request += "&" + key + "=" + params[key];
    }
    return request;
}

// TODO: doc
function createWikidataItemRequest(itemId, callbackFunc) {
    var params = 
        { action : "wbgetentities"
        , languages : "en"
        , format : "json"
        , ids : itemId
        };
    return createWikidataRequest(params, callbackFunc);
}

// TODO: doc
function createWikidataSearchRequest(search, callbackFunc) {
    var params = 
        { action : "wbsearchentities"
        , languages : "en"
        , format : "json"
        , search : search
        };
    return createWikidataRequest(params, callbackFunc);
}

// unsorted functions
// TODO: doc
function extractItemFromJson(data) {
    if (data.hasOwnProperty("error")) {
        // TODO: Replace error string with error object.
        throw data.error.code + ": " + data.error.info;
    }
    // Get the first item id, which was returned.
    var itemId = Object.keys(data.entities)[0];
    var item = data.entities[itemId];
    // Check if the first item id has a corresponding Wikidata item.
    if (item.hasOwnProperty("missing")) {
        throw "There is no item with the ID '" + itemId + "'.";
    } 
    return item;
}
