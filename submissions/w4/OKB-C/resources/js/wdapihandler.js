// API REQUEST FUNCTIONS
/*
* executeApiRequest takes a URL, which represents an API request (e.g. 
* https://www.wikidata.org/w/api.php?action=wbgetentities&ids=Q42) ,
* and executes the request.
* To fully utilize the request, one of the parameters in the request url
* should be callback=<function_name>, where <function_name> represents
* the function, which is called with the API response data as single argument.
*
* @param request a URL string describing an API request.
*/
function executeApiRequest(request) {
    console.log("execute request " + request);
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

/*
* createWikidataItemRequest takes a single Wikidata entity ID and a callback function.
* It returns a URL, which represents a wbgetentities call to the Wikidata API.
* The return format is JSON and the only language English.
*
* @param itemId an Wikidata entity ID (e.g. P1, Q2, ...).
* @param callbackFunc a single argument function handling the response data.
* @return a URL string describing a Wikidata API call to get the specified item.
*/
function createWikidataItemRequest(itemId, callbackFunc) {
    console.log("create wikidata item request");
    var params = 
        { action : "wbgetentities"
        , languages : "en"
        , format : "json"
        , ids : itemId
        };
    return createWikidataRequest(params, callbackFunc);
}

// TODO: doc
function createWikidataEntitiesRequest(entityIds, callbackFunc) {
    console.log("create wikidata entities request");
    var ids = "";
    if (entityIds.length >= 1) {
        ids += entityIds[0];
        for (var i = 1; i < entityIds.length; i++) {
            ids += "|" + entityIds[i];
        }
    }
    var params = 
        { action : "wbgetentities"
        , languages : "en"
        , format : "json"
        , ids : ids
        };
    return createWikidataRequest(params, callbackFunc);
}

/*
* createWikidataSearchRequest takes a search query string and a callback function.
* It returns a URL, which represents a entity search Wikidata API request.
* The return format is JSON and the language of the query and results is English.
*
* @param search a string containing an arbitrary search query string in English language.
* @param callbackFunc a single argument function handling the response data.
* @return a URL string describing a Wikidata API call to get the results the search query.
*
*/
function createWikidataSearchRequest(search, callbackFunc) {
    console.log("create wikidata search request");
    var params = 
        { action : "wbsearchentities"
        , language : "en"
        , format : "json"
		, limit : 50
        , search : search
        };
    return createWikidataRequest(params, callbackFunc);
}

/*
* createWikidataRequest creates a Wikidata API request URL string, where
* params are arguments for the call and the callbackFunc is the callback function,
* which will handle the response.
*
* @param params an object, which contains key-value pairs, which represent parameters 
*   and arguments for a Wikidata API request.
* @param callbackFunc a single argument function handling the response data.
* @return a URL string describing a call to the Wikidata API.
*/
function createWikidataRequest(params, callbackFunc) {
    var wikidataApiUrl = "https://www.wikidata.org/w/api.php";
    var request = wikidataApiUrl + "?callback=" + callbackFunc.name;
    for (key in params) {
        request += "&" + key + "=" + params[key];
    }
    return encodeURI(request);
}
