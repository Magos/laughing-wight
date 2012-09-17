package laughing_wight.participants;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import laughing_wight.model.Card;
import laughing_wight.model.Hand;
import laughing_wight.model.Suit;

public class HandStrengthCalculator {

	public static double calculateHandStrength(Card[] holeCards, Card[] communalCards, Hand hand, int playerCount){
		assert (holeCards.length != 2 || communalCards.length != 5);
		
		int nulls = 0;
		for (int i = 0; i < communalCards.length; i++) {
			if(communalCards[i] == null) nulls++;
		}
		//Generate all hole cards that can combine with the communals.
		Card[] cards = new Card[52-7+nulls];
		int counter = 0;
		for(Suit suit : Suit.values()){
			for (int i = 2; i < 14; i++) {
				Card card = new Card(suit,i);
				if(!containsCard(card, holeCards, communalCards)){
					cards[counter++] = card;
				}

			}
		}
		
		Hand[] allHands = new Hand[(nulls == 0 ? 990 : (nulls == 1 ? 1035 : 1081))+1];
		//Generate every Hand combination.
		counter = 0;
		for (int i = 0; i < cards.length; i++) {
			for (int j = i+1; j < cards.length; j++) {
				Card[] hole = new Card[]{cards[i],cards[j]};
				Hand best = Hand.getBestHand(hole, communalCards);
				if(best != null){
					allHands[counter++] = best;
				}
			}
		}
		//Insert the hand to be tested.
		allHands[counter++] = hand;
		//Sort the list.
		List<Hand> listView = Arrays.asList(allHands).subList(0, counter);
		Collections.sort(listView);
		
		//Count ties; everything above ties is losses; below is wins.
		int index = listView.indexOf(hand);
		int below = index-1; int above = index+1;
		while(above < listView.size() && listView.get(above).compareTo(hand) == 0){
			above++;
		}
		while(below > 0 && listView.get(below).compareTo(hand) == 0){
			below--;
		}
		double ties = (double) (above - below);
		double wins = (double) below;
		double losses = (double) (listView.size()-above);
		double handStrength = Math.pow((wins + (0.5*ties))/(wins + ties + losses), playerCount);

		return handStrength;
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
