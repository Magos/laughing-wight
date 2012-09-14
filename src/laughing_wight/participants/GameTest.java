package laughing_wight.participants;

import static org.junit.Assert.*;

import org.junit.Test;

public class GameTest {
	
	@Test
	public void testStartGame() {
		Dealer dealer = new Dealer();
		Player player1 = new Player();
		Player player2 = new Player();
		Strategy rollOut = new RolloutStrategy();
		player1.setStrategy(rollOut);
		player2.setStrategy(rollOut);
		dealer.addPlayer(player1);
		dealer.addPlayer(player2);
		dealer.runGame();
	}

}
