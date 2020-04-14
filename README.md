### LaunchDarkly Sample Java Application  ###
We've built a simple console application that demonstrates how LaunchDarkly's SDK works.  Below, you'll find the basic build procedure, but for more comprehensive instructions, you can visit your [Quickstart page](https://app.launchdarkly.com/quickstart#/).
##### Build instructions  #####
1. Make sure you have [Maven](https://maven.apache.org/download.cgi) installed. We've tested against Maven 3
2. Create a new project
3. Copy your SDK key and feature flag key from your LaunchDarkly dashboard into `main` 
4. Make sure `pom.xml` is referencing the latest version of `launchdarkly-java-server-sdk`; you can see the latest release [here](https://github.com/launchdarkly/java-server-sdk/releases)
5. Run `mvn clean compile assembly:single`
6. Then run `java -jar target/hello-java-1.0-SNAPSHOT-jar-with-dependencies.jar`
