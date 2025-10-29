package lotto.model;

import lotto.Lotto;

import java.util.ArrayList;
import java.util.List;

/**
 * 로또 당첨 결과를 저장하고 당첨 통계 및 수익률을 계산하는 클래스입니다.
 */
public class LottoResult {

    private int[] resultList;
    private List<Lotto>lottoList;
    private long winningMoney = 0;
    private long purchaseMoney;

    /**
     * 구매 금액을 전달받아 초기화합니다.
     *
     * @param purchaseMoney 구매 금액
     */
    public LottoResult(int purchaseMoney) {
        this.resultList = new int[LottoType.values().length];
        this.lottoList = new ArrayList<>();
        this.purchaseMoney = purchaseMoney;
    }

    /**
     * 총 수익률을 계산해 반환합니다.
     *
     * @return 수익률(%)
     */
    public double getRateOfReturn() {
        double rateOfReturn = (double)winningMoney / purchaseMoney * 100;
        rateOfReturn = Math.round(rateOfReturn * 100.0) / 100.0;
        return rateOfReturn;
    }

    /**
     * 구매한 로또를 추가합니다.
     *
     * @param lotto 추가할 로또
     */
    public void addMyLotto(Lotto lotto){
        lottoList.add(lotto);
    }

    /**
     * 당첨 번호와 보너스 번호를 받아 당첨 결과를 분석합니다.
     * @param winningLotto 당첨 번호 목록
     * @param bonusWinningNumber    보너스 번호
     * @return 각 등수별 당첨 갯수를 포함한 배열
     */
    /**
     * 당첨 번호와 보너스 번호를 받아 당첨 결과를 분석하고 통계를 업데이트합니다.
     * @param winningLotto 당첨 번호 목록
     * @param bonusWinningNumber    보너스 번호
     * @return 각 등수별 당첨 갯수를 포함한 배열 (출력 순서에 맞춘 배열)
     */
    public int[] addResult(List<Integer>winningLotto, int bonusWinningNumber) {
        for(Lotto myLotto : lottoList) {
            processLottoResult(myLotto, winningLotto, bonusWinningNumber);
        }
        return resultList;
    }

    /**
     * 개별 로또의 당첨 여부를 처리하고 통계를 업데이트합니다.
     */
    private void processLottoResult(Lotto myLotto, List<Integer> winningLotto, int bonusWinningNumber) {
        int sameNumber = countMatchingNumbers(myLotto, winningLotto);
        boolean bonusCheck = checkBonusMatch(myLotto, bonusWinningNumber);
        LottoType lottoType = LottoType.valueOf(sameNumber, bonusCheck);

        if (lottoType != LottoType.MISS) {
            updateResult(lottoType);
        }
    }

    /**
     * 내 로또와 당첨 번호 간 일치하는 번호의 개수를 계산합니다.
     */
    private int countMatchingNumbers(Lotto myLotto, List<Integer> winningLotto) {
        int count = 0;
        for (int lottoNum : myLotto.getLottoNumbers()) {
            // List.contains()를 사용하여 일치하는 번호의 개수를 셉니다. (O(N*M) -> N*1)
            if (winningLotto.contains(lottoNum)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 내 로또가 보너스 번호와 일치하는지 확인합니다.
     */
    private boolean checkBonusMatch(Lotto myLotto, int bonusWinningNumber) {
        return myLotto.getLottoNumbers().contains(bonusWinningNumber);
    }

    /**
     * 당첨 등수를 바탕으로 통계와 상금을 업데이트합니다.
     */
    private void updateResult(LottoType lottoType) {
        winningMoney += lottoType.getPrizeMoney();
        List<LottoType> ranksForOutput = LottoType.getRanksForOutput();
        int index = ranksForOutput.indexOf(lottoType);
        if (index != -1) {
            resultList[index]++;
        }
    }
}
