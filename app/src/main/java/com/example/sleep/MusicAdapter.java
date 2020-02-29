package com.example.sleep;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    private List<String> mMusicList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView musicName;

        public ViewHolder(View view) {
            super(view);
            musicName = (TextView) view.findViewById(R.id.music_name);
        }
    }

    public MusicAdapter(List<String> musicList) {
        mMusicList = musicList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        int parentHeight= parent.getHeight();
        parent.getWidth();
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height =  (parentHeight/ 6);//显示六条
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String musicName= mMusicList.get(position);
        holder.musicName.setText(musicName);
    }

    @Override
    public int getItemCount() {
        return mMusicList.size();
    }
}
