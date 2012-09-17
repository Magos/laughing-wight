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
		if(args.length < 3){
			System.err.println("At least 3 args (one iteration count and two players) are required.");
			System.exit(1);
		}
		int gameCount = 0;
		try{gameCount = Integer.parseInt(args[0]);}catch(NumberFormatException e){
			System.err.println("Could not parse iteration count.");
			System.exit(1);
		}
		Dealer dealer = new Dealer();
		Map<Player,Integer> earnings = new HashMap<Player,Integer>();
		
		Player[] players = new Player[args.length];
		for (int i = 1; i < args.length; i++) {
			String name = names[i-1];
			if(args[i].equalsIgnoreCase("RandomPlayer")){
				players[i] = new RandomPlayer(name);
			}else if(args[i].equalsIgnoreCase("SimplePlayer")){
				players[i] = new SimplePlayer(name);
			}
			else if(args[i].equalsIgnoreCase("ThresholdPlayer")){
				players[i] = new ThresholdPlayer(name);
			}else if(args[i].equalsIgnoreCase("RandomizedPlayer")){
				players[i] = new RandomizedPlayer(name);
			}else{
				System.err.println("Args malformed. No such player.");
				System.exit(1);
			}
			dealer.addPlayer(players[i]);
			earnings.put(players[i],0);
		}
		for (int i = 0; i < gameCount; i++) {
			GameResult result = dealer.runGame(i);
			logger.debug("Result was {}.",result);
			int winnings = result.getPot() / result.getWinners().size();
			for (Entry<Player, Integer> entry : earnings.entrySet()) {
				int current = entry.getValue();
				current += (result.getWinners().contains(entry.getKey()) ? winnings : 0) - result.getBets().get(entry.getKey());
				entry.setValue(current);
			}
		}
		logger.info("Total earnings after {} games were {}.", gameCount, earnings);
		
	}

	
}
