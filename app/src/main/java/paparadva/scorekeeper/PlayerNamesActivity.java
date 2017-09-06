package paparadva.scorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlayerNamesActivity extends AppCompatActivity {

    private RecyclerView mNameInputs;
    private int mPlayerNumber;
    private PlayerNamesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_names);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPlayerNumber = getIntent().getIntExtra(LauncherActivity.EXTRA_PLAYER_NUMBER, 0);

        mNameInputs = (RecyclerView) findViewById(R.id.rv_name_inputs);
        mNameInputs.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false);
        mNameInputs.setLayoutManager(layoutManager);

        mAdapter = new PlayerNamesAdapter(mPlayerNumber, getString(R.string.enter_name_label));
        mNameInputs.setAdapter(mAdapter);
    }

    private void finishWithResult() {
        Intent namesData = new Intent();

        ArrayList<String> names = mAdapter.getEnteredText();
        namesData.putStringArrayListExtra(LauncherActivity.EXTRA_PLAYER_NAMES, names);

        setResult(RESULT_OK, namesData);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_accept) {
            finishWithResult();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.acceptmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}

class PlayerNamesAdapter extends RecyclerView.Adapter<PlayerNamesAdapter.NameInputViewHolder> {
    private ArrayList<String> mEnteredTextData;
    private int mPlayerNumber;
    private final String LABEL_FORMAT;

    class NameInputViewHolder extends RecyclerView.ViewHolder {
        private TextView playerLabel;
        private EditText nameEdit;

        NameInputViewHolder(View view) {
            super(view);
            playerLabel = view.findViewById(R.id.tv_enter_name_label);
            nameEdit = view.findViewById(R.id.et_player_name);
        }

        void bind(final int position) {
            int playerOrdinal = position + 1;
            playerLabel.setText(String.format(LABEL_FORMAT, playerOrdinal));

            nameEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable editable) {}

                @Override
                public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                    mEnteredTextData.set(position, s.toString());
                }
            });
        }
    }

    PlayerNamesAdapter(int playerNumber, final String label_format) {
        LABEL_FORMAT = label_format;
        mPlayerNumber = playerNumber;
        mEnteredTextData = new ArrayList<>(playerNumber);
        for(int i=0; i<playerNumber; i++) {
            mEnteredTextData.add("");
        }
    }

    ArrayList<String> getEnteredText() {
        return mEnteredTextData;
    }

    @Override
    public NameInputViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                 .inflate(R.layout.item_enter_player_name, parent, false);
        return new NameInputViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NameInputViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mPlayerNumber;
    }
}
