package officeEntities;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * This class represents a group in the office allocation problem. It includes a list of the group's
 * members and heads along with methods to add members and to check membership. 
 * @author Branko Bajic
 *
 */
import cpsc433.Entity;

public class Group extends Entity {
	private static Map<String, Group> groups =new HashMap<>(); //All instances of class Group currently instantiated.
	private Map<String, Person> members = new HashMap<>();
	private Map<String, Person> groupHeads = new HashMap<>(); 
	
	/**
	 * Constructor for class Group. Creates a group with the given name.
	 * @param groupName
	 */
	public Group(String groupName) {
		super(groupName);
		if (!exists(groupName)){
			groups.put(groupName, this);
		}
	}
	
	/**
	 * Constructor for class Group. Creates a group with the given name and assigns the given person
	 * to the group's list of people.
	 * @param groupName name of the group
	 * @param person name of the person
	 */
	public Group(String groupName, Person person) {
		super(groupName);
		if(!groups.containsKey(this.getName())){  //O(1)
			groups.put(groupName, this); //O(1)
		}
		members.put(person.getName(), person);
	}

	/**
	 * Returns an object representing a group with a given name , if such group exists.
	 * @param name the name of a group which may or may not exist
	 * @return the group object which has the given name 
	 * @throws NoSuchGroupException if a group by the given name is not found
	 */
	public static Group getEntityWithName(String groupName) throws NoSuchGroupException{
		Group g = groups.get(groupName); //O(1)
		if (g == null)
			throw new NoSuchGroupException();
		return g;
	}

	/**
	 * Returns true if this group has a member by the name given.
	 * @param personName
	 * @return
	 */
	public boolean hasMember(String personName) {
		return members.containsKey(personName);//O(1)
	}
	/**
	 * Assigns the person with the given name to be the head of this group. Creates a person 
	 * object with the given name if one does not already exist.
	 * @param personName
	 */
	public void setGroupHead(String personName) {
		Person	personObj;
		try {
			personObj = Person.getEntityWithName(personName);
		} catch (NoSuchPersonException e) {
			personObj= new Person(personName);
		}
		if(!groupHeads.containsKey(personObj.getName())){
			groupHeads.put(personObj.getName(), personObj);
		}
		if(!members.containsKey(personObj.getName())){
			members.put(personObj.getName(), personObj);
		}
		
		//add group-head to person's attribute list
		personObj.addAttribute("group-head");
		
	}
	/**
	 * Returns true if the named person is the head of this group.
	 * @param personName
	 * @return
	 */
	public boolean hasGroupHead(String personName) {
		return !groupHeads.isEmpty();
	}
	/**
	 * Returns true if the named group exsists.
	 * @param groupName
	 * @return boolean
	 */
	public static boolean exists(String groupName){
		return groups.containsKey(groupName); //O(1)
}
	/**
	 * String representation of group. String contains information about all the 
	 * group's members and heads.
	 * @return a string representation of a group
	 */	
	@Override
	public String toString(){
		String groupStr = "";
		groupStr += "group(" + this.getName() + ")\n";
		for(Person member : members.values()){
			groupStr += "group(" + member.getName() + ", " + this.getName() + ")\n";
		}
		for(Person groupHead: groupHeads.values()){
			groupStr += "heads-group(" + groupHead.getName() + ", " + this.getName() + ")\n"; 
		}
		return groupStr;
	}
	/**
	 * Builds a string representing all the Group objects instantiated by calling the
	 * toString() method of each. 
	 * @return a string representing all known information about all groups
	 */	
	public static String groupInfoString(){
		String groupStr = "";
		for(Group g: groups.values()){
			groupStr += g;
		}
		groupStr += "\n";
		return groupStr;
	}
	/**
	 * Assigns a person to the group .
	 * @param person the person being added
	 */	
	public void addMember(Person person){
		members.put(person.getName(), person);
	}
	public static Map<String, Group> getGroups(){
		return groups;
	}
	
	public static Map<String, Person> getAllGroupHeads(){
		Map<String, Person> returnMap = new HashMap<>();
		//returnMap.putAll(groups);
		for (Map.Entry<String, Group> entry : groups.entrySet()) {
			returnMap.putAll(entry.getValue().getGroupHeads());
			// TODO: remove duplicates
		}
		return returnMap;
	}
	public Map<String, Person> getGroupHeads(){
		return groupHeads;
	}
}
