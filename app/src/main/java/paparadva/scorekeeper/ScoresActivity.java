package paparadva.scorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import paparadva.scorekeeper.model.PlayerScore;
import paparadva.scorekeeper.util.StorageUtils;

public class ScoresActivity extends AppCompatActivity {
    public static final String EXTRA_CURRENT_SCORES = "paparadva.scorekeeper.CURRENT_SCORES";

    private static final int REQUEST_UPDATE_SCORES = 0;
    private List<List<PlayerScore>> mScores;
    private ScoresAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mScores = StorageUtils.loadData(this);
        if(mScores == null) {
            mScores = new ArrayList<>();
            mScores.add(createInitialScores());
            StorageUtils.saveData(this, mScores);
        }

        RecyclerView scores = (RecyclerView) findViewById(R.id.rv_current_scores);
        scores.setHasFixedSize(true);
        scores.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new ScoresAdapter(getLastRoundScores());
        scores.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_scores);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUpdateScores();
            }
        });
    }

    private List<PlayerScore> getLastRoundScores() {
        return mScores.get(mScores.size()-1);
    }

    private List<PlayerScore> createInitialScores() {
        List<String> playerNames = getIntent().getStringArrayListExtra(
                LauncherActivity.EXTRA_PLAYER_NAMES);

        List<PlayerScore> zeroScores = new ArrayList<>();
        for(String name : playerNames) {
            zeroScores.add(new PlayerScore(name, 0));
        }

        return zeroScores;
    }

    private void showUpdateScores() {
        Intent updateScores = new Intent(this, UpdateScoresActivity.class);
        updateScores.putExtra(EXTRA_CURRENT_SCORES, (Serializable) getLastRoundScores());
        startActivityForResult(updateScores, REQUEST_UPDATE_SCORES);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_UPDATE_SCORES) {
            List<PlayerScore> newScores =
                    (List<PlayerScore>) data.getSerializableExtra(EXTRA_CURRENT_SCORES);
            mScores.add(newScores);
            mAdapter.setScoreData(getLastRoundScores());
            StorageUtils.saveData(this, mScores);
        }
    }
}

class ScoresAdapter extends RecyclerView.Adapter<ScoresAdapter.ScoreViewHolder> {
    private List<PlayerScore> mScores;

    static class ScoreViewHolder extends RecyclerView.ViewHolder {
        private TextView nameView;
        private TextView scoreView;

        ScoreViewHolder(View view) {
            super(view);
            nameView = (TextView) view.findViewById(R.id.tv_name);
            scoreView = (TextView) view.findViewById(R.id.tv_score);
        }

        void bind(String name, int score) {
            nameView.setText(name);
            scoreView.setText(String.valueOf(score));
        }
    }

    ScoresAdapter(List<PlayerScore> scores) {
        mScores = scores;
    }

    void setScoreData(List<PlayerScore> scores) {
        mScores = scores;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mScores.size();
    }

    @Override
    public ScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_score, parent, false);
        return new ScoreViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ScoreViewHolder holder, int position) {
        PlayerScore score = mScores.get(position);
        holder.bind(score.getName(), score.getScore());
    }
}
