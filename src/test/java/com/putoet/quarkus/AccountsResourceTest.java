package com.putoet.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class AccountsResourceTest {

    @Test
    void accounts() {
        final Response response = given()
                .when().get("/accounts")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        final List<Map<String,Object>> accounts = response.jsonPath().getList("$");
        assertThat(accounts.size(), equalTo(3));
        for (Map<String,Object> account : accounts)
            assertThat((Integer) account.get("accountNumber"), in(List.of(123456789, 121212121, 545454545)));

        System.out.println(response.body().asString());
    }

    @Test
    void account() {
        final Response response = given()
                .when().get("/accounts/121212121")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        final JsonPath account = response.jsonPath();
        assertThat(account.get("accountNumber"), equalTo(121212121));

        System.out.println(response.body().asString());
    }

    @Test
    void accountNotFound() {
        final Response response = given()
                .when().get("/accounts/abc")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .extract().response();

        System.out.println(response.body().asString());
    }
}