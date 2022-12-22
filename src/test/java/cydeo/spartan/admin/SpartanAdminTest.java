package cydeo.spartan.admin;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
@SerenityTest
public class SpartanAdminTest {


    @BeforeAll
    public static  void init(){
        RestAssured.baseURI = "http://44.211.222.236:7000";
    }

    @DisplayName("GET /spartans with PURE REST ASSURED")
    @Test
    public void test1() {

        given().accept(ContentType.JSON)
                .auth().basic("admin","admin").
        when().get("/api/spartans").prettyPeek();

    }

    @DisplayName("GET /spartans with SERENITY REST")
    @Test
    public void test2(){
        SerenityRest.given().accept(ContentType.JSON)
                .auth().basic("admin","admin")
                .pathParam("id",45)
        .when().get("/api/spartans/{id}").prettyPeek();

    }






}
