package tests;

import com.codeborne.selenide.selector.ByText;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class StudentRegistrationFormTest extends tests.TestBase {

    @Test
    void successfulFullFormFillTest() {
        open("/automation-practice-form");
        $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        /* убрать банеры которые перекрывают форму*/
        executeJavaScript(""" 
                document.getElementById('fixedban')?.remove();
                document.querySelector('footer')?.remove();
                """);

        $("#firstName").setValue("Elena");
        $("#lastName").setValue("Ya");
        $("#userEmail").setValue("xxx@ya.ru");
        $("#genterWrapper").$(byText("Female")).click();
        $("#userNumber").setValue("9999999999");

        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption("March");
        $(".react-datepicker__year-select").selectOption("2020");
        $(".react-datepicker__day--030").click();

        $("#subjectsInput").setValue("Maths").pressEnter();
        $("#hobbies-checkbox-2").click();
        $("#uploadPicture").uploadFromClasspath("img/1.png");
        $("#currentAddress").setValue("RU");
        $("#react-select-3-input").setValue("Haryana").pressEnter();
        $("#react-select-4-input").setValue("Karnal").pressEnter();

        $("#submit").scrollTo().click();

        $(".modal-content").shouldBe(visible);

        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        $x("//tr[td[contains(., 'Student Name')]]/td[2]").shouldHave(text("Elena Ya"));
        $x("//tr[td[contains(., 'Student Email')]]/td[2]").shouldHave(text("xxx@ya.ru"));
        $x("//tr[td[contains(., 'Gender')]]/td[2]").shouldHave(text("Female"));
        $x("//tr[td[contains(., 'Mobile')]]/td[2]").shouldHave(text("9999999999"));
        $x("//tr[td[contains(., 'Date of Birth')]]/td[2]").shouldHave(text("30 March,2020"));
        $x("//tr[td[contains(., 'Subjects')]]/td[2]").shouldHave(text("Maths"));
        $x("//tr[td[contains(., 'Hobbies')]]/td[2]").shouldHave(text("Reading"));
        $x("//tr[td[contains(., 'Picture')]]/td[2]").shouldHave(text("1.png"));
        $x("//tr[td[contains(., 'Address')]]/td[2]").shouldHave(text("RU"));
        $x("//tr[td[contains(., 'State and City')]]/td[2]").shouldHave(text("Haryana Karnal"));
    }

    @Test
    void requiredFieldsOnlyFormFillTest() {
        open("/automation-practice-form");
        $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));

        $("#firstName").setValue("Elena");
        $("#lastName").setValue("Ya");
        $("#genterWrapper").$(byText("Male")).click();
        $("#userNumber").setValue("9999999999");

        $("#submit").scrollTo().click();

        $(".modal-content").shouldBe(visible);

        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        $x("//tr[td[contains(., 'Student Name')]]/td[2]").shouldHave(text("Elena Ya"));
        $x("//tr[td[contains(., 'Gender')]]/td[2]").shouldHave(text("Male"));
        $x("//tr[td[contains(., 'Mobile')]]/td[2]").shouldHave(text("9999999999"));
    }

    @Test
    void negativeTestFirstNameOneCharacterRejected() {
        open("/automation-practice-form");
        $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));

        $("#firstName").setValue("E");
        $("#lastName").setValue("Ya");
        $("#genterWrapper").$(byText("Other")).click();
        $("#userNumber").setValue("9999999999");

        $("#submit").click();

        $("#firstName").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    void negativeTestLastNameExceedsMaxLengthShowsRedBorder() {
        open("/automation-practice-form");
        $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));

        $("#firstName").setValue("Elena");
        $("#lastName").setValue("YaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYa" +
                "YaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYa" +
                "YaYaYaYaYaYa-YaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYa" +
                "1234567"); //257 символов
        $("#genterWrapper").$(byText("Male")).click();
        $("#userNumber").setValue("9999999999");

        $("#submit").click();

        $("#lastName").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    void negativeTestEmptyRequiredFieldsShowRedBorders() {
        open("/automation-practice-form");
        $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));

        $("#submit").click();

        $("#firstName").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("#lastName").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("#userNumber").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));

        $("[name=gender][value=Male]").shouldNotBe(checked);
        $("[name=gender][value=Female]").shouldNotBe(checked);
        $("[name=gender][value=Other]").shouldNotBe(checked);
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    void negativeTestInvalidPhoneNumberShowsRedBorder() {
        open("/automation-practice-form");
        $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));

        $("#firstName").setValue("Elena");
        $("#lastName").setValue("Ya");
        $("#genterWrapper").$(byText("Male")).click();
        $("#userNumber").setValue("999999999"); // телефон из 9 символов

        $("#submit").click();

        $("#userNumber").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    void negativeTestPhoneNumberWithLettersShowsRedBorder() {
        open("/automation-practice-form");
        $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));

        $("#firstName").setValue("Elena");
        $("#lastName").setValue("Ya");
        $("#genterWrapper").$(byText("Female")).click();
        $("#userNumber").setValue("ТелеНомера"); // ввод в поле букв

        $("#submit").click();

        $("#userNumber").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    void negativeTestMissingFirstNameShowsRedBorder() {
        open("/automation-practice-form");
        $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));

        $("#lastName").setValue("Ya");
        $("#genterWrapper").$(byText("Female")).click();
        $("#userNumber").setValue("9999999999");

        $("#submit").click();

        $("#firstName").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    void negativeTestMissingLastNameShowsRedBorder() {
        open("/automation-practice-form");
        $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));

        $("#firstName").setValue("Elena");
        $("#genterWrapper").$(byText("Male")).click();
        $("#userNumber").setValue("9999999999");

        $("#submit").click();

        $("#lastName").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    void negativeTestMissingMobileShowsRedBorder() {
        open("/automation-practice-form");
        $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));

        $("#firstName").setValue("Elena");
        $("#lastName").setValue("Ya");
        $("#genterWrapper").$(byText("Male")).click();

        $("#submit").click();

        $("#userNumber").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    void negativeTestUnselectedGenderShowsValidationError() {
        open("/automation-practice-form");
        $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));

        $("#firstName").setValue("Elena");
        $("#lastName").setValue("Ya");
        $("#userNumber").setValue("9999999999");

        $("#submit").click();

        $("[name=gender][value=Male]").shouldNotBe(checked);
        $("[name=gender][value=Female]").shouldNotBe(checked);
        $("[name=gender][value=Other]").shouldNotBe(checked);
        $(".modal-content").shouldNotBe(visible);
    }
}