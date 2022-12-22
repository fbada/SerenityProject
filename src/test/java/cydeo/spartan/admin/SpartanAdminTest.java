package cydeo.spartan.admin;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static net.serenitybdd.rest.SerenityRest.lastResponse;


@SerenityTest
public class SpartanAdminTest {


    @BeforeAll
    public static  void init(){
        RestAssured.baseURI = "http://44.211.222.236:7000";
    }

    @DisplayName("GET /spartans with PURE REST ASSURED")
    @Test
    public void test1() {

        Response response = given().accept(ContentType.JSON)
                .auth().basic("admin", "admin").
                when().get("/api/spartans").prettyPeek();


        System.out.println("response.path(\"id[0]\") = " + response.path("id[0]"));

        System.out.println("response.statusCode() = " + response.statusCode());

    }

    @DisplayName("GET /spartans with SERENITY REST")
    @Test
    public void test2(){
        SerenityRest.given().accept(ContentType.JSON)
                .auth().basic("admin","admin")
                .pathParam("id",45)
        .when().get("/api/spartans/{id}").prettyPeek();


        // lastResponse --> response --> Serenity Rest will generate after sending request and store resposne information
        // without saving in a variable
        lastResponse();

        System.out.println("lastResponse().statusCode() = " + lastResponse().statusCode());

        //RESPONSE
        System.out.println("lastResponse().path(\"id\") = " + lastResponse().path("id"));

        //JSONPATH
        System.out.println("lastResponse().jsonPath().getInt(\"id\") = " + lastResponse().jsonPath().getInt("id"));


    }

}
