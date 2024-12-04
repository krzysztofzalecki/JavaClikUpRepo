package clickupApi.requests.list;

import clickupApi.requests.BaseRequest;
import clickupApi.url.ClickUpUrl;
import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class UpdateListRequest {

    public static Response updateList(JSONObject list, String listId) {

        return given()
                .spec(BaseRequest.requestSpecWithLogs())
                .body(list.toString())
                .when()
                .put(ClickUpUrl.getListURL(listId))
                .then()
                .log().ifError()
                .extract()
                .response();
    }

}
