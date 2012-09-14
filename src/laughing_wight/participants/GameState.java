package laughing_wight.participants;

import java.util.List;

import laughing_wight.model.Card;
import laughing_wight.model.Round;

public interface GameState {

	public abstract Round getRound();

	public abstract int getBets(Player player);

	public abstract int getPot();

	public abstract List<Player> getPlayers();

	public abstract Card[] getCommunalCards();

}