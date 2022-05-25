public class Player {
    
    String name;
    int position;
    int purse;

    boolean in_penalty_box, is_getting_out_of_penatly_box;


    public Player(String name, int start_position, int purse, boolean in_penalty_box){
        this.name = name;
        this.position = start_position;
        this.purse = purse;
        this.in_penalty_box = in_penalty_box;
        this.is_getting_out_of_penatly_box = false;
    }

    public boolean inPenatlyBox(){ return this.in_penalty_box; }
    public boolean isGettinFromPentalyBox(){ return this.is_getting_out_of_penatly_box; }

    public void setInPenatly(boolean in_penatly){this.in_penalty_box = in_penatly;}
    public void setIsgettingFromPenalty(boolean is_getting_from_penatly){ this.is_getting_out_of_penatly_box = is_getting_from_penatly; }
    public void moveToPosition(int new_position){ this.position = new_position; }
    public void setPurse(int purse){this.purse = purse; }
    public void addToPurse(int new_purse){this.purse += new_purse;}

    public int getPosition(){ return this.position; }
    public int getPurse(){ return this.purse; }
    public String getName(){ return this.name; }
}
