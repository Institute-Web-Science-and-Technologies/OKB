// UI EVENT FUNCTIONS
var ITEM_ID = "item";
var RESULTS_ID = "results";
var currentItem;

// TODO: doc
function handleSearchClick(textId) {
    var text = document.getElementById(textId);
    var search = text.value;
    text.value = "";
    // Check if search is an entity ID.
    var entityIdRegExp = new RegExp("[QP][0-9]+");
    if (entityIdRegExp.test(search)) {
        executeItemRequest(search);
    // Default case: execute standard search query.
    } else {
        executeSearchRequest(search);
    }
}

/*
* executeSearchRequest takes a search query and executes a request to the
* Wikidata API to get the results of the query. These results are printed
* into the HTML tag specified by the ID ITEM_DIV_ID.
*
* @param search a string representing a search query.
*/
function executeSearchRequest(search) {
	var request = createWikidataSearchRequest(search, handleSearchRequest);
	executeApiRequest(request);
}

/*
* executeItemRequest takes a valid Wikidata entity ID and executes a request to
* the Wikidata API to get the specified entity. This entity is then printed into
* HTML tag specified by the ID ITEM_DIV_ID.
*
* @param id a string representing a Wikidata entity ID (e.g. P1, Q2, ...).
*/
function executeItemRequest(id) {
	var request = createWikidataItemRequest(id, handleItemRequest);
    executeApiRequest(request);
}

// TODO: doc, improve set timeout
function handleItemRequest(data) {
    var item;
    try {
        item = mapJsonToWikidataItem(data);
    } catch(err) {
        console.log(err);
        window.alert(err);
        return;
    }
    setTimeout(function() { jumpToEventPage(item)}, 1000);
}

// TODO: doc
function jumpToEventPage(item) {
    localStorage.setItem("currentitem", JSON.stringify(item));
    console.log(ID_LABEL_STORE);
    localStorage.setItem("idlabelstore", JSON.stringify(ID_LABEL_STORE));
    window.location = "event.html";
}

// TODO: doc
function handleSearchRequest(data) {
	printSearchResults(data, RESULTS_ID);
}
