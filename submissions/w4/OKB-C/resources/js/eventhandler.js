// UI EVENT FUNCTIONS
var ITEM_DIV_ID = "item";

// TODO: doc
function handleSearchButtonClickEvent(form) {
    var search = form.searchQuery.value;
    
    // Check if search is an entity ID.
    var entityIdRegExp = new RegExp("[QP][0-9]+");
    if (entityIdRegExp.test(search)) {
        executeItemRequest(search);
    // Default case: execute standard search query.
    } else {
        executeSearchRequest(search);
    }
    form.searchQuery.value = "";
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

// TODO: doc
function handleItemRequest(data) {
    var item;
    try {
        item = mapJsonToWikidataItem(data);
    } catch(err) {
        console.log(err);
        window.alert(err);
        return;
    }
    printItem(item, ITEM_DIV_ID);
}

// TODO: doc
function handleSearchRequest(data) {
	printSearchResults(data, ITEM_DIV_ID);
}