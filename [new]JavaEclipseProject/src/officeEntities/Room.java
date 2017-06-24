
package officeEntities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import cpsc433.Entity;

/**
 * <p>This class represents a room in the office allocation problem. It organizes important data
 * such as a list of the people currently assigned to this room and another list of rooms that are 
 * close to a particular room. The size of the room is also stored here. The default room size is medium. The class includes
 * a static array list with all Room instances created so far.
 * 
 * <p>The use of {@link java.util.HashMap} in this class allows for constant time checking if a room with a given name has already 
 * been created which is critical during the input file parsing phase of the program when this must be done every time a room-related
 * predicate is encountered. 
 */

public class Room extends Entity{
	/**All instances of class Room currently instantiated*/
	private static Map<String,Room> rooms =new HashMap<>();
	/**All people assigned to this room.*/
	private Map<String, Person> occupants = new HashMap<>();
	/**All rooms close to this room*/
	private Map<String, Room> closeTo = new HashMap<>();
	/**The {@link RoomSize size} of this room*/
	private RoomSize size;
	
	/**
	 * Constructor for class Room. Creates a room with the given name. Default size is medium.
	 * @param roomName name of room to be created. Default to medium.
	 */
	public Room(String roomName){
		super(roomName);
		size = RoomSize.MEDIUM; //default size
		rooms.put(roomName,this);
	}
	/**
	 * Constructor for class Room. Creates a room with the given name and size.
	 * @param roomName name of room to be created.
	 * @param roomSize size of room to be created.
	 */
	public Room(String roomName, RoomSize roomSize) {
		super(roomName);
		size = roomSize; 
		rooms.put(roomName, this);
	}
	
	/**
	 * Returns true if a Room object with the same name already exists. 
	 * @param name	name to check existence of.
	 * @return true if a Room object with the same name already exists.
	 */
	public static boolean exists(String name){
		return rooms.containsKey(name);
	}
	/**
	 * Compares this Room object to the given object on the basis of the sizes of these rooms (see {@link RoomSize}).
	 * @param r	room object to compare
	 * @return negative number if r is larger than this room
	 * 		   positive number if r is smaller than this room
	 *   	   zero	rooms are of equal size
	 * @throws ClassCastException if the given Entity object is not an instance of Room
	 */
	@Override
	public int compareTo(Entity r){
	    if (r instanceof Room) {
	    		Room rm = (Room) r;
	    		if(this.size.ordinal() < rm.size.ordinal())
	    			return -1;
	    		else if(this.size.ordinal() > rm.size.ordinal())
	    			return 1;
	    		else	
	    			return 0;
			}
		    else throw new java.lang.ClassCastException();
		}
	/**
	 * Returns true if a room object is close to the calling room object.
	 * @param checkRoom room to check close to relation with.
	 * @return true if a room object is close to the calling room object.
	 */
	public boolean isCloseTo(Room checkRoom){
		return closeTo.containsKey(checkRoom.getName());
	}
	/**
	 * Adds a Room object to the calling Room object's close to array.
	 * @param neighbour room to add to closeTo array.
	 */
	public void addCloseTo(Room neighbour){
		if(!closeTo.containsKey(neighbour.getName())) 
			closeTo.put(neighbour.getName(), neighbour);
	}
	/**
	 * Gets the Room object given a name.
	 * @param roomName name of the room to get the Room object of.
	 * @return the room object.
	 * @throws NoSuchRoomException if the room does not exist.
	 */
	public static Room getEntityWithName(String roomName) throws NoSuchRoomException{
		Room rm = rooms.get(roomName);//O(1)
		if(rm == null)
			throw new NoSuchRoomException();
		return rm;
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
		occupants.put(p.getName(), p);
	}
	/**
	 * Checks if a person exists in the calling Room object's occupants array.
	 * @param p the person to check.
	 * @return true if the person exists in the room.
	 */
	public boolean hasPerson(Person p){
		if(occupants.size()!=0){
		return occupants.containsKey(p.getName());
		}
		return false;
	}
	/**
	 * Returns a string with all the information relating to this room.
	 * @return roomStr the information string pertaining to a room.
	 */
	@Override
	public String toString(){
		String roomStr = "";
		roomStr += "room(" + this.getName() + ")\n";
		roomStr += this.size + "(" + this.getName() + ")\n";
		for(Room rm : closeTo.values())
			roomStr += "close(" +this.getName() + ", " + rm.getName() + ")\n";
		for(Person p : occupants.values())
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
		for(Room rm : rooms.values())
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
		if(!occupants.containsKey(personMovingIn.getName()))
			occupants.put(personMovingIn.getName(), personMovingIn);
	}
	/**
	 * Assesses the maximum number people that can be accommodated by the rooms in the knowledge base, assuming there are no bosses.
	 * @return maxPeople the maximum number people that can be accommodated by the rooms in the knowledge base, assuming there are no bosses.
	 */
	public static int buildingCapacity() {
		return rooms.size() * 2;
	}
	/**
	 * Getter for the number of rooms currently in the knowledge base. 
	 * @return numRooms the number of rooms currently in the knowledge base
	 */
	public static int numberOfRooms() {
		return rooms.size();
	}
	/**
	 * Returns true if no more people may be added to the room and false otherwise. 
	 * @return true if two people have already been assigned to this room or if the sole occupant is a {@link Person#isBoss() boss}.
	 */
	public boolean isFull(){
		if (occupants.size() == 2 || occupantIsBoss())
			return true;
		else
			return false;
	}
	/**
	 * Returns true if the person is a group-head, project-head, or manager and therefore
	 * needs their own room. 
	 * @return true if the person is a boss
	 */
	private boolean occupantIsBoss() {
		try{
			Person occupant = (Person) occupants.values().toArray()[0];
			return occupant.hasAttribute(Attribute.GROUP_HEAD) 
					|| occupant.hasAttribute(Attribute.PROJECT_HEAD) 
					|| occupant.hasAttribute(Attribute.MANAGER);
		}catch(Exception e){
			return false;
		}
	}
	/**
	 * Getter for the occupants of this room.
	 * @return occupants the list of people assigned to this room.
	 */
	public Map<String, Person> getOccupants(){
		return occupants;
	}
	/**
	 * Compiles a list of rooms that are not {@link #isFull() full}.
	 * @return availableRooms a list of rooms that are not {@link #isFull() full}.
	 */
	public static ArrayList<Room> getAvailableRooms(){
		ArrayList<Room> availableRooms = new ArrayList<>();
		for(Room r: rooms.values()){
			if(!r.isFull()){
				availableRooms.add(r);
			}
		}
		return availableRooms;
	}
	/**
	 * Checks if a given list of rooms contains at least room that is not {@link #isFull() full}.
	 * @param roomsToCheck a list of rooms
	 * @return true if the given list of rooms contains at least room that is not {@link #isFull() full}.
	 */
	public static boolean hasEmptyRoom(ArrayList<Room> roomsToCheck){
		for(Room r: roomsToCheck){
			if(r.isEmptyRoom()) return true;
		}
		return false;
	}
	/**
	 * Checks if this room is empty. 
	 * @return true if this room has no occupants
	 */
	public boolean isEmptyRoom(){
		return (occupants.size()==0);
	}
}
