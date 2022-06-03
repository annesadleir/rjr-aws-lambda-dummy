# syntax=docker/dockerfile:1

FROM amazonlinux

# copy the executable
COPY target/string-repeater .

# make it executable
RUN chmod +x string-repeater

# start the executable
ENTRYPOINT ["./string-repeater"]

