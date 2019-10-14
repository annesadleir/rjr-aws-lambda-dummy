# rjr-aws-lambda-dummy
An example, using toy code

## deployment
 There needs to be a `bootstrap` file, as included in this repo.  All this does is start the native executable. 
 It's the file that the AWS system calls when using a provided runtime.  
 It has to be set to be executable, e.g. `chmod 777 bootstrap`.  
 
To compile the code into a native executable using GraalVM, first compile it normally using maven. 

`mvn clean package` 

The `pom.xml` is set up to create a fat jar, as usual when building Java for AWS Lambdas.

Once you have the fat jar, use GraalVM's `native-image` command to create the native executable: \
 `native-image --enable-url-protocols=http -cp ./target/rjr-aws-lambda-dummy-1.0.jar \`  
  `-Djava.net.preferIPv4Stack=true \`  
  `-H:Name=string-repeater -H:Class=uk.co.littlestickyleaves.StringRepeaterMain -H:ReflectionConfigurationFiles=reflect.json \`  
  `-H:+ReportUnsupportedElementsAtRuntime --allow-incomplete-classpath`  
  This runs the native-image compiler, which is not snappy.  My understanding of it is only partial, but I think this is how it works:
  * you need the `--enable-url-protocols=http` bit because the executable will use http
  * the `-cp` bit tells it the classpath e.g. the jar you're using
  * you need the `-Djava.net.preferIPv4Stack=true` bit because without it there's an error which seems to be to do with IPv6
  * the Name and Class are pretty self-evident: Name is what the output gets called, and Class is the Main class of the app
  * you need the `-H:ReflectionConfigurationFiles=reflect.json` bit because otherwise GraalVM doesn't like reflection,
  and Jackson JR uses reflection for serialization and de- of output and in-
  * `-H:+ReportUnsupportedElementsAtRuntime` should report on possible difficulties
  * `--allow-incomplete-classpath` reports type resolution errors at run time not build time (could be worth trying without)
  
Make the executable executable (chmod etc).
  
Place the bootstrap file and the executable into a zip file together: \
`zip string-repeater.zip bootstrap notkotlin`  
This is your deployment package.

# putting it onto AWS

You'll also need a role for your lambda.  It has to have `AWSLambdaBasicExecutionRole` permissions: \
[AWS Lambda Execution Role](https://docs.aws.amazon.com/lambda/latest/dg/lambda-intro-execution-role.html) \
Take note of the role's arn so you can put it into your aws cli command.

Now try this command to make the function: \
`aws lambda create-function --function-name repeat-string --zip-file fileb://~/linuxWorkarea/string-repeater.zip --handler function.handler --runtime provided --role arn:aws:iam::`YOUR_ROLE_ARN_HERE \
The zip file has to be referenced with the `fileb://` protocol.  \
You should get back some info about the created function. 
If this is not your first attempt you'll need to delete the previous version: \
`aws lambda delete-function --function-name repeat-string` \

Now you can try to invoke the function on AWS like this: \
`aws lambda invoke --function-name repeat-string --payload '{"input":"beep","repeat":4}' response.txt`\
(Don't put any spaces into your json payload.)
The response to the cli command should give you some idea if it has worked or not. 
It will have put the proper response into `response.txt`, and this will either be more detail on the error, or the correct output.   
Here is a response.txt contents from a successful invocation: \
`{"input":"neenah","repeat":9,"result":"neenahneenahneenahneenahneenahneenahneenahneenahneenah"}` \
If it has failed, more detail can be got from looking at the CloudWatch logs in the AWS console.  The error message may be somewhat opaque.
 
Feedback appreciated.
