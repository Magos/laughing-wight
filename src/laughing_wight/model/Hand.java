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

	private static int tieBreak(HandType type2, Card[] hand, Card[] arg0) {
			switch(type2){
			case HIGH_CARD:
				for (int i = 0; i < hand.length; i++) {
					int comp = hand[i].compareTo(arg0[i]);
					if(comp != 0) return comp;
				}
				return 0;
			case PAIR:
				int pair1 = -1; 
				int pair2 = -1;
				for (int i = 0; i < hand.length-1; i++) {
					if(hand[i].getValue() == hand[i+1].getValue()){
						pair1 = hand[i].getValue();
					}
					if(arg0[i].getValue() == arg0[i+1].getValue()){
						pair2 = arg0[i].getValue();
					}
				}
				if(pair1 - pair2 != 0){
					return pair1 - pair2;
				}else{
					
				}
				return 0;
			default:
				return 0;
			}
	}

}
