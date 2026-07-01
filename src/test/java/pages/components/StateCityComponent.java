package pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class StateCityComponent {
    private SelenideElement stateSelect = $("#react-select-3-input");
    private SelenideElement citySelect = $("#react-select-4-input");

    public void setStateSelect(String state) {
        stateSelect.click();
        $(byText(state)).click();
    }

    public void setCitySelect(String city) {
        citySelect.click();
        $(byText(city)).click();
    }
}
