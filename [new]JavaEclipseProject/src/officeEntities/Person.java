package officeEntities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

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
	private static Map<String,Person> people = new HashMap<>(); //All instances of class Person currently instantiated.
	private TreeSet<Attribute> attributes = new TreeSet<Attribute>(); //The attributes that this instance of Person has.
	private Map<String,Person> colleagues = new HashMap<>(); //All the people this person works with.
	private Map<String, Group> groups = new HashMap<>(); //All of the groups this person is assigned to.
	private Map<String,Project> projects = new HashMap<>(); // All of the projects this person is assigned to.
	private Room homeRoom;
	/**
	 * Constructor for class person. Creates a Person object with a given name. 
	 * @param name the name of the person
	 */
	public Person(String name) {
		super(name);
		people.put(name, this);
	}
	/**
	 * Constructor for class person. Creates a Person object with a given name and then assigns the given attribute to that person. 
	 * @param name	name of person to be instantiated
	 * @param personAttribute the attribute to be added (e.g. secretary)
	 */
	public Person(String name, Attribute personAttribute){
		super(name);
		attributes.add(personAttribute);	
		people.put(name,	 this);
		//people.add(this);
	}
	/**
	 * Compares this Person object to the given object on the basis of these peoples ranking attribute (see {@link #getRankingAttribute()}).
	 * @param p	person object to compare
	 * @return negative number if p has a higher-ranking ranking attribute than this person
	 * 		   positive number if p has a lower-ranking ranking attribute than this person
	 *   	   zero	if this person and p have the same ranking attribute
	 */
	@Override
	public int compareTo(Entity p){
    if (p instanceof Person) {
    		p=(Person) p;
			return this.getRankingAttribute().ordinal() - ((Person) p).getRankingAttribute().ordinal();
		}
	    else throw new java.lang.ClassCastException();
	}
	/**
	 * Adds the given attribute to the person's list of attributes if it is not already there. 
	 * @param personAttribute	the attribute to be added (e.g. "smoker")
	 */
	public void addAttribute(Attribute personAttribute) {
		if(!attributes.contains(personAttribute))
			attributes.add(personAttribute);
	}
	/**
	 * Returns true if an Person object with the same name already exists.
	 * @param name	the name of a person who may or may not exist
	 * @return true if a person with the given name has been breated
	 */
	public static boolean exists(String name){
		return people.containsKey(name);
	}
	/**
	 * Returns an object representing a person with a given name , if such person exists.
	 * @param name the name of a person who may or may not exist
	 * @return the person object which has the given name 
	 * @throws NoSuchPersonException if a person by the given name is not found
	 */
	public static Person getEntityWithName(String name) throws NoSuchPersonException{
		Person p = people.get(name);
		if (p == null)
			throw new NoSuchPersonException();
		return p;
	}
	/**
	 * Returns true if the person has the given attribute (e.g. "secretary").
	 * @param personAttr the attribute to check (e.g. "secretary")
	 * @return true if the given attribute is included in the person's list of attributes.
	 */
	public boolean hasAttribute(Attribute personAttr) {
		return attributes.contains(personAttr);
	}
	/**
	 * Adds the group with the given name to the list of groups with which this person is associated. 
	 * Creates the group if it does not exist.
	 * @param groupName	name of the group to be added or created
	 */
	public void addGroup(String groupName) {
		try{
			groups.put(groupName, Group.getEntityWithName(groupName));
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
		if (!colleagues.containsKey(colleague.getName())){ 
			colleagues.put(colleague.getName(), colleague); 
		}
	/**
	 * Adds the project with the given name to this list of projects with which this person is associated. 
	 * @param projectName the name of the project to be added to the projects list.	
	 */
	}
	public void addProject(String projectName) {
		try{
			projects.put(projectName, Project.getEntityWithName(projectName));
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
		return colleagues.containsValue(colleague);
	}
	/**
	 * Assignees this person to the given room.
	 * @param homeRoom the office being assigned to this person.
	 */
	public void addRoomAssignment(Room homeRoom) {
		this.homeRoom = homeRoom;
		homeRoom.addOccupant(this);
	}
	/**
	 * Clears this persons home-room.
	 */
	public void clearRoomAssignment() {
		this.homeRoom = null;
	}
	/**
	 * String representation of person. String lists information about all the 
	 * person's colleagues and attributes in the form of logical predicates.
	 * @return personString a string representation of a person
	 */
	@Override
	public String toString(){
		String personStr = "";
		personStr += "person(" + this.getName() + ")\n";
		for(Attribute att: attributes)
			personStr +=  att + "(" + this.getName() + ")\n";
		for(Person coll : colleagues.values())
			personStr += "works-with(" + this.getName() + ", " + coll.getName() + ")\n";
		personStr += "\n";
		return personStr;
	}
	/**
	 * Builds a string representing all the Person objects instantiated by calling the
	 * <code>toString()</code> method of each. 
	 * @return peopleString a string representing all known information about all people
	 */
	public static String peopleInfoString(){
		String peopleStr = "";
		for(Person p : people.values())
			peopleStr += p;
		peopleStr += "\n";
		
		return peopleStr;
	}
	/**
	 * Assesses the number of people that exist in the knowledge base. 
	 * @return numPeople the number of people that exist in the knowledge base
	 */
	public static int numberOfPeople() {
		return people.size();
	}
	/**
	 * Getter for the room to which this person is assigned. 
	 * @return homeRoom the room to which this person is assigned
	 */
	public Room getRoom(){
		return homeRoom;
	}
	/**
	 * Getter for the list of projects of which this person is a member. 
	 * @return projects the list of projects of which this person is a member.
	 */
	public Map<String,Project> getProjects(){
		return projects;
	}
	/**
	 * not called?
	 * @return
	 */
	public static HashMap<String, Person> getPersonList(){
		return (HashMap<String, Person>) people;
	}
	/**
	 * Getter for the list of groups of which this person is a member. 
	 * @return	groups	the list of groups of which this person is a member. 
	 */
	public Map<String, Group> getGroups(){
		return groups;
	}
	/**
	 * Assesses the most important attribute (e.g. group-head, smoker) that this person has as defined by the {@link Attribute attribute} enum.
	 * @return rankingAttribute the most important attribute (e.g. group-head, smoker) that this person has as defined by the <code>Attribute</code> enum
	 */
	public Attribute getRankingAttribute(){ 
		Attribute highest = Attribute.PERSON;
		for(Attribute a: attributes){
			if (highest.ordinal() < a.ordinal()){
				highest = a;
			}
		}
		return highest;
	}
	/**
	 * Assesses the number of people who are in a management position as defined by <code>isBoss()<\code>.
	 * @return numBosses the number of people who are in a management position as defined by <code>isBoss()<\code>.
	 */
	public static int numberOfBosses() {
		int numBosses = 0;
		for (Person p: people.values()){
			if(p.isBoss())
				numBosses++;
		}
		return numBosses;
	}
	/**
	 * Checks if the given person is in a management position (group-head, manager, or project-head)
	 * @return true if the given person is either a group-head, manager, or project-head.
	 */
	public boolean isBoss(){
		return (this.hasAttribute(Attribute.GROUP_HEAD)
				|| this.hasAttribute(Attribute.MANAGER)
				|| this.hasAttribute(Attribute.PROJECT_HEAD));
	}
	/**
	 * Gets a list of people who have been assigned to a room.
	 * @return assignedPeople	a list of people who have been assigned to a room
	 */
	public static ArrayList<Person> getAssignedPeople(){
		ArrayList<Person> assignedPeople = new ArrayList<>();
		for(Person p: people.values()){
			if(p.homeRoom!=null){
				assignedPeople.add(p);
			}
		}
		return assignedPeople;
	}
	
	/**
	 * Gets a list of people who have not been assigned a room.
	 * @return unassignedPeople	a list of people who have not been assigned a room.
	 */
	public static ArrayList<Person> getUnAssignedPeople(){
		ArrayList<Person> unassignedPeople = new ArrayList<>();
		for(Person p: people.values()){
			if(p.homeRoom==null){
				unassignedPeople.add(p);
			}
		}
		return unassignedPeople;
	}
	
}

