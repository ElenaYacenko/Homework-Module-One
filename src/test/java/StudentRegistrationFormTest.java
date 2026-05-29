package tests;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class StudentRegistrationFormTest extends tests.TestBase {

    @Test
    void successfulFullFormFillTest() {
        open("/automation-practice-form");

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

        $("[id=firstName]").setValue("Elena");
        $("[id=lastName]").setValue("Ya");
        $("[value=Male]").click();
        $("[id=userNumber]").setValue("9999999999");

        $("[id=submit]").scrollTo().click();

        $(".modal-content").shouldBe(visible);
        $("[id=example-modal-sizes-title-lg]").shouldHave(text("Thanks for submitting the form"));

        $x("//tr[td[contains(., 'Student Name')]]/td[2]").shouldHave(text("Elena Ya"));
        $x("//tr[td[contains(., 'Gender')]]/td[2]").shouldHave(text("Male"));
        $x("//tr[td[contains(., 'Mobile')]]/td[2]").shouldHave(text("9999999999"));



        //$("[id=output] [id=name]").shouldHave(text("Elena Ya"));
        //$("[id=output] [id=email]").shouldHave(text("Male"));
        //$("[id=output] [id=currentAddress]").shouldHave(text("9999999999"));
    }
}