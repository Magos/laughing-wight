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
		List<Card> temp = Arrays.asList(cards);
		Collections.sort(temp);
		
		for (int i = 0; i < temp.size(); i++) {
			for (int j = 0 ; j < temp.size(); j++) {
				if( i == j) continue;
				if(temp.get(i).getValue() == temp.get(j).getValue()){
					return HandType.PAIR;
				}
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
//					for (int i = 0; i < hand1.length; i++) {
//						int cmp = hand1[i].compareTo(hand2[i]);
//						
//					}
				}
			case TWO_PAIR:
				return 0;
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
