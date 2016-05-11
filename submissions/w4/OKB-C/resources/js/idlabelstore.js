// TODO: doc
var ID_LABEL_STORE = {};

// TODO: doc, rename
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
    }
    throw "there is no id " + id + " stored in ID_LABEL_STORE";
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
    for (var i = 0; i < item.claims.length; i++) {
        var claim = item.claims[i];
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
            this.add("Q" + snak.datavalue.value["numeric-id"]);
        } else if (snak.datatype == "wikibase-property") {
            this.add("P" + snak.datavalue.value["numeric-id"]);
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