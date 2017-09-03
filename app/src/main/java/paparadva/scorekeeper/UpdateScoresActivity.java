package paparadva.scorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import paparadva.scorekeeper.model.PlayerScore;

public class UpdateScoresActivity extends AppCompatActivity {

    private List<PlayerScore> mScores;
    private UpdateScoresAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_scores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mScores = (List<PlayerScore>) getIntent().getSerializableExtra(
                ScoresActivity.EXTRA_CURRENT_SCORES);

        RecyclerView inputList = (RecyclerView) findViewById(R.id.rv_update_inputs);
        inputList.setHasFixedSize(true);
        inputList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new UpdateScoresAdapter(mScores);
        inputList.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishWithResult();
            }
        });
    }

    private void finishWithResult() {
        List<Integer> deltas = mAdapter.getDeltas();
        for(int i=0; i<mScores.size(); i++) {
            mScores.get(i).updateScore(deltas.get(i));
        }

        Intent newScoresData = new Intent();
        newScoresData.putExtra(ScoresActivity.EXTRA_CURRENT_SCORES, (Serializable) mScores);
        setResult(RESULT_OK, newScoresData);

        finish();
    }
}

class UpdateScoresAdapter extends RecyclerView.Adapter<UpdateScoresAdapter.UpdateInputViewHolder> {
    private List<PlayerScore> mScores;
    private List<Integer> mDeltas;

    class UpdateInputViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nameText;
        private EditText deltaEdit;

        private int position = 0;
        private int scoreDelta = 0;

        UpdateInputViewHolder(View view) {
            super(view);
            nameText = (TextView) view.findViewById(R.id.tv_update_name);
            deltaEdit = (EditText) view.findViewById(R.id.et_score_delta);
            deltaEdit.setText(String.valueOf(scoreDelta));
            deltaEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable editable) {}

                @Override
                public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                    if(s.length() == 0) {
                        scoreDelta = 0;
                    } else {
                        scoreDelta = Integer.parseInt(s.toString());
                    }

                    UpdateScoresAdapter.this.mDeltas.set(position, scoreDelta);
                }
            });

            // Set this ViewHolder as ClickListener for all Buttons
            ViewGroup buttonContainer = (ViewGroup) view.findViewById(R.id.button_row_layout);
            for(int i = 0; i<buttonContainer.getChildCount(); i++) {
                View child = buttonContainer.getChildAt(i);
                if(child instanceof Button) {
                    child.setOnClickListener(this);
                }
            }
        }

        void bind(int position, String playerName) {
            this.position = position;
            nameText.setText(playerName);
        }

        @Override
        public void onClick(View v) {
            int delta = Integer.parseInt((String)v.getTag());
            deltaEdit.setText(String.valueOf(scoreDelta + delta));
        }
    }

    UpdateScoresAdapter(List<PlayerScore> scores) {
        mScores = scores;
        mDeltas = new ArrayList<>();
        for(int i=0; i<scores.size(); i++) mDeltas.add(0);
    }

    public List<Integer> getDeltas() {
        return mDeltas;
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
        holder.bind(position, mScores.get(position).getName());
    }
}
