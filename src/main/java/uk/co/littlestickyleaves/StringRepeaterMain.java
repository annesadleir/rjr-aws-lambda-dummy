package uk.co.littlestickyleaves;

import uk.co.littlestickyleaves.aws.lambda.base.LambdaRunner;

public class StringRepeaterMain {

    public static void main(String[] args) throws Exception {
        new LambdaRunner<>(new StringRepeater()).loop();
    }
}
