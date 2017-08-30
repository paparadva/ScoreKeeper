package paparadva.scorekeeper.model;

import java.io.Serializable;

public final class PlayerScore implements Serializable {
    private String mName;
    private int mScore;

    public PlayerScore(String playerName, int score) {
        mName = playerName;
        mScore = score;
    }

    public void updateScore(int delta) {
        mScore += delta;
    }

    public int getScore() {
        return mScore;
    }

    public String getName() {
        return mName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerScore that = (PlayerScore) o;

        if (mScore != that.mScore) return false;
        return mName.equals(that.mName);

    }

    @Override
    public int hashCode() {
        int result = mName.hashCode();
        result = 31 * result + mScore;
        return result;
    }
}
