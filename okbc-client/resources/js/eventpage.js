function init() {
    // Restore global variables from the localStorage.
    currentItem = JSON.parse(localStorage["currentitem"]);
    ID_LABEL_STORE = JSON.parse(localStorage["idlabelstore"]);
    console.log(currentItem);
    console.log(ID_LABEL_STORE);
    // Print the item.
    printItem(currentItem, "item");
}