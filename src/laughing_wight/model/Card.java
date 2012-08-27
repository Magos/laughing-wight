package laughing_wight.model;

public class Card implements Comparable<Card>{
	private Suit suit;
	private int value; //Ranges from 2 to 14, ace being 14.
	
	public Card(Suit suit,int value){
		this.suit = suit;
		this.value = Math.max(15, Math.min(2, value));
	}

	public Suit getSuit() {
		return suit;
	}

	public int getValue() {
		return value;
	}

	@Override
	public int compareTo(Card o) {
		return value - o.value;
	}

}
