package laughing_wight.participants;

import static org.junit.Assert.*;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameTest {
	private static Logger logger = LoggerFactory.getLogger(GameTest.class);
	
	@Test
	public void testRunGame() {
		Dealer dealer = new Dealer();
		Player player1 = new RolloutPlayer("Alice");
		Player player2 = new RolloutPlayer("Bob");
		dealer.addPlayer(player1);
		dealer.addPlayer(player2);
		dealer.runGame(0);
		Player player3 = new RandomPlayer("Carol");
		dealer.addPlayer(player3);
		dealer.runGame(1);
	}
	
	@Test
	public void testManyGames(){
		Dealer dealer = new Dealer();
		Player player1 = new RandomPlayer("Alice");
		Player player2 = new SimplePlayer("Bob");
		Player player3 = new RandomPlayer("Carol");
		dealer.addPlayer(player1);
		dealer.addPlayer(player2);
		dealer.addPlayer(player3);
		int player1Earnings = 0;
		int player2Earnings = 0;
		int player3Earnings = 0;
		for (int i = 0; i < 10000; i++) {
			GameResult result = dealer.runGame(i);
			logger.debug("Result was {}.",result);
			player1Earnings += (result.getWinners().contains(player1) ? result.getPot() / result.getWinners().size() : 0) - result.getBets().get(player1);
			player2Earnings += (result.getWinners().contains(player2) ? result.getPot() / result.getWinners().size() : 0) - result.getBets().get(player2);
			player3Earnings += (result.getWinners().contains(player3) ? result.getPot() / result.getWinners().size() : 0) - result.getBets().get(player3);
		}
		logger.info("Total earnings were Alice={} Bob={} Carol={}.",player1Earnings,player2Earnings,player3Earnings);
	}

}
