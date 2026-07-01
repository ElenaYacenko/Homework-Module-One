package pages;

import com.codeborne.selenide.SelenideElement;
import pages.components.CalendarComponent;
import pages.components.StateCityComponent;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static testsData.FormTestData.*;

public class StudentRegistrationPage {
    //Компоненты
    CalendarComponent calendar = new CalendarComponent();
    StateCityComponent locationComponent = new StateCityComponent();
    //Элемены формы
    private SelenideElement formTitlePage = $(".practice-form-wrapper h5");
    private SelenideElement firstNameInput = $("#firstName");
    private SelenideElement lastNameInput = $("#lastName");
    private SelenideElement emailInput = $("#userEmail");
    private SelenideElement genderRadioInput = $("#genterWrapper");
    private SelenideElement phoneNumberInput = $("#userNumber");
    private SelenideElement birthInput = $("#dateOfBirthInput");
    private SelenideElement subjectsCompleteInput = $("#subjectsInput");
    private SelenideElement imagePathInput = $("#uploadPicture");
    private SelenideElement currentAddressInput = $("#currentAddress");
    private SelenideElement submitButton = $("#submit");
    private SelenideElement formModal=$(".modal-content");
    private SelenideElement formTitleModal = $("#example-modal-sizes-title-lg");
    private SelenideElement studentName = $x("//tr[td[contains(., 'Student Name')]]/td[2]");
    private SelenideElement studentEmail = $x("//tr[td[contains(., 'Student Email')]]/td[2]");
    private SelenideElement studentGender = $x("//tr[td[contains(., 'Gender')]]/td[2]");
    private SelenideElement studentPhone = $x("//tr[td[contains(., 'Mobile')]]/td[2]");
    private SelenideElement studentDateOfBirth = $x("//tr[td[contains(., 'Date of Birth')]]/td[2]");
    private SelenideElement studentSubjects =  $x("//tr[td[contains(., 'Subjects')]]/td[2]");
    private SelenideElement studentHobbies = $x("//tr[td[contains(., 'Hobbies')]]/td[2]");
    private SelenideElement studentPicture = $x("//tr[td[contains(., 'Picture')]]/td[2]");
    private SelenideElement studentAddress =  $x("//tr[td[contains(., 'Address')]]/td[2]");
    private SelenideElement studentStateAndCity = $x("//tr[td[contains(., 'State and City')]]/td[2]");

    //Действия
    public StudentRegistrationPage openPage() {
        open("/automation-practice-form");
        /* убрать банеры которые перекрывают форму*/
        executeJavaScript(""" 
                document.getElementById('fixedban')?.remove();
                document.querySelector('footer')?.remove();
                """);
        return this;
    }

    public StudentRegistrationPage clickOnSubmitButton() {
        submitButton.scrollTo().click();
        return this;
    }
    public StudentRegistrationPage typesFirstNameInput(String value) {
        firstNameInput.setValue(value);
        return this;
    }
    public StudentRegistrationPage typesLastNameInput(String value) {
        lastNameInput.setValue(value);
        return this;
    }
    public StudentRegistrationPage typesEmailInput(String value) {
        emailInput.setValue(value);
        return this;
    }
    public StudentRegistrationPage selectGender(String value) {
        genderRadioInput.$(byText(value)).click();
        return this;
    }
    public StudentRegistrationPage typesPhoneNumber(String value) {
        phoneNumberInput.setValue(value);
        return this;
    }
    public StudentRegistrationPage setDateOfBirth(String year, String month, String day) {
        birthInput.click();
        calendar.setDate(year, month, day);
        return this;
    }
    public StudentRegistrationPage typesSubjectsInput(String value) {
        subjectsCompleteInput.setValue(value).pressEnter();
        return this;
    }
    public StudentRegistrationPage selectHobbiesCheckbox(String hobbies) {
        $x("//label[contains(text(), '" + hobbies + "')]").click();
        return this;
    }
    public StudentRegistrationPage imageUploadPath(String value) {
        imagePathInput.uploadFromClasspath(value);
        return this;
    }
    public StudentRegistrationPage typesCurrentAddressInput(String value) {
        currentAddressInput.setValue(value);
        return this;
    }
    public StudentRegistrationPage selectState(String state) {
        locationComponent.setStateSelect(state);
        return this;
    }

    public StudentRegistrationPage selectCity(String city) {
        locationComponent.setCitySelect(city);
        return this;
    }
    //проверки
    public StudentRegistrationPage verifyFormTitle(String expectedTitle) {
        formTitlePage.shouldHave(text(expectedTitle));
        return this;
    }
    public StudentRegistrationPage visibleModalForm() {
        formModal.shouldBe(visible);
        return this;
    }
    public StudentRegistrationPage verifyFormTitleModal(String expectedTitle) {
        formTitleModal.shouldHave(text(expectedTitle));
        return this;
    }
    public StudentRegistrationPage verifyStudentName(String expectedTitle) {
        studentName.shouldHave(text(expectedTitle));
        return this;
    }
    public StudentRegistrationPage verifyStudentEmail(String expectedTitle) {
        studentEmail.shouldHave(text(expectedTitle));
        return this;
    }
    public StudentRegistrationPage verifyStudentGender(String expectedTitle) {
        studentGender.shouldHave(text(expectedTitle));
        return this;
    }
    public StudentRegistrationPage verifyStudentPhone(String expectedTitle) {
        studentPhone.shouldHave(text(expectedTitle));
        return this;
    }
    public StudentRegistrationPage verifyStudentDateOfBirth(String day, String month, String year) {
        studentDateOfBirth.shouldHave(text(day + " " + month + "," + year));
        return this;
    }
    public StudentRegistrationPage verifyStudentSubjects(String expectedTitle) {
        studentSubjects.shouldHave(text(expectedTitle));
        return this;
    }
    public StudentRegistrationPage verifyStudentHobbies(String expectedTitle) {
        studentHobbies.shouldHave(text(expectedTitle));
        return this;
    }
    public StudentRegistrationPage verifyStudentPicture(String expectedTitle) {
        studentPicture.shouldHave(text(expectedTitle));
        return this;
    }
    public StudentRegistrationPage verifyStudentAddress(String expectedTitle) {
        studentAddress.shouldHave(text(expectedTitle));
        return this;
    }
    public StudentRegistrationPage verifyStudentStateAndCity(String state, String city) {
        studentStateAndCity.shouldHave(text(state + " " + city));
        return this;
    }
}






