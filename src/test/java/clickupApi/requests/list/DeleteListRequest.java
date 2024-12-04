package clickupApi.requests.list;

import clickupApi.requests.BaseRequest;
import clickupApi.url.ClickUpUrl;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DeleteListRequest {

    public static Response deleteList(String listId) {

        return given()
                .spec(BaseRequest.requestSpecWithLogs())
                .when()
                .delete(ClickUpUrl.getListURL(listId))
                .then()
                .log().ifError()
                .extract()
                .response();
    }
}
