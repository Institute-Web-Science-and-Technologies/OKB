// OUTPUT FUNCTIONS
/*
* printItem takes a WikidataItem and the ID of an HTML tag.
* It creates an HTML representation of the item and inserts it
* into tag, more specifically it is assigned to the innerHTML attribute
* of the tag.
*
* @param item an WikidataItem object.
* @param elementId an ID of an HTML tag, which has the attribute innerHTML.
*/
function printItem(item, elementId) {
    console.log("print item in " + elementId);
    console.log(item);
    // Add functions to the item object for in-template use.
    // Function returns an HTML string describing the applied snak object.
    item.printSnak = function () {
        return function (text, render) {
            return createHtmlRepresentationOfSnak(
                JSON.parse(render(text).replace(/&quot;/g, '"'))
            );
        }
    };
    // Generate HTML string for pretty printing of item.
    var template = document.getElementById("itemTemplate").innerHTML;
    var output = Mustache.render(template, item);
    // Write the itemString into the item-div.
    var outputTag = document.getElementById(elementId);
    outputTag.innerHTML = output;
}

// HELPER: HTML GENERATORS
/*
* @param snak a WikidataSnak object.
* @return an HTML string representing the snak.
*/
function createHtmlRepresentationOfSnak(snak) {
    var result = getLabelOfId(snak.propertyId) + "(" 
        + createWikidataLinkToProperty(snak.propertyId) + ") = ";
    var datatype = snak.datatype;
    var datavalue = snak.datavalue;
    var snaktype = snak.snaktype;
    if (snaktype == "value") {
        result += createHtmlRepresentationOfData(datatype, datavalue);
    } else {
        result += datatype + ": " + snaktype;
    }
    return result;
}

/*
* @param datatype a string containing the data type of datavalue.
* @param datavalue an object, which is directly gathered from the Wikidata API.
* @return an HTML string representing the data value.
*/
// TODO: Change function to use a WikidataDatavalue object instead of the current undefined object.
function createHtmlRepresentationOfData(datatype, datavalue) {
    var result = datatype + ": ";
    // Check cases for different data types with snaktype=value.
    if (datatype == "commonsMedia") {
        result += '"' + datavalue.value + '"';
    } else if (datatype == "wikibase-item") {
        var id = "Q" + datavalue.value["numeric-id"];
        result += getLabelOfId(id) + "(" + createWikidataLinkToItem(id) + ")";
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
    } else if (datatype == "monolingualtext") {
        result += datavalue.value.text;
    } else if (datatype == "wikibase-property") {
        var id = "P" + datavalue.value["numeric-id"];
        result += getLabelOfId(id) + "(" + createWikidataLinkToProperty(id) + ")";
    } // TODO: handle all missing cases, if there are any.
    else {
        console.log("WARNING: Following datatype not implemented: " + datatype);
    }
    return result;
}

// TODO: remove soon
function createWikidataLinkToItem(itemId) {
    return "<a href=https://www.wikidata.org/wiki/" + itemId + ">" + itemId + "</a>"
}

// TODO: remove soon
function createWikidataLinkToProperty(propertyId) {
    return "<a href=https://www.wikidata.org/wiki/Property:" + propertyId + ">" + propertyId + "</a>"
}
