package laughing_wight.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DeckTest {

	@Test
	public void testDraw() {
		Deck deck = new Deck();
		List<Card> list = new ArrayList<Card>();
		while(list.size() < 52){
			list.add(deck.draw());
		}
		List<Card> allCards = new ArrayList<Card>();
		for(Suit suit: Suit.values()){
			for (int i = 2; i < 14; i++) {
				allCards.add(new Card(suit,i));
			}
		}
		assertTrue("Deck should contain all cards.", list.containsAll(allCards));
	}
	
	@Test (expected=IllegalStateException.class)
	public void testEmptyDeck(){
		Deck deck = new Deck();
		for(int i = 0; i < 53; i++){
			assertEquals(52-i, deck.getRemaining());
			deck.draw();
		}
	}

}
