package laughing_wight.participants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import laughing_wight.model.Card;
import laughing_wight.model.Deck;
import laughing_wight.model.Round;

public class Dealer implements GameState {
	private static Logger logger = LoggerFactory.getLogger(Dealer.class);

	private List<Player> players;
	private boolean gameInProgress;

	protected Deck deck;
	private Round round;
	private Card[] communalCards;
	private Map<Player,Integer> bets;
	private Map<Player,Boolean> active;




	public Dealer(){
		players = new ArrayList<Player>();
		gameInProgress = false;
		communalCards = new Card[5];
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
		setupGame();
		//Pre-flop betting.
		doBetting();

		//Flop
		Card flop1, flop2, flop3;
		flop1 = deck.draw();
		flop2 = deck.draw();
		flop3 = deck.draw();
		logger.trace("Flop was {} {} {}",flop1, flop2, flop3);
		doBetting();

		//Turn
		Card turn = deck.draw();
		logger.trace("Turn was {}",turn);
		doBetting();

		//River
		Card river = deck.draw();
		logger.trace("River was {}",river);
		doBetting();

		//Showdown

		//Record game results. 
		gameInProgress  = false;
	}

	private void setupGame() {
		this.round = Round.PRE_FLOP;
		deck = new Deck();
		bets = new HashMap<Player,Integer>();
		active = new HashMap<Player,Boolean>();
		for (Player ply : players) {
			bets.put(ply, 0);
			active.put(ply, true);
		}
		logger.debug("Starting a game.");

		for(int i = 0; i < players.size(); i++){
			players.get(i).reset();
			Card hole1 = deck.draw();
			Card hole2 = deck.draw();
			logger.trace("Player {} drew hole cards {} and {}", i,hole1, hole2);
			players.get(i).dealHoleCards(hole1, hole2);
		}

	}

	private void doBetting() {
		for(int i = 0; i < players.size(); i++){
			Player ply = players.get(i);
			if(!active.get(ply))continue;
			Action action = ply.getAction(this);
			logger.trace("Player {} chose action {}.",i,action);
			updateState(ply,action);
		}
	}

	private void updateState(Player ply, Action action) {
		switch(action){
		case FOLD:
			active.put(ply,false);
			break;
		case CALL:
			bets.put(ply, Collections.max(bets.values()));
			break;
		case BET:
			bets.put(ply, Collections.max(bets.values())+1);
			break;
		default:
			assert false;
		}

	}


	@Override
	public Round getRound() {
		return round;
	}


	@Override
	public int getBets(Player player) {
		return bets.get(player);
	}


	@Override
	public int getPot(){
		int ret = 0;
		for(Integer i : bets.values()){
			ret += i;
		}
		return ret;
	}


	@Override
	public List<Player> getPlayers() {
		return players;
	}


	@Override
	public Card[] getCommunalCards() {
		return communalCards;
	}
}
