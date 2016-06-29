/*
* curationform.js contains loading and processing of the curation form.
* mustache.js templating is used for loading the multiple steps.
* Resulting data is hold in the object "curatingData".
* The data is deleted, if somehow the function "resetCurationForm" is called.
*/

/*
* TEMPLATE is a container for all mustache.js templates used in the curation.
* Each attribute of TEMPLATE is a string containing a mustache-template.
*/
var TEMPLATE = {
    START_CURATION: undefined,
    CHOOSE_PROPERTY: undefined,
    ENTER_VALUE: undefined,
    CHOOSE_CLAIM_TYPE: undefined,
    QUALIFIERS_TO_ADD: undefined,
    CREATE_SOURCE_INFORMATION: undefined,
    CREATE_QUALIFIER: undefined,
    SHOW_OVERVIEW: undefined,
    ADDITIONAL_SUGGESTED_PROPERTIES: undefined
};

/*
* curatingData is a container for the data, which is created in the curation process.
*/
var curatingData = {
    'eventId' : undefined,
    'user': undefined,
    'propertyName': undefined,
    'value': undefined,
    'multiClaimType': undefined,
    'qualifiers': [],
    'source': {
        'url': undefined,
        'reliabilityRating': undefined,
        'neutralityRating': undefined,
        'publicationDate': undefined,
        'retrievalDate': undefined,
        'authors': []

    }
};

function initCurationForm() {
    loadCurationTemplates();
    loadPropertyOptionDatalist();
    loadStartCurationForm();
}

/*
* loadCurationTemplates intitializes the TEMPLATE container.
* Each template is parsed to achieve better performance.
*/
function loadCurationTemplates() {
    TEMPLATE.START_CURATION = $('#startCurationTemplate').html();
    Mustache.parse(TEMPLATE.START_CURATION);

    TEMPLATE.CHOOSE_PROPERTY = $('#choosePropertyTemplate').html();
    Mustache.parse(TEMPLATE.CHOOSE_PROPERTY);

    TEMPLATE.ENTER_VALUE = $('#enterValueTemplate').html();
    Mustache.parse(TEMPLATE.ENTER_VALUE);

    TEMPLATE.CHOOSE_CLAIM_TYPE = $('#chooseClaimTypeTemplate').html();
    Mustache.parse(TEMPLATE.CHOOSE_CLAIM_TYPE);
    
    TEMPLATE.QUALIFIERS_TO_ADD = $('#qualifiersToAddTemplate').html();
    Mustache.parse(TEMPLATE.QUALIFIERS_TO_ADD);
    
    TEMPLATE.CREATE_SOURCE_INFORMATION = $('#createSourceInformationTemplate').html();
    Mustache.parse(TEMPLATE.CREATE_SOURCE_INFORMATION);

    TEMPLATE.CREATE_QUALIFIER = $('#createQualifierTemplate').html();
    Mustache.parse(TEMPLATE.CREATE_QUALIFIER);

    TEMPLATE.SHOW_OVERVIEW = $('#showOverviewTemplate').html();
    Mustache.parse(TEMPLATE.SHOW_OVERVIEW);

    TEMPLATE.ADDITIONAL_SUGGESTED_PROPERTIES = $('#additionalSuggestedPropertiesTemplate').html();
    Mustache.parse(TEMPLATE.ADDITIONAL_SUGGESTED_PROPERTIES);

    TEMPLATE.PROPERTY_INPUT_OPTIONS = $('#propertyInputOptionsTemplate').html();
    Mustache.parse(TEMPLATE.PROPERTY_INPUT_OPTIONS);
}

/*
* loadPropertyOptionDatalist fills the datalist with ID 'propertyOptions' 
* with all properties defined in PROPERTIES.
*/
function loadPropertyOptionDatalist() {
    var args = {'options': Object.keys(PROPERTIES)};
    $('#propertyOptions').html(Mustache.render(TEMPLATE.PROPERTY_INPUT_OPTIONS, args));
}

/*
* loadStartCurationForm loads the initial form of the curation process.
*/
function loadStartCurationForm() {
    var args = {'properties' : getSuggestedProperties(currentEvent.id)};
    $('#curationForm').html(Mustache.render(TEMPLATE.START_CURATION, args));
    loadSuggestedProperties();
}

