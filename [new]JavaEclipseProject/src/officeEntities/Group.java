package officeEntities;

import java.util.ArrayList;

import cpsc433.Entity;

public class Group extends Entity {
	private static ArrayList<Group> groups =new ArrayList<Group>(); //All instances of class Group currently instantiated.
	
	private ArrayList<Person> members = new ArrayList<Person>();
	private Person groupHead; //TODO:can a group have more than one head?
	
	/**
	 * Constructor for class Group. Creates a group with the given name.
	 * @param groupName
	 */
	public Group(String groupName) {
		super(groupName);
	}
	
	
	/**
	 * Constructor for class Group. Creates a group with the given name and assigns the given person
	 * to the group's list of people.
	 * @param groupName
	 */
	public Group(String groupName, Person person) {
		super(groupName);
		members.add(person);
	}

	public static Group getEntityWithName(String groupName) throws NoSuchGroupException{
		for(Group g : groups)
			if(g.equals(groupName)) return g;
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
			groupHead = Person.getEntityWithName(personName);
		} catch (NoSuchPersonException e) {
			(new Person(personName)).addGroup(this.getName());
		}
	}
	/**
	 * Returns true if the named person is the head of this group.
	 * @param personName
	 * @return
	 */
	public boolean hasGroupHead(String personName) {
		return groupHead.getName().equals(personName);
	}
	/**
	 * Returns true if the named group exsists.
	 * @param groupName
	 * @return boolean
	 */
	public static boolean exists(String groupName){
		for(Group g : groups)
			if(g.equals(groupName)) return true;
		return false;
	}
	//test

}
