package laughing_wight.participants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import laughing_wight.model.Card;
import laughing_wight.model.RolloutDeck;
import laughing_wight.model.RolloutResult;
import laughing_wight.model.Suit;


public class RolloutDealer extends Dealer {
	private static final int NUM_TESTS = 250;
	
	private Card hole1, hole2;
	
	

	public RolloutDealer(Card hole1, Card hole2) {
		this.hole1 = hole1;
		this.hole2 = hole2;
	}


	public static void main(String[] args) {
		//9 different table sizes are considered, and there are 169 equivalence classes of paired cards
		double[][][] suited = new double[9][13][13];
		double[][][] unSuited = new double[9][13][13];
		for(int playerCount = 2; playerCount <= 10; playerCount++){
			System.out.println("Starting on table size " + playerCount);
			for(int cardIValue = 2; cardIValue <= 14; cardIValue++){
				for(int cardJValue = 2; cardJValue <= 14;cardJValue++){
					Card cardI = new Card(Suit.DIAMONDS,cardIValue);
					Card cardJSuited = new Card(Suit.DIAMONDS,cardJValue);
					Card cardJUnsuited = new Card(Suit.CLUBS,cardJValue);
					
					RolloutDealer suitedDealer = new RolloutDealer(cardI,cardJSuited);
					RolloutDealer unsuitedDealer = new RolloutDealer(cardI,cardJUnsuited);
					
					Player suitedPlayer = new RolloutPlayer("0");
					Player unsuitedPlayer = new RolloutPlayer("0");
					suitedDealer.addPlayer(suitedPlayer);
					unsuitedDealer.addPlayer(unsuitedPlayer);
					for (int i = 1; i < playerCount; i++) {
						suitedDealer.addPlayer(new RolloutPlayer(Integer.toString(i)));
						unsuitedDealer.addPlayer(new RolloutPlayer(Integer.toString(i)));
					}
					
					
					long suitedWins = 0;
					long unsuitedWins = 0;
					for(int i = 0; i < NUM_TESTS;i++){
						GameResult result = suitedDealer.runGame(i);
						if(result.getWinners().contains(suitedPlayer)){
							suitedWins++;
						}
						result = unsuitedDealer.runGame(i);
						if(result.getWinners().contains(unsuitedPlayer)){
							unsuitedWins++;
						}
					}
					suited[playerCount-2][cardIValue-2][cardJValue-2] =   ((double)(suitedWins) / (double) (NUM_TESTS));
					unSuited[playerCount-2][cardIValue-2][cardJValue-2] = ((double)(unsuitedWins) / (double) (NUM_TESTS));
//					System.out.println("Player count is " + playerCount);
//					System.out.println("Card values are " + cardIValue + " " + cardJValue);
//					System.out.println("Suited wins " + suitedWins + " " + suited[playerCount-2][cardIValue-2][cardJValue-2] );
//					System.out.println("Unsuited wins " + unsuitedWins + " " + unSuited[playerCount-2][cardIValue-2][cardJValue-2]);
					
				}
			}
		}
		
		RolloutResult result = new RolloutResult(suited, unSuited);
		FileOutputStream file;
		try {
			file = new FileOutputStream("Output.rollout");
			ObjectOutputStream stream = new ObjectOutputStream(file);
			stream.writeObject(result);
			stream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void resetGame(int gameNumber) {
		super.resetGame(gameNumber);
		deck = new RolloutDeck(hole1, hole2);
	}
	
	@Override
	protected void drawHoleCards() {
		//Deal the hole cards being rollout simulated to the first player.
		Player chosen = players.get(0);
		chosen.dealHoleCards(hole1, hole2);
		holeCards.put(chosen, new Card[]{hole1,hole2});
		
		//Deal remainder of cards as normal.
		for(int i = 1; i < players.size(); i++){
			Player ply = players.get(i);
			ply.reset();
			Card hole1 = deck.draw();
			Card hole2 = deck.draw();
			logger.trace("Player {} drew hole cards {} and {}", i,hole1, hole2);
			ply.dealHoleCards(hole1, hole2);
			holeCards.put(ply,new Card[]{hole1,hole2});
		}
	}

}
