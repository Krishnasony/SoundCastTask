package i.krishnasony.souncasttask.MVVM.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import i.krishnasony.souncasttask.ApiServices.ApiInterface;
import i.krishnasony.souncasttask.MVVM.Model.Results;
import i.krishnasony.souncasttask.MVVM.Model.SongListPojo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SongListViewModel  extends ViewModel {
    String BASE_URL ="https://soundcast.back4app.io";
    private MutableLiveData<List<Results>> songList;
    //we will call this method to get the data
    public LiveData<List<Results>> getSongs() {
        //if the list is null
        if (songList == null) {
            songList = new MutableLiveData<List<Results>>();
            //we will load it asynchronously from server in this method
            loadSongs();
        }

        return songList;
    }

    private void loadSongs() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        retrofit.create(ApiInterface.class).getSonglist().enqueue(new Callback<SongListPojo>() {
            @Override
            public void onResponse(Call<SongListPojo> call, Response<SongListPojo> response) {
                songList.setValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<SongListPojo> call, Throwable t) {
                t.getLocalizedMessage();
            }
        });

    }
}
