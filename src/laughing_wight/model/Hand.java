package laughing_wight.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Hand implements Comparable<Hand> {
	private Card[] cards = new Card[5];
	private HandType type;

	public Hand(Card[] cards){
		assert cards.length == 5;
		List<Card> temp = Arrays.asList(cards);
		Collections.sort(temp);
		Card[] toSave = new Card[5];
		for (int i = 0; i < toSave.length; i++) {
			toSave[i] = temp.get(i);
		}
		this.cards = toSave;
		type = getHandType(toSave);

	}

	public Card[] getCards() {
		return cards;
	}

	public HandType getType() {
		return type;
	}

	private static HandType getHandType(Card[] cards) {
		assert cards.length == 5;

		for (int i = cards.length-1; i > 1; i--) {
			if(cards[i].getValue() == cards[i-1].getValue()){
				//At least one pair.
				if(i == 4 && cards[2].getValue() == cards[1].getValue() && cards[1].getValue() == cards[0].getValue()){//Full house involves every card, so check for that at the start.
					return HandType.FULL_HOUSE;
				}
				//See if there are two pairs.
				for (int j = i-1; j > 0; j--) {
					if(cards[j].getValue() == cards[j-1].getValue()){
						return HandType.TWO_PAIR;
					}
				}
				return HandType.PAIR;
			}
		}
		return HandType.HIGH_CARD;
	}

	@Override
	public int compareTo(Hand arg0) {
		int coarsecomparison = type.ordinal() - arg0.type.ordinal();
		if(coarsecomparison != 0){ 
			return coarsecomparison;
		}else{
			return tieBreak(type, this.cards,arg0.cards);
		}
	}

	private static int tieBreak(HandType type, Card[] hand1, Card[] hand2) {
		switch(type){
		case HIGH_CARD:
			for (int i = hand1.length-1; i > 0; i--) {//Reversed order to start at highest valued card.
				int comp = hand1[i].compareTo(hand2[i]);
				if(comp != 0) return comp;
			}
			return 0;
		case PAIR:
			int pair1 = -1; 
			int pair2 = -1;
			for (int i = 0; i < hand1.length-1; i++) {
				if(hand1[i].getValue() == hand1[i+1].getValue()){
					pair1 = hand1[i].getValue();
				}
				if(hand2[i].getValue() == hand2[i+1].getValue()){
					pair2 = hand2[i].getValue();
				}
			}
			if(pair1 - pair2 != 0){
				return pair1 - pair2; //Higher pair wins
			}else{//If same pairs, ignore them and compare as high card.
				return tieBreak(HandType.HIGH_CARD, hand1, hand2);
			}
		case TWO_PAIR:
			int pair11 = -1; 
			int pair12 = -1;
			int pair21 = -1;
			int pair22 = -1;
			for (int i = 0; i < hand1.length-1; i++) {
				if(pair11 == -1 &&hand1[i].getValue() == hand1[i+1].getValue()){
					pair11 = hand1[i].getValue();
				}else if(pair12 == -1 &&hand1[i].getValue() == hand1[i+1].getValue()){
					pair12 = hand1[i].getValue();
				}
				if(pair21 == -1 &&hand2[i].getValue() == hand2[i+1].getValue()){
					pair21 = hand2[i].getValue();
				}else if(pair22 == -1 &&hand2[i].getValue() == hand2[i+1].getValue()){
					pair22 = hand1[i].getValue();
				}
			}
			int temp = Math.min(pair11, pair12);
			pair11 = Math.max(pair11,pair12);
			pair12 = temp;
			temp = Math.min(pair21, pair22);
			pair21 = Math.max(pair21, pair22);
			pair22 = temp;
			if(pair11 - pair21 != 0){
				return pair11 - pair21;
			}else if(pair12 - pair22 != 0){
				return pair12 - pair22;
			}else{
				return tieBreak(HandType.HIGH_CARD, hand1, hand2);
			}
		case THREE_OF_A_KIND:
			return 0;
		case FOUR_OF_A_KIND:
			return 0;
		case FLUSH:
			return 0;
		case FULL_HOUSE:
			return 0;
		case STRAIGHT:
			return 0;
		case STRAIGHT_FLUSH:
			return 0;

		default:
			assert false;
			return 0;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		builder.append(type.toString());
		builder.append(";");
		for (int i = 0; i < cards.length; i++) {
			builder.append(cards[i].toString());
			builder.append(" ");
		}
		builder.append("]");
		return builder.toString();
	}
}
