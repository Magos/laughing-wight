package laughing_wight.participants;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import laughing_wight.model.Card;
import laughing_wight.model.Deck;

public class Dealer {
	protected Deck deck;
	private List<Player> players;
	private boolean gameInProgress;
	private static Logger logger = LoggerFactory.getLogger(Dealer.class);
	
	public Dealer(){
		deck = new Deck();
		players = new ArrayList<Player>();
		gameInProgress = false;
	}
	
	public void addPlayer(Player player){
		if(gameInProgress) return;
		players.add(player);
	}
	
	public void removePlayer(Player player){
		if(gameInProgress) return;
		players.remove(player);
	}
	
	public void startGame(){
		gameInProgress = true;
		logger.debug("Starting a game.");
		for(int i = 1; i <= players.size(); i++){
			Card hole1 = deck.draw();
			Card hole2 = deck.draw();
			logger.trace("Player {} drew hole cards {} and {}", i,hole1, hole2);
			players.get(i).dealHoleCards(hole1, hole2);
		}
		State game = new State();
	}
}
