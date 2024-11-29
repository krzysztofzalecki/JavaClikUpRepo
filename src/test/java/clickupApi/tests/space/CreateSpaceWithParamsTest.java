package clickupApi.tests.space;


import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import clickupApi.dto.requests.UpdateSpaceRequestDto;
import clickupApi.requests.space.CreateSpaceRequest;
import clickupApi.requests.space.DeleteSpaceRequest;
import clickupApi.requests.space.UpdateSpaceRequest;

import java.util.stream.Stream;

class CreateSpaceWithParamsTest {


    @ParameterizedTest(name = "Create space with space name: {0}")
    @DisplayName("Create space with valid space name")
    @MethodSource("createUpdateSpaceData")
    void createSpaceTest(String spaceName, String updateSpaceName) {

        JSONObject space = new JSONObject();
        space.put("name", spaceName);

        UpdateSpaceRequestDto spaceDto = new UpdateSpaceRequestDto();
        spaceDto.setName(updateSpaceName);

        final Response response = CreateSpaceRequest.createSpace(space);

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(response.jsonPath().getString("name")).isEqualTo(spaceName);

        final var spaceId = response.jsonPath().getString("id");


        final var updateSpaceRequestDto = UpdateSpaceRequest.updateSpace(spaceDto, spaceId);
        Assertions.assertThat(updateSpaceRequestDto.getName()).isEqualTo(updateSpaceName);

        Response deleteRespond = DeleteSpaceRequest.deleteSpace(spaceId);
        Assertions.assertThat(deleteRespond.statusCode()).isEqualTo(200);

    }

    private static Stream<Arguments> createUpdateSpaceData() {

        return Stream.of(
                Arguments.of("TEST SPACE", "Space 123"),
                Arguments.of("123", "!@#"),
                Arguments.of("*", "0_0")
        );
    }
}
