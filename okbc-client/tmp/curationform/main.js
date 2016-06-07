// TODO: renaming of functions

var TEMPLATE = {
    CREATE_CLAIM: undefined,
    CHOOSE_PROPERTY: undefined,
    ENTER_VALUE: undefined,
    MULTI_CLAIM_TYPE: undefined
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
        'userRating': undefined,
        'publicationDate': undefined,
        'retrievalDate': undefined,
        'author': undefined
        // custom key-value pairs can be added by the user.
    }
};

// STUB
var activeItem = {};

function init() {
    CURATION_FORM = document.getElementById('curationForm');
    parseTemplatesOfCurationHtml();
    loadInitialCurationForm();
}

function parseTemplatesOfCurationHtml() {
    TEMPLATE.CREATE_CLAIM = document.getElementById('createClaimTemplate').innerHTML;
    Mustache.parse(TEMPLATE.CREATE_CLAIM);

    TEMPLATE.CHOOSE_PROPERTY = document.getElementById('choosePropertyTemplate').innerHTML;
    Mustache.parse(TEMPLATE.CHOOSE_PROPERTY);

    TEMPLATE.ENTER_VALUE = document.getElementById('enterValueTemplate').innerHTML;
    Mustache.parse(TEMPLATE.ENTER_VALUE);

    TEMPLATE.MULTI_CLAIM_TYPE = document.getElementById('multiClaimTypeTemplate').innerHTML;
    Mustache.parse(TEMPLATE.MULTI_CLAIM_TYPE);
}

function loadInitialCurationForm() {
    // Reset curatingData.
    curatingData = {};
    var args = {'properties' : getSuggestedProperties("someItemId")};
    CURATION_FORM.innerHTML = Mustache.render(TEMPLATE.CREATE_CLAIM, args);
}

function startClaimCreation() {
    var choices = document.getElementById('suggestedProperties').getElementsByTagName('input');
    var chosenProperty;
    for (var i = 0; i < choices.length; i++) {
        if (choices[i].checked) {
            chosenProperty = choices[i].value;
            break;
        }
    }
    if (chosenProperty == 'null') {
        loadPropertyForm();
    } else {
        loadValueForm(chosenProperty);
    }
}

function resetCurationForm() {
    //if (window.confirm('this will reset your progress')) {
        loadInitialCurationForm();
    //}
}

function loadPropertyForm() {
    var args = {};
    CURATION_FORM.innerHTML = Mustache.render(TEMPLATE.CHOOSE_PROPERTY, args);
}

function loadValueFormFromPropertyForm() {
    var property = CURATION_FORM.getElementsByTagName('input')[0].value;
    if (isValidProperty(property)) {
        loadValueForm(property);
    } else {
        property.value = '';
        window.alert('invalid property. please try again');
    }
}

function loadValueForm(property) {
    // Add the name of the property of the claim to the data.
    curatingData.propertyName = property;

    var type = getInputTypeForProperty(property);
    var args = {'propertyName': property, 'type': type};
    CURATION_FORM.innerHTML = Mustache.render(TEMPLATE.ENTER_VALUE, args);
}

function continueFromValueForm() {
    // Add the value of the claim to the data.
    curatingData.value = document.getElementById('value').value;

    if (isFirstClaimOfProperty(curatingData.propertyName, activeItem)) {
        var claims = getClaimsWithProperty(curatingData.propertyName, activeItem);
        var args = {'propertyName': curatingData.propertyName, 'value': curatingData.value, 'claims': claims};
        CURATION_FORM.innerHTML = Mustache.render(TEMPLATE.MULTI_CLAIM_TYPE, args);
    } else {
        // TODO
    }
}

// STUBS
function getSuggestedProperties(itemId) {
    return ['number of deaths', 'point in time', 'instance of', 'number of injured'];
}

function getInputTypeForProperty(property) {
    return "text";
}

function isValidProperty(property) {
    return property != '';
}

function isFirstClaimOfProperty(property, item) {
    return true;
}

function getClaimsWithProperty(property, item) {
    return [property + ': something', property + ': bread'];
}