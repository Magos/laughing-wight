package laughing_wight.participants;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameSimulator {
	private static Logger logger = LoggerFactory.getLogger(GameSimulator.class);
	private final static String[] names = new String[]{"Alice","Bob","Carol","David","Eve","Frank","Giacomo","Harold","Irene","James"};
	
	public static void main(String[] args) {		
		Dealer dealer = new Dealer();
		Map<Player,Integer> earnings = new HashMap<Player,Integer>();
		
		Player[] players = new Player[args.length];
		for (int i = 0; i < args.length; i++) {
			if(args[i].equals("RandomPlayer")){
				players[i] = new RandomPlayer(names[i]);
			}else if(args[i].equals("SimplePlayer")){
				players[i] = new SimplePlayer(names[i]);
			}
			else if(args[i].equals("PhaseIIThresholdPlayer")){
				players[i] = new PhaseIIThresholdPlayer(names[i]);
			}else{
				System.err.println("Args malformed. No such player.");
				System.exit(1);
			}
			dealer.addPlayer(players[i]);
			earnings.put(players[i],0);
		}
		for (int i = 0; i < 1000; i++) {
			GameResult result = dealer.runGame(i);
			logger.debug("Result was {}.",result);
			int winnings = result.getPot() / result.getWinners().size();
			for (Entry<Player, Integer> entry : earnings.entrySet()) {
				int current = entry.getValue();
				current += (result.getWinners().contains(entry.getKey()) ? winnings : 0) - result.getBets().get(entry.getKey());
				entry.setValue(current);
			}
		}
		logger.info("Total earnings were {}.", earnings);
		
	}

	
}
