package laughing_wight.model;

/** A special Deck that is missing two specific cards, given in its constructor. 
 * For use in rollout simulations.*/
public class RolloutDeck extends Deck {
	public RolloutDeck(Card hole1, Card hole2){
		super();
		for (int i = 0; i < cards.length; i++) {
			if(cards[i].equals(hole1) || cards[i].equals(hole2)){
				cards[i] = cards[count];
				count--;
				
			}
		}
	}

}
