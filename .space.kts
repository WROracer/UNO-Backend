job("Build, Test, Deploy"){
    container("Build, Test"){
        env["REPOSITORY_URL"] = "https://maven.pkg.jetbrains.space/mycompany/p/key/my-maven-repo"

        shellScript("Build") {
                content = """
                echo Build artifacts...
                set -e -x -u
                mvn versions:set -DnewVersion=${'$'}uno_version${'$'}JB_SPACE_EXECUTION_NUMBER
                mvn package -s settings.xml \
                    -DrepositoryUrl=${'$'}REPOSITORY_URL \
                    -DspaceUsername=${'$'}JB_SPACE_CLIENT_ID \
                    -DspacePassword=${'$'}JB_SPACE_CLIENT_TOKEN
            """
        }
        shellScript("Test"){
            content = """
                echo Build artifacts...
                set -e -x -u
                mvn versions:set -DnewVersion=${'$'}uno_version${'$'}JB_SPACE_EXECUTION_NUMBER
                mvn test -s settings.xml \
                    -DrepositoryUrl=${'$'}REPOSITORY_URL \
                    -DspaceUsername=${'$'}JB_SPACE_CLIENT_ID \
                    -DspacePassword=${'$'}JB_SPACE_CLIENT_TOKEN
            """
        }

        shellScript("Publish") {
            content = """
                echo Build and publish artifacts...
                set -e -x -u
                mvn versions:set -DnewVersion=${'$'}uno_version${'$'}JB_SPACE_EXECUTION_NUMBER
                cp target/UNO-Backend-${'$'}uno_version${'$'}JB_SPACE_EXECUTION_NUMBER.jar $mountDir/share/artifact/UNO-Backend-${'$'}uno_version${'$'}JB_SPACE_EXECUTION_NUMBER.jar
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
                SOURCE_PATH=$mountDir/share/artifact/UNO-Backend-${'$'}uno_version${'$'}JB_SPACE_EXECUTION_NUMBER.jar
                TARGET_PATH=logs/${'$'}JB_SPACE_EXECUTION_NUMBER/log.txt
                REPO_URL=https://files.pkg.jetbrains.space/mycompany/p/my-project/filesrepo
                curl -k "${'$'}backend_server_url\${'$'}TARGET_PATH" --user "${'$'}	backend_server_user:${'$'}backend_server_pw" -T "${'$'}SOURCE_PATH" --ftp-create-dirs
                curl -i -H "Authorization: Bearer ${'$'}JB_SPACE_CLIENT_TOKEN" -F file=@"${'$'}SOURCE_PATH" ${'$'}REPO_URL/${'$'}TARGET_PATH
            """
        }
    }
}