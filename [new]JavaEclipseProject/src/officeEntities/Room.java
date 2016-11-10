package officeEntities;

import java.util.ArrayList;

import cpsc433.Entity;
import officeEntities.Room.RoomSize;

public class Room extends Entity{
	private static ArrayList<Room> rooms =new ArrayList<Room>(); //All instances of class Room currently instantiated.
	
	private ArrayList<Person> members = new ArrayList<Person>();
	private ArrayList<Room> closeTo = new ArrayList<Room>();
	public static enum RoomSize {SMALL, MEDIUM, LARGE};
	private RoomSize size = RoomSize.MEDIUM; 
	
	// 0 = small, 1 = medium, 2 = large
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
		members.add(p);
	}
	
	public boolean hasPerson(Person p){
		return members.contains(p);
	}
}
