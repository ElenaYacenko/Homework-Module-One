package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static testsData.FormTestData.*;

public class TextBoxPage {
    //Элемены формы
    private final SelenideElement userNameInput = $("#userName");
    private final SelenideElement userEmailInput = $("#userEmail");
    private final SelenideElement userCurrentAddressInput = $("#currentAddress");
    private final SelenideElement userPermanentAddressInput = $("#permanentAddress");
    private final SelenideElement outputResults = $("#output");
    private final SelenideElement submitButton = $("#submit");


    //Действия
    public TextBoxPage openPage() {
        open("/text-box.html");
        return this;
    }

    public TextBoxPage typeUserNameInput(String value) {
        userNameInput.setValue(value);
        return this;
    }

    public TextBoxPage typeUserEmailInput(String value) {
        userEmailInput.setValue(value);
        return this;
    }

    public TextBoxPage typeCurrentAddressInput(String value) {
        userCurrentAddressInput.setValue(value);
        return this;
    }

    public TextBoxPage typePermanentAddressInput(String value) {
        userPermanentAddressInput.setValue(value);
        return this;
    }

    public TextBoxPage clickOnSubmitButton() {
        submitButton.click();
        return this;
    }

    //Проверки
    /*Проверяет, что в блоке результатов отображается ожидаемое значение для указанного поля
    (key   ID поля [id=name], value ожидаемое значение - значение переменной из файла FormTestData*/

    public TextBoxPage checkField(String key, String value) {
        outputResults.$(byId(key)).shouldHave(text(value));
        return this;
    }

    public TextBoxPage verifyEmailFieldHasRedBorder() {
        outputResults.shouldNotHave(cssClass(resultTableClass));
        return this;
    }
}