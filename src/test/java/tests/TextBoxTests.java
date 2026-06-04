import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TextBoxTests extends TestBase {

    @Test
    @DisplayName("Позитивный тест: форма с заполнеными полями")
    void successfulFillFormTest() {
        open("/text-box.html");
        $("#userName").setValue("Alex Black");
        $("#userEmail").setValue("alex@black.com");
        $("#currentAddress").setValue("first address 1");
        $("#permanentAddress").setValue("second address 2");
        $("#submit").click();

        $("[id=output] [id=name]").shouldHave(text("Alex Black"));
        $("[id=output] [id=email]").shouldHave(text("alex@black.com"));
        $("[id=output] [id=currentAddress]").shouldHave(text("first address 1"));
        $("[id=output] [id=permanentAddress]").shouldHave(text("second address 2"));
    }

    @Test
    @DisplayName("Негативный тест: отправка пустой формы")
    void emptyFormTest() {
        open("/text-box.html");

        $("#submit").click();

        $("[id=output] [id=name]").shouldHave(text("Name:"));
        $("[id=output] [id=email]").shouldHave(text("Email:"));
        $("[id=output] [id=currentAddress]").shouldHave(text("Current Address :"));
        $("[id=output] [id=permanentAddress]").shouldHave(text("Permananet Address :"));
    }

    @Test
    @DisplayName("Отправка формы с не заполнеными полями адресов")
    void empryAddress() {
        open("/text-box.html");
        $("#userName").setValue("Alex Black");
        $("#userEmail").setValue("alex@black.com");
        $("#submit").click();

        $("[id=output] [id=name]").shouldHave(text("Alex Black"));
        $("[id=output] [id=email]").shouldHave(text("alex@black.com"));
        $("[id=output] [id=currentAddress]").shouldHave(text("Current Address :"));
        $("[id=output] [id=permanentAddress]").shouldHave(text("Permananet Address :"));
    }

    @Test
    @DisplayName("Валидация: ошибка при вводе email без символа @")
    void invalidEmailWithoutAtSymbolTest() {
        open("/text-box.html");

        // Заполняем форму
        $("#userName").setValue("Елена");
        $("#userEmail").setValue("test1mail.ru");
        $("#currentAddress").setValue("Ля ля ФА");
        $("#permanentAddress").setValue("До РЕ Ми Фа");

        $("#submit").click();

        $("#output").shouldNotHave(cssClass("has-content"));
    }
}