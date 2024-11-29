package clickupApi.requests.space;

import io.restassured.response.Response;
import org.json.JSONObject;
import clickupApi.properties.ClickUpProperties;
import clickupApi.requests.BaseRequest;
import clickupApi.url.ClickUpUrl;

import static io.restassured.RestAssured.given;

public class CreateSpaceRequest {

    public static Response createSpace(JSONObject space) {

        return given()
                .spec(BaseRequest.requestSpecWithLogs())
                .body(space.toString())
                .when()
                .post(ClickUpUrl.getSpacesUrl(ClickUpProperties.getTeamId()))
                .then()
                .log().ifError()
                .extract()
                .response();
    }
}

