package laughing_wight.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Hand implements Comparable<Hand> {
	private Card[] cards = new Card[5];
	private HandType type;
	
	public Hand(Card[] cards){
		type = getHandType(cards);
		
	}
	
	private HandType getHandType(Card[] cards) {
		List<Card> temp = Arrays.asList(cards);
		Collections.sort(temp);
		for(Card card : temp){
			
		}
		return null;
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

	private static int tieBreak(HandType type2, Card[] hand1, Card[] hand2) {
			switch(type2){
			case HIGH_CARD:
				for (int i = 0; i < hand1.length; i++) {
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
				}else{//If same pairs, remove them and compare as high card.
					Card[] newHand1 = new Card[3];
					int hand1Pointer = 0;
					Card[] newHand2 = new Card[3];
					int hand2Pointer = 0;
					for (int i = 0; i < hand1.length; i++) {
						if(hand1[i].getValue() != pair1){
							newHand1[++hand1Pointer] = hand1[i]; 
						}
						if(hand2[i].getValue() != pair1){
							newHand2[++hand2Pointer] = hand2[i]; 
						}
					}
					return tieBreak(HandType.HIGH_CARD, newHand1,newHand2);
				}
			default:
				return 0;
			}
	}

}
