package pl.demo.creditsuissedemo.Objects;

public class CreditSuisseException extends Exception {
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public CreditSuisseException(String message) {
        this.message = message;
    }

    public CreditSuisseException(String message, String message1) {
        super(message);
        this.message = message1;
    }
}
