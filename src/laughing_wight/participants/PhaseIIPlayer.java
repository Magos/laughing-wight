package laughing_wight.participants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import laughing_wight.model.RolloutResult;
import laughing_wight.model.Round;

public class PhaseIIPlayer extends Player {
	private RolloutResult rollout;
	private static Logger logger = LoggerFactory.getLogger(PhaseIIPlayer.class);

	public PhaseIIPlayer(String name) {
		super(name);
		loadRolloutData();
		
	}

	private void loadRolloutData() {
		try {
			FileInputStream rolloutFile = new FileInputStream("Output.rollout");
			ObjectInputStream stream = new ObjectInputStream(rolloutFile);
			Object result = stream.readObject();
			if(result instanceof RolloutResult){
				rollout = (RolloutResult) result;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Action getAction(GameState state) {
		if(state.getRound() == Round.PRE_FLOP){
			double prob = rollout.winRatio(state.getActivePlayerCount(), holeCard1, holeCard2);
			if(prob > 0.6){
				return Action.BET;
			}else if(prob > 0.15){
				return Action.CALL;
			}else{
				return Action.FOLD;
			}
		}
		
		//TODO:Hand strength calculations.
		return Action.CALL;
	}

}
