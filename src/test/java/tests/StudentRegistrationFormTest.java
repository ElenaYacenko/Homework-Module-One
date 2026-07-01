package tests;

import base.TestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.StudentRegistrationPage;

import static testsData.FormTestData.*;


public class StudentRegistrationFormTest extends TestBase {
    StudentRegistrationPage registrationPage = new StudentRegistrationPage();

    @Test
    @DisplayName("Успешная регистрация с полным заполнением всех полей формы")
    void successfulFullFormFillTest() {

        registrationPage
                .openPage()
                .verifyFormTitle(formTitle)
                .typeFirstNameInput(firstName)
                .typeLastNameInput(lastName)
                .typeEmailInput(userEmail)
                .selectGender(gender)
                .typePhoneNumber(phoneNumber)
                .setDateOfBirth(dobYear, dobMonth, dobDay)
                .typeSubjectsInput(subject)
                .selectHobbiesCheckbox(hobbies)
                .imageUploadPath(imagePath)
                .typeCurrentAddressInput(currentAddress)
                .selectState(state)
                .selectCity(city)
                .clickOnSubmitButton()
                .visibleModalForm()
                .verifyFormTitleModal(successModalTitle)
                .verifyStudentName(fullName)
                .verifyStudentEmail(userEmail)
                .verifyStudentGender(gender)
                .verifyStudentPhone(phoneNumber)
                .verifyStudentDateOfBirth(dobDay, dobMonth, dobYear)
                .verifyStudentSubjects(subject)
                .verifyStudentHobbies(hobbies)
                .verifyStudentPicture(testImagePath)
                .verifyStudentAddress(currentAddress)
                .verifyStudentStateAndCity(state, city);
    }

    @Test
    @DisplayName("Успешная регистрация с заполнением только обязательных полей")
    void requiredFieldsOnlyFormFillTest() {

        registrationPage
                .openPage()
                .verifyFormTitle(formTitle)
                .typeFirstNameInput(firstName)
                .typeLastNameInput(lastName)
                .selectGender(gender)
                .typePhoneNumber(phoneNumber)
                .clickOnSubmitButton()
                .visibleModalForm()
                .verifyFormTitleModal(successModalTitle)
                .verifyStudentName(fullName)
                .verifyStudentGender(gender)
                .verifyStudentPhone(phoneNumber);
    }

    @Test
    @DisplayName("При вводе 1 символа в поле First Name подсвечивается красной рамкой")
    void negativeTestFirstNameOneCharacterRejected() {

        registrationPage
                .openPage()
                .verifyFormTitle(formTitle)
                .typeFirstNameInput(tooShortFirstName)
                .typeLastNameInput(lastName)
                .selectGender(gender)
                .typePhoneNumber(phoneNumber)
                .clickOnSubmitButton()
                .noVisibleModalForm()
                .firstNameBorderError();
    }

    @Test
    @DisplayName("При вводе 257 символов в поле Last Name подсвечивается красной рамкой")
    void negativeTestLastNameExceedsMaxLengthShowsRedBorder() {

        registrationPage
                .openPage()
                .verifyFormTitle(formTitle)
                .typeFirstNameInput(firstName)
                .typeLastNameInput(tooLongLastName)
                .selectGender(gender)
                .typePhoneNumber(phoneNumber)
                .clickOnSubmitButton()
                .noVisibleModalForm()
                .lastNameBorderError();
    }

    @Test
    @DisplayName("При отправке пустой формы обязательные поля подсвечиваются красным")
    void negativeTestEmptyRequiredFieldsShowRedBorders() {

        registrationPage
                .openPage()
                .verifyFormTitle(formTitle)
                .clickOnSubmitButton()
                .firstNameBorderError()
                .lastNameBorderError()
                .firstNameBorderError()
                .phoneNumberBorderError()
                .genderBorderError()
                .noVisibleModalForm();
    }

    @Test
    @DisplayName("Ввод 9 цифр в поле телефона - поле подсвечивается красной рамкой")
    void negativeTestInvalidPhoneNumberShowsRedBorder() {

        registrationPage
                .openPage()
                .verifyFormTitle(formTitle)
                .typeFirstNameInput(firstName)
                .typeLastNameInput(lastName)
                .selectGender(gender)
                .typePhoneNumber(tooShortPhoneNumber)
                .clickOnSubmitButton()
                .phoneNumberBorderError()
                .noVisibleModalForm();

    }

    @Test
    @DisplayName("Ввод букв в поле телефона - поле подсвечивается красной рамкой")
    void negativeTestPhoneNumberWithLettersShowsRedBorder() {

        registrationPage
                .openPage()
                .verifyFormTitle(formTitle)
                .typeFirstNameInput(firstName)
                .typeLastNameInput(lastName)
                .selectGender(gender)
                .typePhoneNumber(phoneWithLetters)
                .clickOnSubmitButton()
                .phoneNumberBorderError()
                .noVisibleModalForm();
    }

    @Test
    @DisplayName("Отправка формы с пустым полем First Name - поле подсвечивается красной рамкой")
    void negativeTestMissingFirstNameShowsRedBorder() {

        registrationPage
                .openPage()
                .verifyFormTitle(formTitle)
                .typeLastNameInput(lastName)
                .selectGender(gender)
                .typePhoneNumber(phoneNumber)
                .clickOnSubmitButton()
                .firstNameBorderError()
                .noVisibleModalForm();
    }

    @Test
    @DisplayName("Отправка формы с пустым полем Last Name - поле подсвечивается красной рамкой")
    void negativeTestMissingLastNameShowsRedBorder() {

        registrationPage
                .openPage()
                .verifyFormTitle(formTitle)
                .typeFirstNameInput(firstName)
                .selectGender(gender)
                .typePhoneNumber(phoneNumber)
                .clickOnSubmitButton()
                .lastNameBorderError()
                .noVisibleModalForm();
    }

    @Test
    @DisplayName("Отправка формы с пустым полем телефона - поле подсвечивается красной рамкой")
    void negativeTestMissingMobileShowsRedBorder() {

        registrationPage
                .openPage()
                .verifyFormTitle(formTitle)
                .typeFirstNameInput(firstName)
                .typeLastNameInput(lastName)
                .selectGender(gender)
                .clickOnSubmitButton()
                .phoneNumberBorderError()
                .noVisibleModalForm();
    }

    @Test
    @DisplayName("Отправка формы при отсутствие выбора Gender - поле подсвечивается красной рамкой")
    void negativeTestUnselectedGenderShowsValidationError() {

        registrationPage
                .openPage()
                .verifyFormTitle(formTitle)
                .typeFirstNameInput(firstName)
                .typeLastNameInput(lastName)
                .typePhoneNumber(phoneNumber)
                .clickOnSubmitButton()
                .genderBorderError()
                .noVisibleModalForm();
    }
}