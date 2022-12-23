package cydeo.spartan.editor;

import Utilities.SpartanTestBase;
import Utilities.SpartanUtil;
import io.cucumber.java.af.En;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.json.Json;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.given;
import static net.serenitybdd.rest.SerenityRest.then;
import static org.hamcrest.Matchers.*;

@SerenityTest
public class EditorPOSTTest extends SpartanTestBase {


    @Test
    @DisplayName("POST /spartans with Serenity BDD")
    void test2() {

        Map<String, Object> mapSpartan = SpartanUtil.getRandomSpartanMap ();
        Response response = given ().auth ().basic ( "editor", "editor" )
                .contentType ( ContentType.JSON )
                .body ( mapSpartan )
                .when ()
                .post ( "/spartans" ).prettyPeek ();

        Ensure.that ( "status code is 201", thenResponse -> thenResponse.statusCode ( 201 ) );
        Ensure.that ( "content type is Json", thenResponse -> thenResponse.contentType ( ContentType.JSON ) );
        Ensure.that ( "success message is A Spartan is Born!", thenResponse -> thenResponse.body ( "success", is ( "A Spartan is Born!" ) ) );
        Ensure.that ( "id is not null", thenResponse -> thenResponse.body ( "data.id", is ( notNullValue () ) ) );
        Ensure.that ( "name is correct", thenResponse -> thenResponse.body ( "data.name", is ( mapSpartan.get ( "name" ) ) ) );
        Ensure.that ( "gender is correct", thenResponse -> thenResponse.body ( "data.gender", is ( mapSpartan.get ( "gender" ) ) ) );
        Ensure.that ( "phone is correct", thenResponse -> thenResponse.body ( "data.phone", is ( mapSpartan.get ( "phone" ) ) ) );

        JsonPath json = response.jsonPath ();
        int id = json.getInt ( "data.id" );

        Ensure.that ( "Location header ends with new ID",
                thenResponse -> thenResponse.header ( "Location", endsWith ( String.valueOf ( id ) ) ) );

    }

    @ParameterizedTest(name = "POST Spartan {index} name {0}")
    @CsvFileSource(resources = "/spartansGenerated.csv", numLinesToSkip = 1)
    public void test1(String name, String gender, long phone){

        Map<String, Object> mapSpartan = new LinkedHashMap<> ();
        mapSpartan.put ( "name", name );
        mapSpartan.put ( "gender", gender );
        mapSpartan.put ( "phone", phone );

        given ().auth ().basic ( "editor", "editor" )
                .accept ( ContentType.JSON )
                .contentType ( ContentType.JSON )
                .body ( mapSpartan )
                .when ().post ( "/spartans" ).prettyPeek ();

        Ensure.that ( "Status code is 201: ", thenResponse -> thenResponse.statusCode ( 201 ) );
        Ensure.that ( "Content type is Json: ", thenResponse -> thenResponse.contentType ( ContentType.JSON ) );
        Ensure.that ( "Success message is A Spartan is Born!: ", thenResponse -> thenResponse.body ( "success", is ( "A Spartan is Born!" ) ) );
        Ensure.that ( "ID is not null: ", thenResponse -> thenResponse.body ( "data.id", is ( notNullValue () ) ) );
        Ensure.that ( "Name is correct: ", thenResponse -> thenResponse.body ( "data.name", is ( name ) ) );

    }
}
