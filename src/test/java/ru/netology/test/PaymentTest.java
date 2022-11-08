package ru.netology.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentTest {
    @Test
    @DisplayName("Positive: should transfer money between two cards")
    void shouldTransferMoney() {
        var authInfo = DataHelper.AuthInfo.getFirstUser();
        var verificationCode = DataHelper.VerificationCode.getValidCode();
        var cardTo = DataHelper.CardInfo.getFirstCard();
        var cardFrom = DataHelper.CardInfo.getSecondCard();

        var loginPage = open("http://localhost:9999", LoginPage.class);
        var validationPage = loginPage.validLogin(authInfo);
        var accountPage = validationPage.validVerify(verificationCode);

        int cardToBalanceBefore = accountPage.getBalance(cardTo.getCardNumber());
        int cardFromBalanceBefore = accountPage.getBalance(cardFrom.getCardNumber());
        int transferAmount = cardFromBalanceBefore / 100;

        var transferPage = accountPage.chooseCard(cardTo);
        accountPage = transferPage.makeTransfer(transferAmount, cardFrom.getCardNumber());
        int cardToBalanceAfter = accountPage.getBalance(cardTo.getCardNumber());
        int cardFromBalanceAfter = accountPage.getBalance(cardFrom.getCardNumber());
        assertEquals(cardToBalanceBefore + transferAmount, cardToBalanceAfter);
        assertEquals(cardFromBalanceBefore - transferAmount, cardFromBalanceAfter);
    }

    @Test
    @DisplayName("Negative: should NOT transfer money more than deposited")
    void shouldNotTransferMoreThanDeposited() {
        var authInfo = DataHelper.AuthInfo.getFirstUser();
        var verificationCode = DataHelper.VerificationCode.getValidCode();
        var cardTo = DataHelper.CardInfo.getSecondCard();
        var cardFrom = DataHelper.CardInfo.getFirstCard();

        var loginPage = open("http://localhost:9999", LoginPage.class);
        var validationPage = loginPage.validLogin(authInfo);
        var accountPage = validationPage.validVerify(verificationCode);

        int cardToBalanceBefore = accountPage.getBalance(cardTo.getCardNumber());
        int cardFromBalanceBefore = accountPage.getBalance(cardFrom.getCardNumber());
        int transferAmount = cardFromBalanceBefore * 2;

        var transferPage = accountPage.chooseCard(cardTo);
        accountPage = transferPage.makeTransfer(transferAmount, cardFrom.getCardNumber());
        int cardToBalanceAfter = accountPage.getBalance(cardTo.getCardNumber());
        int cardFromBalanceAfter = accountPage.getBalance(cardFrom.getCardNumber());
        assertEquals(cardToBalanceBefore + transferAmount, cardToBalanceAfter);
        assertEquals(cardFromBalanceBefore - transferAmount, cardFromBalanceAfter);
    }

    /*
        Negative test set for Login page
     */

    @Test
    @DisplayName("Negative: Invalid login")
    void invalidLogin() {
        var authInfo = DataHelper.AuthInfo.getInvalidLogin();
        var loginPage = open("http://localhost:9999", LoginPage.class);
        String expected = "Ошибка! Неверно указан логин или пароль";
        String actual = loginPage.invalidLogin(authInfo);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Negative: Invalid password")
    void invalidPassword() {
        var authInfo = DataHelper.AuthInfo.getInvalidPassword();
        var loginPage = open("http://localhost:9999", LoginPage.class);
        String expected = "Ошибка! Неверно указан логин или пароль";
        String actual = loginPage.invalidLogin(authInfo);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Negative: empty Login field")
    void emptyLoginField() {
        var authInfo = DataHelper.AuthInfo.getEmptyLogin();
        var loginPage = open("http://localhost:9999", LoginPage.class);
        String expected = "Поле обязательно для заполнения";
        String actual = loginPage.emptyFieldLogin(authInfo);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Negative: empty Password field")
    void emptyPasswordField() {
        var authInfo = DataHelper.AuthInfo.getEmptyPassword();
        var loginPage = open("http://localhost:9999", LoginPage.class);
        String expected = "Поле обязательно для заполнения";
        String actual = loginPage.emptyFieldLogin(authInfo);
        assertEquals(expected, actual);
    }

    /*
        Negative test set for Validation page
     */

    @Test
    @DisplayName("Negative: Invalid verification code")
    void invalidCode() {
        var authInfo = DataHelper.AuthInfo.getFirstUser();
        var verificationCode = DataHelper.VerificationCode.getInvalidCode();
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var validationPage = loginPage.validLogin(authInfo);
        String expected = "Ошибка! Неверно указан код! Попробуйте ещё раз.";
        String actual = validationPage.invalidCode(verificationCode);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Negative: Verification attempts exceeded")
    void verificationExceed() {
        var authInfo = DataHelper.AuthInfo.getSecondUser();
        var verificationCode = DataHelper.VerificationCode.getInvalidCode();
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var validationPage = loginPage.validLogin(authInfo);
        String expected = "Ошибка! Превышено количество попыток ввода кода!";
        validationPage.invalidCode(verificationCode);
        validationPage.invalidCode(verificationCode);
        validationPage.invalidCode(verificationCode);
        String actual = validationPage.invalidCode(verificationCode);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Negative: Empty verification code field")
    void emptyCodeField() {
        var authInfo = DataHelper.AuthInfo.getSecondUser();
        var verificationCode = DataHelper.VerificationCode.getEmptyCode();
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var validationPage = loginPage.validLogin(authInfo);
        String expected = "Поле обязательно для заполнения";
        String actual = validationPage.emptyVerificationCode(verificationCode);
        assertEquals(expected, actual);
    }
}