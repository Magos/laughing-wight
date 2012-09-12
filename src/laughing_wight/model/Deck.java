package laughing_wight.model;

import java.util.Random;

public class Deck {
	private Card[] cards;
	private int count;
	private Random rand;
	
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

}
