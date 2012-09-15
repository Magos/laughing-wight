package laughing_wight.participants;

import java.util.List;
import java.util.Map;

public class GameResult {
	private Map<Player,Integer> bets;
	private List<Player> winners;
	
	public GameResult(Map<Player,Integer> bets, List<Player> winners){
		this.bets = bets;
		this.winners = winners;
	}
	
	public Map<Player,Integer> getBets() {
		return bets;
	}
	public List<Player> getWinners() {
		return winners;
	}
	
	public int getPot(){
		int ret = 0;
		for(Integer i : bets.values()){
			ret += i;
		}
		return ret;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder("[");
		sb.append(bets.toString());
		sb.append(", ");
		sb.append(winners.toString());
		sb.append("]");
		return sb.toString();
	}

}
