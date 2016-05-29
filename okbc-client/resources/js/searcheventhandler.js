// UI EVENT FUNCTIONS
var RESULTS_ID = "results";

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

// TODO: doc, rename
function handleItemRequest(data) {
    sessionStorage.setItem("itemdata", JSON.stringify(data));
    console.log(sessionStorage["itemdata"]);
    window.location = "event.html";
}

// TODO: doc
function handleSearchRequest(data) {
	printSearchResults(data, RESULTS_ID);
}
