package lotto.domain;

import lotto.exception.LottoStoreException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class LottoTickets {
    private static final int SUBLIST_FROM_INDEX = 0;
    private static final int SUBLIST_TO_INDEX = 6;

    private final List<LottoTicket> lottoTickets;

    public LottoTickets(List<LottoTicket> lottoTickets) {
        this.lottoTickets = lottoTickets;
    }

    public static LottoTickets ofAutoLottoTickets(int randomTicketCount) {
        List<LottoTicket> randomLottoTicket = Stream.generate(LottoTickets::createAutoLottoTicket).limit(randomTicketCount)
                .collect(toList());
        return new LottoTickets(randomLottoTicket);
    }

    private static LottoTicket createAutoLottoTicket() {
        List<LottoNumber> copiedNumbers = new ArrayList<>(LottoNumber.values());
        Collections.shuffle(copiedNumbers);
        List<LottoNumber> subNumbers = copiedNumbers.subList(SUBLIST_FROM_INDEX, SUBLIST_TO_INDEX);
        return LottoTicket.fromSixNumbers(subNumbers);
    }

    public static LottoTickets ofManualLottoTickets(int manualTicketCount, List<String> inputsForNumbers) {
        if (manualTicketCount != inputsForNumbers.size()) {
            throw new LottoStoreException("수동으로 구매할 로또 티켓의 개수와 로또 번호의 개수가 일치하지 않습니다.");
        }

        List<LottoTicket> manualLottoTicket = inputsForNumbers.stream()
                .map(LottoTicket::fromInput)
                .collect(Collectors.toList());
        return new LottoTickets(manualLottoTicket);
    }

    public static LottoTickets add(LottoTickets manualLottoTickets, LottoTickets randomLottoTickets) {
        List<LottoTicket> allLottoTickets = new ArrayList<>();
        allLottoTickets.addAll(manualLottoTickets.lottoTickets);
        allLottoTickets.addAll(randomLottoTickets.lottoTickets);

        return new LottoTickets(allLottoTickets);
    }

    public int size() {
        return lottoTickets.size();
    }

    public List<Rank> checkOutLottos(LottoTicket winningLottoTicket, LottoNumber bonusNumber) {
        return lottoTickets.stream()
                .map(lottoTicket -> lottoTicket.checkOut(winningLottoTicket, bonusNumber))
                .filter(Rank::isValidRank)
                .collect(Collectors.toList());
    }

    public List<LottoTicket> getLottoTickets() {
        return Collections.unmodifiableList(lottoTickets);
    }
}