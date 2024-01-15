package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PokerHand implements Comparable<PokerHand>{

    private final String cardsString;

    public PokerHand(String cardsString) {
        this.cardsString = cardsString;
    }

    public HandCombinationInfo define() {
        Combination combination = Combination.HIGH_CARD;
        List<Card> combinationCards = new ArrayList<>();
        List<Card> kickers = new ArrayList<>();

        List<Card> cards = CardUtil.parseCards(this.cardsString).stream()
                .sorted()
                .toList();

        Map<String, List<Card>> cardsByValue = cards.stream()
                .collect(Collectors.toMap(
                        Card::value,
                        c -> new ArrayList<>(List.of(c)),
                        (list1, list2) -> {
                            list1.addAll(list2);
                            return list1;
                        }
                ));

        switch (cardsByValue.size()) {
            case 2 -> {
                if (cardsByValue.values().stream().anyMatch(l -> l.size() == 4)) {
                    combination = Combination.FOUR_OF_KIND;
                    fillInfoLists(cardsByValue, 4, combinationCards, kickers);
                }

                if (cardsByValue.values().stream().anyMatch(l -> l.size() == 3)) {
                    combination = Combination.FULL_HOUSE;
                    fillInfoLists(cardsByValue, 3, combinationCards, kickers);
                }
            }
            case 3 -> {
                if (cardsByValue.values().stream().anyMatch(l -> l.size() == 2)) {
                    combination = Combination.TWO_PAIRS;
                    fillInfoLists(cardsByValue, 2, combinationCards, kickers);
                } else {
                    combination = Combination.TREE_OF_A_KIND;
                    fillInfoLists(cardsByValue, 3, combinationCards, kickers);
                }
            }
            case 4 -> {
                combination = Combination.PAIR;
                fillInfoLists(cardsByValue, 2, combinationCards, kickers);
            }
            case 5 -> {
                boolean isSameSuite = CardUtil.sameSuite(cards);
                boolean isAscOrder = CardUtil.isAscOrder(cards);

                if (isAscOrder) {
                    combination = isSameSuite
                            ? Combination.STRAIGHT_FLUSH
                            : Combination.STRAIGHT;
                } else if (isSameSuite) {
                    combination = Combination.FLUSH;
                }

                Card maxCard = cards.get(cards.size() - 1);
                combinationCards.add(maxCard);

            }
        }

        if(!kickers.isEmpty()) {
            kickers.sort(Card::compareTo);
            Card highKicker = kickers.get(kickers.size() - 1);

            combinationCards.add(highKicker);
        }

        return new HandCombinationInfo(combination, combinationCards);
    }

    private void fillInfoLists(Map<String, List<Card>> cardsByValue, int combinationLength, List<Card> combinationCards, List<Card> kickers) {
        cardsByValue.forEach((key, val) -> {
            Card card = new Card(key);
            if (val.size() == combinationLength) {
                combinationCards.add(card);
            } else {
                kickers.add(card);
            }
        });
    }

    @Override
    public int compareTo(PokerHand o) {
        HandCombinationInfo info1 = this.define();
        HandCombinationInfo info2 = o.define();

        return info1.compareTo(info2);
    }
}
