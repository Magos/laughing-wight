package laughing_wight.participants;

/** Strategy used by all players in roll-out simulations. Every player always calls, thus keeping every player in the showdown. */
public class RolloutPlayer extends Player {

	
	public RolloutPlayer(String name) {
		super(name);
	}

	@Override
	public Action getAction(GameState state) {
		return Action.CALL;
	}

	@Override
	public void reset() {
		//Do nothing.
	}

}
