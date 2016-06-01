package WikiDataItem;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "LangVal")
public class LangVal {
    @DatabaseField(generatedId = true)
    private int pid;

    @DatabaseField
    private String shortcut;

    @DatabaseField
    private String language;

    @DatabaseField
    private String value;

    public LangVal(String shortcut, String language, String value) {
        this.shortcut = shortcut;
        this.language = language;
        this.value = value;
    }

    public LangVal() {
    }

    public String getLanguage() {

        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
