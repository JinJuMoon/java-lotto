package lotto.exception;

public class MoneyException extends IllegalArgumentException {
    private static final String DEFAULT_MESSAGE = "구매금액은 1000원 이상이어야 합니다.";

    public MoneyException(String message) {
        super(message);
    }

    public MoneyException() {
        this(DEFAULT_MESSAGE);
    }
}
