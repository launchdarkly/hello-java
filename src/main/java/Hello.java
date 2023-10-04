import com.launchdarkly.logging.LDLogLevel;
import com.launchdarkly.sdk.*;
import com.launchdarkly.sdk.server.*;
import java.time.Duration;

public class Hello {

  // Set SDK_KEY to your LaunchDarkly SDK key.
  static final String SDK_KEY = "YOUR_KEY";
  
  private static void showMessage(String s) {
    System.out.println("*** " + s);
    System.out.println();
  }

  public static void main(String... args) throws Exception {
    if (SDK_KEY.equals("")) {
      showMessage("Please edit Hello.java to set SDK_KEY to your LaunchDarkly SDK key first");
      System.exit(1);
    }

    LDConfig config = new LDConfig.Builder()
      .http(Components.httpConfiguration()
        .connectTimeout(Duration.ofSeconds(0))
        .socketTimeout(Duration.ofSeconds(0)))
      .logging(
        Components.logging()
          .level(LDLogLevel.DEBUG)
      )
      .build();

    LDClient client = new LDClient(SDK_KEY, config);

    if (client.isInitialized()) {
      showMessage("SDK successfully initialized!");
    } else {
      showMessage("SDK failed to initialize");
      // System.exit(1);
    }
    
    while(true) {
      // Set up the evaluation context. This context should appear on your LaunchDarkly contexts
      // dashboard soon after you run the demo.
      LDContext context = LDContext.builder("flying toaster")
                              .name("Sandy")
                              .build();

      String INT_FLAG_KEY = "int-flag";
      int flagValue = client.intVariation(INT_FLAG_KEY, context, 0);

      showMessage("Feature flag '" + INT_FLAG_KEY + "' is " + flagValue + " for this context");

      String STRING_FLAG_KEY = "string-flag";
      String flagValue2 = client.stringVariation(STRING_FLAG_KEY, context, "default");

      showMessage("Feature flag '" + STRING_FLAG_KEY + "' is " + flagValue2 + " for this context");

      // Here we ensure that the SDK shuts down cleanly and has a chance to deliver analytics
      // events to LaunchDarkly before the program exits. If analytics events are not delivered,
      // the context attributes and flag usage statistics will not appear on your dashboard. In
      // a normal long-running application, the SDK would continue running and events would be
      // delivered automatically in the background.
      Thread.sleep(60000);
    }
    // client.close();
  }
}
