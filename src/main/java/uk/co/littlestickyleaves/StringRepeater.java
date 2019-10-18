package uk.co.littlestickyleaves;

import com.fasterxml.jackson.jr.ob.JSON;
import uk.co.littlestickyleaves.aws.lambda.base.LambdaWorker;

import java.io.IOException;

/**
 * A LambdaWorker implementation which repeats a string as instructed
 */
public class StringRepeater implements LambdaWorker {

    private final JSON json = JSON.std;

    @Override
    public String handleRaw(String rawInput) {
        try {
            StringRepetitionInstruction instruction = json.beanFrom(StringRepetitionInstruction.class, rawInput);
            StringRepetitionResult stringRepeatResult = handle(instruction);
            return json.asString(stringRepeatResult);
        } catch (IOException e) {
            throw new RuntimeException("Unable to deserialize StringRepeatInstruction from input: " + rawInput, e);
        }
    }

    private StringRepetitionResult handle(StringRepetitionInstruction instruction) {
        String result = repeat(instruction.getInput(), instruction.getRepeat());
        return new StringRepetitionResult(instruction.getInput(), instruction.getRepeat(), result);
    }

    private String repeat(String input, int times) {
        if (times <= 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int step = 0; step < times; step++) {
            stringBuilder.append(input);
        }
        return stringBuilder.toString();
    }
}
