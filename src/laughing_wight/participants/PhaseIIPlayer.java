package laughing_wight.participants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import laughing_wight.model.RolloutResult;

public abstract class PhaseIIPlayer extends Player {

	protected RolloutResult rollout;

	public PhaseIIPlayer(String name) {
		super(name);
		loadRolloutData();
	}

	protected void loadRolloutData() {
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

}