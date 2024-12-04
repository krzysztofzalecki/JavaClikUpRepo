package clickupApi.tests.space;


import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import clickupApi.requests.space.CreateSpaceRequest;
import clickupApi.requests.space.DeleteSpaceRequest;

class CreateSpaceTest {

    private static final String SPACE_NAME = "MY SPACE FORM JAVA";

    @Test
    void createSpaceTest() {

        JSONObject space = new JSONObject();
        space.put("name", SPACE_NAME);

        // Create Space

        final Response response = CreateSpaceRequest.createSpace(space);

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(response.jsonPath().getString("name")).isEqualTo(SPACE_NAME);

        final var spaceId = response.jsonPath().getString("id");

        // Delete Space

        final Response deleteRespond = DeleteSpaceRequest.deleteSpace(spaceId);
        Assertions.assertThat(deleteRespond.statusCode()).isEqualTo(200);

    }
}