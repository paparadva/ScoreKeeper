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

public class PlayerNamesActivity extends AppCompatActivity {

    private RecyclerView mNameInputs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_names);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int playerNumber = getIntent().getIntExtra(LauncherActivity.EXTRA_PLAYER_NUMBER, 0);

        mNameInputs = (RecyclerView) findViewById(R.id.rv_name_inputs);
        mNameInputs.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false);
        mNameInputs.setLayoutManager(layoutManager);

        mNameInputs.setAdapter(
                new PlayerNamesAdapter(playerNumber, getString(R.string.enter_name_label))
        );

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}

class PlayerNamesAdapter extends RecyclerView.Adapter<PlayerNamesAdapter.NameInputViewHolder> {
    private int mPlayerNumber;
    private final String LABEL_FORMAT;

    class NameInputViewHolder extends RecyclerView.ViewHolder {
        private TextView playerLabel;

        NameInputViewHolder(View view) {
            super(view);
            playerLabel = view.findViewById(R.id.tv_enter_name_label);
        }

        void bind(int playerOrdinal) {
            playerLabel.setText(String.format(LABEL_FORMAT, playerOrdinal));
        }
    }

    PlayerNamesAdapter(int playerNumber, final String label_format) {
        LABEL_FORMAT = label_format;
        mPlayerNumber = playerNumber;
    }

    @Override
    public NameInputViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                 .inflate(R.layout.item_enter_player_name, parent, false);
        return new NameInputViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NameInputViewHolder holder, int position) {
        holder.bind(position + 1);
    }

    @Override
    public int getItemCount() {
        return mPlayerNumber;
    }
}
