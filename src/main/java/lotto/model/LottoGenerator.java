package lotto.model;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.List;

/**
 * 로또 번호 6개를 무작위로 생성하는 클래스.
 */
public class LottoGenerator {
    public LottoGenerator() {
    }
    /**
     * 1~45 사이 중복없는 번호 6개를 반환합니다.
     * @return 번호 리스트
     */
    public List<Integer> generate() {
        return Randoms.pickUniqueNumbersInRange(1, 45, 6);
    }
}
