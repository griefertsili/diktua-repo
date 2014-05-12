
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;


public class Ballot {
    private final ArrayList<BallotB> ballotstats;
    
    public Ballot(JSONArray keys, JSONArray values) {
        this.ballotstats = new ArrayList<>();
        for(int i = 0; i < keys.length(); i++) {
            try {
				ballotstats.add(new BallotB(keys.getString(i), values.getInt(i)));
			} catch (JSONException e) {
			
				e.printStackTrace();
			}
        }
    }
    
    public void sortPower() {
        BallotB temp;
        for(int i = 0; i < ballotstats.size()-1; i++ ) {
            for(int j = 0; j < ballotstats.size()-1-i; j++) {
                if(ballotstats.get(j).getBallotpower() < ballotstats.get(j+1).getBallotpower()) {
                	temp = new BallotB(ballotstats.get(j).getCandidatename(), ballotstats.get(j).getBallotpower());
                    ballotstats.get(j).setCandidatename(ballotstats.get(j+1).getCandidatename());
                    ballotstats.get(j).setBallotPower(ballotstats.get(j+1).getBallotpower());
                    ballotstats.get(j+1).setCandidatename(temp.getCandidatename());   
                    ballotstats.get(j+1).setBallotPower(temp.getBallotpower());                 
                }
            }
        }
    }
    
    
     public ArrayList<BallotB> getBallotstats() {
        return ballotstats;
    }
     
}
