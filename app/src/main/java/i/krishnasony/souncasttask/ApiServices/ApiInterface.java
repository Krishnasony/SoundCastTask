package i.krishnasony.souncasttask.ApiServices;

import i.krishnasony.souncasttask.MVVM.Model.SongListPojo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApiInterface {
    @Headers({
            "X-Parse-Application-Id: VSPdpDKRMND382hqIRFIaiVLgbkhM0E1rL32l1SQ",
            "X-Parse-REST-API-Key: E4ZeObhQv3XoHaQ3Q6baHGgbDPOkuO9jPlY9gzgA"}
    )
    @GET("/classes/songs_library")
    Call<SongListPojo> getSonglist();
}
