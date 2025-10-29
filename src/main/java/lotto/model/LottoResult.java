package lotto.model;

import lotto.Lotto;

import java.util.ArrayList;
import java.util.List;

/**
 * 로또 당첨 결과를 저장하고 당첨 통계 및 수익률을 계산하는 클래스입니다.
 */
public class LottoResult {

    private int[] resultList = new int[5];
    private List<Lotto>lottoList;
    private long winningMoney = 0;
    private long purchaseMoney;
    private double rateOfReturn = 0;
    private final int[] winningPrize = {5000, 50000, 1500000, 30000000, 2000000000};

    /**
     * 구매 금액을 전달받아 초기화합니다.
     *
     * @param purchaseMoney 구매 금액
     */
    public LottoResult(int purchaseMoney) {
        lottoList = new ArrayList<>();
        this.purchaseMoney = purchaseMoney;
    }

    /**
     * 총 수익률을 계산해 반환합니다.
     *
     * @return 수익률(%)
     */
    public double getRateOfReturn() {
        rateOfReturn = (double)winningMoney / purchaseMoney * 100;
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
    public int[] addResult(List<Integer>winningLotto, int bonusWinningNumber) {
        for(Lotto list : lottoList) {
            List<Integer>lotto=list.getLottoNumbers();
            int sameNumber = 0;
            for(int i=0; i<lotto.size(); i++) {
                for(int j=0; j<winningLotto.size(); j++) {
                    if (lotto.get(i).equals(winningLotto.get(j))) sameNumber++;
                }
            }
            boolean bonusCheck = false;
            if(lotto.contains(bonusWinningNumber)) bonusCheck = true;
            checkWinningPrize(sameNumber,bonusCheck);
        }
        return resultList;
    }

    /**
     * 당첨 번호 개수와 보너스 번호 적중 여부로 결과를 계산합니다.
     * @param sameNumber  로또 적중 여부 개수
     * @param bonusCheck  보너스 번호 적중 여부
     */
    private void checkWinningPrize(int sameNumber, boolean bonusCheck) {
        if(sameNumber == 3){
            winningMoney += winningPrize[0];
            resultList[0]++;
        } else if (sameNumber == 4) {
            winningMoney += winningPrize[1];
            resultList[1]++;
        } else if (sameNumber == 5 && bonusCheck == false) {
            winningMoney += winningPrize[2];
            resultList[2]++;
        } else if (sameNumber == 5 && bonusCheck == true) {
            winningMoney += winningPrize[3];
            resultList[3]++;
        } else if (sameNumber == 6) {
            winningMoney += winningPrize[4];
            resultList[4]++;
        }
    }
}
