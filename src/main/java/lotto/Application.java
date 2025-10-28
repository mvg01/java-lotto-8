package lotto;

import lotto.controller.LottoController;

/**
 * 프로그램 실행 진입점 클래스입니다.
 */
public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        LottoController controller = new LottoController();
        controller.run();
    }
}
