package lotto.model;

import java.util.Arrays;
import java.util.List;

/**
 * 로또 당첨 등수와 기준을 정의하는 Enum 클래스.
 */
public enum LottoType {
    FIRST(6, 2000000000, false, "6개 일치 (2,000,000,000원)"),
    SECOND(5, 30000000, true, "5개 일치, 보너스 볼 일치 (30,000,000원)"),
    THIRD(5, 1500_000, false, "5개 일치 (1,500,000원)"),
    FOURTH(4, 50000, false, "4개 일치 (50,000원)"),
    FIFTH(3, 5000, false, "3개 일치 (5,000원)"),
    MISS(0, 0, false, "낙첨");

    private final int matchCount;
    private final long prizeMoney;
    private final boolean bonus;
    private final String message;

    LottoType(int matchCount, long prizeMoney, boolean bonus, String message) {
        this.matchCount = matchCount;
        this.prizeMoney = prizeMoney;
        this.bonus = bonus;
        this.message = message;
    }

    public long getPrizeMoney() {
        return prizeMoney;
    }

    public String getMessage() {
        return message;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public boolean isBonus(){
        return bonus;
    }

    /**
     * 일치 개수와 보너스 볼 일치 여부를 바탕으로 해당 로또의 등수를 반환합니다.
     * @param matchCount 일치 개수
     * @param matchBonus 보너스 볼 일치 여부
     * @return 해당하는 LottoType (당첨 또는 MISS)
     */
    public static LottoType valueOf(int matchCount, boolean matchBonus) {
        if (matchCount < 3) return MISS;
        if (matchCount == 5) {
            if (matchBonus) {
                return SECOND;
            }
            return THIRD;
        }
        for (LottoType type : values()) {
            if (type.matchCount == matchCount && type.bonus == matchBonus) return type;
        }
        return MISS;
    }

    /**
     * 출력 순서(3등, 4등, 5등, 2등, 1등)를 맞추기 위한 정렬 기준을 제공합니다.
     */
    public static List<LottoType> getRanksForOutput() {
        return Arrays.asList(FIFTH, FOURTH, THIRD, SECOND, FIRST);
    }
}
