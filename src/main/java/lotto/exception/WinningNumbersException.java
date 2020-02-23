package lotto.exception;

public class WinningNumbersException extends IllegalArgumentException {
    private static final String DEFAULT_MESSAGE = "보너스 번호는 당첨 번호와 중복될 수 없습니다.";

    public WinningNumbersException(String message) {
        super(message);
    }

    public WinningNumbersException() {
        this(DEFAULT_MESSAGE);
    }
}
