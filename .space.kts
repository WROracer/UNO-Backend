job("Build, Test, Deploy"){
    container("maven:3-openjdk-8-slim"){
        env["REPOSITORY_URL"] = "https://maven.pkg.jetbrains.space/mycompany/p/key/my-maven-repo"
        env["UNO_VERSION"] = Params("uno_version")
        shellScript("Build Test Deploy") {
            content = """
                echo Setup ...
                mvn versions:set -DnewVersion=${'$'}UNO_VERSION${'$'}JB_SPACE_EXECUTION_NUMBER
                echo Setting new Version: ${'$'}UNO_VERSION${'$'}JB_SPACE_EXECUTION_NUMBER
                
                echo Build artifacts...
                mvn versions:set -DnewVersion=${'$'}UNO_VERSION${'$'}JB_SPACE_EXECUTION_NUMBER
                mvn package -s settings.xml \
                    -DrepositoryUrl=${'$'}REPOSITORY_URL \
                    -DspaceUsername=${'$'}JB_SPACE_CLIENT_ID \
                    -DspacePassword=${'$'}JB_SPACE_CLIENT_TOKEN
                    
                echo Testing artifacts ...
                echo mvn test -s settings.xml \
                    -DrepositoryUrl=${'$'}REPOSITORY_URL \
                    -DspaceUsername=${'$'}JB_SPACE_CLIENT_ID \
                    -DspacePassword=${'$'}JB_SPACE_CLIENT_TOKEN
                
                echo Publishing Artifacts
                cp target/UNO-Backend-${'$'}UNO_VERSION${'$'}JB_SPACE_EXECUTION_NUMBER.jar $mountDir/share/UNO-Backend-${'$'}UNO_VERSION${'$'}JB_SPACE_EXECUTION_NUMBER.jar
                set -e -x -u
                mvn deploy -s settings.xml \
                    -DrepositoryUrl=${'$'}REPOSITORY_URL \
                    -DspaceUsername=${'$'}JB_SPACE_CLIENT_ID \
                    -DspacePassword=${'$'}JB_SPACE_CLIENT_TOKEN
            """
        }

    }
}