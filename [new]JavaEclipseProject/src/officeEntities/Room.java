package officeEntities;

import java.util.ArrayList;

import cpsc433.Entity;

public class Room extends Entity{
	private static ArrayList<Room> rooms =new ArrayList<Room>(); //All instances of class Room currently instantiated.
	
	private ArrayList<Person> members = new ArrayList<Person>();
	private ArrayList<Room> closeTo = new ArrayList<Room>();
	private int size; // 0 = small, 1 = medium, 2 = large
	
	public Room(String roomName){
		super(roomName);
		setSize(1);
		rooms.add(this);
	}
	
	public Room(String roomName, int roomSize) {
		super(roomName);
		setSize(roomSize); 
		rooms.add(this);
	}
	
	/**
	 * Returns true if an Person object with the same name already exists. TODO: move to Entity??
	 * @param name	
	 * @return
	 */
	public static boolean exists(String name){
		for(Room r : rooms)
			if(r.equals(name)) return true;
		return false;
	}
	
	public boolean iscloseTo(Room checkRoom){
		for (Room r: closeTo)
			if(checkRoom.equals(r)) return true;
		return false;
	}
	
	public void addcloseTo(Room addRoom){
		if(!closeTo.contains(addRoom))
			closeTo.add(addRoom);
	}
	
	public static Room getEntityWithName(String roomName){
		for(Room r : rooms){
			if(r.equals(roomName)) 
				return r;
		}
		return null;
	
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	
}
