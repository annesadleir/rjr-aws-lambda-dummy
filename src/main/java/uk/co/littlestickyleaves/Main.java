package uk.co.littlestickyleaves;

import uk.co.littlestickyleaves.aws.lambda.base.LambdaRunner;

public class Main {

    public static void main(String[] args) throws Exception {
        new LambdaRunner<>(new StringRepeater()).loop();
    }
}
