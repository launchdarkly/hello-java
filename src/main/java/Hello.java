import java.io.IOException;

import com.launchdarkly.sdk.*;
import com.launchdarkly.sdk.server.*;

public class Hello {

  // Set SDK_KEY to your LaunchDarkly SDK key.
  static final String SDK_KEY = "";

  // Set FEATURE_FLAG_KEY to the feature flag key you want to evaluate.
  static final String FEATURE_FLAG_KEY = "my-boolean-flag";
  
  private static void showMessage(String s) {
    System.out.println("*** " + s);
    System.out.println();
  }

  public static void main(String... args) throws Exception {
    LDConfig config = new LDConfig.Builder().build();

    final LDClient client = new LDClient(SDK_KEY, config);
    if (client.isInitialized()) {
      showMessage("SDK successfully initialized!");
    } else {
      showMessage("SDK failed to initialize");
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

    // Here we request that the SDK flush pending analytic events so that you see
    // data for the above evaluation on the dashboard immediatelynow rather than
    // at the next automatic flush interval. You don't need to do this under normal
    // circumstances in your own application.
    client.flush();

    // We set up a flag change listener so you can see flag changes as you change
    // the flag rules.
    client.getFlagTracker().addFlagChangeListener(event -> {
      showMessage("Feature flag '" + event.getKey() + "' has changed.");
      if (event.getKey().equals(FEATURE_FLAG_KEY)) {
        boolean value = client.boolVariation(FEATURE_FLAG_KEY, context, false);
        showMessage("Feature flag '" + FEATURE_FLAG_KEY + "' is " + value + " for this context");
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
