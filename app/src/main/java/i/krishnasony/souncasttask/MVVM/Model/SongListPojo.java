package i.krishnasony.souncasttask.MVVM.Model;
import java.util.List;

/**
 * Awesome Pojo Generator
 * */
public class SongListPojo{
  private List<Results> results;
  public void setResults(List<Results> results){
   this.results=results;
  }
  public List<Results> getResults(){
   return results;
  }
}