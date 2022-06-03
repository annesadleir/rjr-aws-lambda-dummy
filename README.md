# rjr-aws-lambda-dummy
I wanted to get a Java app compiled to a native executable and 
running as a custom runtime on AWS Lambda.

The base code that implements the runtime can be found in 
[rjr-aws-lambda-base](https://github.com/annesadleir/rjr-aws-lambda-base) repo.
If you want to build this repo, 
you'll need to run `mvn clean install` on that repo first 
to make it available for this one.

## build a native executable from Maven
`mvn package -Pnative`

Puts the executable into the `target` folder with the other build artefacts.

## deploy it as a custom runtime on AWS Lambda
There needs to be a `bootstrap` file, as included in this repo.
All this does is start the native executable. 
It's the file that the AWS system calls when using a provided runtime.  
It has to be set to be executable, e.g. `chmod 777 bootstrap`.  

The native executable built in the previous step also needs to be set to be executable.

You'll also need an IAM role for your lambda.  It has to have `AWSLambdaBasicExecutionRole` permissions: \
[AWS Lambda Execution Role](https://docs.aws.amazon.com/lambda/latest/dg/lambda-intro-execution-role.html) \
Take note of the role's arn so you can put it into your aws cli command.

Now try this command to make the function: \
`aws lambda create-function --function-name repeat-string `
`--zip-file fileb://~/linuxWorkarea/string-repeater.zip --handler function.handler --runtime provided `
`--role arn:aws:iam::`YOUR_ROLE_ARN_HERE
* the zip file has to be referenced with the `fileb://` protocol -- but obviously adjust the value to your location
* the `--handler function.handler` bit is required, but is just a pretend value as this base layer cannot be called with different handler values
* `--runtime provided` lets it know it doesn't need to provide a specific runtime
* `--role` specifies your specific IAM role with lambda permissions

You should get back some info about the created function. 
If this is not your first attempt you'll need to delete the previous version: \
`aws lambda delete-function --function-name repeat-string`

Now you can try to invoke the function on AWS like this: \
`aws lambda invoke --function-name repeat-string --payload '{"input":"beep","repeat":4}' response.txt`\
(Don't put any spaces into your json payload.)
The response to the cli command should give you some idea if it has worked or not. 
It will have put the proper response into `response.txt`, and this will either be more detail on the error, or the correct output.   
Here is a response.txt contents from a successful invocation: \
`{"input":"neenah","repeat":9,"result":"neenahneenahneenahneenahneenahneenahneenahneenahneenah"}` \
If it has failed, more detail can be got from looking at the CloudWatch logs in the AWS console.  The error message may be somewhat opaque.
 

## or deploy it as a container on AWS Lambda

### Build the native executable into a Docker container
Using the provided Dockerfile:  
`docker build -t string repeater .`

### Push the container to ECR  
[Using aws cli](https://docs.aws.amazon.com/lambda/latest/dg/images-create.html#images-upload)

### Deploy as a lambda from the GUI  
After clicking 'Create Function' choose the container option from the large buttons at the top.

## Next step
1. Repeat with a distroless docker container.
2. *NEEDS to be in 2 steps*

For the old readme, see [docs/2019-readme.md](docs/2019-readme.md)

