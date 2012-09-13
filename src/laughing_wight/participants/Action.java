package laughing_wight.participants;

/** Enum indicating what a player chooses to do with their turn. */
public enum Action {
	/** The player chooses to call the current bet (or check if no bets have been made).*/
	CALL,
	/** The player chooses to raise the bet.*/
	BET,
	/** The player chooses to fold.*/
	FOLD;
}
