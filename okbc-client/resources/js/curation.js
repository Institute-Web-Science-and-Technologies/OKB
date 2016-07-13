/*
* curation.js contains the actual functionality used for evaluating the user-generated
* data. It contains functions for recommending properties, checking validity of user input
* and sending the curated data to the server.
*/

// TODO: doc
var DATATYPE = {
    COMMONS_MEDIA: 1,
    GLOBE_COORDINATE: 2,
    MONOLINGUAL_TEXT: 3,
    QUANTITY: 4,
    STRING: 5,
    TIME: 6,
    URL: 7,
    EXTERNAL_IDENTIFIER: 8,
    ITEM: 8,
    PROPERTY: 9
};

// TODO: doc
var PROPERTIES = {
    // Alphabetically sorted
    "aircraft registration": {id: "P426", type: DATATYPE.STRING},
    "attendance": {id: "P1110", type: DATATYPE.QUANTITY},
    "candidate": {id: "P726", type: DATATYPE.ITEM},
    "chairperson": {id: "P488", type: DATATYPE.ITEM},
    "coordinate location": {id: "P625", type: DATATYPE.GLOBAL_COORDINATE},
    "cost of damage": {id: "P2630", type: DATATYPE.QUANTITY},
    "country": {id: "P17", type: DATATYPE.ITEM},
    "duration": {id: "P2047", type: DATATYPE.QUANTITY}, // unit is missing? probably as qualifier
    "eligible voters": {id: "P1867", type: DATATYPE.QUANTITY},
    "frequency of event": {id: "P2257", type: DATATYPE.QUANTITY},
    "has cause": {id: "P828", type: DATATYPE.ITEM},
    "instance of":  {id: "P31", type: DATATYPE.ITEM},
    "location": {id: "P276", type: DATATYPE.ITEM},
    "located in the administrative territorial entity": {id: "P131", type: DATATYPE.ITEM},
    "losses": {id: "P1356", type: DATATYPE.QUANTITY},
    "number of deaths": {id: "P1120", type: DATATYPE.QUANTITY},
    "number of injured": {id: "P1339", type: DATATYPE.QUANTITY},
    "number of matches played": {id: "P1350", type: DATATYPE.QUANTITY},
    "number of missing": {id: "P1446", type: DATATYPE.QUANTITY},
    "number of participants": {id: "P1132", type: DATATYPE.QUANTITY},
    "number of points/goals scored": {id: "P1351", type: DATATYPE.QUANTITY},
    "participant": {id: "P710", type: DATATYPE.ITEM},
    "participating teams": {id: "P1923", type: DATATYPE.ITEM},
    "part of": {id: "P361", type: DATATYPE.ITEM},
    "point in time": {id: "P585", type: DATATYPE.TIME},
    "publication date": {id: "P577", type: DATATYPE.TIME},
    "reference URL": {id: "P854", type: DATATYPE.URL},
    "retrieved": {id: "P813", type: DATATYPE.TIME},
    "sport": {id: "P641", type: DATATYPE.ITEM},
    "target": {id: "P533", type: DATATYPE.ITEM},
    "total valid votes": {id: "P1697", type: DATATYPE.QUANTITY},
    "wins": {id: "P1335", type: DATATYPE.QUANTITY},
    "winner": {id: "P1346", type: DATATYPE.ITEM}
    // TODO: add further relevant properties to the dictionary.
};

function getInputTypeForProperty(propertyName) {
    if (PROPERTIES[propertyName].type == DATATYPE.QUANTITY) {
        return 'number';
    } else if (PROPERTIES[propertyName].type == DATATYPE.TIME) {
        return 'date';
    } else if (PROPERTIES[propertyName].type == DATATYPE.URL) {
        return 'url';
    }
    return 'text';
}

function isValidProperty(propertyName) {
    return PROPERTIES.hasOwnProperty(propertyName);
}

// TODO: doc
// TODO: add check for url, item, coordinate, ... .
function isValidValue(value, propertyName) {
    // Check if value is an integer.
    if (PROPERTIES[propertyName].type == DATATYPE.QUANTITY) {
        return !isNaN(value);
    // Check if value is a date.
    } else if (PROPERTIES[propertyName].type == DATATYPE.TIME) {
        // Check for date of form YYYY-MM-DD.
        // TODO: Add other options for date and time formats.
        return /\d\d\d\d-[01]?\d-\d?\d/.test(value);
    }
    return true;
}

// TODO: doc
function isFirstClaimOfProperty(propertyName, item, claims) {
    for (var i = 0; i < claims.length; i++) {
        if (claims[i].propertyName == propertyName) {
            return false;
        }
    }
    for (var i = 0; i < item.statements.length; i++) {
        if (item.statements[i].propertyId == PROPERTIES[propertyName].id) {
            return false;
        }
    }
    return true;
}

// TODO: doc
function getClaimsWithProperty(propertyName, item, claims) {
    var result = [];
    for (var i = 0; i < claims.length; i++) {
        if (claims[i].propertyName == propertyName) {
            result.push(propertyName + ': ' + claims[i].value);
        }
    }
    for (var i = 0; i < item.statements.length; i++) {
        if (item.statements[i].propertyId == PROPERTIES[propertyName].id) {
            for (var j = 0; j < item.statements[i].claims.length; j++) {
                var claim = item.statements[i].claims[j];
                result.push(propertyName + ': ' + claim.mainsnak.datavalue);
            }
        }
    }
    return result;
}

// TODO: doc
function normalizeRating(rating) {
    return rating/100;
}

// TODO: doc
function normalizeAuthors(authors) {
    return authors.split(',').map(function(x) {return x.trim();});
}