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
                mvn test -s settings.xml \
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
    container("Deploy to Server","alpine/curl") {
        env["UNO_VERSION"] = Params("uno_version")
        env["BACKEND_SERVER_URL"] = Params("backend_server_url")
        env["BACKEND_SERVER_USER"] = Params("backend_server_user")
        env["BACKEND_SERVER_PW"] = Secrets("backend_server_pw")
        shellScript {
            // SOURCE_PATH is path to the build artifact
            // TARGET_PATH is the destination path in the file repository
            // Note that each run of the build script creates a separate directory (name = build number)
            content = """
                echo Here go your build activities...

                echo Uploading artifacts...
                SOURCE_PATH=$mountDir/share/UNO-Backend-${'$'}UNO_VERSION${'$'}JB_SPACE_EXECUTION_NUMBER.jar
                TARGET_PATH=logs/${'$'}JB_SPACE_EXECUTION_NUMBER/UNO-Backend-${'$'}UNO_VERSION${'$'}JB_SPACE_EXECUTION_NUMBER.jar
                URL=${'$'}BACKEND_SERVER_URL/${'$'}TARGET_PATH
                echo Uploading "${'$'}SOURCE_PATH to ${'$'}URL"
                curl -k "${'$'}URL" --user "${'$'}BACKEND_SERVER_USER:${'$'}BACKEND_SERVER_PW" -T "${'$'}SOURCE_PATH" --ftp-create-dirs
            """
        }
    }
}