/*
* resetCurationForm resets the progress of curation and loads the intial curation form.
*/
function resetCurationForm() {
    // Reset curatingData.
    curatingData = {
        'eventId' : undefined,
        'user': undefined,
        'propertyName': undefined,
        'value': undefined,
        'multiClaimType': undefined,
        'qualifiers': [],
        'source': {
            'url': undefined,
            'reliabilityRating': undefined,
            'neutralityRating': undefined,
            'publicationDate': undefined,
            'retrievalDate': undefined,
            'authors': []
        }
    };
    loadStartCurationForm();
}

/*
* processStartCurationForm should only be called, if the START_CURATION form is active.
* It loads either the CHOOSE_PROPERTY form or the ENTER_VALUE form depending on the user decision.
*/
function processStartCurationForm() {
    var chosenProperty = $('input[name=property]:checked').val();
    if (chosenProperty == 'null') {
        loadChoosePropertyForm();
    } else {
        loadEnterValueForm(chosenProperty);
    }
}

// TODO: doc
function loadChoosePropertyForm() {
    var args = {};
    $('#curationForm').html(Mustache.render(TEMPLATE.CHOOSE_PROPERTY, args));
}

/*
* processChoosePropertyForm checks, whether the user entered property is valid.
* If it is not valid, the user is alerted of this fact and gets another try at entering a property.
* If it is valid, the ENTER_VALUE form is loaded.
* This function can only be called if the CHOOSE_PROPERTY form is active.
*/
function processChoosePropertyForm() {
    var property = $('#property');
    if (isValidProperty(property.val())) {
        loadEnterValueForm(property.val());
    } else {
        property.val('');
        window.alert('invalid property. please try again');
    }
}

/*
* loadEnterValueForm stores the given property label in curatingData
* and loads the ENTER_VALUE form.
* @param property a string containing the label of a Wikidata property.
*/
function loadEnterValueForm(property) {
    // Add the name of the property of the claim to the data.
    curatingData.propertyName = property;

    var type = getInputTypeForProperty(property);
    var args = {'propertyName': property, 'type': type};
    $('#curationForm').html(Mustache.render(TEMPLATE.ENTER_VALUE, args));
}

/*
* processEnterValueForm validates the value input of the user.
* If it is not valid, the user is notified of this and gets another try.
* Otherwise the depending on the number of claims with the same property,
* the QUALIFIERS_TO_ADD or the CHOOSE_CLAIM_TYPE form are loaded.
* This function should only be called, if the ENTER_VALUE form is active.
*/
function processEnterValueForm() {
    var valueInput = $('#value');
    // Check if value is valid. If not alert the user and return.
    if (!isValidValue(valueInput.val(), curatingData.propertyName)) {
        window.alert('invalid value. please try again.');
        valueInput.val('');
        return;
    }

    // Add the value of the claim to the data.
    curatingData.value = valueInput.val();
    
    if (isFirstClaimOfProperty(curatingData.propertyName, currentEvent)) {
        loadQualifiersToAddForm();       
    } else {
        loadChooseClaimTypeForm();
    }
}

// TODO: doc
function loadChooseClaimTypeForm() {
    var claims = getClaimsWithProperty(curatingData.propertyName, currentEvent);
    var args = {
        'propertyName': curatingData.propertyName, 
        'value': curatingData.value, 
        'claims': claims
    };
    $('#curationForm').html(Mustache.render(TEMPLATE.CHOOSE_CLAIM_TYPE, args));
}

// TODO: doc
function processChooseClaimTypeForm() {
    curatingData.multiClaimType = $('input[name=relation]:checked').val();
    loadQualifiersToAddForm();
}

// TODO: doc
function loadQualifiersToAddForm() {
    var args = {
        'propertyName': curatingData.propertyName, 
        'value': curatingData.value,
        'claimType': curatingData.multiClaimType,
        'qualifiers': curatingData.qualifiers
    };
    $('#curationForm').html(Mustache.render(TEMPLATE.QUALIFIERS_TO_ADD, args));
}

// TODO: doc
function processQualifiersToAddForm(hasToAdd) {
    if (hasToAdd) {
        loadCreateQualifierForm();
    } else {
        loadCreateSourceInformationForm();
    }
}

