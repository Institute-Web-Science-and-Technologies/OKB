package WikiDataItem;

import java.util.List;


public class Aliases {

    private int aid;
    private String alias;
    private List<LangVal> langval;

    public Aliases(String alias, List<LangVal> langval) {
        this.alias = alias;
        this.langval = langval;
    }

    public Aliases() {
    }

    public List<LangVal> getLangval() {
        return langval;
    }

    public void setLangval(List<LangVal> langval) {
        this.langval = langval;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
