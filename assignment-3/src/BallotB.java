class BallotB {
    private String candidatename;
    private int ballotpower;

    public BallotB(String candidatename, int ballotpower) {
        this.candidatename = candidatename;
        this.ballotpower = ballotpower;
    }

    
    public void setCandidatename(String candidateName) {
        this.candidatename = candidateName;
    }
    public String getCandidatename() {
        return candidatename;
    }


    public void setBallotPower(int ballotpower) {
        this.ballotpower = ballotpower;
    }
    public int getBallotpower() {
        return ballotpower;
    }

}
