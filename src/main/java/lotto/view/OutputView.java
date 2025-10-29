package lotto.view;

import lotto.model.LottoType;

import java.util.List;

/**
 * 화면 출력 관련 기능을 담당하는 클래스입니다.
 */
public class OutputView {
    public OutputView() {
    }

    /**
     * 구매 금액 입력 안내 메시지를 출력합니다.
     */
    public void inputPurchasePrice() {
        System.out.println("구입금액을 입력해 주세요.");
    }

    /**
     * 구매한 로또 수량을 출력합니다.
     *
     * @param count 구매한 로또 갯수
     */
    public void outputPurchaseCount(int count) {
        System.out.println();
        System.out.println(count +"개를 구매했습니다.");
    }

    /**
     * 발행된 로또 번호를 출력합니다.
     *
     * @param numbers 로또 번호 리스트
     */
    public void outputPurchasedLotto(List<Integer> numbers) {
        System.out.print("[");
        for (int i = 0; i < numbers.size(); i++) {
            System.out.print(numbers.get(i));
            if (i != numbers.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    /**
     * 당첨 번호 입력 메시지를 출력합니다.
     */
    public void inputWinningLotto() {
        System.out.println();
        System.out.println("당첨 번호를 입력해 주세요.");
    }

    /**
     * 보너스 번호 입력 메시지를 출력합니다.
     */
    public void inputBonusLotto() {
        System.out.println();
        System.out.println("보너스 번호를 입력해 주세요.");
    }

    /**
     * 결과를 출력합니다.
     * @param resultList 일치 개수 배열
     * @param rateOfReturn 수익률
     */
    public void outputResult(int[] resultList, double rateOfReturn) {
        System.out.println();
        System.out.println("당첨 통계");
        System.out.println("---");
        List<LottoType> ranks = LottoType.getRanksForOutput();
        for (int i = 0; i < ranks.size(); i++) {
            LottoType rank = ranks.get(i);
            String message = rank.getMessage();
            int count = resultList[i];
            System.out.println(message + " - " + count + "개");
        }
        System.out.println("총 수익률은 "+rateOfReturn+"%입니다.");
    }


    /**
     * 에러 메시지 출력
     *
     * @param error 에러 메시지
     */
    public void printError(String error) {
        System.out.println("[ERROR] : "+ error);
    }
}
