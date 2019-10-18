package uk.co.littlestickyleaves;

import uk.co.littlestickyleaves.aws.lambda.base.LambdaRunner;

/**
 * Just a class to hold the psvm method which starts the runtime
 */
public class StringRepeaterMain {

    public static void main(String[] args) throws Exception {
        new LambdaRunner(new StringRepeater()).loop();
    }
}
