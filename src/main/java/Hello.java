import java.io.IOException;

import com.launchdarkly.sdk.*;
import com.launchdarkly.sdk.server.*;

public class Hello {

  // Set SDK_KEY to your LaunchDarkly SDK key.
  static final String SDK_KEY = "";

  // Set FEATURE_FLAG_KEY to the feature flag key you want to evaluate.
  static final String FEATURE_FLAG_KEY = "sample-feature";
  
  private static void showMessage(String s) {
    System.out.println("*** " + s);
    System.out.println();
  }

  private static void printLogo() {
    showMessage("\n        ██       \n" +
                "          ██     \n" +
                "      ████████   \n" +
                "         ███████ \n" +
                "██ LAUNCHDARKLY █\n" +
                "         ███████ \n" +
                "      ████████   \n" +
                "          ██     \n" +
                "        ██       \n");
  }

  public static void main(String... args) throws Exception {
    // CI Mode (./gradlew run --args="CI")
    boolean CIMode = args.length > 0 && args[0].equals("CI");

    System.out.println(CIMode);

    LDConfig config = new LDConfig.Builder().build();

    if (SDK_KEY == null || SDK_KEY.equals("")) {
      showMessage("Please edit Hello.java to set SDK_KEY to your LaunchDarkly SDK key first.");
      System.exit(1);
    }

    final LDClient client = new LDClient(SDK_KEY, config);
    if (client.isInitialized()) {
      showMessage("SDK successfully initialized!");
    } else {
      showMessage("SDK failed to initialize.  Please check your internet connection and SDK credential for any typo.");
      System.exit(1);
    }

    // Set up the evaluation context. This context should appear on your
    // LaunchDarkly contexts dashboard soon after you run the demo.
    final LDContext context = LDContext.builder("example-user-key")
        .name("Sandy")
        .build();

    // Evaluate the feature flag for this context.
    boolean flagValue = client.boolVariation(FEATURE_FLAG_KEY, context, false);
    showMessage("Feature flag '" + FEATURE_FLAG_KEY + "' is " + flagValue + " for this context");

    if (flagValue) {
      printLogo();
    }

    //If this is building for CI, we don't need to keep running the Hello App continously.
    if(CIMode) {
      System.exit(0);
    }

    // We set up a flag change listener so you can see flag changes as you change
    // the flag rules.
    client.getFlagTracker().addFlagChangeListener(event -> {
      if (event.getKey().equals(FEATURE_FLAG_KEY)) {
        boolean value = client.boolVariation(FEATURE_FLAG_KEY, context, false);
        showMessage("Feature flag '" + FEATURE_FLAG_KEY + "' is " + value + " for this context");

        if (value) {
          printLogo();
        }
      }
    });
    showMessage("Listening for feature flag changes.  Use Ctrl+C to terminate.");

    // Here we ensure that when the application terminates, the SDK shuts down
    // cleanly and has a chance to deliver analytics events to LaunchDarkly.
    // If analytics events are not delivered, the context attributes and flag usage
    // statistics may not appear on your dashboard. In a normal long-running
    // application, the SDK would continue running and events would be delivered
    // automatically in the background.
    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
      public void run() {
        try {
          client.close();
        } catch (IOException e) {
          // ignore
        }
      }
    }, "ldclient-cleanup-thread"));

    // Keeps example application alive.
    Object mon = new Object();
    synchronized (mon) {
      mon.wait();
    }
  }
}
