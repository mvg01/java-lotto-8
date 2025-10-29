package lotto;

import java.util.Collections;
import java.util.List;

/**
 * 로또 번호 6개를 가진 로또 한 장을 나타냅니다.
 */
public class Lotto {
    private final List<Integer> numbers;

    /**
     * 로또 번호 리스트를 받아 생성하고 유효성을 검증.
     * @param numbers 6개의 번호를 가진 리스트
     */
    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    /**
     * 번호 리스트가 6개인지 검증.
     * @param numbers 로또 번호 리스트
     */
    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개여야 합니다.");
        }
        for(int num: numbers) {
            if(Collections.frequency(numbers, num) > 1) {
                throw new IllegalArgumentException("[ERROR] 로또 번호에 중복된 숫자가 있습니다.");
            }
            if(num <= 0 || num > 45) {
                throw new IllegalArgumentException("[ERROR] 로또 번호는 1부터 45 사이 숫자여야 합니다.");
            }
        }
    }

    /**
     * 로또 번호를 오름차순 정렬.
     */
    public void sortingNumbers() {
        Collections.sort(numbers);
    }

    /**
     * 로또 번호 리스트를 반환합니다.
     * @return 번호 리스트
     */
    public List<Integer> getLottoNumbers() {
        return numbers;
    }
}
