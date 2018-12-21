package i.krishnasony.souncasttask;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import i.krishnasony.souncasttask.Activities.PlayMusic.PlayMusicActivity;
import i.krishnasony.souncasttask.Adapter.SongListAdapter;
import i.krishnasony.souncasttask.MVVM.Model.Results;
import i.krishnasony.souncasttask.MVVM.Navigator.Navigator;
import i.krishnasony.souncasttask.MVVM.ViewModel.SongListViewModel;

public class SongsListActivity extends AppCompatActivity implements Navigator {

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

        toolbar.setTitle(R.string.app_title);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SongListViewModel songListViewModel = ViewModelProviders.of(this).get(SongListViewModel.class);
        songListViewModel.getSongs().observe(this, new Observer<List<Results>>() {
            @Override
            public void onChanged(@Nullable List<Results> results) {
                songlist = new ArrayList<>(results);
                Toast.makeText(SongsListActivity.this, "response"+results, Toast.LENGTH_SHORT).show();

                songListAdapter = new SongListAdapter(SongsListActivity.this,results);

                recyclerView.setAdapter(songListAdapter);
                songListAdapter.setClickListener(SongsListActivity.this);

            }
        });

    }

    private void init() {
        recyclerView = findViewById(R.id.songlist_rv);
        toolbar = findViewById(R.id.toolbar);

    }

    @Override
    public void onClick(View view, int position) {
        Results results = songlist.get(position);
        Intent i = new Intent(SongsListActivity.this,PlayMusicActivity.class);
        i.putExtra("Songname",results.getTitle());
        i.putExtra("songThumbnail",results.getThumbnail());
        i.putExtra("link",results.getLink());
        startActivity(i);

    }
}