// TODO: doc
function loadCreateQualifierForm() {
    var args = {
        'propertyName': curatingData.propertyName, 
        'value': curatingData.value,
        'claimType': curatingData.multiClaimType,
        'qualifiers': curatingData.qualifiers
    };
    $('#curationForm').html(Mustache.render(TEMPLATE.CREATE_QUALIFIER, args));
}

// TODO: doc
function processCreateQualifierForm(isFinalStep) {
    var propertyInput = $('#qualifierProperty');
    var property = propertyInput.val();
    if (!isValidProperty(property)) {
        propertyInput.val('');
        window.alert('invalid property. please try again');
        return;
    }
    var valueInput = $('#qualifierValue');
    var value = valueInput.val();
    if (!isValidValue(value, property)) {
        valueInput.val('');
        window.alert('entered value is not valid. please try again');
        return;
    }
    // save qualifier in curatingData
    curatingData.qualifiers.push(new PropertyValue(property, value));

    loadQualifiersToAddForm();
}

// TODO: doc
function loadCreateSourceInformationForm() {
    var args = {
        'propertyName': curatingData.propertyName, 
        'value': curatingData.value,
        'claimType': curatingData.multiClaimType,
        'qualifiers': curatingData.qualifiers
    }
    $('#curationForm').html(Mustache.render(TEMPLATE.CREATE_SOURCE_INFORMATION, args));
}

// TODO: doc
function processCreateSourceInformationForm() {
    var url = $('#url').val();
    var reliabilityRating = $('#reliabilityRating').val();
    var neutralityRating = $('#neutralityRating').val();
    var publicationDate = $('#pubdate').val();
    var retrievalDate = $('#retdate').val();
    var authors = $('#authors').val();
    // TODO: validate input.
    // TODO: transform input into normalized form.
    curatingData.source.url = url;
    curatingData.source.reliabiltyRating = normalizeRating(reliabilityRating);
    curatingData.source.neutralityRating = normalizeRating(neutralityRating);
    curatingData.source.publicationDate = normalizeDate(publicationDate);
    curatingData.source.retrievalDate = normalizeDate(retrievalDate);
    curatingData.source.authors = normalizeAuthors(authors);

    loadOverviewForm();
}

// TODO: doc
function loadOverviewForm() {
    args = {
        'propertyName': curatingData.propertyName, 
        'value': curatingData.value,
        'claimType': curatingData.multiClaimType,
        'qualifiers': curatingData.qualifiers,
        'url': curatingData.source.url,
        'pubdate': curatingData.source.publicationDate,
        'retdate': curatingData.source.retrievalDate,
        'reliability': curatingData.source.reliabiltyRating,
        'neutrality': curatingData.source.neutralityRating,
        'authors': curatingData.source.authors
    };
    $('#curationForm').html(Mustache.render(TEMPLATE.SHOW_OVERVIEW, args));
}

// TODO: doc
function processOverviewForm() {
    // Add event ID to the curating data.
    curatingData.eventId = currentEvent.id;
    // Submit curation data to server.
    $.post(CURATING_DATA_POST_URL, curatingData, function(data){console.log('POST success');});
    // TODO: add it to the current item representation etc.
    resetCurationForm();
    
}

// TODO: doc
function loadSuggestedProperties() {
    var request = createWikidataGetSuggestionsRequest(currentEvent.id, printSuggestedProperties);
    executeApiRequest(request);
}

// TODO: doc
function printSuggestedProperties(data) {
    var properties = [];
    for (var i = 0; i < data.search.length; i++) {
        // Only get acknowledged properties.
        if (isValidProperty(data.search[i].label)) {
            properties.push(data.search[i].label);
        }
        storeIdLabelPair(data.search[i].id, data.search[i].label);
    }
    var args = {'properties': properties};
    $('#additionalSuggestedProperties').html(Mustache.render(TEMPLATE.ADDITIONAL_SUGGESTED_PROPERTIES, args));
}

// Helper classes
/*
* PropertyValue is a simple data capsule for storing a property-value pair.
*/
function PropertyValue(property, value) {
    this.property = property;
    this.value = value;
}
PropertyValue.prototype.toString = function() {
    return this.property + ': ' + this.value;
};
