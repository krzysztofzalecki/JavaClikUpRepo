package clickupApi.requests.task;

import io.restassured.response.Response;
import org.json.JSONObject;
import clickupApi.requests.BaseRequest;
import clickupApi.url.ClickUpUrl;

import static io.restassured.RestAssured.given;

public class UpdateTaskRequest {

    public static Response updateTask(JSONObject updateTask, String taskId) {

        return given()
                .spec(BaseRequest.requestSpecWithLogs())
                .body(updateTask.toString())
                .when()
                .put(ClickUpUrl.getTaskURL(taskId))
                .then()
                .log().ifError()
                .extract()
                .response();
    }

}
