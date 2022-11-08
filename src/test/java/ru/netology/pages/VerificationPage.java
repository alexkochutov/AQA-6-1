package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id='code'] input");
    private final SelenideElement verifyButton = $("[data-test-id='action-verify']");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification']");
    private final SelenideElement errorContent = $("[data-test-id='error-notification'] .notification__content");
    private final SelenideElement emptyField = $(".input_invalid .input__sub");

    public AccountPage validVerify(DataHelper.VerificationCode code) {
        codeField.setValue(code.getCode());
        verifyButton.click();
        return new AccountPage();
    }

    public String invalidCode(DataHelper.VerificationCode code) {
        codeField.setValue(code.getCode());
        verifyButton.click();
        errorNotification.shouldBe(Condition.visible, Duration.ofSeconds(10));
        return errorContent.getText();
    }

    public String emptyVerificationCode(DataHelper.VerificationCode code) {
        codeField.setValue(code.getCode());
        verifyButton.click();
        emptyField.shouldBe(Condition.visible);
        return emptyField.getText();
    }
}