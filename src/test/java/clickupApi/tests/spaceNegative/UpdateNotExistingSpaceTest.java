package clickupApi.tests.spaceNegative;

import clickupApi.requests.space.UpdateSpaceRequest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class UpdateNotExistingSpaceTest {



    @Test
    void updateNotExistingSpaceTest() {

        JSONObject space = new JSONObject();

        final var spaceId = "0";

        space.put("name", "UPDATED SPACE");
        space.put("color", "#ab5b2c");

        final Response updateResponse = UpdateSpaceRequest.updateSpace(space, spaceId);
        Assertions.assertThat(updateResponse.statusCode()).isEqualTo(401);

        JsonPath json = updateResponse.jsonPath();
        Assertions.assertThat(json.getString("err")).isEqualTo("Team not authorized");
        Assertions.assertThat(json.getString("ECODE")).isEqualTo("OAUTH_027");

    }
}
