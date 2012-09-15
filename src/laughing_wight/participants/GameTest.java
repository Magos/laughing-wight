package laughing_wight.participants;

import static org.junit.Assert.*;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameTest {
	private static Logger logger = LoggerFactory.getLogger(GameTest.class);
	
	@Test
	public void testStartGame() {
		Dealer dealer = new Dealer();
		Player player1 = new Player("Alice");
		Player player2 = new Player("Bob");
		Strategy rollOut = new RolloutStrategy();
		player1.setStrategy(rollOut);
		player2.setStrategy(rollOut);
		dealer.addPlayer(player1);
		dealer.addPlayer(player2);
		dealer.runGame(0);
		Player player3 = new Player("Carol");
		player3.setStrategy(new RandomStrategy());
		dealer.addPlayer(player3);
		dealer.runGame(1);
	}
	
	@Test
	public void testManyGames(){
		Dealer dealer = new Dealer();
		Player player1 = new Player("Alice");
		Player player2 = new Player("Bob");
		Player player3 = new Player("Carol");
		Strategy random = new RandomStrategy();
		player1.setStrategy(random);
		player2.setStrategy(random);
		player3.setStrategy(random);
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
