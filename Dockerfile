FROM ghcr.io/graalvm/native-image:ol7-java17-22.3.0 as build

RUN yum install -y zip

WORKDIR /app
COPY ./ ./
RUN ./gradlew nativeCompile
RUN chmod a+x ./build/native/nativeCompile/application
RUN zip lambda.zip -j ./native/bootstrap ./build/native/nativeCompile/application

#To keep image small on local machine
FROM alpine:latest
WORKDIR /app
COPY --from=build /app/lambda.zip ./