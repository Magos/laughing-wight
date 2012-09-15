package laughing_wight.participants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import laughing_wight.model.Card;
import laughing_wight.model.Deck;
import laughing_wight.model.Hand;
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
	private Map<Player, Card[]> holeCards;




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

	public GameResult runGame(int gameNumber){
		gameInProgress = true;
		setupGame(gameNumber);

		for(Round chosenRound : Round.values()){
			this.round = chosenRound;
			
			//Deal cards, if any.
			switch(round){
			case FLOP:
				Card flop1 = deck.draw();
				Card flop2 = deck.draw();
				Card flop3 = deck.draw();
				logger.trace("Flop was {} {} {}.", flop1, flop2, flop3);
				communalCards[0] = flop1;
				communalCards[1] = flop2;
				communalCards[2] = flop3;
				break;
			case TURN:
				Card turn = deck.draw();
				logger.trace("Turn was {}.", turn);
				communalCards[3] = turn;
				break;
			case RIVER:
				Card river = deck.draw();
				logger.trace("River was {}.",river);
				communalCards[4] = river;
				break;
			default:
				//Do nothing.
			}
			
			//Do betting
			doBetting(gameNumber);
			
			//Check for victory by folding.
			if(getActivePlayerCount()== 1){
				for(Entry<Player,Boolean> entry : active.entrySet()){
					if(entry.getValue() == true){
						List<Player> winners = new ArrayList<Player>();
						winners.add(entry.getKey());
						return new GameResult(bets, winners);
					}
				}
			}
		}
		
		//We've reached showdown. Compare active players's cards.
		List<Player> actives = new ArrayList<Player>();
		for(Entry<Player,Boolean> entry : active.entrySet()){
			if(entry.getValue() == true){
				actives.add(entry.getKey());
			}
		}
		Map<Player,Hand> hands = new HashMap<Player,Hand>();
		for(Player ply : actives){
			hands.put(ply, getBestHand(ply));
		}
		
		
		return null;
	}

	private Hand getBestHand(Player ply) {
		Card[] hole = holeCards.get(ply);
		Hand[] allHands = new Hand[21]; 
		Card[] available = new Card[]{hole[0],hole[1],communalCards[0], communalCards[1],communalCards[2],communalCards[3],communalCards[4]};
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				if(i == j) continue;
				allHands[i*7 + j] = generateHand(available,i, j);
			}
			
		}
		List<Hand> temp = Arrays.asList(allHands);
		Collections.sort(temp);
		return temp.get(temp.size()-1);
	}

	private static Hand generateHand(Card[] available, int skip1, int skip2) {
		assert available.length == 7;
		Card[] cards = new Card[5];
		for (int i = 0; i < 5; i++) {
			int j = i;
			if(j >= skip1) j++;
			if(j >= skip2) j++;
			cards[i] = available[j];
		}
		return new Hand(cards);
	}

	private void setupGame(int gameNumber) {
		this.round = Round.PRE_FLOP;
		deck = new Deck();
		bets = new HashMap<Player,Integer>();
		active = new HashMap<Player,Boolean>();
		holeCards = new HashMap<Player,Card[]>();
		for (Player ply : players) {
			bets.put(ply, 0);
			active.put(ply, true);
		}
		logger.debug("Starting game {}.",gameNumber);

		for(int i = 0; i < players.size(); i++){
			Player ply = players.get(i);
			ply.reset();
			Card hole1 = deck.draw();
			Card hole2 = deck.draw();
			logger.trace("Player {} drew hole cards {} and {}", i,hole1, hole2);
			ply.dealHoleCards(hole1, hole2);
			holeCards.put(ply,new Card[]{hole1,hole2});
		}

	}

	private void doBetting(int gameNumber) {
		for(int i = 0; i < players.size(); i++){
			int j = (i + gameNumber) % players.size();
			Player ply = players.get(j);
			if(!active.get(ply))continue;
			Action action = ply.getAction(this);
			logger.trace("Player {} chose action {}.",j,action);
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

	@Override
	public boolean isPlayerActive(Player player) {
		return active.get(player);
	}

	@Override
	public int getActivePlayerCount() {
		int ret = 0;
		for(Boolean bool : active.values()){
			ret += (bool ? 1 : 0);
		}
		return ret;
	}
}
