package lotto.controller;

import lotto.Lotto;
import lotto.model.LottoGenerator;
import lotto.model.LottoResult;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 사용자 입력과 출력, 게임 진행 흐름을 제어하는 컨트롤러 클래스입니다.
 */
public class LottoController {
    InputView inputView;
    OutputView outputView;
    LottoGenerator lottoGenerator;
    LottoResult lottoResult;

    public LottoController() {
        inputView = new InputView();
        outputView = new OutputView();
        lottoGenerator = new LottoGenerator();
    }

    /**
     * 로또 프로그램 실행 로직
     */
    public void run() {
        try {
            int lottoQuantity = inputPurchaseMoney();
            buyingLotto(lottoQuantity);
            List<Integer> winningNumbers = inputWinningNumbers();
            int bonusWinningNumber = bonusWinningNumber(winningNumbers);
            int resultList[] = lottoResult.addResult(winningNumbers, bonusWinningNumber);
            double rateOfReturn = lottoResult.getRateOfReturn();
            outputView.outputResult(resultList, rateOfReturn);
        } catch (Exception e) {
            outputView.printError("예기치 못한 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * 구매 금액을 입력받고, 1000원 단위 검증 및 예외처리를 수행합니다.
     * @return 구입할 로또 장수
     */
    private int inputPurchaseMoney() {
        while(true) {
            try {
                outputView.inputPurchasePrice();
                int money = Integer.parseInt(inputView.readInput());
                if (money % 1000 != 0) {
                    throw new IllegalArgumentException("1000원 단위로 입력해주세요.");
                }
                lottoResult = new LottoResult(money);
                return money/1000;
            } catch (NumberFormatException e) {
                outputView.printError("숫자만 입력가능합니다.");
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    /**
     * 구입할 로또 수만큼 로또를 생성하고 출력합니다.
     * @param quantity 구입할 로또 수
     */
    private void buyingLotto(int quantity) {
        outputView.outputPurchaseCount(quantity);
        for(int i = 0; i < quantity; i++) {
            List<Integer>lottoNumber = lottoGenerator.generate();
            Lotto lotto = new Lotto(new ArrayList<>(lottoNumber));
            lotto.sortingNumbers();
            lottoResult.addMyLotto(lotto);
            outputView.outputPurchasedLotto(lotto.getLottoNumbers());
        }
    }

    /**
     * 당첨 번호 입력 및 유효성 검사, 잘못된 입력 시 재입력 요청
     * @return 유효한 당첨 번호 리스트
     */
    private List<Integer> inputWinningNumbers() {
        while(true) {
            try {
                outputView.inputWinningLotto();
                String inputList = inputView.readInput();
                return validateWinningNumbers(inputList);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private List<Integer> validateWinningNumbers(String inputList) {
        String[] tokens = inputList.split(",");
        if (tokens.length != 6) {
            throw new IllegalArgumentException("로또 번호는 6개여야 합니다.");
        }
        List<Integer> winningNumbers = parseAndValidateNumbers(tokens);
        Collections.sort(winningNumbers);
        return winningNumbers;
    }

    private List<Integer> parseAndValidateNumbers(String[] tokens) {
        List<Integer> winningNumbers = new ArrayList<>();
        for (String token : tokens) {
            int num = Integer.parseInt(token.trim());
            validateLottoNumberRange(num);
            validateDuplicateNumber(winningNumbers, num);
            winningNumbers.add(num);
        }
        return winningNumbers;
    }

    private void validateLottoNumberRange(int num) {
        if (num < 1 || num > 45) {
            throw new IllegalArgumentException("로또 번호는 1부터 45 사이 숫자여야 합니다.");
        }
    }

    private void validateDuplicateNumber(List<Integer> numbers, int num) {
        if (numbers.contains(num)) {
            throw new IllegalArgumentException("중복된 번호가 있습니다.");
        }
    }

    /**
     * 보너스 번호 입력 및 검증, 중복 및 범위 체크, 잘못된 입력 시 재입력 요청
     * @param winningNumbers 당첨 번호 리스트
     * @return 유효한 보너스 번호
     */
    private int bonusWinningNumber(List<Integer> winningNumbers) {
        while(true) {
            try{
                outputView.inputBonusLotto();
                int bonus = Integer.parseInt(inputView.readInput());
                validateBonusNumber(winningNumbers, bonus);
                return bonus;
            } catch (NumberFormatException e) {
                outputView.printError("숫자만 입력가능합니다.");
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void validateBonusNumber(List<Integer> winningNumbers, int bonus) {
        if(bonus < 1 || bonus > 45) {
            throw new IllegalArgumentException("보너스 번호는 1부터 45 사이 숫자여야 합니다.");
        }
        if(winningNumbers.contains(bonus)) {
            throw new IllegalArgumentException("당첨 번호와 중복된 보너스 번호입니다.");
        }
    }
}
