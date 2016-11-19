package officeEntities;

import java.util.TreeSet;

/**
 * This class represents a group in the office allocation problem. It includes a list of the group's
 * members and heads along with methods to add members and to check membership. 
 * @author Branko Bajic
 *
 */

import cpsc433.Entity;

public class Group extends Entity {
	private static TreeSet<Group> groups =new TreeSet<Group>(); //All instances of class Group currently instantiated.
	
	private TreeSet<Person> members = new TreeSet<Person>();
	private TreeSet<Person> groupHeads = new TreeSet<Person>(); //TODO:can a group have more than one head?
	
	/**
	 * Constructor for class Group. Creates a group with the given name.
	 * @param groupName
	 */
	public Group(String groupName) {
		super(groupName);
		if (!groups.contains(this)){
			groups.add(this);
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
		if(!groups.contains(this)){
			groups.add(this);
		}
		members.add(person);
	}

	/**
	 * Returns an object representing a group with a given name , if such group exists.
	 * @param name the name of a group which may or may not exist
	 * @return the group object which has the given name 
	 * @throws NoSuchGroupException if a group by the given name is not found
	 */
	public static Group getEntityWithName(String groupName) throws NoSuchGroupException{
		for(Group g : groups)
			if(g.getName().equals(groupName)) return g;
		throw new NoSuchGroupException();
	}

	/**
	 * Returns true if this group has a member by the name given.
	 * @param personName
	 * @return
	 */
	public boolean hasMember(String personName) {
		for(Person m: members){
			if(m.getName().equals(personName))
				return true;
		}
		return false;
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
		if(!groupHeads.contains(personObj)){
			groupHeads.add(personObj);
		}
		if(!members.contains(personObj)){
			members.add(personObj);
		}
		
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
		for(Group g : groups){
			if(g.getName().equals(groupName)){ return true; }
	}
		return false;
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
		for(Person member : members){
			groupStr += "group(" + member.getName() + ", " + this.getName() + ")\n";
		}
		for(Person groupHead: groupHeads){
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
		for(Group g: groups){
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
		members.add(person);
	}
}
