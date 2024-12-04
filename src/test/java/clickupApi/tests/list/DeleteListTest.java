package clickupApi.tests.list;

import clickupApi.requests.list.CreateListRequest;
import clickupApi.requests.list.DeleteListRequest;
import clickupApi.requests.list.UpdateListRequest;
import clickupApi.requests.space.CreateSpaceRequest;
import clickupApi.requests.space.DeleteSpaceRequest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteListTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateListTest.class);
    private static String spaceName = "Space for List";
    private static String listName = "List With Name";

    private String spaceId;
    private String listId;


    @Test
    void deleteListTest() {

        spaceId = createSpaceStep();
        LOGGER.info("Space created with id {}", spaceId);

        listId = createListStep();
        LOGGER.info("List created with id {}", listId);

        deleteList();
        deleteSpace();

    }

    private String createSpaceStep() {
        JSONObject json = new JSONObject();
        json.put("name", spaceName);

        final Response response = CreateSpaceRequest.createSpace(json);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);

        JsonPath jsonData = response.jsonPath();
        Assertions.assertThat(jsonData.getString("name")).isEqualTo(spaceName);

        return jsonData.getString("id");
    }

    private String createListStep() {
        JSONObject json = new JSONObject();
        json.put("name", listName);

        final Response listResponse = CreateListRequest.createList(json, spaceId);
        Assertions.assertThat(listResponse.statusCode()).isEqualTo(200);

        JsonPath jsonData = listResponse.jsonPath();
        Assertions.assertThat(jsonData.getString("name")).isEqualTo(listName);

        return jsonData.getString("id");
    }

    private void deleteList() {

        final Response deleteListResponse = DeleteListRequest.deleteList(listId);
        Assertions.assertThat(deleteListResponse.statusCode()).isEqualTo(200);


    }

    private void deleteSpace() {

        Response response = DeleteSpaceRequest.deleteSpace(spaceId);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }
}
