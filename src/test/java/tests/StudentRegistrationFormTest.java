package tests;

import base.TestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static testsData.FormTestData.*;


public class StudentRegistrationFormTest extends TestBase {

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

        $(".practice-form-wrapper").shouldHave(text(formTitle));

        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(userEmail);
        $("#genterWrapper").$(byText(gender)).click();
        $("#userNumber").setValue(phoneNumber);
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption(dobMonth);
        $(".react-datepicker__year-select").selectOption(dobYear);
        $(".react-datepicker__day--0" + dobDay).click();
        $("#subjectsInput").setValue(subject).pressEnter();
        $x("//label[contains(text(), '" + hobbies + "')]").click();
        $("#uploadPicture").uploadFromClasspath(ImagePath);
        $("#currentAddress").setValue(currentAddress);
        $("#react-select-3-input").setValue(state).pressEnter();
        $("#react-select-4-input").setValue(city).pressEnter();
        $("#submit").scrollTo().click();

        $(".modal-content").shouldBe(visible);

        $("#example-modal-sizes-title-lg").shouldHave(text(successModalTitle));
        $x("//tr[td[contains(., 'Student Name')]]/td[2]").shouldHave(text(fullName));
        $x("//tr[td[contains(., 'Student Email')]]/td[2]").shouldHave(text(userEmail));
        $x("//tr[td[contains(., 'Gender')]]/td[2]").shouldHave(text(gender));
        $x("//tr[td[contains(., 'Mobile')]]/td[2]").shouldHave(text(phoneNumber));
        $x("//tr[td[contains(., 'Date of Birth')]]/td[2]").shouldHave(text(dobDay + " " + dobMonth + "," + dobYear));
        $x("//tr[td[contains(., 'Subjects')]]/td[2]").shouldHave(text(subject));
        $x("//tr[td[contains(., 'Hobbies')]]/td[2]").shouldHave(text(hobbies));
        $x("//tr[td[contains(., 'Picture')]]/td[2]").shouldHave(text(testImagePath));
        $x("//tr[td[contains(., 'Address')]]/td[2]").shouldHave(text(currentAddress));
        $x("//tr[td[contains(., 'State and City')]]/td[2]").shouldHave(text(state + " " + city));
    }

    @Test
    @DisplayName("Успешная регистрация с заполнением только обязательных полей")
    void requiredFieldsOnlyFormFillTest() {

        $(".practice-form-wrapper").shouldHave(text(formTitle));

        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#genterWrapper").$(byText(gender)).click();
        $("#userNumber").setValue(phoneNumber);
        $("#submit").scrollTo().click();

        $(".modal-content").shouldBe(visible);

        $("#example-modal-sizes-title-lg").shouldHave(text(successModalTitle));
        $x("//tr[td[contains(., 'Student Name')]]/td[2]").shouldHave(text(fullName));
        $x("//tr[td[contains(., 'Gender')]]/td[2]").shouldHave(text(gender));
        $x("//tr[td[contains(., 'Mobile')]]/td[2]").shouldHave(text(phoneNumber));
    }

    @Test
    @DisplayName("При вводе 1 символа в поле First Name подсвечивается красной рамкой")
    void negativeTestFirstNameOneCharacterRejected() {

        $(".practice-form-wrapper").shouldHave(text(formTitle));

        $("#firstName").setValue(tooShortFirstName);
        $("#lastName").setValue(lastName);
        $("#genterWrapper").$(byText(gender)).click();
        $("#userNumber").setValue(phoneNumber);
        $("#submit").click();

        $("#firstName").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    @DisplayName("При вводе 257 символов в поле Last Name подсвечивается красной рамкой")
    void negativeTestLastNameExceedsMaxLengthShowsRedBorder() {

        $(".practice-form-wrapper").shouldHave(text(formTitle));

        $("#firstName").setValue(firstName);
        $("#lastName").setValue(tooLongLastName);
        $("#genterWrapper").$(byText(gender)).click();
        $("#userNumber").setValue(phoneNumber);
        $("#submit").click();

        $("#lastName").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    @DisplayName("При отправке пустой формы обязательные поля подсвечиваются красным")
    void negativeTestEmptyRequiredFieldsShowRedBorders() {

        $(".practice-form-wrapper").shouldHave(text(formTitle));

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

        $(".practice-form-wrapper").shouldHave(text(formTitle));

        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#genterWrapper").$(byText(gender)).click();
        $("#userNumber").setValue(tooShortPhoneNumber); // телефон из 9 символов
        $("#submit").click();

        $("#userNumber").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    @DisplayName("Ввод букв в поле телефона - поле подсвечивается красной рамкой")
    void negativeTestPhoneNumberWithLettersShowsRedBorder() {

        $(".practice-form-wrapper").shouldHave(text(formTitle));

        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#genterWrapper").$(byText(gender)).click();
        $("#userNumber").setValue(phoneWithLetters); // ввод в поле букв
        $("#submit").click();

        $("#userNumber").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    @DisplayName("Отправка формы с пустым полем First Name - поле подсвечивается красной рамкой")
    void negativeTestMissingFirstNameShowsRedBorder() {

        $(".practice-form-wrapper").shouldHave(text(formTitle));

        $("#lastName").setValue(lastName);
        $("#genterWrapper").$(byText(gender)).click();
        $("#userNumber").setValue(phoneNumber);
        $("#submit").click();

        $("#firstName").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    @DisplayName("Отправка формы с пустым полем Last Name - поле подсвечивается красной рамкой")
    void negativeTestMissingLastNameShowsRedBorder() {

        $(".practice-form-wrapper").shouldHave(text(formTitle));

        $("#firstName").setValue(firstName);
        $("#genterWrapper").$(byText(gender)).click();
        $("#userNumber").setValue(phoneNumber);
        $("#submit").click();

        $("#lastName").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    @DisplayName("Отправка формы с пустым полем телефона - поле подсвечивается красной рамкой")
    void negativeTestMissingMobileShowsRedBorder() {

        $(".practice-form-wrapper").shouldHave(text(formTitle));

        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#genterWrapper").$(byText(gender)).click();
        $("#submit").click();

        $("#userNumber").shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    @DisplayName("Отправка формы при отсутствие выбора Gender - поле подсвечивается красной рамкой")
    void negativeTestUnselectedGenderShowsValidationError() {

        $(".practice-form-wrapper").shouldHave(text(formTitle));

        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userNumber").setValue(phoneNumber);
        $("#submit").click();

        $("[name=gender][value=Male]").shouldNotBe(checked);
        $("[name=gender][value=Female]").shouldNotBe(checked);
        $("[name=gender][value=Other]").shouldNotBe(checked);
        $(".modal-content").shouldNotBe(visible);
    }
}