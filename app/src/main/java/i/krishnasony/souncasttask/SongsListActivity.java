package i.krishnasony.souncasttask;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import i.krishnasony.souncasttask.Adapter.SongListAdapter;
import i.krishnasony.souncasttask.MVVM.Model.Results;
import i.krishnasony.souncasttask.MVVM.ViewModel.SongListViewModel;

public class SongsListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Results> songlist;
    SongListAdapter songListAdapter;
    Toolbar toolbar;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_list);
        init();
        recyclerView = findViewById(R.id.songlist_rv);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_title);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SongListViewModel songListViewModel = ViewModelProviders.of(this).get(SongListViewModel.class);
        songListViewModel.getSongs().observe(this, new Observer<List<Results>>() {
            @Override
            public void onChanged(@Nullable List<Results> results) {
                Toast.makeText(SongsListActivity.this, "response"+results, Toast.LENGTH_SHORT).show();

                songListAdapter = new SongListAdapter(SongsListActivity.this,results);

                recyclerView.setAdapter(songListAdapter);

            }
        });

    }

    private void init() {


    }
}
