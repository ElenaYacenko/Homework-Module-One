package tests;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class StudentRegistrationFormTest extends tests.TestBase {

    @Test
     void successfulFullFormFillTest() {
        open("/automation-practice-form.html");
        $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));

        //open("/automation-practice-form");  // если запускать не локально
        /* убрать банеры которые перекрывают форму */
        executeJavaScript("$('#fixedban').remove()");
        executeJavaScript("$('footer').remove()");

        $("[id=firstName]").setValue("Elena");
        $("[id=lastName]").setValue("Ya");
        $("[id=userEmail]").setValue("xxx@ya.ru");
        $("[id='genterWrapper'] input[value='Female']").click();
        $("[id=userNumber]").setValue("9999999999");

        $("[id='dateOfBirthInput']").click();
        $(".react-datepicker__month-select").selectOption("March");
        $(".react-datepicker__year-select").selectOption("2020");
        $(".react-datepicker__day--030").click();

        $("[id=subjectsInput]").setValue("Maths").pressEnter();
        $("[id=hobbies-checkbox-2]").click();
        $("[id=currentAddress]").setValue("RU");
        $("[id=react-select-3-input]").setValue("Haryana").pressEnter();
        $("[id=react-select-4-input]").setValue("Karnal").pressEnter();

        $("[id=submit]").scrollTo().click();

        $(".modal-content").shouldBe(visible);

        $("[id=example-modal-sizes-title-lg]").shouldHave(text("Thanks for submitting the form"));
        $x("//tr[td[contains(., 'Student Name')]]/td[2]").shouldHave(text("Elena Ya"));
        $x("//tr[td[contains(., 'Student Email')]]/td[2]").shouldHave(text("xxx@ya.ru"));
        $x("//tr[td[contains(., 'Gender')]]/td[2]").shouldHave(text("Female"));
        $x("//tr[td[contains(., 'Mobile')]]/td[2]").shouldHave(text("9999999999"));
        $x("//tr[td[contains(., 'Date of Birth')]]/td[2]").shouldHave(text("30 March,2020"));
        $x("//tr[td[contains(., 'Subjects')]]/td[2]").shouldHave(text("Maths"));
        $x("//tr[td[contains(., 'Hobbies')]]/td[2]").shouldHave(text("Reading"));
        $x("//tr[td[contains(., 'Address')]]/td[2]").shouldHave(text("RU"));
        $x("//tr[td[contains(., 'State and City')]]/td[2]").shouldHave(text("Haryana Karnal"));
    }

    @Test
    void requiredFieldsOnlyFormFillTest() {
        open("/automation-practice-form");
        executeJavaScript("document.getElementById('fixedban')?.remove();\n" +
                          "document.querySelector('footer')?.remove();\n");

        $("[id=firstName]").setValue("Elena");
        $("[id=lastName]").setValue("Ya");
        $("[id='genterWrapper'] input[value='Male']").click();
        $("[id=userNumber]").setValue("9999999999");

        $("[id=submit]").scrollTo().click();

        $(".modal-content").shouldBe(visible);

        $("[id=example-modal-sizes-title-lg]").shouldHave(text("Thanks for submitting the form"));
        $x("//tr[td[contains(., 'Student Name')]]/td[2]").shouldHave(text("Elena Ya"));
        $x("//tr[td[contains(., 'Gender')]]/td[2]").shouldHave(text("Male"));
        $x("//tr[td[contains(., 'Mobile')]]/td[2]").shouldHave(text("9999999999"));
    }

    @Test
    void negativeTestFirstNameOneCharacterRejected() {
        open("/automation-practice-form");
        executeJavaScript("document.getElementById('fixedban')?.remove();\n" +
                          "document.querySelector('footer')?.remove();\n");

        $("[id=firstName]").setValue("E");
        $("[id=lastName]").setValue("Ya");
        $("[id='genterWrapper'] input[value='Other']").click();
        $("[id=userNumber]").setValue("9999999999");

        $("[id=submit]").click();

        $("[id=firstName]").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    void negativeTestLastNameExceedsMaxLengthShowsRedBorder() {
        open("/automation-practice-form");
        executeJavaScript("document.getElementById('fixedban')?.remove();\n" +
                          "document.querySelector('footer')?.remove();\n");

        $("[id=firstName]").setValue("Elena");
        $("[id=lastName]").setValue("YaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYa" +
                "YaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYa" +
                "YaYaYaYaYaYa-YaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYaYa" +
                "1234567"); //257 символов
        $("[id='genterWrapper'] input[value='Male']").click();
        $("[id=userNumber]").setValue("9999999999");

        $("[id=submit]").click();

        $("[id=lastName]").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    void negativeTestEmptyRequiredFieldsShowRedBorders() {
        open("/automation-practice-form");
        executeJavaScript("document.getElementById('fixedban')?.remove();\n" +
                          "document.querySelector('footer')?.remove();\n");

        $("[id=submit]").click();

        $("[id=firstName]").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("[id=lastName]").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $("[id=userNumber]").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));

        $("[name=gender][value=Male]").shouldNotBe(checked);
        $("[name=gender][value=Female]").shouldNotBe(checked);
        $("[name=gender][value=Other]").shouldNotBe(checked);
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    void negativeTestInvalidPhoneNumberShowsRedBorder() {
        open("/automation-practice-form");
        executeJavaScript("document.getElementById('fixedban')?.remove();\n" +
                          "document.querySelector('footer')?.remove();\n");

        $("[id=firstName]").setValue("Elena");
        $("[id=lastName]").setValue("Ya");
        $("[id='genterWrapper'] input[value='Male']").click();
        $("[id=userNumber]").setValue("999999999"); // телефон из 9 символов

        $("[id=submit]").click();

        $("[id=userNumber]").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    void negativeTestPhoneNumberWithLettersShowsRedBorder() {
        open("/automation-practice-form");
        executeJavaScript("document.getElementById('fixedban')?.remove();\n" +
                          "document.querySelector('footer')?.remove();\n");

        $("[id=firstName]").setValue("Elena");
        $("[id=lastName]").setValue("Ya");
        $("[id='genterWrapper'] input[value='Female']").click();
        $("[id=userNumber]").setValue("ТелеНомера"); // ввод в поле букв

        $("[id=submit]").click();

        $("[id=userNumber]").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    void negativeTestMissingFirstNameShowsRedBorder(){
        open("/automation-practice-form");
        executeJavaScript("document.getElementById('fixedban')?.remove();\n" +
                          "document.querySelector('footer')?.remove();\n");

        $("[id=lastName]").setValue("Ya");
        $("[id='genterWrapper'] input[value='Female']").click();
        $("[id=userNumber]").setValue("9999999999");

        $("[id=submit]").click();

        $("[id=firstName]").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    void negativeTestMissingLastNameShowsRedBorder() {
        open("/automation-practice-form");
        executeJavaScript("document.getElementById('fixedban')?.remove();\n" +
                          "document.querySelector('footer')?.remove();\n");

        $("[id=firstName]").setValue("Elena");
        $("[id='genterWrapper'] input[value='Male']").click();
        $("[id=userNumber]").setValue("9999999999");

        $("[id=submit]").click();

        $("[id=lastName]").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    void negativeTestMissingMobileShowsRedBorder() {
        open("/automation-practice-form");
        executeJavaScript("document.getElementById('fixedban')?.remove();\n" +
                          "document.querySelector('footer')?.remove();\n");

        $("[id=firstName]").setValue("Elena");
        $("[id=lastName]").setValue("Ya");
        $("[id='genterWrapper'] input[value='Male']").click();

        $("[id=submit]").click();

        $("[id=userNumber]").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    void negativeTestUnselectedGenderShowsValidationError() {
        open("/automation-practice-form");
        executeJavaScript("document.getElementById('fixedban')?.remove();\n" +
                          "document.querySelector('footer')?.remove();\n");

        $("[id=firstName]").setValue("Elena");
        $("[id=lastName]").setValue("Ya");
        $("[id=userNumber]").setValue("9999999999");

        $("[id=submit]").click();

        $("[name=gender][value=Male]").shouldNotBe(checked);
        $("[name=gender][value=Female]").shouldNotBe(checked);
        $("[name=gender][value=Other]").shouldNotBe(checked);
        $(".modal-content").shouldNotBe(visible);
    }
}