package org.example;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class PokerHandTest {

    @ParameterizedTest
    @MethodSource("defineTestArgs")
    public void defineTest(PokerHand hand, Combination expected) {
        //when
        HandCombinationInfo combinationInfo = hand.define();
        Combination actual = combinationInfo.combination();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest(name = "{3}")
    @MethodSource("compareTestArgs")
    public void compareTest(PokerHand hand1, PokerHand hand2, int expected, String name) {
        //when
        int actual = hand1.compareTo(hand2);

        //then
        Assertions.assertEquals(expected, actual);
    }

    static Stream<Arguments> defineTestArgs(){
        return Stream.of(
                Arguments.of(new PokerHand("2C 3C 4C 5C 6C"), Combination.STRAIGHT_FLUSH),
                Arguments.of(new PokerHand("6S 6D 6H 6C KH"), Combination.FOUR_OF_KIND),
                Arguments.of(new PokerHand("KS KD KC 7H 7C"), Combination.FULL_HOUSE),
                Arguments.of(new PokerHand("4H 6H KH QH 3H"), Combination.FLUSH),
                Arguments.of(new PokerHand("4H 5S 6C 7H 8H"), Combination.STRAIGHT),
                Arguments.of(new PokerHand("KS KD KC 4H 7C"), Combination.TREE_OF_A_KIND),
                Arguments.of(new PokerHand("4C 4D 5H 5C 7H"), Combination.TWO_PAIRS),
                Arguments.of(new PokerHand("4C 4D 5H 6C 7H"), Combination.PAIR),
                Arguments.of(new PokerHand("4C 3D 5H 6C 9H"), Combination.HIGH_CARD)
        );
    }

    static Stream<Arguments> compareTestArgs() {
        return Stream.of(
                Arguments.of(
                        new PokerHand("2C 3C 4C 5C 6C"),
                        new PokerHand("4C 4D 5H 6C 7H"),
                        1,
                        "STRAIGHT_FLUSH vs PAIR"
                        ),
                Arguments.of(
                        new PokerHand("KS KD KC 4H 7C"),
                        new PokerHand("4H 6H KH QH 3H"),
                        -1,
                        "PAIR vs STRAIGHT_FLUSH"),
                Arguments.of(
                        new PokerHand("2C 3C 4C 5C 6C"),
                        new PokerHand("4C 5C 6C 7C 8C"),
                        -1,
                        "STRAIGHT_FLUSH vs STRAIGHT_FLUSH : одинаковый ранг, на разная последовательность"
                ),
                Arguments.of(
                        new PokerHand("2C AC 4C 5C 6C"),
                        new PokerHand("4C 5C 9C 7C 8C"),
                        1,
                        "FLUSH vs FLUSH : одинаковый ранг, но разная максимальная карта"
                ),
                Arguments.of(
                        new PokerHand("4C 4D 5H 5C 9H"),
                        new PokerHand("4C 4D 5H 5C 7H"),
                        1,
                        "TWO PAIR vs TWO PAIR : одинаковый ранг, но разные кикеры"),
                Arguments.of(
                        new PokerHand("4C 4D 5H 5C 9H"),
                        new PokerHand("4C 4D 5H 5C AH"),
                        -1,
                        "TWO PAIR vs TWO PAIR : одинаковый ранг, но разные кикеры - в обратную сторону"),
                Arguments.of(
                        new PokerHand("4C 4D 8H 5C 9H"),
                        new PokerHand("5C 5D 3H 7C AH"),
                        -1,
                        "TWO PAIR vs TWO PAIR : одинаковый ранг, но у первой двойки меньше номинал"),
                Arguments.of(
                        new PokerHand("4C 4D 8H 5C 7H"),
                        new PokerHand("4C 4D 3H 7C 9H"),
                        -1,
                        "PAIR с одинаковым номиналом и большим кикером vs  PAIR"),
                Arguments.of(
                        new PokerHand("KS KD KC 7H 7C"),
                        new PokerHand("KS KD KC 6H 6C"),
                        1,
                        "FULL_HOUSE vs FULL_HOUSE: одинаковый номинал троек, но разный у пары"),
                Arguments.of(
                        new PokerHand("AS AD AC 6H 6C"),
                        new PokerHand("KS KD KC 6C 6H"),
                        1,
                        "FULL_HOUSE vs FULL_HOUSE: разный номинал троек"),
                Arguments.of(
                        new PokerHand("4C 3D 5H 6C 9H"),
                        new PokerHand("4C 3D 5H 6C TH"),
                        -1,
                        "HIGH_CARD vs HIGH_CARD")

        );
    }

}
