package WikiDataItem;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

@DatabaseTable(tableName = "Entities")
public class Entities {
    @DatabaseField
    private ID id;

    @DatabaseField
    private List<LangVal> descriptions;

    @DatabaseField
    private List<Aliases> aliases;

    @DatabaseField
    private List<Claims> claims;

    @DatabaseField
    private List<Sitelinks> sitelinks;


    public Entities() {
    }

    public Entities(ID id, List<LangVal> descriptions, List<Aliases> aliases,
                    List<Claims> claims, List<Sitelinks> sitelinks) {
        this.id = id;
        this.descriptions = descriptions;
        this.aliases = aliases;
        this.claims = claims;
        this.sitelinks = sitelinks;
    }

    public String toString() {
        String a = "Entities: {" + id+"}";
        a += ", descriptions: {";
        for (LangVal i : descriptions)
            a += i.getShortcut() + ", " + i.getLanguage() + ", " + i.getValue() + ", ";
        a += "}, Aliases: ";
        for (Aliases i : getAliases()) {
            a += i.getAlias() + ": ";
            for (LangVal j : i.getLangval())
                a += "{" + j.getShortcut() + ", " + j.getLanguage() + ", " + j.getValue() + "} ";
        }
        return a;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public List<LangVal> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<LangVal> descriptions) {
        this.descriptions = descriptions;
    }

    public List<Aliases> getAliases() {
        return aliases;
    }

    public void setAliases(List<Aliases> aliases) {
        this.aliases = aliases;
    }

    public List<Claims> getClaims() {
        return claims;
    }

    public void setClaims(List<Claims> claims) {
        this.claims = claims;
    }

    public List<Sitelinks> getSitelinks() {
        return sitelinks;
    }

    public void setSitelinks(List<Sitelinks> sitelinks) {
        this.sitelinks = sitelinks;
    }
}
