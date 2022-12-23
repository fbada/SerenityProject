package cydeo.library;

import net.serenitybdd.junit5.SerenityTest;
import org.junit.jupiter.api.Test;


@SerenityTest
public class LibraryTest {

    @Test
    public void libraryTest()
    {
        System.out.println( utilities.ConfigReader.getProperty("base.url"));
        System.out.println( utilities.ConfigReader.getProperty("base.path"));
        System.out.println( utilities.ConfigReader.getProperty("librarian.username"));
        System.out.println( utilities.ConfigReader.getProperty("librarian.password"));
    }
}