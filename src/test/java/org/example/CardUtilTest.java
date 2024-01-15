package org.example;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CardUtilTest {

    @Test
    public void parseCardsTest() {
        // given
        String cardString = "2C 3C 4C 5C 6C";

        List<Card> exceptedCards = List.of(
                new Card("2", "C"),
                new Card("3", "C"),
                new Card("4", "C"),
                new Card("5", "C"),
                new Card("6", "C")
        );

        //when
        List<Card> actualCards = CardUtil.parseCards(cardString);

        //then
        Assertions.assertEquals(exceptedCards, actualCards);
    }

    @Test
    public void isAscOrderTest() {
        //given
        List<Card> ascOrder = List.of(new Card("2","C"), new Card("3","H"), new Card("4","H"));
        List<Card> descOrder = List.of(new Card("4","H"), new Card("3","H"), new Card("2","C"));
        List<Card> nonSequence = List.of(new Card("3","H"), new Card("6","C"));

        //when
        boolean ascOrderActual = CardUtil.isAscOrder(ascOrder);
        boolean descOrderActual = CardUtil.isAscOrder(descOrder);
        boolean nonSequenceActual = CardUtil.isAscOrder(nonSequence);

        //then
        Assertions.assertTrue(ascOrderActual);
        Assertions.assertFalse(descOrderActual);
        Assertions.assertFalse(nonSequenceActual);
    }

}
