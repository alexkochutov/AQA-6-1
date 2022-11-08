package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private final SelenideElement amountField = $("[data-test-id='amount'] input");
    private final SelenideElement fromField = $("[data-test-id='from'] input");
    private final SelenideElement toField = $("[data-test-id='to'] input");
    private final SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private final SelenideElement cancelButton = $("[data-test-id='action-cancel']");

    public AccountPage makeTransfer(int amount, String from) {
        amountField.setValue(String.valueOf(amount));
        fromField.setValue(from);
        transferButton.click();
        return new AccountPage();
    }

    public AccountPage cancelTransfer() {
        return new AccountPage();
    }
}