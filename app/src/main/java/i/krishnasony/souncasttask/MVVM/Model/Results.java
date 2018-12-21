package i.krishnasony.souncasttask.MVVM.Model;
/**
 * Awesome Pojo Generator
 * */
public class Results{
  private String createdAt;
  private String thumbnail;
  private String link;
  private Thumbnail_file thumbnail_file;
  private  String title;
  private Music_file music_file;
  private String objectId;
  private String updatedAt;
  public void setCreatedAt(String createdAt){
   this.createdAt=createdAt;
  }
  public String getCreatedAt(){
   return createdAt;
  }
  public void setThumbnail(String thumbnail){
   this.thumbnail=thumbnail;
  }
  public String getThumbnail(){
   return thumbnail;
  }
  public void setLink(String link){
   this.link=link;
  }
  public String getLink(){
   return link;
  }
  public void setThumbnail_file(Thumbnail_file thumbnail_file){
   this.thumbnail_file=thumbnail_file;
  }
  public Thumbnail_file getThumbnail_file(){
   return thumbnail_file;
  }
  public void setTitle(String title){
   this.title=title;
  }
  public String getTitle(){
   return title;
  }
  public void setMusic_file(Music_file music_file){
   this.music_file=music_file;
  }
  public Music_file getMusic_file(){
   return music_file;
  }
  public void setObjectId(String objectId){
   this.objectId=objectId;
  }
  public String getObjectId(){
   return objectId;
  }
  public void setUpdatedAt(String updatedAt){
   this.updatedAt=updatedAt;
  }
  public String getUpdatedAt(){
   return updatedAt;
  }
}