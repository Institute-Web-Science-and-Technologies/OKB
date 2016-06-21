/*
* curationform.js contains loading and processing of the curation form.
* mustache.js templating is used for loading the multiple steps.
* Resulting data is hold in the object "curatingData".
* The data is deleted, if somehow the function "resetCurationForm" is called.
*/

// TODO: renaming of functions

var TEMPLATE = {
    START_CURATION: undefined,
    CHOOSE_PROPERTY: undefined,
    ENTER_VALUE: undefined,
    CHOOSE_CLAIM_TYPE: undefined,
    QUALIFIERS_TO_ADD: undefined,
    CREATE_SOURCE_INFORMATION: undefined,
    CREATE_QUALIFIER: undefined,
    SHOW_OVERVIEW: undefined
};

var CURATION_FORM;

// holds the data, which is created while curating
var curatingData = {
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

// STUB
var activeItem = {};

function initCurationForm() {
    CURATION_FORM = document.getElementById('curationForm');
    loadCurationTemplates();
    loadStartCurationForm();
}

function loadCurationTemplates() {
    TEMPLATE.START_CURATION = 
        document.getElementById('startCurationTemplate').innerHTML;
    Mustache.parse(TEMPLATE.START_CURATION);

    TEMPLATE.CHOOSE_PROPERTY = 
        document.getElementById('choosePropertyTemplate').innerHTML;
    Mustache.parse(TEMPLATE.CHOOSE_PROPERTY);

    TEMPLATE.ENTER_VALUE = 
        document.getElementById('enterValueTemplate').innerHTML;
    Mustache.parse(TEMPLATE.ENTER_VALUE);

    TEMPLATE.CHOOSE_CLAIM_TYPE = 
        document.getElementById('chooseClaimTypeTemplate').innerHTML;
    Mustache.parse(TEMPLATE.CHOOSE_CLAIM_TYPE);
    
    TEMPLATE.QUALIFIERS_TO_ADD = 
        document.getElementById('qualifiersToAddTemplate').innerHTML;
    Mustache.parse(TEMPLATE.QUALIFIERS_TO_ADD);
    
    TEMPLATE.CREATE_SOURCE_INFORMATION =
        document.getElementById('createSourceInformationTemplate').innerHTML;
    Mustache.parse(TEMPLATE.CREATE_SOURCE_INFORMATION);

    TEMPLATE.CREATE_QUALIFIER =
        document.getElementById('createQualifierTemplate').innerHTML;
    Mustache.parse(TEMPLATE.CREATE_QUALIFIER);

    TEMPLATE.SHOW_OVERVIEW =
        document.getElementById('showOverviewTemplate').innerHTML;
    Mustache.parse(TEMPLATE.SHOW_OVERVIEW);
}

function loadStartCurationForm() {
    var args = {'properties' : getSuggestedProperties("someItemId")};
    CURATION_FORM.innerHTML = Mustache.render(TEMPLATE.START_CURATION, args);
}

function resetCurationForm() {
    if (window.confirm('this will reset your progress')) {
        // Reset curatingData.
        curatingData = {
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
}

function processStartCurationForm() {
    var choices = document.getElementById('suggestedProperties').getElementsByTagName('input');
    var chosenProperty;
    for (var i = 0; i < choices.length; i++) {
        if (choices[i].checked) {
            chosenProperty = choices[i].value;
            break;
        }
    }
    if (chosenProperty == 'null') {
        loadChoosePropertyForm();
    } else {
        loadEnterValueForm(chosenProperty);
    }
}

function loadChoosePropertyForm() {
    var args = {};
    CURATION_FORM.innerHTML = Mustache.render(TEMPLATE.CHOOSE_PROPERTY, args);
}

function processChoosePropertyForm() {
    var property = document.getElementById('curationForm').getElementsByTagName('input')[0].value;
    if (isValidProperty(property)) {
        loadEnterValueForm(property);
    } else {
        property.value = '';
        window.alert('invalid property. please try again');
    }
}

function loadEnterValueForm(property) {
    // Add the name of the property of the claim to the data.
    curatingData.propertyName = property;

    var type = getInputTypeForProperty(property);
    var args = {'propertyName': property, 'type': type};
    CURATION_FORM.innerHTML = Mustache.render(TEMPLATE.ENTER_VALUE, args);
}

function processEnterValueForm() {
    // Add the value of the claim to the data.
    curatingData.value = document.getElementById('value').value;

    if (isFirstClaimOfProperty(curatingData.propertyName, activeItem)) {
        loadQualifiersToAddForm();       
    } else {
        loadChooseClaimTypeForm();
    }
}

function loadChooseClaimTypeForm() {
    var claims = getClaimsWithProperty(curatingData.propertyName, activeItem);
    var args = {
        'propertyName': curatingData.propertyName, 
        'value': curatingData.value, 
        'claims': claims
    };
    CURATION_FORM.innerHTML = Mustache.render(TEMPLATE.CHOOSE_CLAIM_TYPE, args);
}

function processChooseClaimTypeForm() {
    var choices = document.getElementById('curationForm').getElementsByTagName('input');
    for (var i = 0; i < choices.length; i++) {
        if (choices[i].checked){
            curatingData.multiClaimType = choices[i].value;
            break;
        }
    }
    loadQualifiersToAddForm();
}

function loadQualifiersToAddForm() {
    var args = {
        'propertyName': curatingData.propertyName, 
        'value': curatingData.value,
        'claimType': curatingData.multiClaimType,
        'qualifiers': curatingData.qualifiers
    };
    CURATION_FORM.innerHTML = Mustache.render(TEMPLATE.QUALIFIERS_TO_ADD, args);
}

function processQualifiersToAddForm(hasToAdd) {
    if (hasToAdd) {
        loadCreateQualifierForm();
    } else {
        loadCreateSourceInformationForm();
    }
}

function loadCreateQualifierForm() {
    var args = {
        'propertyName': curatingData.propertyName, 
        'value': curatingData.value,
        'claimType': curatingData.multiClaimType,
        'qualifiers': curatingData.qualifiers
    };
    CURATION_FORM.innerHTML =
        Mustache.render(TEMPLATE.CREATE_QUALIFIER, args);
}

function processCreateQualifierForm(isFinalStep) {
    var propertyInput = document.getElementById('qualifierProperty');
    var property = propertyInput.value;
    if (!isValidProperty(property)) {
        propertyInput.value = '';
        window.alert('invalid property. please try again');
        return;
    }
    var valueInput = document.getElementById('qualifierValue');
    var value = valueInput.value;
    if (!isValidValue(value, property)) {
        valueInput.value = '';
        window.alert('entered value is not valid. please try again');
        return;
    }
    // save qualifier in curatingData
    curatingData.qualifiers.push(new PropertyValue(property, value));

    loadQualifiersToAddForm();
}

function loadCreateSourceInformationForm() {
    var args = {
        'propertyName': curatingData.propertyName, 
        'value': curatingData.value,
        'claimType': curatingData.multiClaimType,
        'qualifiers': curatingData.qualifiers
    }
    CURATION_FORM.innerHTML = 
        Mustache.render(TEMPLATE.CREATE_SOURCE_INFORMATION, args);
}

function processCreateSourceInformationForm() {
    var url = document.getElementById('url').value;
    var reliabilityRating = document.getElementById('reliabilityRating').value;
    var neutralityRating = document.getElementById('neutralityRating').value;
    var publicationDate = document.getElementById('pubdate').value;
    var retrievalDate = document.getElementById('retdate').value;
    var authors = document.getElementById('authors').value;
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
    CURATION_FORM.innerHTML = Mustache.render(TEMPLATE.SHOW_OVERVIEW, args);
}

function processOverviewForm() {
    // TODO: submit curation data to server.
    // add it to the current item representation etc.
    // reset curation form
}

// Make the page a bit more dynamic
function updateReliabilityRating(value) {
    document.getElementById('reliabilityValue').innerHTML = value;
}

function updateNeutralityRating(value) {
    document.getElementById('neutralityValue').innerHTML = value;
}

// Helper classes
function PropertyValue(property, value) {
    this.property = property;
    this.value = value;
}
PropertyValue.prototype.toString = function() {
    return this.property + ': ' + this.value;
};
