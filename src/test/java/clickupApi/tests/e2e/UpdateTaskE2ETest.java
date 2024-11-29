package clickupApi.tests.e2e;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import clickupApi.dto.requests.CreateTaskRequestDto;
import clickupApi.requests.list.CreateListRequest;
import clickupApi.requests.space.CreateSpaceRequest;
import clickupApi.requests.space.DeleteSpaceRequest;
import clickupApi.requests.task.CreateTaskRequest;
import clickupApi.requests.task.UpdateTaskRequest;

class UpdateTaskE2ETest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateTaskE2ETest.class);
    private static String spaceName = "SpaceE2E";
    private static String listName = "Tasks";
    private static String taskName = "Test ClickUp";
    private String spaceId;
    private String listId;
    private String tasktId;

    @Test
    void updateTaskE2ETest() {
        spaceId = createSpaceStep();
        LOGGER.info("Space created with id {}", spaceId);


        listId = createListStep();
        LOGGER.info("List created with id {}", listId);


        tasktId = createTaskStep();
        LOGGER.info("Task created with id {}", tasktId);

        updateTaskStep();
        closeTaskStep();
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

    private String createTaskStep() {

        CreateTaskRequestDto taskDto = new CreateTaskRequestDto();
        taskDto.setName(taskName);
        taskDto.setDescription("Test description");
        taskDto.setStatus("to do");
        taskDto.setPriority("3");

        final var response = CreateTaskRequest.createTask(taskDto, listId);

        Assertions.assertThat(response.getName()).isEqualTo(taskName);
        Assertions.assertThat(response.getDescription()).isEqualTo("Test description");

        return response.getId();
    }

    private void updateTaskStep() {

        JSONObject updateTask = new JSONObject();
        updateTask.put("name", "New task name");
        updateTask.put("description", "New description");
        updateTask.put("assignees", "");
        updateTask.put("status", "to do");
        updateTask.put("priority", "3");

        final var response = UpdateTaskRequest.updateTask(updateTask, tasktId);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);

        JsonPath jsonData = response.jsonPath();
        Assertions.assertThat(jsonData.getString("name")).isEqualTo("New task name");
        Assertions.assertThat(jsonData.getString("description")).isEqualTo("New description");

    }

    private void closeTaskStep() {

        JSONObject closeTask = new JSONObject();
        closeTask.put("status", "complete");

        Response response = UpdateTaskRequest.updateTask(closeTask, tasktId);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);

        JsonPath jsonData = response.jsonPath();
        Assertions.assertThat(jsonData.getString("status.status")).isEqualTo("complete");
    }

    private void deleteSpace() {

        Response response = DeleteSpaceRequest.deleteSpace(spaceId);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }
}
