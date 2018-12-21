package i.krishnasony.souncasttask.Adapter;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import i.krishnasony.souncasttask.MVVM.Model.Results;
import i.krishnasony.souncasttask.MVVM.Model.SongListPojo;
import i.krishnasony.souncasttask.MVVM.Navigator.Navigator;
import i.krishnasony.souncasttask.R;
import i.krishnasony.souncasttask.SongsListActivity;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongsViewHolder> {

    Context mCtx;
    List<Results> songList;
    private Navigator  clickListener;

    public SongListAdapter(Context mCtx, List<Results> songList) {
        this.mCtx = mCtx;
        this.songList = songList;
    }

    @NonNull
    @Override
    public SongListAdapter.SongsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.song_list_item,viewGroup, false);
        return new SongsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongListAdapter.SongsViewHolder songsViewHolder, int i) {
        Results songs = songList.get(i);

        Glide.with(mCtx)
                .load(songs.getThumbnail())
                .into(songsViewHolder.songthumbnail);

        songsViewHolder.textViewtitle.setText(songs.getTitle());


    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public void setClickListener(Navigator itemClickListener) {
        this.clickListener =  itemClickListener;
    }


    public class SongsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewtitle;
        ImageView downloadservice ;
        CircleImageView songthumbnail;

        public SongsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewtitle = itemView.findViewById(R.id.songtitle);
            downloadservice = itemView.findViewById(R.id.downloadsong);
            songthumbnail = itemView.findViewById(R.id.thumbnailsong);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());

        }
    }
}
