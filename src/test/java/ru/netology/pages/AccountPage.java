package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class AccountPage {
    private final ElementsCollection cardList = $$("li.list__item");
    private final SelenideElement refreshButton = $("[data-test-id='action-reload']");

    private SelenideElement getCardElement(String cardNumber) {
        String cardId = cardNumber.substring(15);
        return cardList.findBy(Condition.text(cardId));
    }

    private int extractBalance(String text) {
        String balanceStart = "баланс: ";
        String balanceFinish = " р.";
        int start = text.indexOf(balanceStart) + balanceStart.length();
        int finish = text.indexOf(balanceFinish);
        String value = text.substring(start, finish);
        return Integer.parseInt(value);
    }

    public int getBalance(String cardNumber) {
        String text = getCardElement(cardNumber).getText();
        return extractBalance(text);
    }

    public TransferPage chooseCard(DataHelper.CardInfo cardTo) {
        getCardElement(cardTo.getCardNumber()).$("button").click();
        return new TransferPage();
    }

    public void refreshPage() {
        refreshButton.click();
    }
}