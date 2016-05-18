// TODO: doctype, error handling
// TODO: add id labels pairs from this query to the store.
function printSearchResults(data, elementId) {
    console.log("print search results");
	var template = document.getElementById("searchResultTemplate").innerHTML;
	var output = Mustache.render(template, data);
	var outputTag = document.getElementById(elementId);
	outputTag.innerHTML = output;
}
	
	
	