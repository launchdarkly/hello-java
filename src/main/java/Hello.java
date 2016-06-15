import com.launchdarkly.client.LDClient;
import com.launchdarkly.client.LDUser;

import java.io.IOException;

import static java.util.Collections.singletonList;

public class Hello {

 public static void main(String... args) throws IOException {
   LDClient client = new LDClient("YOUR_API_KEY");

   LDUser user = new LDUser.Builder("bob@example.com")
                           .firstName("Bob")
                           .lastName("Loblaw")
                           .customString("groups", singletonList("beta_testers"))
                           .build();

   boolean showFeature = client.toggle("YOUR_FEATURE_KEY", user, false);

   if (showFeature) {
    System.out.println("Showing your feature");
   } else {
    System.out.println("Not showing your feature");
   }

   client.flush();
   client.close();
 }
}
