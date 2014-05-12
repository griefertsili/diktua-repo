
import java.lang.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;
import org.json.JSONObject;

public class Schulze {

    public static void main(String[] args) {
    	URI file;
        Ballot vote;
        ArrayList<Integer>[] printlist;
        ArrayList<Ballot> ballots = new ArrayList<>();
        HashMap<String, Integer> candidates = new HashMap<>();
        ArrayList<Node> nodes = new ArrayList<>();
        int[][] p;
        int[][] widest;
        int[][] pwpref;
        int[][] pwprefgraph;
        
        
        try {
            String filename = args[0];
            file = new URI("file:///"+ filename);
            JSONTokener tokener = new JSONTokener(file.toURL().openStream());
            JSONObject obj = new JSONObject(tokener);
            JSONArray arraycandidates = obj.getJSONArray("candidates");
            JSONArray arrayballots  = obj.getJSONArray("ballots");
            
            for(int a = 0; a < arraycandidates.length(); a++) {
                candidates.put(arraycandidates.getString(a), a);
                nodes.add(new Node(arraycandidates.getString(a)));
            }   
            for(int b = 0; b < arrayballots.length(); b++) {
                vote = new Ballot(arraycandidates, arrayballots.getJSONArray(b));
                ballots.add(vote);
            }
           
            
            p = new int[arraycandidates.length()][arraycandidates.length()];
            widest = new int[arraycandidates.length()][arraycandidates.length()];
            pwpref = new int[arraycandidates.length()][arraycandidates.length()];
            pwprefgraph = new int[arraycandidates.length()][arraycandidates.length()];
            printlist = new ArrayList[arraycandidates.length()];
            
            
            for(int i = 0; i < arraycandidates.length(); i++) {
                for(int j = 0; j < arraycandidates.length(); j++) {
                    pwpref[i][j] = 0;
                }
            }
            
            
            for(int i= 0; i < ballots.size(); i++) {
                ballots.get(i).sortPower();
                for(int a = 0; a < ballots.get(i).getBallotstats().size(); a++) {
                    for(int b = a+1; b < ballots.get(i).getBallotstats().size(); b++) {
                        pwpref[candidates.get(ballots.get(i).getBallotstats().get(a).getCandidatename())][candidates.get(ballots.get(i).getBallotstats().get(b).getCandidatename())]++;
                    }
                }
            }

            for(int i = 0; i < nodes.size(); i ++) {
                for(int j=0; j < nodes.size(); j++) {
                    if(i != j) {
                        if(pwpref[i][j]-pwpref[j][i] > 0) {
                            pwprefgraph[i][j] = pwpref[i][j] - pwpref[j][i];
                        } else {
                            pwprefgraph[i][j] = 0;
                        }
                    } else {
                        pwprefgraph[i][j] = 0;
                    }
                }
            }
            for(int i = 0; i < nodes.size(); i++) {
                for(int j = 0; j < nodes.size(); j++) {
                    if(pwprefgraph[i][j] - pwprefgraph[j][i] > 0) {
                        widest[i][j] = pwprefgraph[i][j] - pwprefgraph[j][i];
                        p[i][j] = i;
                    } else {
                        widest[i][j] = -1;
                        p[i][j] = -1;
                    }
                }
            }
            for(int a = 0; a < nodes.size(); a++){
                for(int b = 0; b < nodes.size(); b++) {
                    if(b != a) {
                        for(int c = 0; c < nodes.size(); c++) {
                            if(c != b) {
                                if(widest[b][c] != -1 && widest[b][a] != -1 && widest[a][c] != -1) {
                                    if(widest[b][c] < Math.min(widest[b][a], widest[a][c])) {
                                        widest[b][c] = Math.min(widest[b][a], widest[a][c]);
                                        p[b][c] = p[a][c];
                                    }
                                }
                            }
                        }
                    }
                }
            }
            for(int i = 0; i < nodes.size(); i++) {
                printlist[i] = new ArrayList<>();
                for(int j = 0; j < nodes.size(); j++) {
                    if(i != j) {
                        if(widest[i][j] != -1) {
                            if(widest[j][i] != -1) {
                                if(widest[i][j] > widest[j][i]) {
                                    printlist[i].add(j);
                                }
                            }else {
                                printlist[i].add(j);
                            }
                        }
                    }
                }
            }
            for(int i = 0; i < nodes.size(); i++) {
                System.out.print(nodes.get(i).getCandidateName()+ " = " + printlist[i].size() + " [");
                for(int j = 0; j < printlist[i].size(); j++) {
                    if(j == printlist[i].size()-1) {
                        System.out.print(nodes.get(printlist[i].get(j)).getCandidateName() + "]");
                    } else {
                        System.out.print(nodes.get(printlist[i].get(j)).getCandidateName() + ", ");
                    }
                    
                }
                if(printlist[i].isEmpty()) {
                    System.out.print("]");
                }
                System.out.println("");
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(Schulze.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Schulze.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Schulze.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException e) {
			e.printStackTrace();
		}
        
        
    }

}
