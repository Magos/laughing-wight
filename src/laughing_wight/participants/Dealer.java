package laughing_wight.participants;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import laughing_wight.model.Card;
import laughing_wight.model.Deck;
import laughing_wight.model.Round;

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
	
	public void runGame(){
		gameInProgress = true;
		logger.debug("Starting a game.");
		for(int i = 0; i < players.size(); i++){
			Card hole1 = deck.draw();
			Card hole2 = deck.draw();
			logger.trace("Player {} drew hole cards {} and {}", i,hole1, hole2);
			players.get(i).dealHoleCards(hole1, hole2);
		}
		State game = new State();
		//Pre-flop betting.
		doBetting(game);
		
		//Flop
		Card flop1, flop2, flop3;
		flop1 = deck.draw();
		flop2 = deck.draw();
		flop3 = deck.draw();
		logger.trace("Flop was {} {} {}",flop1, flop2, flop3);
		game.setRound(Round.FLOP);
		doBetting(game);
		
		//Turn
		Card turn = deck.draw();
		logger.trace("Turn was {}",turn);
		game.dealTurn(turn);
		game.setRound(Round.TURN);
		doBetting(game);
		
		//River
		Card river = deck.draw();
		logger.trace("River was {}",river);
		game.dealRiver(river);
		game.setRound(Round.RIVER);
		doBetting(game);
		
		//Showdown
		gameInProgress  = false;
	}

	private void doBetting(State game) {
		for(int i = 0; i < players.size(); i++){
			Player ply = players.get(i);
			Action action = ply.getAction(game);
			logger.trace("Player {} chose action {}.",i,action);
			updateState(game,ply,action);
		}
	}

	private void updateState(State game, Player ply, Action action) {
		// TODO Auto-generated method stub
		
	}
}
