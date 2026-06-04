import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class LoginTests extends TestBase {

    @Test
    @DisplayName("Позитивный тест: успешная авторизация")
    void successfulAuthorizationTest() {
        open("/login.html");

        $("[data-testid=login-input]").setValue("user1");
        $("[data-testid=password-input]").setValue("password1");
        $("[data-testid=submit-button]").click();

        $("[data-testid=welcome-message]").shouldHave(text("Welcome, user1!"));
    }

    @Test
    @DisplayName("Негативный тест: не верный пароль")
    void wrongPasswordAuthorizationTest() {
        open("/login.html");

        $("[data-testid=login-input]").setValue("user1");
        $("[data-testid=password-input]").setValue("WRONG PASSWORD");
        $("[data-testid=submit-button]").click();

        $("[data-testid=error-message]").shouldHave(text("Wrong login or password"));
    }

    @Test
    @DisplayName("Негативный тест: не заполнять пароль")
    void emptyPasswordAuthorizationTest() {
        open("/login.html");

        $("[data-testid=login-input]").setValue("user1");
        $("[data-testid=submit-button]").click();

        $("[data-testid=error-message]").shouldHave(text("Password is required (minimum 6 characters)"));
    }

    @Test
    @DisplayName("Негативный тест: не верный логин")
    void wrongLogindAuthorizationTest () {
        open("/login.html");

        $("[data-testid=login-input]").setValue("WRONG Login");
        $("[data-testid=password-input]").setValue("password1");
        $("[data-testid=submit-button]").click();

        $("[data-testid=error-message]").shouldHave(text("Wrong login or password"));
    }

    @Test
    @DisplayName("Негативный тест: не заполнять логин")
    void emptyLogindAuthorizationTest () {
        open("/login.html");

        $("[data-testid=password-input]").setValue("password1");
        $("[data-testid=submit-button]").click();

        $("[data-testid=error-message]").shouldHave(text("Login is required (minimum 3 characters)"));
    }

    @Test
    @DisplayName("Негативный тест: отправка формы с пустыми полями")
    void emptyRequiredFields () {
        open("/login.html");

        $("[data-testid=submit-button]").click();

        $("[data-testid=error-message]").shouldHave(text("Login and password are required (minimum 3 and 6 characters)"));
    }

    @Test
    @DisplayName("UI тест: отправка формы нажатием клавиши Enter")
    void submitFormByEnterKeyTest() {
        open("/login.html");
        $("[data-testid=login-input]").setValue("user1");
        $("[data-testid=password-input]").setValue("password1").pressEnter();

        $("[data-testid=welcome-message]").shouldHave(text("Welcome, user1!"));
    }

    @Test
    @DisplayName("Безопасность: базовая проверка на XSS в поле логина")
    void xssPreventionInLoginTest() {
        open("/login.html");

        $("[data-testid=login-input]").setValue("<script>alert('XSS')</script>");
        $("[data-testid=password-input]").setValue("password1");
        $("[data-testid=submit-button]").click();

        $("[data-testid=error-message]").shouldHave(text("Wrong login or password"));
    }
}