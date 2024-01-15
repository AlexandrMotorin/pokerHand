package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CardUtil {

    public static final String cardsOrder = "23456789TJQKA";
    public static final String delimiter = "\s";

    public static List<Card> parseCards(String cardsStr) {
        return Arrays.stream(cardsStr.split(delimiter))
                .map(c -> {
                    char val = c.charAt(0);
                    char suite = c.charAt(1);
                    return new Card(String.valueOf(val), String.valueOf(suite));
                })
                .toList();
    }

    public static boolean isAscOrder(List<Card> cards) {
        String str = cards.stream()
                .map(Card::value)
                .collect(Collectors.joining());

        return cardsOrder.contains(str);
    }

    public static boolean sameSuite(List<Card> cards) {
        return cards.stream()
                .map(Card::suite)
                .distinct()
                .count() == 1;
    }

}
