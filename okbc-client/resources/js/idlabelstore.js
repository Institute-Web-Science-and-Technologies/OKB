/*
* ID_LABEL_STORE behaves as a key-value set.
* Keys are Wikidata entity IDs and the values are the corresponding labels.
*/
var ID_LABEL_STORE = {
    "P426": "aircraft registration",
    "P1110": "attendance",
    "P726": "candidate",
    "P488": "chairperson",
    "P625": "coordinate location",
    "P2630": "cost of damage",
    "P17": "country",
    "P2047": "duration",
    "P1867": "eligible voters",
    "P2257": "frequency of event",
    "P828": "has cause",
    "P31": "instance of",
    "P276": "location",
    "P131": "located in the administrative territorial entity",
    "P1356": "losses",
    "P1120": "number of deaths",
    "P1339": "number of injured",
    "P1350": "number of matches played",
    "P1446": "number of missing",
    "P1132": "number of participants",
    "P1351": "number of points/goals scored",
    "P710": "participant",
    "P1923": "participating teams",
    "P361": "part of",
    "P585": "point in time",
    "P577": "publication date",
    "P854": "reference URL",
    "P813": "retrieved",
    "P641": "sport",
    "P533": "target",
    "P1697": "total valid votes",
    "P1335": "wins",
    "P1346": "winner"
};
/*
* Counts the number of currently active requests to Wikidata,
* which were made to get missing labels.
*/
var unfinishedGatherStoreCalls = 0;

/*
* requestMissingIdLabelPairs takes an WikidataItem object as argument.
* It executes requests to the Wikidata API for the entity IDs found in the item,
* which are currently not element of the ID_LABEL_STORE.
* Side effects: 
*   - unfinishedGatherStoreCalls may be increment multiple times.
*
* @params item a WikidataItem object.
*/
function requestMissingIdLabelPairs(item) {
    var missingIds = getAllIdsFrom(item).subtract(Object.keys(ID_LABEL_STORE));
    // One API request can only handle 50 entity IDs at once.
    var c = 0;
    var ids = [];
    for (var id of missingIds) {
        ids.push(id);
        if (ids.length >= 50) {
            var request = createWikidataEntitiesRequest(ids, gatherAndStoreIdLabelPairsFromData);
            executeApiRequest(request);
            ids = [];
        }
    }
    if (ids.length > 0) {
        var request = createWikidataEntitiesRequest(ids, gatherAndStoreIdLabelPairsFromData);
        unfinishedGatherStoreCalls++;
        executeApiRequest(request);
    }
}

// TODO: doc, error handling, divide function into a gathering and a storing function
function gatherAndStoreIdLabelPairsFromData(data) {
    var ids = Object.keys(data.entities);

    for (var i = 0; i < ids.length; i++) {
        var id = ids[i];
        var entity = data.entities[id];
        var label = "empty";
        if (entity.labels.hasOwnProperty("en")) {
             label = entity.labels.en.value;
        }
        storeIdLabelPair(id, label);
    }
    unfinishedGatherStoreCalls--;
}

// TODO: doc
function storeIdLabelPair(id, label) {
    ID_LABEL_STORE[id] = label;
}

// TODO: doc
// TODO: because the API calls and storing takes some time, the function throw errors even
//  though the labels were already requested. Current solution is insufficient.
function getLabelOfId(id) {
    if (ID_LABEL_STORE.hasOwnProperty(id)) {
        return ID_LABEL_STORE[id];   
    } else if (ID_LABEL_STORE.hasOwnProperty("P"+id)) {
        return ID_LABEL_STORE["P"+id]; 
    }
    else {
        throw new NotExistingIdError(id);
    }
}

/*
* getAllIdsFrom takes a WikidataItem object and iterates over the object,
* finding all appearing entity IDs. These are returned in a set.
*
* @param item an WikidataItem object
* @return a set of entitity IDs (e.g. Q11, P325, ...)
*/
function getAllIdsFrom(item) {
    var ids = new Set();
    // Get all IDs, which occur in the item.
    ids.add(item.id);
    for (var c = 0; c < item.statements.length; c++) {
        var claims = item.statements[c].claims;
        for (var i = 0; i < claims.length; i++) {
            var claim = claims[i];
            ids.addIdsFromSnak(claim.mainsnak);
            for (var j = 0; j < claim.references.length; j++) {
                var reference = claim.references[j];
                for (var k = 0; k < reference.snaks.length; k++) {
                    ids.addIdsFromSnak(reference.snaks[k]);
                }
            }
            for (var j = 0; j < claim.qualifiers.length; j++) {
                var qualifier = claim.qualifiers[j];
                ids.addIdsFromSnak(qualifier.snak);
            }
        }
    }
    return ids;
}

// Set functions
/*
* This is a method for objects of the class Set. It takes a WikidataSnak object
* and finds all entity IDs in the object. These are added to the set from which
* the method was called. The set is returned.
*
* @param a WikidataSnak object
* @return the Set object from which the method was called.
*/
Set.prototype.addIdsFromSnak = function addIdsFromSnak(snak) {
    this.add(snak.propertyId);
    if (snak.snaktype == "value") {
        if (snak.datatype == "wikibase-item") {
            this.add("Q" + snak.datavalue.numericid);
        } else if (snak.datatype == "wikibase-property") {
            this.add("P" + snak.datavalue.numericid);
        }
    }
    return this;
};

/*
* subtract is method of Set class, which removes the elements in the current set,
* if they appear in the argument set s.
*
* @param s a Set object, which contains the elements to be removed from the current set.
*/
Set.prototype.subtract = function subtract(s) {
    for (var e of s) {
        if (this.has(e)) {
            this.delete(e);
        }
    }
    return this;
};

// TODO: doc
function NotExistingIdError(id) {
    this.name = "NotExisitingIdError";
    this.message = "there is no ID " + id + " stored in ID_LABEL_STORE";
    this.id = id;
}
NotExistingIdError.prototype = Error.prototype;