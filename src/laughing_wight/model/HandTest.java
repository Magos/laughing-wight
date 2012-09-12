package laughing_wight.model;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;


public class HandTest {

	@Test
	public void testHand() {
		assertNotNull(new Hand(new Card[]{new Card(Suit.HEARTS,13),new Card(Suit.DIAMONDS,13),new Card(Suit.SPADES,2),new Card(Suit.CLUBS,2),new Card(Suit.HEARTS,4)}));
	}
	
	@Test
	public void testHighCards(){
		//Create some non-flush, non-straight "low cards"
		Card s2 = new Card(Suit.SPADES,2);
		Card d3 = new Card(Suit.DIAMONDS,3);
		Card d5 = new Card(Suit.DIAMONDS,5);
		Card c6 = new Card(Suit.CLUBS,6);
		//Test that each possible choice of high card for these is higher than the previous.
		Card previous = new Card(Suit.DIAMONDS,7);
		Set<Hand> previousHands = new HashSet<Hand>();
		previousHands.add(new Hand(new Card[]{s2,d3,d5,c6,previous}));
		for (int i = 8; i < 14; i++) {
			Card highCard = new Card(Suit.DIAMONDS,i);
			Hand high = new Hand(new Card[]{s2,d3,d5,c6,highCard});
			for (Hand hand : previousHands) {				
				assertTrue("High card " + highCard.toString() +  " beat "  + hand.getCards()[0].toString(), high.compareTo(hand) >0 );
			}
			previousHands.add(high);
			previous = highCard;
		}
	}

	@Test
	public void testDifferentHandTypes() {
		Hand twopair = new Hand(new Card[]{new Card(Suit.HEARTS,13),new Card(Suit.DIAMONDS,13),new Card(Suit.SPADES,2),new Card(Suit.CLUBS,3),new Card(Suit.HEARTS,4)});
		Hand onepair = new Hand(new Card[]{new Card(Suit.HEARTS,13),new Card(Suit.DIAMONDS,13),new Card(Suit.SPADES,2),new Card(Suit.CLUBS,8),new Card(Suit.HEARTS,4)});
		assertTrue("Two pair > One pair", twopair.compareTo(onepair)>0);
	}

}
