package lotto.exception;

public class LottoNumberException extends IllegalArgumentException {
    private static final String DEFAULT_MESSAGE = "로또 숫자는 1~45사이어야 합니다.";

    public LottoNumberException(String message) {
        super(message);
    }

    public LottoNumberException() {
        this(DEFAULT_MESSAGE);
    }
}
