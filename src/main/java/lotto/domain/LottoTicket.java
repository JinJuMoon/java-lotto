package lotto.domain;

import lotto.exception.LottoTicketException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LottoTicket {
    private static final int COUNT_FOR_SECOND_RANK = 5;
    private static final int LOTTO_SIZE = 6;

    private final List<LottoNumber> numbers;

    private LottoTicket(List<LottoNumber> numbers) {
        this.numbers = numbers;
    }

    public static LottoTicket fromSixNumbers(List<LottoNumber> numbers) {
        validate(numbers);
        Collections.sort(numbers);
        return new LottoTicket(numbers);
    }

    public static LottoTicket fromInput(String input) {
        List<LottoNumber> numbers = generateSixNumbersFromInput(input);
        return fromSixNumbers(numbers);
    }

    private static void validate(List<LottoNumber> numbers) {
        validateNumbersCount(numbers);
        validateNumbersDuplication(numbers);
    }

    private static void validateNumbersCount(List<LottoNumber> numbers) {
        if (numbers.size() != LOTTO_SIZE) {
            throw new LottoTicketException("로또의 숫자는 6개여야 합니다.");
        }
    }

    private static void validateNumbersDuplication(List<LottoNumber> numbers) {
        if (hasDuplicatedNumbers(numbers)) {
            throw new LottoTicketException("로또의 숫자는 중복될 수 없습니다.");
        }
    }

    private static boolean hasDuplicatedNumbers(List<LottoNumber> numbers) {
        return getDistinctCount(numbers) != numbers.size();
    }

    private static long getDistinctCount(List<LottoNumber> numbers) {
        return numbers.stream()
                .distinct()
                .count();
    }

    private static List<LottoNumber> generateSixNumbersFromInput(String inputForNumbers) {
        return Arrays.stream(inputForNumbers.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .map(LottoNumber::new)
                .collect(Collectors.toList());
    }

    public Rank checkOut(LottoTicket winningLottoTicket, LottoNumber bonusNumber) {
        int count = (int) numbers.stream()
                .filter(winningLottoTicket.numbers::contains)
                .count();

        if (count == COUNT_FOR_SECOND_RANK && numbers.contains(bonusNumber)) {
            return Rank.SECOND;
        }

        return Rank.of(count);
    }

    public boolean contains(LottoNumber bonusNumber) {
        return numbers.contains(bonusNumber);
    }

    public List<LottoNumber> getNumbers() {
        return Collections.unmodifiableList(numbers);
    }
}