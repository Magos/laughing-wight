package laughing_wight.model;

import java.util.Random;

public class Deck {
	private Card[] cards;
	private int count;
	private Random rand;
	
	/** Create a new, shuffled deck of cards. */
	public Deck(){
		count = 51;
		cards = new Card[52];
		rand = new Random();
		for(Suit suit : Suit.values()){
			for (int i = 2; i < 15; i++) {
				cards[suit.ordinal()*13 + i - 2] = new Card(suit,i);
			}
		}
	}
	
	/** Draw a card from the deck. */
	public Card draw(){
		if(count < 0 ){
			throw new IllegalStateException("Drew from an empty card deck!");
		}
		int pick = (count == 0 ? 0 : rand.nextInt(count));
		Card ret = cards[pick];
		cards[pick] = cards[count];
		count--;
		return ret;
	}

	/** Get the count of cards remaining in the deck. */
	public int getRemaining(){
		return count+1;
	}
}
