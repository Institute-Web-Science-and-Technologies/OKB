package rankingProvenance.rankProv;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class References {

  Integer id;

  String url;
  String publicationDate;
  String retrievalDate;
  String authors;
  float  trustRating;
  String articleType;
  String title;
  float neutralityRating;
  
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }

  
  public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public String getPublicationDate() {
    return publicationDate;
  }
  public void setPublicationDate(String publicationDate) {
    this.publicationDate = publicationDate;
  }
  public String getRetrievalDate() {
    return retrievalDate;
  }
  public void setRetrievalDate(String retrievalDate) {
    this.retrievalDate = retrievalDate;
  }
  public String getAuthors() {
    return authors;
  }
  public void setAuthors(String authors) {
    this.authors = authors;
  }
  public float getTrustRating() {
    return trustRating;
  }
  public void setTrustRating(float trustRating) {
    this.trustRating = trustRating;
  }
  public String getArticleType() {
    return articleType;
  }
  public void setArticleType(String articleType) {
    this.articleType = articleType;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public float getNeutralityRating() {
    return neutralityRating;
  }
  public void setNeutralityRating(float neutralityRating) {
    this.neutralityRating = neutralityRating;
  }
  
  public boolean save() throws SQLException
  {
  
    int i =  MySql.getDbCon().insert("INSERT INTO `references` (`id`,`url`, `publicationDate`, `retreivalDate`, `authors`, `trustRating`, `articleType`, `title`, `neutralityRating`) VALUES ("+this.id+",'"+this.url+"','"+this.publicationDate+"','"+this.retrievalDate+"','"+this.authors+"',"+this.trustRating+",'"+this.articleType+"','"+this.title+"',"+this.neutralityRating+") ");
    if(i>0)
      return true;
    else
      return false;
  }
  
  public List<Map<String,String>> getReference(int id) throws SQLException
  {
    HashMap<String, String> hm = new HashMap<String, String>();
    ResultSet rs = MySql.getDbCon().query("Select * from `references` WHERE id="+id);
   
    ResultSetMetaData rsmd = rs.getMetaData();
    List<String> columns = new ArrayList<String>(rsmd.getColumnCount());
    for(int i = 1; i <= rsmd.getColumnCount(); i++){
        columns.add(rsmd.getColumnName(i));
    }
    List<Map<String,String>> data = new ArrayList<Map<String,String>>();
    while(rs.next()){                
        Map<String,String> row = new HashMap<String, String>(columns.size());
        for(String col : columns) {
            row.put(col, rs.getString(col));
        }
        data.add(row);
    }
    return data;
    
  }
  
}
