var currentEvent;

// TODO: doc, improve timeout, save id label store in localStorage
function init() {
    initCurationForm();
    if (!sessionStorage["itemdata"]) {
        window.location = "selection.html";
        return;
    }
    var data = JSON.parse(sessionStorage["itemdata"]);
    var item;

    try {
        item = mapJsonToWikidataItem(data);
        currentEvent = item;
    } catch(err) {
        console.log(err);
        window.alert(err);
        return;
    }
    setTimeout(function () {printItem(currentEvent, "item");}, 2000);
}

