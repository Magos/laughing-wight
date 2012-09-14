package laughing_wight.participants;

import java.util.Random;

public class RandomStrategy implements Strategy {

	@Override
	public Action selectAction(GameState state) {
		Action[] potentials = Action.values();
		return potentials[new Random().nextInt(potentials.length)];
	}

	@Override
	public void reset() {
		//Do nothing.
	}

}
