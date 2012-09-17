package laughing_wight.participants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import laughing_wight.model.Card;
import laughing_wight.model.Hand;
import laughing_wight.model.RolloutResult;
import laughing_wight.model.Round;

public class PhaseIIThresholdPlayer extends Player {
	private static final double CALL_THRESHOLD_MEAN = 0.2;
	private static final double BET_THRESHOLD_MEAN = 0.55;
	private static final double VARIANCE = 0.15;
	
	private double CALL_THRESHOLD;
	private double BET_THRESHOLD;
	private RolloutResult rollout;
	private static Logger logger = LoggerFactory.getLogger(PhaseIIThresholdPlayer.class);

	public PhaseIIThresholdPlayer(String name) {
		super(name);
		double scaleFactor = getScaleFactor();
		CALL_THRESHOLD = CALL_THRESHOLD_MEAN + VARIANCE*scaleFactor;
		scaleFactor = getScaleFactor();
		BET_THRESHOLD = BET_THRESHOLD_MEAN + VARIANCE*scaleFactor;
		logger.info("{} chose bet threshold {} and call threshold {}.", this,BET_THRESHOLD,CALL_THRESHOLD);
		loadRolloutData();
		
	}

	private double getScaleFactor() {
		Random random = new Random();
		double scaleFactor = random.nextGaussian();
		while(scaleFactor > 1){scaleFactor -= 1;}
		while(scaleFactor < 0){scaleFactor += 1;}
		return scaleFactor;
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
			if(prob > BET_THRESHOLD_MEAN){
				return Action.BET;
			}else if(prob > CALL_THRESHOLD_MEAN){
				return Action.CALL;
			}else{
				return Action.FOLD;
			}
		}
		Card[] hole = new Card[]{holeCard1,holeCard2};
		Card[] communalCards = state.getCommunalCards();
		Hand myHand = Hand.getBestHand(hole, communalCards);
		double handStrength = HandStrengthCalculator.calculateHandStrength(hole,communalCards, myHand, state.getActivePlayerCount());
		logger.trace("Hand strength with hand {} was {}",myHand,handStrength);
		if(handStrength > BET_THRESHOLD_MEAN){
			return Action.BET;
		}else if(handStrength > CALL_THRESHOLD_MEAN){
			return Action.CALL;
		}else{
			return Action.FOLD;
		}
	}

}
