package paparadva.scorekeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class LauncherActivity extends AppCompatActivity {
    private static final int REQUEST_PLAYER_NUMBER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        Intent getPlayerNum = new Intent(this, PlayerNumberActivity.class);
        startActivityForResult(getPlayerNum, REQUEST_PLAYER_NUMBER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            switch(requestCode) {
                case REQUEST_PLAYER_NUMBER: {
                    int playerNum = data.getIntExtra(Intent.EXTRA_RETURN_RESULT, 0);
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }
}
