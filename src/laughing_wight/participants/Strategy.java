package laughing_wight.participants;

public interface Strategy {

	//What action should be taken in this state?
	public Action selectAction(State state);

	//Reset any internal state to be ready for a new game.
	public void reset();
}
