package laughing_wight.participants;


import laughing_wight.model.Card;
import laughing_wight.model.Suit;

public class HandStrengthCalculator {

	public static double calculateHandStrength(Card[] holeCards, Card[] communalCards){
		assert (holeCards.length != 2 || communalCards.length != 5);
		
		//Generate all hole cards that can combine with the communals.
		Card[] cards = new Card[47];
		int counter = 0;
		for(Suit suit : Suit.values()){
			for (int i = 2; i < 14; i++) {
				Card card = new Card(suit,i);
				if(!containsCard(card, holeCards, communalCards)){
					cards[counter++] = card;
				}

			}
		}
		//Generate every Hand combination.
		
		//Insert the hand to be tested.
		
		//Sort the list.
		//Count ties; everything above ties is losses; below is wins.
		
		

		return -1;
	}
	
	private static boolean containsCard(Card card,Card[] holeCards, Card[] communalCards ){
		for (int i = 0; i < communalCards.length; i++) {
			if(card.equals(communalCards[i])) return true;
		}
		for (int i = 0; i < holeCards.length; i++) {
			if(card.equals(holeCards[i])) return true;
		}
		return false;
	}

}
