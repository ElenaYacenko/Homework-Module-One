import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static testsData.DataFormTest.*;

public class BoxTest extends TestBase {
    @BeforeEach
    void beforeEach() {
        open("/text-box.html");
    }

    @Test
    @DisplayName("Позитивный тест: форма с заполнеными полями")
    void successfulFillFormTest() {

        $("#userName").setValue(firstName);
        $("#userEmail").setValue(userEmail);
        $("#currentAddress").setValue("first address 1");
        $("#permanentAddress").setValue("second address 2");
        $("#submit").click();

        $("[id=output] [id=name]").shouldHave(text(firstName));
        $("[id=output] [id=email]").shouldHave(text(userEmail));
        $("[id=output] [id=currentAddress]").shouldHave(text(currentAddress));
        $("[id=output] [id=permanentAddress]").shouldHave(text(permanentAddress));
    }

    @Test
    @DisplayName("Отправка пустой формы")
    void emptyFormTest() {

        $("#submit").click();

        $("[id=output] [id=name]").shouldHave(text("Name:"));
        $("[id=output] [id=email]").shouldHave(text("Email:"));
        $("[id=output] [id=currentAddress]").shouldHave(text("Current Address :"));
        $("[id=output] [id=permanentAddress]").shouldHave(text("Permananet Address :"));
    }

    @Test
    @DisplayName("Отправка формы с не заполнеными полями адресов")
    void empryAddress() {

        $("#userName").setValue(firstName);
        $("#userEmail").setValue(userEmail);
        $("#submit").click();

        $("[id=output] [id=name]").shouldHave(text(firstName));
        $("[id=output] [id=email]").shouldHave(text(userEmail));
        $("[id=output] [id=currentAddress]").shouldHave(text("Current Address :"));
        $("[id=output] [id=permanentAddress]").shouldHave(text("Permananet Address :"));
    }

    @Test
    @DisplayName("Валидация: ошибка при вводе email без символа @")
    void invalidEmailWithoutAtSymbolTest() {

        $("#userName").setValue(firstName);
        $("#userEmail").setValue(invalidUserEmail);
        $("#currentAddress").setValue(currentAddress);
        $("#permanentAddress").setValue(permanentAddress);

        $("#submit").click();

        $("#output").shouldNotHave(cssClass("has-content"));
    }
}