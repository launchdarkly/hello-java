# LaunchDarkly sample Java application

We've built a simple console application that demonstrates how LaunchDarkly's SDK works.

Below, you'll find the basic build procedure, but for more comprehensive instructions, you can visit your [Quickstart page](https://app.launchdarkly.com/quickstart#/) or the [Java SDK reference guide](https://docs.launchdarkly.com/sdk/server-side/java).

## Build instructions

This project uses [Gradle](https://gradle.org/). It requires that Java is already installed on your system (version 8 or higher). It will automatically use the latest release of the LaunchDarkly SDK with major version 7.

1. Set the value of environment variable `LAUNCHDARKLY_SDK_KEY` to your LaunchDarkly SDK key. If there is an existing boolean feature flag in your LaunchDarkly project that you want to evaluate, edit `src/main/java/Hello.java` and set `FEATURE_FLAG_KEY` in code to the flag key.

```sh
  export LAUNCHDARKLY_SDK_KEY=1234567890abcdef
```

```java
  static final String FEATURE_FLAG_KEY = "my-flag";
```

2. On the command line, run `./gradlew run` (or, on Windows, `gradlew run`).

You should receive the message "The <flagKey> feature flag evaluates to <flagValue>." The application will run continuously and react to the flag changes in LaunchDarkly.
