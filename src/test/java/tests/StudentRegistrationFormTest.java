package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static testsData.DataFormTest.*;


public class StudentRegistrationFormTest extends tests.TestBase {

    @BeforeEach
    void beforeEach() {
        open("/automation-practice-form");
        /* убрать банеры которые перекрывают форму*/
        executeJavaScript(""" 
                document.getElementById('fixedban')?.remove();
                document.querySelector('footer')?.remove();
                """);
    }

    @Test
    @DisplayName("Успешная регистрация с полным заполнением всех полей формы")
    void successfulFullFormFillTest() {

        $(".practice-form-wrapper").shouldHave(text(nameOfTheForm));

        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(userEmail);
        $("#genterWrapper").$(byText(genterWrapper)).click();
        $("#userNumber").setValue(userNumber);
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption(month);
        $(".react-datepicker__year-select").selectOption(year);
        $(".react-datepicker__day--0" + day).click();
        $("#subjectsInput").setValue(subject).pressEnter();
        $("#hobbies-checkbox-2").click();
        $("#uploadPicture").uploadFromClasspath("img/1.png");
        $("#currentAddress").setValue(currentAddress);
        $("#react-select-3-input").setValue(country).pressEnter();
        $("#react-select-4-input").setValue(city).pressEnter();
        $("#submit").scrollTo().click();

        $(".modal-content").shouldBe(visible);

        $("#example-modal-sizes-title-lg").shouldHave(text(nameOfTheFinalForm));
        $x("//tr[td[contains(., 'Student Name')]]/td[2]").shouldHave(text(fullName));
        $x("//tr[td[contains(., 'Student Email')]]/td[2]").shouldHave(text(userEmail));
        $x("//tr[td[contains(., 'Gender')]]/td[2]").shouldHave(text(genterWrapper));
        $x("//tr[td[contains(., 'Mobile')]]/td[2]").shouldHave(text(userNumber));
        $x("//tr[td[contains(., 'Date of Birth')]]/td[2]").shouldHave(text(day + " " + month + "," + year));
        $x("//tr[td[contains(., 'Subjects')]]/td[2]").shouldHave(text(subject));
        $x("//tr[td[contains(., 'Hobbies')]]/td[2]").shouldHave(text(hobbies));
        $x("//tr[td[contains(., 'Picture')]]/td[2]").shouldHave(text("1.png"));
        $x("//tr[td[contains(., 'Address')]]/td[2]").shouldHave(text(currentAddress));
        $x("//tr[td[contains(., 'State and City')]]/td[2]").shouldHave(text(country + " " + city));
    }

    @Test
    @DisplayName("Успешная регистрация с заполнением только обязательных полей")
    void requiredFieldsOnlyFormFillTest() {

        $(".practice-form-wrapper").shouldHave(text(nameOfTheForm));

        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#genterWrapper").$(byText(genterWrapper)).click();
        $("#userNumber").setValue(userNumber);
        $("#submit").scrollTo().click();

        $(".modal-content").shouldBe(visible);

        $("#example-modal-sizes-title-lg").shouldHave(text(nameOfTheFinalForm));
        $x("//tr[td[contains(., 'Student Name')]]/td[2]").shouldHave(text(fullName));
        $x("//tr[td[contains(., 'Gender')]]/td[2]").shouldHave(text(genterWrapper));
        $x("//tr[td[contains(., 'Mobile')]]/td[2]").shouldHave(text(userNumber));
    }

    @Test
    @DisplayName("При вводе 1 символа в поле First Name подсвечивается красной рамкой")
    void negativeTestFirstNameOneCharacterRejected() {

        $(".practice-form-wrapper").shouldHave(text(nameOfTheForm));

        $("#firstName").setValue(invalidFirstName);
        $("#lastName").setValue(lastName);
        $("#genterWrapper").$(byText(genterWrapper)).click();
        $("#userNumber").setValue(userNumber);
        $("#submit").click();

        $("#firstName").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    @DisplayName("При вводе 257 символов в поле Last Name подсвечивается красной рамкой")
    void negativeTestLastNameExceedsMaxLengthShowsRedBorder() {

        $(".practice-form-wrapper").shouldHave(text(nameOfTheForm));

        $("#firstName").setValue(firstName);
        $("#lastName").setValue(invalidLastName);
        $("#genterWrapper").$(byText(genterWrapper)).click();
        $("#userNumber").setValue(userNumber);
        $("#submit").click();

        $("#lastName").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    @DisplayName("При отправке пустой формы обязательные поля подсвечиваются красным")
    void negativeTestEmptyRequiredFieldsShowRedBorders() {

        $(".practice-form-wrapper").shouldHave(text(nameOfTheForm));

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
    @DisplayName("Ввод 9 цифр в поле телефона - поле подсвечивается красной рамкой")
    void negativeTestInvalidPhoneNumberShowsRedBorder() {

        $(".practice-form-wrapper").shouldHave(text(nameOfTheForm));

        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#genterWrapper").$(byText(genterWrapper)).click();
        $("#userNumber").setValue(invalidUserNumber); // телефон из 9 символов
        $("#submit").click();

        $("#userNumber").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    @DisplayName("Ввод букв в поле телефона - поле подсвечивается красной рамкой")
    void negativeTestPhoneNumberWithLettersShowsRedBorder() {

        $(".practice-form-wrapper").shouldHave(text(nameOfTheForm));

        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#genterWrapper").$(byText(genterWrapper)).click();
        $("#userNumber").setValue(userNumberNotNumbers); // ввод в поле букв
        $("#submit").click();

        $("#userNumber").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    @DisplayName("Отправка формы с пустым полем First Name - поле подсвечивается красной рамкой")
    void negativeTestMissingFirstNameShowsRedBorder() {

        $(".practice-form-wrapper").shouldHave(text(nameOfTheForm));

        $("#lastName").setValue(lastName);
        $("#genterWrapper").$(byText(genterWrapper)).click();
        $("#userNumber").setValue(userNumber);
        $("#submit").click();

        $("#firstName").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    @DisplayName("Отправка формы с пустым полем Last Name - поле подсвечивается красной рамкой")
    void negativeTestMissingLastNameShowsRedBorder() {

        $(".practice-form-wrapper").shouldHave(text(nameOfTheForm));

        $("#firstName").setValue(firstName);
        $("#genterWrapper").$(byText(genterWrapper)).click();
        $("#userNumber").setValue(userNumber);
        $("#submit").click();

        $("#lastName").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    @DisplayName("Отправка формы с пустым полем телефона - поле подсвечивается красной рамкой")
    void negativeTestMissingMobileShowsRedBorder() {

        $(".practice-form-wrapper").shouldHave(text(nameOfTheForm));

        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#genterWrapper").$(byText(genterWrapper)).click();
        $("#submit").click();

        $("#userNumber").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    @DisplayName("Отправка формы при отсутствие выбора Gender - поле подсвечивается красной рамкой")
    void negativeTestUnselectedGenderShowsValidationError() {

        $(".practice-form-wrapper").shouldHave(text(nameOfTheForm));

        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userNumber").setValue(userNumber);
        $("#submit").click();

        $("[name=gender][value=Male]").shouldNotBe(checked);
        $("[name=gender][value=Female]").shouldNotBe(checked);
        $("[name=gender][value=Other]").shouldNotBe(checked);
        $(".modal-content").shouldNotBe(visible);
    }
}