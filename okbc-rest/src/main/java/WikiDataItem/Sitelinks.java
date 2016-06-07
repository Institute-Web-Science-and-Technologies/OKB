package WikiDataItem;

import java.util.List;

public class Sitelinks {

    private int slid;
    private String sitelink;
    private String site;
    private String title;
    private List<String> badges;

    public Sitelinks(String sitelink, String site, String title, List<String> badges) {
        this.sitelink = sitelink;
        this.site = site;
        this.title = title;
        this.badges = badges;
    }

    public Sitelinks() {
    }

    public String toString() {
        String a = "SiteLinks: {" + sitelink + ", " + site + ", " + title + ", badges: {";
        for (String i : badges)
            a += i + " ";
        a += "}}";
        return a;
    }

    public String getSitelink() {
        return sitelink;
    }

    public void setSitelink(String sitelink) {
        this.sitelink = sitelink;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getBadges() {
        return badges;
    }

    public void setBadges(List<String> badges) {
        this.badges = badges;
    }
}
