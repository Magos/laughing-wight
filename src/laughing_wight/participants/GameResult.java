package laughing_wight.participants;

import java.util.List;

public class GameResult {
	private int[] bets;
	private List<Player> winners;
	
	public GameResult(int[] bets, List<Player> winners){
		this.bets = bets;
		this.winners = winners;
	}
	
	public int[] getBets() {
		return bets;
	}
	public List<Player> getWinners() {
		return winners;
	}
	
	public int getPot(){
		int ret = 0;
		for(Integer i : bets){
			ret += i;
		}
		return ret;
	}

}
