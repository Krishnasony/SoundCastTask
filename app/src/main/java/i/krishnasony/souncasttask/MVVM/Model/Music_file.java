package i.krishnasony.souncasttask.MVVM.Model;

import java.io.Serializable;

/**
 * Awesome Pojo Generator
 * */
public class Music_file  implements Serializable {
  private String __type;
  private String name;
  private String url;
  public void set__type(String __type){
   this.__type=__type;
  }
  public String get__type(){
   return __type;
  }
  public void setName(String name){
   this.name=name;
  }
  public String getName(){
   return name;
  }
  public void setUrl(String url){
   this.url=url;
  }
  public String getUrl(){
   return url;
  }
}