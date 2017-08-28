package paparadva.scorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PlayerNumberActivity extends AppCompatActivity {

    private TextView mTextError;
    private EditText mPlayerNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_number);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPlayerNumber = (EditText) findViewById(R.id.et_player_number);
        mTextError = (TextView) findViewById(R.id.tv_player_number_error);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int playerNum = getEnteredNumber();
                    Intent data = new Intent();
                    data.putExtra(Intent.EXTRA_RETURN_RESULT, playerNum);
                    setResult(RESULT_OK, data);
                    finish();
                } catch (Exception e) {
                    mTextError.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private int getEnteredNumber() {
        int number = Integer.parseInt(mPlayerNumber.getText().toString());
        if(number <= 0) throw new IllegalArgumentException("Entered number of players is <= 0");
        return number;
    }
}
