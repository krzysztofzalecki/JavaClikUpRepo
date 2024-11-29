package clickupApi.requests.task;


import io.restassured.response.Response;
import org.json.JSONObject;
import clickupApi.dto.requests.CreateTaskRequestDto;
import clickupApi.dto.response.CreateTaskResponseDto;
import clickupApi.requests.BaseRequest;
import clickupApi.url.ClickUpUrl;

import static io.restassured.RestAssured.given;

public class CreateTaskRequest {
    public static Response createTask(JSONObject task, String listId) {

        return given()
                .spec(BaseRequest.requestSpecWithLogs())
                .body(task.toString())
                .when()
                .post(ClickUpUrl.getTasksURL(listId))
                .then()
                .log().ifError()
                .extract()
                .response();
    }

    public static CreateTaskResponseDto createTask(CreateTaskRequestDto taskDto, String listId) {

        return given()
                .spec(BaseRequest.requestSpecWithLogs())
                .body(taskDto)
                .when()
                .post(ClickUpUrl.getTasksURL(listId))
                .then()
                .statusCode(200)
                .log().ifError()
                .extract()
                .response()
                .as(CreateTaskResponseDto.class);
    }

}
