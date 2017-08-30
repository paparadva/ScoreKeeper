package paparadva.scorekeeper;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import paparadva.scorekeeper.model.PlayerScore;

public class UpdateScoresActivity extends AppCompatActivity {

    private List<PlayerScore> mOldScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_scores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mOldScores = (List<PlayerScore>) getIntent().getSerializableExtra(
                ScoresActivity.EXTRA_CURRENT_SCORES);

        RecyclerView inputList = (RecyclerView) findViewById(R.id.rv_update_inputs);
        inputList.setHasFixedSize(true);
        inputList.setLayoutManager(new LinearLayoutManager(this));
        inputList.setAdapter(new UpdateScoresAdapter(mOldScores));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishWithResult();
            }
        });
    }

    private void finishWithResult() {
        finish();
    }
}

class UpdateScoresAdapter extends RecyclerView.Adapter<UpdateScoresAdapter.UpdateInputViewHolder> {
    private List<PlayerScore> mScores;

    class UpdateInputViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText;

        UpdateInputViewHolder(View view) {
            super(view);
            nameText = (TextView) view.findViewById(R.id.tv_update_name);
        }

        void bind(String playerName) {
            nameText.setText(playerName);
        }
    }

    UpdateScoresAdapter(List<PlayerScore> scores) {
        mScores = scores;
    }

    @Override
    public int getItemCount() {
        return mScores.size();
    }

    @Override
    public UpdateInputViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_update_score, parent, false);
        return new UpdateInputViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UpdateInputViewHolder holder, int position) {
        holder.bind(mScores.get(position).getName());
    }
}
