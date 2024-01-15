package org.example;

import java.util.List;

public record HandCombinationInfo(Combination combination, List<Card> combinationCards)
        implements Comparable<HandCombinationInfo>{

    @Override
    public int compareTo(HandCombinationInfo o) {

        if(!this.combination.equals(o.combination)) {
            return Integer.compare(this.combination.ordinal(), o.combination.ordinal());
        }

        for (int i = 0; i < this.combinationCards.size(); i++) {
            Card card1 = this.combinationCards.get(i);
            Card card2 = o.combinationCards.get(i);

            int cardComparing = card1.compareTo(card2);
            boolean isLustCard = i == this.combinationCards.size() - 1;

            if(cardComparing == 0 && !isLustCard) {
                continue;
            }

            return cardComparing;
        }

        return 0;
    }
}
