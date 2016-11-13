package officeEntities;

import java.util.ArrayList;

import cpsc433.Entity;

/**
 * This class represents a person in the office allocation problem. It includes a list of the person's
 * attributes (e.g. smoker, hacker, etc), a list of the people with whom the person works, and the office 
 * the person works in as well as the groups and projects he is associated with.
 * @author Chris Hawk
 *
 */
public class Person extends Entity 
{
	private static ArrayList<Person> people =new ArrayList<>(); //All instances of class Person currently instantiated.

	private ArrayList<String> attributes = new ArrayList<String>(); //The attributes that this instance of Person has.
	private ArrayList<Person> colleagues = new ArrayList<Person>(); //All the people this person works with.
	private ArrayList<Group> groups = new ArrayList<Group>(); //All of the groups this person is assigned to.
	private ArrayList<Project> projects = new ArrayList<Project>(); // All of the projects this person is assigned to.
	private Room homeRoom;
	/**
	 * Constructor for class person. Creates a Person object with a given name. 
	 * @param name the name of the person
	 */
	public Person(String name) {
		super(name);
		people.add(this);
	}
	/**
	 * Constructor for class person. Creates a Person object with a given name and then assigns the given attribute to that person. 
	 * @param name	name of person to be instantiated
	 * @param attribute the attribute to be added (e.g. secretary)
	 */
	public Person(String name, String attribute){
		super(name);
		attributes.add(attribute);			
		people.add(this);
	}

	/**
	 * Adds the given attribute to the person's list of attributes if it is not already there. 
	 * @param attribute	the attribute to be added (e.g. "smoker")
	 */
	public void addAttribute(String attribute) {
		if(!attributes.contains(attribute))
			attributes.add(attribute);
	}

	/**
	 * Returns true if an Person object with the same name already exists.
	 * @param name	the name of a person who may or may not exist
	 * @return true if a person with the given name has been breated
	 */
	public static boolean exists(String name){
		for(Person p : people){
			if(p.getName().equals(name)){
					return true;
			}
		}
		return false;
	}

	/**
	 * Returns an object representing a person with a given name , if such person exists.
	 * @param name the name of a person who may or may not exist
	 * @return the person object which has the given name 
	 * @throws NoSuchPersonException if a person by the given name is not found
	 */
	public static Person getEntityWithName(String name) throws NoSuchPersonException{
		for(Person p : people){
			if(p.getName().equals(name)){
				return p; }
	}
		throw new NoSuchPersonException();//person by given name not found
	}
	/**
	 * Returns true if the person has the given attribute (e.g. "secretary").
	 * @param attribute the attribute to check (e.g. "secretary")
	 * @return true if the given attribute is included in the person's list of attributes.
	 */
	public boolean hasAttribute(String attribute) {
		return attributes.contains(attribute);
	}

	/**
	 * Adds the group with the given name to the list of groups with which this person is associated. 
	 * Creates the group if it does not exist.
	 * @param groupName	name of the group to be added or created
	 */
	public void addGroup(String groupName) {
		try{
			groups.add(Group.getEntityWithName(groupName));
		}
		catch (NoSuchGroupException e){
			new Group(groupName, this);
		}
	}
	
	/**
	 * Adds the given person to this person's list of colleagues.  
	 * @param colleague the person to add to this person's list of colleagues
	 */
	public void addColleague(Person colleague) {
		if (!colleagues.contains(colleague)){
			colleagues.add(colleague);
		}
	/**
	 * Adds the project with the given name to this list of projects with which this person is associated. 
	 * @param projectName the name of the project to be added to the projects list.	
	 */
	}
	public void addProject(String projectName) {
		try{
			projects.add(Project.getEntityWithName(projectName));
		}
		catch (NoSuchProjectException e){
			new Project(projectName, this);
		}
	}
	/**
	 * Returns true if the given person is a colleague.
	 * @param colleague the person who may or may not be a colleague of the given person
	 * @return true if the given person is a colleague.
	 */
	public boolean isColleague(Person colleague){
		return colleagues.contains(colleague);
	}
	/**
	 * String representation of person. String contains information about all the 
	 * person's colleagues and attributes.
	 * @return a string representation of a person
	 */
	@Override
	public String toString(){
		String personStr = "";
		personStr += "person(" + this.getName() + ")\n";
		for(String att: attributes)
			personStr +=  att + "(" + this.getName() + ")\n";
		for(Person coll : colleagues)
			personStr += "works-with(" + this.getName() + ", " + coll.getName() + ")\n";
		personStr += "\n";
		return personStr;
	}
	/**
	 * Builds a string representing all the Person objects instantiated by calling the
	 * toString() method of each. 
	 * @return a string representing all known information about all people
	 */
	public static String peopleInfoString(){
		String peopleStr = "";
		for(Person p : people)
			peopleStr += p;
		peopleStr += "\n";
		
		return peopleStr;
	}
	/**
	 * Assignees this person to the given room .
	 * @param homeRoom the office being assigned to this person.
	 */
	public void addRoomAssignment(Room homeRoom) {
		this.homeRoom = homeRoom;
		homeRoom.addOccupant(this);
	}
}

