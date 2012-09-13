package laughing_wight.participants;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.BasicConfigurator;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

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
