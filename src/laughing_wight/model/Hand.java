package laughing_wight.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Hand implements Comparable<Hand> {
	private Card[] cards = new Card[5];
	
	public Hand(Card[] cards){
		List<Card> temp = Arrays.asList(cards);
		Collections.sort(temp);
		for (int i = 0; i < temp.size(); i++) {
			cards[i] = temp.get(i);
		}
	}
	
	@Override
	public int compareTo(Hand arg0) {
		return 0;
	}

}
