package clickupApi.requests.list;

import io.restassured.response.Response;
import org.json.JSONObject;
import clickupApi.requests.BaseRequest;
import clickupApi.url.ClickUpUrl;

import static io.restassured.RestAssured.given;

public class CreateListRequest {

    public static Response createList(JSONObject list, String spaceId) {

        return given()
                .spec(BaseRequest.requestSpecWithLogs())
                .body(list.toString())
                .when()
                .post(ClickUpUrl.getListsUrl(spaceId))
                .then()
                .log().ifError()
                .extract()
                .response();
    }
}
