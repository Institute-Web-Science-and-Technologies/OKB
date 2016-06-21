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
    "instance of":  {id: "P31", type: DATATYPE.ITEM},
    "country": {id: "P17", type: DATATYPE.ITEM},
    "point in time": {id: "P585", type: DATATYPE.TIME},
    "coordinate location": {id: "P625", type: DATATYPE.GLOBAL_COORDINATE},
    "target" : {id: "P533", type: DATATYPE.ITEM},
    "number of deaths": {id: "P1120", type: DATATYPE.QUANTITY},
    "number of injured": {id: "P1339", type: DATATYPE.QUANTITY},
    "location": {id: "P276", type: DATATYPE.ITEM},
    "participant": {id: "P710", type: DATATYPE.ITEM},
    "reference URL": {id: "P854", type: DATATYPE.URL},
    "retrieved": {id: "P813", type: DATATYPE.TIME},
    "publication data": {id: "P577", type: DATATYPE.TIME},
    // TODO: add further relevant properties to the dictionary.
};

// STUBS
// TODO: implement functionality
function getInputTypeForProperty(propertyLabel) {
    return 'text';
}

function isValidProperty(propertyLabel) {
    return true;
}

function isValidValue(value, propertyName) {
    return true;
}

function getSuggestedProperties(itemId) {
    return ['number of deaths', 'point in time', 'instance of', 'number of injured'];
}

function isFirstClaimOfProperty(property, item) {
    return false;
}

function getClaimsWithProperty(property, item) {
    return [property + ': something', property + ': bread'];
}

function normalizeRating(rating) {
    return rating/100;
}

function normalizeDate(date) {
    return date;
}

function normalizeAuthors(authors) {
    return [authors];
}