# every-note-persister
An AWS Lambda build on top of Spring Boot 3 and GraalVM for persisting requests in DynamoDB, managed by SAM and automated CI/CD process via GitHub Actions.

> Most important classes and files have documentation/comments which explain their purpose.


> To read more details how this application works, read my blog post: [Production ready AWS Lambda using Spring Boot 3 and GraalVM](https://gvart.dev/post/2023/02/native_spring_boot_aws_lambda/)
# Tech stack
* Java 17
* Spring Boot 3
* GraalVM (native-image)
* Docker
* SAM


# How to build and deploy
To build a native executable you'll need a Docker environment al least with 4GB of RAM.

1. Create a docker-image which will be used by SAM to build native-executable compatible with Amazon Linux 2 OS. by running:

   `./build-image.sh` _(**Note**: this script contains versions of GraalVM, Java and Gradle which can be updated)_
2. After a successful build of the container we can build lambda itself by running:

    `sam build --use-container`

3. To deploy lambda to AWS use following command: (Don't forget to replace `<AWS_REGION>` placeholder with the corresponding rego )

    ```shell
    sam deploy \
     --no-confirm-changeset \
     --no-fail-on-empty-changeset \
     --resolve-s3 \
     --region <AWS_REGION> \
     --capabilities CAPABILITY_IAM \
     --stack-name every-note-persister
   ```
   

# Native Image hints
The entire application is simple Java Spring Boot application, but in order to build and run it as a native-executable
we need to provide a couple of hints.
1. [resource-config.json](./src/main/resources/META-INF/native-image/resource-config.json), this file is used by **io.awspring.cloud:spring-cloud-aws-starter-dynamodb**, potentially will be added to the library itself, [Issue to track](https://github.com/awspring/spring-cloud-aws/issues/673)
2. [Reflection Hints](./src/main/java/com/everynote/perister/config/NativeConfig.java) All classes which are accessed by reflection should be explicitly provided as reflection hints.
By having all this in place application is ready to be run as a native executable file.