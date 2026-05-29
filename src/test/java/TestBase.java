package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {
    @BeforeAll
    static void beforeAll() {
        Configuration.browser = "chrome";
        Configuration.baseUrl = "https://demoqa.com";

        //Configuration.timeout = 10000; // default 4000
    }

    @AfterEach
    void afterEach() {
        closeWebDriver();
    }
}