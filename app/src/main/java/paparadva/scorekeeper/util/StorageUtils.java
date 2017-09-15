package paparadva.scorekeeper.util;

import android.content.Context;
import android.util.Log;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import paparadva.scorekeeper.LauncherActivity;
import paparadva.scorekeeper.model.PlayerScore;

public final class StorageUtils {
    private static final String SESSION_FILENAME = "scores.json";

    public static List<List<PlayerScore>> loadData(Context context) {
        try {
            FileInputStream fis = context.openFileInput(SESSION_FILENAME);
            BufferedReader buf = new BufferedReader(new InputStreamReader(fis));

            StringBuilder builder = new StringBuilder();
            String line = buf.readLine();

            while(line != null) {
                builder.append(line);
                line = buf.readLine();
            }

            Log.d("loadData", builder.toString());

            return new Genson().deserialize(builder.toString(),
                    new GenericType<List<List<PlayerScore>>>() {});

        } catch(FileNotFoundException e) {
            Log.d("loadData", e.toString());
            return null;
        } catch(IOException e) {
            Log.d("loadData", e.toString());
            return null;
        }
    }

    public static void saveData(Context context, List<List<PlayerScore>> scoreData) {
        String scores = new Genson().serialize(scoreData);
        try {
            FileOutputStream fos = context.openFileOutput(SESSION_FILENAME, Context.MODE_PRIVATE);
            fos.write(scores.getBytes());
            fos.close();
        } catch(FileNotFoundException e) {
            Log.d("saveData", e.toString());
        } catch(IOException e) {
            Log.d("saveData", e.toString());
        }
    }
}
