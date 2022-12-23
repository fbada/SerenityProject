package cydeo.spartan.admin;

import Utilities.SpartanTestBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.hamcrest.Matchers.equalTo;


@SerenityTest
public class SpartanAdminTest extends SpartanTestBase {




    @DisplayName("GET /spartans with PURE REST ASSURED")
    @Test
    public void test1() {

        given().accept(ContentType.JSON)
                .auth().basic("admin", "admin").
                when().get("/spartans").prettyPeek()
                .then().statusCode(200);


      //  System.out.println("response.path(\"id[0]\") = " + response.path("id[0]"));

      //  System.out.println("response.statusCode() = " + response.statusCode());

    }

    @DisplayName("GET /spartans with SERENITY REST")
    @Test
    public void test2(){
        SerenityRest.given().accept(ContentType.JSON)
                .auth().basic("admin","admin")
                .pathParam("id",3)
        .when().get("/spartans/{id}").prettyPeek();


        // lastResponse --> response --> Serenity Rest will generate after sending request and store resposne information
        // without saving in a variable
        lastResponse();

        System.out.println("lastResponse().statusCode() = " + lastResponse().statusCode());

        //RESPONSE
        System.out.println("lastResponse().path(\"id\") = " + lastResponse().path("id"));

        //JSONPATH
        System.out.println("lastResponse().jsonPath().getInt(\"id\") = " + lastResponse().jsonPath().getInt("id"));


        // ASSERTIONS IN SERENITY WAY
        /*
            Ensure.that --> it is method that comes from Serenity to put asssertions into Serenity Report in a good way
                             Descriptionn --> will appear into report just like a step information
                             vRes->vRes.statusCode(200) --> it will validate resposne based on provided assertions


         */


        Ensure.that("Status code is 200",x->x.statusCode(200));

        // Ensure that content type is CONTENT TYPE JSON
        Ensure.that("Content type is JSON",x->x.contentType(ContentType.JSON));


        // Ensure that ID  is 45
        Ensure.that("ID is 3",x->x.body("id",equalTo(3)));
    }

}
