package WikiDataItem;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;


@DatabaseTable(tableName = "Aliases")
public class Aliases {
    @DatabaseField(generatedId = true)
    private int pid;

    @DatabaseField
    private String alias;

    @DatabaseField
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
