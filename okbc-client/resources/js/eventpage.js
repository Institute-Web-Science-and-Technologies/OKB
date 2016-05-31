var currentEvent;

// TODO: doc, improve timeout, save id label store in localStorage
function init() {
    var propertyList = document.getElementById("propertyList");
    for (var property in PROPERTIES) {
        var option = document.createElement("option");
        option.value = property;
        propertyList.appendChild(option);
    }

    if (!sessionStorage["itemdata"]) {
        window.location = "selection.html";
        return;
    }
    var data = JSON.parse(sessionStorage["itemdata"]);
    var item;
    try {
        item = mapJsonToWikidataItem(data);
        currentEvent = item;
    } catch(err) {
        console.log(err);
        window.alert(err);
        return;
    }
    setTimeout(function () {printItem(currentEvent, "item");}, 1000);
}

// TODO: doc
function chooseProperty(propertyInputId) {
    var property = document.getElementById(propertyInputId).value;
    if (PROPERTIES.hasOwnProperty(property)) {
        window.alert("Existing property: " + property);
    } else {
        window.alert("Not existing property: " + property);
    }
}

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