package laughing_wight.model;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
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
		//Test that each possible choice of high card for these is higher than the previous ones.
		Card previous = new Card(Suit.DIAMONDS,7);
		Set<Hand> previousHands = new HashSet<Hand>();
		previousHands.add(new Hand(new Card[]{s2,d3,d5,c6,previous}));
		for (int i = 8; i < 14; i++) {
			Card highCard = new Card(Suit.DIAMONDS,i);
			Hand high = new Hand(new Card[]{s2,d3,d5,c6,highCard});
			for (Hand hand : previousHands) {				
				assertTrue("High card " + hand.getCards()[0].toString() +  " beat "  + highCard.toString(), high.compareTo(hand) > 0 );
			}
			previousHands.add(high);
			previous = highCard;
		}
	}

	@Test
	public void testPairs(){
		//Create some non-flush, non-straight "junk cards".
		Card s2 = new Card(Suit.SPADES,2);	
		Card d3 = new Card(Suit.DIAMONDS,3);
		Card d5 = new Card(Suit.DIAMONDS,5);
		Card c6 = new Card(Suit.CLUBS,6);
		Card s4 = new Card(Suit.SPADES,4);
		//Test that each possible pairing is valued correctly.
		//Start with some special cases around the used junk cards
		Hand pairFour = new Hand(new Card[]{s2,d3,c6,new Card(Suit.HEARTS,4), s4, new Card(Suit.CLUBS,8)});
		assertTrue("Pair of fours is not a pair",pairFour.getType()==HandType.PAIR);
		Set<Hand> previous = new HashSet<Hand>();
		previous.add(pairFour);
		Hand pairFive = new Hand(new Card[]{s2,d3,c6,d5,new Card(Suit.HEARTS,5), new Card(Suit.DIAMONDS,13)});
		assertTrue("Pair of 5 < Pair of 4", pairFive.compareTo(pairFour) > 0);
		Hand pairFiveLowkicker = new Hand(new Card[]{s2,d3,c6,new Card(Suit.HEARTS,5), new Card(Suit.DIAMONDS,11)});
		assertTrue("Pair of 5, king high loses to pair of 5 jack high.", pairFive.compareTo(pairFiveLowkicker) > 0);
		previous.add(pairFive);
		previous.add(pairFiveLowkicker);
		Hand pairSix = new Hand(new Card[]{s2,d3,d5,c6,new Card(Suit.DIAMONDS,6)});
		for (Hand hand : previous) {
			assertTrue(pairSix.compareTo(hand) > 0);
		}
		previous.add(pairSix);
		//Then do the higher cards on a loop.
		for (int i = 7; i < 14; i++) {
			Hand low = new Hand(new Card[]{s2,d3,s4,new Card(Suit.DIAMONDS,i),new Card(Suit.HEARTS,i)});
			Hand high = new Hand(new Card[]{s2,d3,d5,new Card(Suit.DIAMONDS,i),new Card(Suit.HEARTS,i)});
			for (Hand hand : previous) {
				assertTrue(high.compareTo(hand) > 0);
				assertTrue(low.compareTo(hand) > 0);
			}
			assertTrue(high.compareTo(low) > 0);
			previous.add(low);
			previous.add(high);
		}

	}

	@Test
	public void testTwoPair(){
		Card d2 = new Card(Suit.DIAMONDS,2);
		Card h2 = new Card(Suit.HEARTS,2);
		Card s3 = new Card(Suit.SPADES,3);
		Card s4 = new Card(Suit.SPADES,4);
		Set<Hand> previous = new HashSet<Hand>();
		for (int n = 3; n < 14; n++) {
			Hand high = new Hand(new Card[]{d2,h2,(n == 3 ? s4 : s3), new Card(Suit.HEARTS,n), new Card(Suit.DIAMONDS,n)});
			for (Hand hand : previous) {
				assertTrue(hand.toString() + " >= " + high.toString(), high.compareTo(hand) > 0);
			}
			previous.add(high);
		}
	}


	@Test
	public void testDifferentHandTypes() {
		Hand onePair = new Hand(new Card[]{new Card(Suit.HEARTS,13),new Card(Suit.DIAMONDS,13),new Card(Suit.SPADES,2),new Card(Suit.CLUBS,8),new Card(Suit.HEARTS,4)});
		Hand highCard = new Hand(new Card[]{new Card(Suit.HEARTS,13),new Card(Suit.DIAMONDS,12),new Card(Suit.SPADES,2),new Card(Suit.CLUBS,8),new Card(Suit.HEARTS,4)});
		assertTrue("High card > Pair", onePair.compareTo(highCard)>0);
	}

}
