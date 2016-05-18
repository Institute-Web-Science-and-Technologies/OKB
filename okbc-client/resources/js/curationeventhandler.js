// TODO: doc
// This values are only increased but never decreased, so collisions are prevented.
var numberOfQualifierFields = 0;
var numberOfReferenceFields = 0;

// TODO: doc, renaming
function handleCreateNewClaimClick(form) {
    if ((typeof currentItem) == "undefined") {
        window.alert("An Wikidata event has to be active for curating claims.");
        return;
    }
    var claim = new CuratedClaim();
    claim.itemid = currentItem.id;
    claim.propertyid = form.property.value;
    if (form.value.value == "") {
        claim.snaktype = "missingvalue";
    } else {
        claim.snaktype = "value";
    }
    claim.datavalue = form.value.value;
    
    var qualTest = new RegExp("qualifierProperty[0-9]+");
    var refTest = new RegExp("refUrlValue[0-9]+");

    for (var i = 0; i < form.length; i++) {
        if ((form[i].type == "text") || (form[i].type == "url") || (form[i].type == "date")) {
            if (qualTest.test(form[i].name)) {
                var qualifier = new CuratedSnak();
                qualifier.propertyid = form[i].value;
                i++;
                if (form[i].value == "") {
                    qualifier.snaktype = "missingvalue";
                } else {
                    qualifier.snaktype = "value";
                }
                qualifier.datavalue = form[i].value;
                claim.qualifiers.push(qualifier);
            } else if (refTest.test(form[i].name)) {
                var reference = new CuratedReference();
                var refUrlSnak = new CuratedSnak();
                refUrlSnak.propertyid = "P854";
                refUrlSnak.snaktype = "value";
                refUrlSnak.datavalue = form[i].value;
                refUrlSnak.datatype = "url";
                reference.snaks.push(refUrlSnak);
                i++;
                var retrievedSnak = new CuratedSnak();
                retrievedSnak.propertyid = "P813";
                retrievedSnak.snaktype = "value";
                retrievedSnak.value = form[i].value;
                retrievedSnak.datatype = "time";
                reference.snaks.push(retrievedSnak);
                i++;
                var pubDateSnak = new CuratedSnak();
                pubDateSnak.propertyid = "P8577";
                pubDateSnak.snaktype = "value";
                pubDateSnak.datavalue = form[i].value;
                pubDateSnak.datatype = "time";
                reference.snaks.push(pubDateSnak);
                claim.references.push(reference);
            }
        }
    }
    console.log(JSON.stringify(claim));
}

// TODO: doc, subdivide, use templates?
function addQualifierField(form) {
    numberOfQualifierFields++;

    console.log("Create qualifier field no " + numberOfQualifierFields);

    var qualifierField = document.createElement("div");
    qualifierField.id = "qualifierField" + numberOfQualifierFields;
    qualifierField.className = "qualifierField";
    
    var property = document.createElement("input");
    property.type = "text";
    property.name = "qualifierProperty" + numberOfQualifierFields;
    property.placeholder = "Property ID";
    qualifierField.appendChild(property);

    var value= document.createElement("input");
    value.type = "text";
    value.name = "qualifierValue" + numberOfQualifierFields;
    value.placeholder = "Value";
    qualifierField.appendChild(value);

    var remove = document.createElement("input");
    remove.type = "button";
    remove.value = "Remove";
    remove.setAttribute("onclick", "removeQualifierField("+ numberOfQualifierFields + ")");
    qualifierField.appendChild(remove);

    var qualifierDiv = document.getElementById("new_qualifiers");
    qualifierDiv.appendChild(qualifierField);
}

function removeQualifierField(qualifierFieldId) {
    console.log("Remove qualifier field no " + qualifierFieldId);
    var newQualifiers = document.getElementById("new_qualifiers");
    var qualifierField = document.getElementById("qualifierField" + qualifierFieldId);
    newQualifiers.removeChild(qualifierField);
}

// TODO: doc, subdivide, use templates?
function addReferenceField(form) {
    numberOfReferenceFields++;

    console.log("Create reference field no " + numberOfReferenceFields);

    var referenceField = document.createElement("div");
    referenceField.id = "referenceField" + numberOfReferenceFields;
    referenceField.className = "referenceField";

    var refUrlProperty = document.createElement("label");
    refUrlProperty.innerHTML = "reference URL (P854): ";
    referenceField.appendChild(refUrlProperty);

    var refUrlValue = document.createElement("input");
    refUrlValue.type = "url";
    refUrlValue.name = "refUrlValue" + numberOfReferenceFields;
    referenceField.appendChild(refUrlValue);

    var br1 = document.createElement("br");
    referenceField.appendChild(br1);

    var retrievedProperty = document.createElement("label");
    retrievedProperty.innerHTML = "retrieved (P813): ";
    referenceField.appendChild(retrievedProperty);

    var retrievedValue = document.createElement("input");
    retrievedValue.type = "date";
    retrievedValue.name = "retrievedValue" + numberOfReferenceFields;
    referenceField.appendChild(retrievedValue);

    var br2 = document.createElement("br");
    referenceField.appendChild(br2);

    var pubDateProperty = document.createElement("label");
    pubDateProperty.innerHTML = "publication date (P577): ";
    referenceField.appendChild(pubDateProperty);

    var pubDateValue = document.createElement("input");
    pubDateValue.type = "date";
    pubDateValue.name = "pubDateValue" + numberOfReferenceFields;
    referenceField.appendChild(pubDateValue);

    var remove = document.createElement("input");
    remove.type = "button";
    remove.value = "Remove";
    remove.setAttribute("onclick", "removeReferenceField("+ numberOfReferenceFields + ")");
    referenceField.appendChild(remove);

    var referenceDiv = document.getElementById("new_references");
    referenceDiv.appendChild(referenceField);
}

// TODO: doc
function removeReferenceField(referenceFieldId) {
    console.log("Remove reference field no " + referenceFieldId);
    var newReferences = document.getElementById("new_references");
    var referenceField = document.getElementById("referenceField" + referenceFieldId);
    newReferences.removeChild(referenceField);
}