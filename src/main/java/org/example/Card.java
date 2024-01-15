package org.example;


public record Card(String value, String suite) implements Comparable<Card> {

    public Card(String value) {
        this(value, null);
    }

    @Override
    public int compareTo(Card o) {
        return Integer.compare(
                CardUtil.cardsOrder.indexOf(value),
                CardUtil.cardsOrder.indexOf(o.value)
        );
    }
}
