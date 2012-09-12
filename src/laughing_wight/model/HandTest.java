package laughing_wight.model;
import static org.junit.Assert.*;

import org.junit.Test;


public class HandTest {

	@Test
	public void testHand() {
		assertNotNull(new Hand(new Card[]{}));
	}

	@Test
	public void testCompareTo() {
		Hand twopair = new Hand(new Card[]{new Card(Suit.HEARTS,13),new Card(Suit.DIAMONDS,13),new Card(Suit.SPADES,2),new Card(Suit.CLUBS,2),new Card(Suit.HEARTS,4)});
		Hand onepair = new Hand(new Card[]{new Card(Suit.HEARTS,13),new Card(Suit.DIAMONDS,13),new Card(Suit.SPADES,2),new Card(Suit.CLUBS,8),new Card(Suit.HEARTS,4)});
		assertTrue("Two pair > One pair", twopair.compareTo(onepair)>0);
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

}
