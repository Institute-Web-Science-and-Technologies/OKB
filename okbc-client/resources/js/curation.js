// TODO: doc
function CuratedClaim() {
    this.itemid;
    this.propertyid;
    this.datatype;
    this.snaktype;
    this.datavalue;
    this.references = [];
    this.qualifiers = [];
}

// TODO: doc
function CuratedReference() {
    this.snaks = [];
}

// TODO: doc
function CuratedSnak() {
    this.propertyid;
    this.datatype;
    this.snaktype;
    this.datavalue;
}