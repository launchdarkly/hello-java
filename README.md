### LaunchDarkly Sample Java Application  ###
We've built a simple console application that demonstrates how LaunchDarkly's SDK works.  Below, you'll find the basic build procedure, but for more comprehensive instructions, you can visit your [Quickstart page](https://app.launchdarkly.com/quickstart#/).
##### Build instructions  #####
0. Get and install [Maven](https://maven.apache.org/download.cgi) if you don't have it already. We're compatible with version 3.
1. Copy your API key and feature flag key from your LaunchDarkly dashboard into `main` 
2. Run `mvn clean compile assembly:single`
3. Then run `java -jar target/hello-java-1.0-SNAPSHOT-jar-with-dependencies.jar`