package uk.co.littlestickyleaves;

import com.fasterxml.jackson.jr.ob.JSON;
import uk.co.littlestickyleaves.aws.lambda.base.LambdaWorker;
import uk.co.littlestickyleaves.aws.lambda.base.api.LambdaException;

import java.io.IOException;

public class StringRepeater implements LambdaWorker {

    @Override
    public String handleRaw(String rawInput) {
        try {
            StringRepetitionInstruction instruction = JSON.std.beanFrom(StringRepetitionInstruction.class, rawInput);
            StringRepetitionResult stringRepeatResult = handle(instruction);
            return JSON.std.asString(stringRepeatResult);
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
