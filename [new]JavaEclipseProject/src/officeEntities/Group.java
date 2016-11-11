//TODO: Branko comment this and related env methods
package officeEntities;

import java.util.ArrayList;

import cpsc433.Entity;

public class Group extends Entity {
	private static ArrayList<Group> groups =new ArrayList<Group>(); //All instances of class Group currently instantiated.
	
	private ArrayList<Person> members = new ArrayList<Person>();
	private ArrayList<Person> groupHeads = new ArrayList<Person>(); //TODO:can a group have more than one head?
	
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
	 */
	public Group(String groupName, Person person) {
		super(groupName);
		if(!groups.contains(this)){
			groups.add(this);
		}
		members.add(person);
	}

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
		try {
			groupHeads.add(Person.getEntityWithName(personName));
		} catch (NoSuchPersonException e) {
			groupHeads.add(new Person(personName));
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
	
	public static String groupInfoString(){
		String groupStr = "";
		for(Group g: groups){
			groupStr += g;
		}
		groupStr += "\n";
		return groupStr;
	}
	
	public void addMember(Person person){
		members.add(person);
	}


}
