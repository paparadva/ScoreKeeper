package paparadva.scorekeeper;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<String> players = getIntent().getStringArrayListExtra(
                LauncherActivity.EXTRA_PLAYER_NAMES);

        RecyclerView scores = (RecyclerView) findViewById(R.id.rv_current_scores);
        scores.setHasFixedSize(true);

        GridLayoutManager lm = new GridLayoutManager(this, 3);
        scores.setLayoutManager(lm);

        ScoresAdapter adapter = new ScoresAdapter(players);
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

}

class ScoresAdapter extends RecyclerView.Adapter<ScoresAdapter.ScoreViewHolder> {
    private List<String> mPlayers;

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

    ScoresAdapter(List<String> players) {
        mPlayers = players;
    }

    @Override
    public int getItemCount() {
        return mPlayers.size();
    }

    @Override
    public ScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_score, parent, false);
        return new ScoreViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ScoreViewHolder holder, int position) {
        holder.bind(mPlayers.get(position), 340); //placeholder score
    }
}
