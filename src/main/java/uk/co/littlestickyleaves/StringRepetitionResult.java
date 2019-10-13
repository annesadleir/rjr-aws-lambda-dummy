package uk.co.littlestickyleaves;

/**
 * POJO to hold info about the result of a String Repetition
 */
public class StringRepetitionResult {

    private String input;

    private int repeat;

    private String result;

    public StringRepetitionResult() {
    }

    public StringRepetitionResult(String input, int repeat, String result) {
        this.input = input;
        this.repeat = repeat;
        this.result = result;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
