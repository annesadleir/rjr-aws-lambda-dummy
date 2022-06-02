# rjr-aws-lambda-dummy
I wanted to get a Java app compiled to a native executable and running as a custom runtime on AWS Lambda.

The base code that implements the runtime can be found in [rjr-aws-lambda-base](https://github.com/annesadleir/rjr-aws-lambda-base) repo.
If you want to build this repo, you'll need to run `mvn clean install` on that repo first to make it available for this one.

## build a native executable from Maven
`mvn package -Pnative`

Puts the executable into the `target` folder with the other build artefacts.
(This executable can be successfully deployed and invoked from the cli.)

## Next step
1. Create dockerfile to build into a container.
2. Deploy and run the lambda from the dockerfile.
3. Repeat with a distroless docker container.

For the old readme, see [docs/2019-readme.md](docs/2019-readme.md)

