package clickupApi.requests.task;

import clickupApi.requests.BaseRequest;
import clickupApi.url.ClickUpUrl;
import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class DeleteTaskRequest {

    public static Response deleteTask(JSONObject updateTask, String taskId) {

        return given()
                .spec(BaseRequest.requestSpecWithLogs())
                .body(updateTask.toString())
                .when()
                .delete(ClickUpUrl.getTaskURL(taskId))
                .then()
                .log().ifError()
                .extract()
                .response();
    }
}
