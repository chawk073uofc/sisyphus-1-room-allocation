package officeEntities;

import java.util.ArrayList;

import cpsc433.Entity;
import officeEntities.Room.RoomSize;

public class Room extends Entity{
	private static ArrayList<Room> rooms =new ArrayList<Room>(); //All instances of class Room currently instantiated.
	private ArrayList<Person> occupants = new ArrayList<Person>();
	private ArrayList<Room> closeTo = new ArrayList<Room>();
	private RoomSize size = RoomSize.MEDIUM; 
	
	/**
	 * Constructor for class Room. Creates a room with the given name. Default size is medium.
	 * @param roomName
	 */
	public Room(String roomName){
		super(roomName);
		//setSize(1);
		rooms.add(this);
	}
	/**
	 * Constructor for class Room. Creates a room with the given name and size.
	 * @param roomName
	 * @param roomSize
	 */
	public Room(String roomName, RoomSize roomSize) {
		super(roomName);
		size = roomSize; 
		rooms.add(this);
	}
	
	/**
	 * Returns true if a Room object with the same name already exists. 
	 * @param name	
	 * @return
	 */
	public static boolean exists(String name){
		for(Room r : rooms)
			if(r.getName().equals(name)) return true;
		return false;
	}
	
	public boolean isCloseTo(Room checkRoom){
		for (Room r: closeTo)
			if(checkRoom.equals(r)) return true;
		return false;
	}
	
	public void addCloseTo(Room addRoom){
		if(!closeTo.contains(addRoom))
			closeTo.add(addRoom);
	}
	
	public static Room getEntityWithName(String roomName){
		for(Room r : rooms){
			if(r.getName().equals(roomName)) 
				return r;
		}
		return null;
	}

	public RoomSize getSize() {
		return size;
	}

	public void setSize(RoomSize size) {
		this.size = size;
	}
	
	public void addPerson(Person p){
		occupants.add(p);
	}
	
	public boolean hasPerson(Person p){
		return occupants.contains(p);
	}
	/**
	 * Returns a string with all the information relating to this room.
	 * @return room_string 
	 */
	@Override
	public String toString(){
		String roomStr = "";
		roomStr += "room(" + this.getName() + ")\n";
		roomStr += this.size + "(" + this.getName() + ")\n";//TODO enum toString
		for(Room rm : closeTo)
			roomStr += "close(" +this.getName() + ", " + rm.getName() + ")\n";
		for(Person p : occupants)
			roomStr += "assigned-to(" + p.getName() + ", " + this.getName() + ")\n"; 
		roomStr += "\n";
		return roomStr;
	}

	/**
	 * Returns a string with all the information relating to all the rooms.
	 * @return
	 */
	public static String roomInfoString(){
		String roomsStr = "";
		for(Room rm : rooms)
			roomsStr += rm;
		roomsStr += "\n";
		
		return roomsStr;
	}
	/**
	 * Represents all valid room sizes. Provides appropriate string representations of each. 
	 *
	 */
	public enum RoomSize{
		SMALL("small-room"), MEDIUM("medium-room"), LARGE("large-room");
		private String displayName;
		RoomSize(String displayName){this.displayName = displayName;}
		@Override public String toString() {return displayName;}
	}
}
