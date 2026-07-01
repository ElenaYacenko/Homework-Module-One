import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import base.TestBase;
import pages.TextBoxPage;

import static testsData.FormTestData.*;

public class BoxTest extends TestBase {
    TextBoxPage textBoxPage = new TextBoxPage();

    @Test
    @DisplayName("Отправка формы со всеми заполнеными полями")
    void successfulFillFormTest() {
        textBoxPage
                .openPage()
                .typeUserNameInput(firstName)
                .typeUserEmailInput(userEmail)
                .typeCurrentAddressInput(currentAddress)
                .typePermanentAddressInput(permanentAddress)
                .clickOnSubmitButton()
                .checkField("name", firstName)
                .checkField("email", userEmail)
                .checkField("currentAddress", currentAddress)
                .checkField("permanentAddress", permanentAddress);
    }

    @Test
    @DisplayName("Отправка пустой формы")
    void emptyFormTest() {

        textBoxPage
                .openPage()
                .clickOnSubmitButton()
                .checkField("name", "")
                .checkField("email", "")
                .checkField("currentAddress", "")
                .checkField("permanentAddress", "");
    }

    @Test
    @DisplayName("Отправка формы с не заполнеными полями адресов")
    void emptyAddressTest() {

        textBoxPage
                .openPage()
                .typeUserNameInput(firstName)
                .typeUserEmailInput(userEmail)
                .clickOnSubmitButton()
                .checkField("name", firstName)
                .checkField("email", userEmail)
                .checkField("currentAddress", "")
                .checkField("permanentAddress"," ");
    }

    @Test
    @DisplayName("Валидация: ошибка при вводе email без символа @")
    void invalidEmailWithoutAtSymbolTest() {

        textBoxPage
                .openPage()
                .typeUserNameInput(firstName)
                .typeUserEmailInput(emailWithoutAt)
                .typeCurrentAddressInput(currentAddress)
                .typePermanentAddressInput(permanentAddress)
                .clickOnSubmitButton()
                .verifyEmailFieldHasRedBorder();
    }
}