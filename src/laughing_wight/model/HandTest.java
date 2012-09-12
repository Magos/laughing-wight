package laughing_wight.model;
import static org.junit.Assert.*;

import java.util.HashSet;
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
			assertEquals(HandType.PAIR,high.getType());
			assertEquals(HandType.PAIR, low.getType());
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
			assertEquals(HandType.TWO_PAIR, high.getType());
			for (Hand hand : previous) {
				assertTrue(hand.toString() + " >= " + high.toString(), high.compareTo(hand) > 0);
			}
			previous.add(high);
		}
	}

	@Test
	public void testThreeOfAKind(){
		Card s2 = new Card(Suit.SPADES,2);
		Card d4 = new Card(Suit.DIAMONDS,4);
		Hand tripleThree = new Hand(new Card[]{s2,d4,new Card(Suit.DIAMONDS,3), new Card(Suit.HEARTS,3),new Card(Suit.CLUBS,3)});
		assertEquals(HandType.THREE_OF_A_KIND, tripleThree.getType());
		Hand tripleFour = new Hand(new Card[]{s2,new Card(Suit.CLUBS,3),d4,new Card(Suit.HEARTS, 4),new Card(Suit.SPADES,4)});
		assertEquals(HandType.THREE_OF_A_KIND, tripleFour.getType());
		assertTrue("Triple 3 >= triple 4", tripleFour.compareTo(tripleThree) > 0);
		Set<Hand> previous = new HashSet<Hand>();
		previous.add(tripleThree);
		previous.add(tripleFour);
		for (int i = 5; i < 14; i++) {
			Hand high = new Hand(new Card[]{s2,d4,new Card(Suit.HEARTS,i),new Card(Suit.DIAMONDS,i), new Card(Suit.CLUBS,i)});
			for (Hand hand : previous) {
				assertTrue(hand.toString() + " >= " + high.toString(),high.compareTo(hand) > 0);
			}
		}
	}
	
	@Test
	public void testStraight(){
		Hand FiveHigh = new Hand(new Card[]{new Card(Suit.DIAMONDS,14), new Card(Suit.SPADES,2), new Card(Suit.HEARTS,3), new Card(Suit.CLUBS,4), new Card(Suit.DIAMONDS,5)});
		assertEquals(HandType.STRAIGHT,FiveHigh.getType());
		Set<Hand> previous = new HashSet<Hand>(); 
		previous.add(FiveHigh);
		for (int i = 6; i < 14-5; i++) {
			Card[] cards = new Card[5];
			for (int j = 0; j < 5; j++) {
				Suit suit = (j == 3 ? Suit.CLUBS : Suit.DIAMONDS);
				cards[j] = new Card(suit,i+j);
			}
			Hand hand = new Hand(cards);
			assertEquals(HandType.STRAIGHT,hand.getType());
			for (Hand low : previous) {
				assertTrue(low.toString() + " >= " + hand.toString(),hand.compareTo(low) >0);
			}
		}
	}

	@Test
	public void testDifferentHandTypes() {
		Hand straight = new Hand(new Card[]{new Card(Suit.CLUBS,4),new Card(Suit.HEARTS,5),new Card(Suit.HEARTS,6),new Card(Suit.CLUBS, 7), new Card(Suit.CLUBS,8)});
		Hand threeOfAKind = new Hand(new Card[]{new Card(Suit.HEARTS,13),new Card(Suit.DIAMONDS,13),new Card(Suit.SPADES,13),new Card(Suit.CLUBS,2),new Card(Suit.HEARTS,4)});
		Hand twoPair = new Hand(new Card[]{new Card(Suit.HEARTS,13),new Card(Suit.DIAMONDS,13),new Card(Suit.SPADES,2),new Card(Suit.CLUBS,2),new Card(Suit.HEARTS,4)});
		Hand onePair = new Hand(new Card[]{new Card(Suit.HEARTS,13),new Card(Suit.DIAMONDS,13),new Card(Suit.SPADES,2),new Card(Suit.CLUBS,8),new Card(Suit.HEARTS,4)});
		Hand highCard = new Hand(new Card[]{new Card(Suit.HEARTS,13),new Card(Suit.DIAMONDS,12),new Card(Suit.SPADES,2),new Card(Suit.CLUBS,8),new Card(Suit.HEARTS,4)});
		assertTrue("High card >= Straight", straight.compareTo(highCard) >0);
		assertTrue("High card >= Three of a kind", threeOfAKind.compareTo(highCard) >0);
		assertTrue("High card >= Pair", onePair.compareTo(highCard) >0);
		assertTrue("High card >= Two Pair", twoPair.compareTo(highCard) >0);
		assertTrue("One Pair >= Two Pair", twoPair.compareTo(onePair) >0);
		assertTrue("One Pair >= Three of a kind", threeOfAKind.compareTo(onePair) >0);
		assertTrue("Two Pair >= Three of a kind",threeOfAKind.compareTo(twoPair) >0);
	}

}
