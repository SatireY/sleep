package com.example.sleep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SelectMusicActivity extends BaseActivity {

    private SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView recyclerView;

    private List<String> musicList = new ArrayList<>();

    private ProgressDialog progressDialog;

    private MusicAdapter adapter;

    private static final String[] MUSIC_LIST = new String[]{
            "羽根", "if i were a Bird", "Old Memory", "天空之城", "Frosti", "志在千里","永远同在","时间一去不复返","希达的决心","风之谷","风的通道","夜想曲","临海小镇","追忆昔日","鸟人","重新开始"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_music);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initMusic();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MusicAdapter(musicList);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showProgressDialog();
                refreshMusic();
            }
        });
    }

    private void refreshMusic() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    showProgressDialog();
                    Thread.sleep(2000);
//                    closeProgressDialog();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        initMusic();
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }


    private void initMusic() {
        Random random = new Random();
        int j;
        musicList.clear();
        for(int i = 0; i < 6; i++) {
            j = random.nextInt(16);
            musicList.add(MUSIC_LIST[j]);
        }
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
