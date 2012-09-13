package laughing_wight.model;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.Test;


public class HandTest {

	private void checkGreater(Hand high, Hand low){
		assertTrue(low.toString() +  " beat "  + high.toString(), high.compareTo(low) > 0 );
		assertTrue(low.toString() +  " beat "  + high.toString(), low.compareTo(high) < 0 );
	}
	
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
				checkGreater(high,hand);
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
		checkGreater(pairFive,pairFour);
		Hand pairFiveLowkicker = new Hand(new Card[]{s2,d3,c6,new Card(Suit.HEARTS,5), new Card(Suit.DIAMONDS,11)});
		checkGreater(pairFive, pairFiveLowkicker);
		previous.add(pairFive);
		previous.add(pairFiveLowkicker);
		Hand pairSix = new Hand(new Card[]{s2,d3,d5,c6,new Card(Suit.DIAMONDS,6)});
		for (Hand hand : previous) {
			checkGreater(pairSix, hand);
		}
		previous.add(pairSix);
		//Then do the higher cards on a loop.
		for (int i = 7; i < 14; i++) {
			Hand low = new Hand(new Card[]{s2,d3,s4,new Card(Suit.DIAMONDS,i),new Card(Suit.HEARTS,i)});
			Hand high = new Hand(new Card[]{s2,d3,d5,new Card(Suit.DIAMONDS,i),new Card(Suit.HEARTS,i)});
			for (Hand hand : previous) {
				checkGreater(high,hand);
				checkGreater(low,hand);
			}
			checkGreater(high,low);
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
				checkGreater(high,hand);
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
				checkGreater(high,hand);
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
				checkGreater(hand, low);
			}
		}
	}
	
	@Test
	public void testFlush(){
		fail("Not yet implemented.");
//		for(Suit suit : Suit.values()){
//			for(int i = 2; i < 14;i++){
//				//Generate all selections of 5 distinct values in [2,14] which do not include i
//				//Create hands and compare based on high card.
//			}
//		}
		
	}
	
	@Test
	public void testFullHouse(){
		Set<Hand> previous = new HashSet<Hand>();
		for (int i = 2; i < 14; i++) {
			for (int j = 2; j < 14; j++) {
				if(i == j) continue;
				Hand high = new Hand(new Card[]{new Card(Suit.HEARTS,i),new Card(Suit.DIAMONDS,i), new Card(Suit.CLUBS,i), new Card(Suit.DIAMONDS,j), new Card(Suit.SPADES,j)});
				for (Hand hand : previous) {
					checkGreater(high, hand);
				}
				previous.add(high);
			}
		}
	}
	
	@Test
	public void testStraightFlush(){
		for(Suit suit : Suit.values()){
			Hand fiveHigh = new Hand(new Card[]{new Card(suit,14), new Card(suit,2), new Card(suit,3), new Card(suit,4), new Card(suit,5)});
			Set<Hand> previous = new HashSet<Hand>();
			previous.add(fiveHigh);
			for(int i = 6; i < 14;i++){
				Hand high = new Hand(new Card[]{new Card(suit,i-4), new Card(suit,i-3), new Card(suit,i-2), new Card(suit,i-1), new Card(suit,i)});
				for (Hand hand : previous) {
					checkGreater(high, hand);
				}
				previous.add(high);
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
