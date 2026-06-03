package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import java.nio.file.Paths;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {
    @BeforeAll
    static void beforeAll() {

        Configuration.baseUrl = Paths.get("src/test/resources/pages").toUri().toString();
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
//        Configuration.browserVersion = "148.0"; // тестировать в определенной версии браузера
//        Configuration.headless = true;  // не открывает браузер, работает быстрее без открытия
//        Configuration.pageLoadStrategy = "eager";
//        Configuration.timeout = 5000;   // задержка в секундах

        /*Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.timeout = 10000; // default 4000*/
    }

    @AfterEach
    void afterEach() {
        closeWebDriver();
    }
}