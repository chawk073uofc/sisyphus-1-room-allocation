
package officeEntities;

import java.util.TreeSet;

import cpsc433.Entity;
import officeEntities.Room.RoomSize;
/**
 * This class represents a room in the office allocation problem. It organizes important data
 * such as a list of the people currently assigned to this room and another list of rooms that are 
 * close to a particular room. The size of the room is also stored here. The class includes
 * a static array list with all Room instances created so far.
 * @author Brandon Sieu
 *
 */
public class Room extends Entity{
	private static TreeSet<Room> rooms =new TreeSet<Room>(); //All instances of class Room currently instantiated.
	private TreeSet<Person> occupants = new TreeSet<Person>();
	private TreeSet<Room> closeTo = new TreeSet<Room>();
	private RoomSize size = RoomSize.MEDIUM; 
	
	/**
	 * Constructor for class Room. Creates a room with the given name. Default size is medium.
	 * @param roomName name of room to be created. Default to medium.
	 */
	public Room(String roomName){
		super(roomName);
		rooms.add(this);
	}
	/**
	 * Constructor for class Room. Creates a room with the given name and size.
	 * @param roomName name of room to be created.
	 * @param roomSize size of room to be created.
	 */
	public Room(String roomName, RoomSize roomSize) {
		super(roomName);
		size = roomSize; 
		rooms.add(this);
	}
	
	/**
	 * Returns true if a Room object with the same name already exists. 
	 * @param name	name to check existence of.
	 * @return true if a Room object with the same name already exists.
	 */
	public static boolean exists(String name){
		for(Room r : rooms)
			if(r.getName().equals(name)) return true;
		return false;
	}
	
	/**
	 * Returns true if a room object is close to the calling room object.
	 * @param checkRoom room to check close to relation with.
	 * @return true if a room object is close to the calling room object.
	 */
	public boolean isCloseTo(Room checkRoom){
		for (Room r: closeTo)
			if(checkRoom.equals(r)) return true;
		return false;
	}
	
	/**
	 * Adds a Room object to the calling Room object's close to array.
	 * @param addRoom room to add to closeTo array.
	 */
	public void addCloseTo(Room addRoom){
		if(!closeTo.contains(addRoom))
			closeTo.add(addRoom);
	}
	
	/**
	 * Gets the Room object given a name.
	 * @param roomName name of the room to get the Room object of.
	 * @return the room object.
	 * @throws NoSuchRoomException if the room does not exist.
	 */
	public static Room getEntityWithName(String roomName) throws NoSuchRoomException{
		for(Room r : rooms){
			if(r.getName().equals(roomName)) 
				return r;
		}
		throw new NoSuchRoomException();
	}

	/**
	 * Returns the room size of the calling Room object.
	 * @return size of the room.
	 */
	public RoomSize getSize() {
		return size;
	}

	/**
	 * Sets the room size of the calling Room object.
	 * @param size of the room to set.
	 */
	public void setSize(RoomSize size) {
		this.size = size;
	}
	
	/**
	 * Adds a person to the calling Room object's occupants array.
	 * @param p the person to add.
	 */
	public void addPerson(Person p){
		occupants.add(p);
	}
	
	/**
	 * Checks if a person exists in the calling Room object's occupants array.
	 * @param p the person to check.
	 * @return true if the person exists in the room.
	 */
	public boolean hasPerson(Person p){
		return occupants.contains(p);		
	}
	
	/**
	 * Returns a string with all the information relating to this room.
	 * @return roomStr the information string pertaining to a room.
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
	 * @return the string with all the information relating to all the rooms.
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
	
	/**
	 * Adds a person to the calling Room object's occupant array.
	 * @param personMovingIn the person to be put into the room.
	 */
	public void addOccupant(Person personMovingIn) {
		if(!occupants.contains(personMovingIn))
			occupants.add(personMovingIn);	
	}
	
}
