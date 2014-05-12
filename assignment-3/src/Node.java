public class Node {
    private static int nodecount;
    private String candidateName;
    
    
    public Node(String candidateName) {
        this.candidateName = candidateName;
    }

    public static int getEdgeCount() {
        return nodecount;
    }
  
    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }
     public String getCandidateName() {
        return candidateName;
    }
}
