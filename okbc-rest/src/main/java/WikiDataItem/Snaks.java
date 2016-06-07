package WikiDataItem;


public class Snaks {

    private int sid;
    private String snak;
    private String snaktype;
    private String property;

    public Snaks() {
    }

    public Snaks(String snak) {
        this.snak = snak;
    }

    public String toString() {
        return "Snaks: {" + snak + ", " + snaktype + ", " + property + "} ";

    }

    public String getSnak() {
        return snak;
    }

    public void setSnak(String snak) {
        this.snak = snak;
    }

    public class Snak {
        private String snaktype;
        private String property;
        private Datavalue datavalue;

        public Snak() {
        }

        public Snak(String snaktype, String property, Datavalue datavalue) {
            this.snaktype = snaktype;
            this.property = property;
            this.datavalue = datavalue;
        }

        public String getSnaktype() {
            return snaktype;
        }

        public void setSnaktype(String snaktype) {
            this.snaktype = snaktype;
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public Datavalue getDatavalue() {
            return datavalue;
        }

        public void setDatavalue(Datavalue datavalue) {
            this.datavalue = datavalue;
        }

        public class Datavalue {
            private String datavalue;
            private Value value;

            public Datavalue() {
            }

            public Datavalue(String datavalue, Value value) {
                this.datavalue = datavalue;
                this.value = value;
            }

            public String getDatavalue() {
                return datavalue;
            }

            public void setDatavalue(String datavalue) {
                this.datavalue = datavalue;
            }

            public Value getValue() {
                return value;
            }

            public void setValue(Value value) {
                this.value = value;
            }

            public class Value {
                private String entity_type;
                private String numeric_id;
                private String type;
                private String datatype;

                public Value() {
                }

                public Value(String entity_type, String numeric_id, String type, String datatype) {
                    this.entity_type = entity_type;
                    this.numeric_id = numeric_id;
                    this.type = type;
                    this.datatype = datatype;
                }

                public String getEntity_type() {
                    return entity_type;
                }

                public void setEntity_type(String entity_type) {
                    this.entity_type = entity_type;
                }

                public String getNumeric_id() {
                    return numeric_id;
                }

                public void setNumeric_id(String numeric_id) {
                    this.numeric_id = numeric_id;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getDatatype() {
                    return datatype;
                }

                public void setDatatype(String datatype) {
                    this.datatype = datatype;
                }
            }
        }
    }
}