package paparadva.scorekeeper.model;

import java.io.Serializable;

public final class PlayerScore implements Serializable {
    private String name;
    private int score;

    public PlayerScore() {
        this.name = "";
        this.score = 0;
    }

    public PlayerScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public void updateScore(int delta) {
        score += delta;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerScore that = (PlayerScore) o;

        if (score != that.score) return false;
        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + score;
        return result;
    }
}
