import java.util.HashMap;
public class Room {
    private String description;
    private HashMap<String, Room>exists;

    public Room(String description){
        this.description = description;
        exists = new HashMap<String,Room>();
    }
    public void setExit(String direction, Room neighbor){
        exists.put(direction, neighbor);
    }
    public Room getExit(String direction){
        return exists.get(direction);
    }
    public String getShortDescription(){
        return description;
    }
}