package ru.netology.data;

public class DataHelper {

    private DataHelper() {}

    public static class AuthInfo {
        private String login;
        private String password;

        private AuthInfo() {}

        public AuthInfo(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }

        public static AuthInfo getFirstUser() {
            return new AuthInfo("vasya", "qwerty123");
        }

        public static AuthInfo getSecondUser() {
            return new AuthInfo("petya", "123qwerty");
        }

        public static AuthInfo getInvalidLogin () {
            return new AuthInfo("tolya", "qwerty123");
        }

        public static AuthInfo getEmptyLogin () {
            return new AuthInfo("", "qwerty123");
        }

        public static AuthInfo getInvalidPassword () {
            return new AuthInfo("vasya", "123qwerty");
        }

        public static AuthInfo getEmptyPassword () {
            return new AuthInfo("vasya", "");
        }
    }

    public static class VerificationCode {
        private String code;

        private VerificationCode() {}

        public VerificationCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public static VerificationCode getValidCode() {
            return new VerificationCode("12345");
        }

        public static VerificationCode getInvalidCode() {
            return new VerificationCode("54321");
        }

        public static VerificationCode getEmptyCode() {
            return new VerificationCode("");
        }
    }

    public static class CardInfo {
        private String cardNumber;

        private CardInfo() {}

        public CardInfo(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public static CardInfo getFirstCard() {
            return new CardInfo("5559 0000 0000 0001");
        }

        public static CardInfo getSecondCard() {
            return new CardInfo("5559 0000 0000 0002");
        }
    }
}