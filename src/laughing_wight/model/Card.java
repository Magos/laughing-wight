package laughing_wight.model;

public class Card implements Comparable<Card>{
	private Suit suit;
	private int value; //Ranges from 2 to 14, ace being 14.
	
	public Card(Suit suit,int value){
		this.suit = suit;
		this.value = (value < 2 ? 2 : (value > 14 ? 14 : value));
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

	@Override
	public String toString() {
		String ret = "";
		switch(suit){
		case CLUBS:
			ret = "C";
			break;
		case DIAMONDS:
			ret = "D";
			break;
		case HEARTS:
			ret = "H";
			break;
		case SPADES:
			ret = "S";
			break;
		}
		switch(value){
		case 14:
			ret += "A";
			break;
		case 13:
			ret += "K";
			break;
		case 12:
			ret += "Q";
			break;
		case 11:
			ret += "J";
			break;
		default:
			ret += value;
		}
		return ret;
	}
}
