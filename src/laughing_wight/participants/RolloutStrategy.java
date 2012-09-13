package laughing_wight.participants;

/** Strategy used by all players in roll-out simulations. Every player always calls, thus keeping every player in the showdown. */
public class RolloutStrategy implements Strategy {

	@Override
	public Action selectAction(State state) {
		return Action.CALL;
	}

}
