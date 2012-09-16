package laughing_wight.participants;

import java.util.Random;

public class RandomPlayer extends Player {

	public RandomPlayer(String name) {
		super(name);
	}

	@Override
	public Action getAction(GameState state) {
		if(state.getActivePlayerCount()==1){
			return Action.CALL;//Always call if this round of betting ends in our win.
		}
		Action[] potentials = Action.values();
		return potentials[new Random().nextInt(potentials.length)];
	}

	@Override
	public void reset() {
		//Do nothing.
	}

}
