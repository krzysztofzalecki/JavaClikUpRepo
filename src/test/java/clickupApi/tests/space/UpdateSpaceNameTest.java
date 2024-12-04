package clickupApi.tests.space;

import clickupApi.dto.requests.UpdateSpaceRequestDto;
import clickupApi.requests.space.CreateSpaceRequest;
import clickupApi.requests.space.DeleteSpaceRequest;
import clickupApi.requests.space.UpdateSpaceRequest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class UpdateSpaceNameTest {

    private static final String SPACE_NAME = "Updated Space";

    @Test
    void updateSpaceNameTest() {

        JSONObject space = new JSONObject();
        space.put("name", SPACE_NAME);

        final Response response = CreateSpaceRequest.createSpace(space);

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(response.jsonPath().getString("name")).isEqualTo(SPACE_NAME);

        final var spaceId = response.jsonPath().getString("id");

        // Update space by Json Object

        JSONObject updateSpace = new JSONObject();
        updateSpace.put("name", "UPDATED SPACE");
        updateSpace.put("color", "#ab5b2c");

        final Response updateResponse = UpdateSpaceRequest.updateSpace(updateSpace, spaceId);
        Assertions.assertThat(updateResponse.statusCode()).isEqualTo(200);

        JsonPath json = updateResponse.jsonPath();
        Assertions.assertThat(json.getString("name")).isEqualTo("UPDATED SPACE");
        Assertions.assertThat(json.getString("color")).isEqualTo("#ab5b2c");

        // Update space by POJO

        UpdateSpaceRequestDto spaceDto = new UpdateSpaceRequestDto();
        spaceDto.setName("New Space Dto");
        spaceDto.setColor("#c96e38");

        final var  updateSpaceResponse = UpdateSpaceRequest.updateSpace(spaceDto, spaceId);
        Assertions.assertThat(updateSpaceResponse.getName()).isEqualTo("New Space Dto");
        Assertions.assertThat(updateSpaceResponse.getColor()).isEqualTo("#c96e38");

        // Delete Space

        final Response deleteRespond = DeleteSpaceRequest.deleteSpace(spaceId);
        Assertions.assertThat(deleteRespond.statusCode()).isEqualTo(200);

    }
}
