job("Build, Test, Deploy"){
    container("maven:3-openjdk-8-slim"){
        env["REPOSITORY_URL"] = "https://maven.pkg.jetbrains.space/mycompany/p/key/my-maven-repo"

        shellScript("Build Test Deploy") {
                content = """
                echo Build artifacts...
                set -e -x -u
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
                cp target/UNO-Backend-${'$'}UNO_VERSION${'$'}JB_SPACE_EXECUTION_NUMBER.jar $mountDir/share/artifact/UNO-Backend-${'$'}UNO_VERSION${'$'}JB_SPACE_EXECUTION_NUMBER.jar
                mvn deploy -s settings.xml \
                    -DrepositoryUrl=${'$'}REPOSITORY_URL \
                    -DspaceUsername=${'$'}JB_SPACE_CLIENT_ID \
                    -DspacePassword=${'$'}JB_SPACE_CLIENT_TOKEN
            """
        }
    }
    container("Deploy to Server","alpine/curl") {
        shellScript {
            // SOURCE_PATH is path to the build artifact
            // TARGET_PATH is the destination path in the file repository
            // Note that each run of the build script creates a separate directory (name = build number)
            content = """
                echo Here go your build activities...

                echo Uploading artifacts...
                SOURCE_PATH=$mountDir/share/artifact/UNO-Backend-${'$'}UNO_VERSION${'$'}JB_SPACE_EXECUTION_NUMBER.jar
                TARGET_PATH=logs/${'$'}JB_SPACE_EXECUTION_NUMBER/log.txt
                REPO_URL=https://files.pkg.jetbrains.space/mycompany/p/my-project/filesrepo
                curl -k "${'$'}BACKEND_SERVER_URL\${'$'}TARGET_PATH" --user "${'$'}BACKEND_SERVER_USER:${'$'}BACKEND_SERVER_PW" -T "${'$'}SOURCE_PATH" --ftp-create-dirs
                curl -i -H "Authorization: Bearer ${'$'}JB_SPACE_CLIENT_TOKEN" -F file=@"${'$'}SOURCE_PATH" ${'$'}REPO_URL/${'$'}TARGET_PATH
            """
        }
    }
}