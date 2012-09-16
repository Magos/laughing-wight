package laughing_wight.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import laughing_wight.participants.Player;

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

		//Check for flushness and straightness.
		boolean isFlush = isFlush(cards);
		boolean isStraight = isStraight(cards);
		if(isFlush && isStraight){
			return HandType.STRAIGHT_FLUSH;
		}else if(isFlush){
			return HandType.FLUSH;
		}else if(isStraight){
			return HandType.STRAIGHT;
		}

		//Check value pairings.
		int[] counts = new int[15];
		for (int i = 0; i < cards.length; i++) {
			int value = cards[i].getValue();
			counts[value]++;
		}
		int highest = 0;
		for (int i = 1; i < counts.length; i++) {
			if(counts[i] > counts[highest]){
				highest = i;
			}
		}
		int nextHighest = 0;
		for (int i = 1; i < counts.length; i++) {
			if(counts[i] > counts[nextHighest] && i != highest){
				nextHighest = i;
			}
		}
		int highestCount = counts[highest];
		int nextHighestCount = counts[nextHighest];
		if(highestCount == 4){
			return HandType.FOUR_OF_A_KIND;
		}else if(highestCount == 3 && nextHighestCount == 2){
			return HandType.FULL_HOUSE;
		}else if(highestCount == 3 && nextHighestCount == 1){
			return HandType.THREE_OF_A_KIND;
		}else if(highestCount == 2 && nextHighestCount == 2){
			return HandType.TWO_PAIR;
		}else if(highestCount == 2 && nextHighestCount == 1){
			return HandType.PAIR;
		}
		return HandType.HIGH_CARD;
	}

	private static boolean isStraight(Card[] cards) {
		//Check for the 5 high special case (Where ace plays as 1 rather than 14).
		if(cards[0].getValue()==2 && cards[1].getValue() == 3 && cards[2].getValue() == 4 && cards[3].getValue() == 5 && cards[4].getValue() == 14){
			return true;
		}
		//Consider every other case.
		int i = cards[0].getValue();
		for (int j = 1; j < cards.length; j++) {
			i++;
			if(cards[j].getValue() != i){
				return false;
			}
		}
		return true;
	}


	private static boolean isFlush(Card[] cards){
		Suit suit = cards[0].getSuit();
		for (int i = 1; i < cards.length; i++) {
			if(cards[i].getSuit() != suit){
				return false;
			}
		}
		return true;
	}

	@Override
	public int compareTo(Hand arg0) {
		if(arg0 == null){
			return 1;
		}
		int coarsecomparison = type.ordinal() - arg0.type.ordinal();
		if(coarsecomparison != 0){ 
			return coarsecomparison;
		}else{
			return tieBreak(type, this.cards,arg0.cards);
		}
	}

	public static Hand getBestHand(Card[] hole, Card[] communalCards) {
		assert hole.length == 2;
		assert communalCards.length == 5;
		Hand[] allHands = new Hand[21]; 
		Card[] available = new Card[]{hole[0],hole[1],communalCards[0], communalCards[1],communalCards[2],communalCards[3],communalCards[4]};
		int counter = 0;
		for (int i = 0; i < 7; i++) {
			for (int j = i+1; j < 7; j++) {
				Hand hand =  generateHand(available,i, j);
				if(hand != null){
					allHands[counter++] = hand;
				}
			}
		}
		if(counter > 0){
			List<Hand> temp = Arrays.asList(allHands).subList(0, counter);
			Collections.sort(temp);
			return temp.get(temp.size()-1);
		}else{
			return null;
		}
	}

	private static Hand generateHand(Card[] available, int skip1, int skip2) {
		assert available.length == 7;
		Card[] cards = new Card[5];
		for (int i = 0; i < 5; i++) {
			int j = i;
			if(j >= skip1) j++;
			if(j >= skip2) j++;
			if(available[j] == null) return null;
			cards[i] = available[j];
		}
		return new Hand(cards);
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
			if(pair11 - pair21 != 0){
				return pair11 - pair21;
			}else if(pair12 - pair22 != 0){
				return pair12 - pair22;
			}else{
				return tieBreak(HandType.HIGH_CARD, hand1, hand2);
			}
		case THREE_OF_A_KIND:
			pair1 = -1; pair2 = -1;
			for (int i = 0; i < hand1.length-1; i++) {
				if(hand1[i].getValue() == hand1[i+1].getValue()){
					pair1 = hand1[i].getValue();
				}
				if(hand2[i].getValue() == hand2[i+1].getValue()){
					pair2 = hand2[i].getValue();
				}
			}
			if (pair1 - pair2 != 0){
				return pair1 - pair2;//Highest pair wins.
			}else{
				return tieBreak(HandType.HIGH_CARD,hand1,hand2);//Compare kickers.
			}
		case STRAIGHT:
			//Check for 5-high
			int high1 = (hand1[4].getValue() == 14 && hand1[3].getValue() == 5 ? 5 : hand1[4].getValue());
			int high2 = (hand2[4].getValue() == 14 && hand2[3].getValue() == 5 ? 5 : hand2[4].getValue());
			return high1 - high2;
		case FLUSH:
			return tieBreak(HandType.HIGH_CARD,hand1,hand2);//Highest card wins.
		case FULL_HOUSE:
			//Highest triple wins.  
			int cmp = hand1[2].getValue() - hand2[2].getValue(); //In every full house, the 3rd card is part of the triple, not the pair.
			if(cmp != 0){
				return cmp;
			}
			pair1 = (hand1[1].getValue() != hand1[2].getValue() ? hand1[1].getValue() : hand1[3].getValue());
			pair2 = (hand2[1].getValue() != hand2[2].getValue() ? hand2[1].getValue() : hand2[3].getValue());
			return pair1 - pair2;
		case FOUR_OF_A_KIND:
			cmp = hand1[1].getValue() - hand2[1].getValue();
			if(cmp != 0){
				return cmp;
			}
			return tieBreak(HandType.HIGH_CARD, hand1, hand2);
		case STRAIGHT_FLUSH:
			//As per straights.
			high1 = (hand1[4].getValue() == 14 && hand1[3].getValue() == 5 ? 5 : hand1[4].getValue());
			high2 = (hand2[4].getValue() == 14 && hand2[3].getValue() == 5 ? 5 : hand2[4].getValue());
			return high1 - high2;

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
