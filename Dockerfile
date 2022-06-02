FROM container-registry.oracle.com/java/openjdk:17-oraclelinux8 # Base Image

ARG JAR_FILE                   # Pass in the JAR file as an argument to the image build

EXPOSE 8080                    # This image will need to expose TCP port 8080, as this is the port on which your app will listen

COPY ${JAR_FILE} app.jar       # Copy the JAR file from the `target` directory into the root of the image 
ENTRYPOINT ["./string-repeater"]     # start the native executable
