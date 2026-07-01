package pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class CalendarComponent {

    private SelenideElement monthInput = $(".react-datepicker__month-select");
    private SelenideElement yearInput = $(".react-datepicker__year-select");

    public void setDate(String year, String month, String day) {
        yearInput.selectOption(year);
        monthInput.selectOption(month);
        $(".react-datepicker__day--0" + day + ":not(.react-datepicker__day--outside-month)").click();
    }
}