/*
* printItem takes a WikidataItem and the ID of an HTML tag.
* It creates an HTML representation of the item and inserts it
* into tag, more specifically it is assigned to the innerHTML attribute
* of the tag.
*
* @param item an WikidataItem object.
* @param elementId an ID of an HTML tag, which has the attribute innerHTML.
*/
function printItem(item, elementId) {
    console.log("print item in " + elementId);
    console.log(item);
    // Add function to replace propertyIds with labels.
    item.labelIt = function() {
        return function(text, render) {
            var pid = render(text);
            var label;
            try {
                label = getLabelOfId(pid);
            } catch (err) {
                label = pid;
            }
            return label;
        }
    };
    // Generate HTML string for pretty printing of item.
    var template = document.getElementById("itemTemplate").innerHTML;
    var output = Mustache.render(template, item);
    // Write the itemString into the item-div.
    var outputTag = document.getElementById(elementId);
    outputTag.innerHTML = output;
}