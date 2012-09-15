package laughing_wight.participants;

import java.util.List;

import laughing_wight.model.Card;
import laughing_wight.model.Round;

public interface GameState {

	/** Which round of play is this? */
	public abstract Round getRound();

	/** What bets have been made by this player? */
	public abstract int getBets(Player player);
	/** What is the total pot?*/
	public abstract int getPot();
	/** What players are at the table? */
	public abstract List<Player> getPlayers();
	/** Is this player still in the game? */
	public abstract boolean isPlayerActive(Player player);
	/** How many players are still in the game? */
	public abstract int getActivePlayerCount();
	/** What communal cards are on the table.*/
	public abstract Card[] getCommunalCards();

}