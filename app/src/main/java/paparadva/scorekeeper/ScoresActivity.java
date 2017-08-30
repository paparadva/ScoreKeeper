package paparadva.scorekeeper;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import paparadva.scorekeeper.model.PlayerScore;

public class ScoresActivity extends AppCompatActivity {
    private List<List<PlayerScore>> mScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mScores = new ArrayList<>();
        mScores.add(createInitialScores());

        RecyclerView scores = (RecyclerView) findViewById(R.id.rv_current_scores);
        scores.setHasFixedSize(true);

        GridLayoutManager lm = new GridLayoutManager(this, 3);
        scores.setLayoutManager(lm);

        ScoresAdapter adapter = new ScoresAdapter(getLastRoundScores());
        scores.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_scores);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Update scores", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
