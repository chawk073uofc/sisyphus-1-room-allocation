package officeEntities;

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
	 * Compares this Person object to the given object.
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
		return attributes.contains(personAttr);//O(1)
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
		if (!colleagues.containsKey(colleague.getName())){ //O(1)
			colleagues.put(colleague.getName(), colleague); //O(1)
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
		System.out.println(homeRoom);
		homeRoom.addOccupant(this);
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
		for(Attribute att: attributes)
			personStr +=  att + "(" + this.getName() + ")\n";
		for(Person coll : colleagues.values())
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
		for(Person p : people.values())
			peopleStr += p;
		peopleStr += "\n";
		
		return peopleStr;
	}
	
	public static int numberOfPeople() {
		return people.size();
	}
	public Room getRoom(){
		return homeRoom;
	}
	
	public Map<String,Project> getProjects(){
		return projects;
	}
	
	public static HashMap<String, Person> getPersonList(){
		return (HashMap<String, Person>) people;
	}
	
	public Map<String, Group> getGroups(){
		return groups;
	}
	/**
	 * Computes the total soft  constraint penalty score for the person.
	 * @return an integer representing the total penalty
	 */
	public int computePenalty(){
		int penalty = 0;	
		return penalty;
	}
	
	public Attribute getRankingAttribute(){ 
		Attribute highest = Attribute.PERSON;
		for(Attribute a: attributes){
			if (highest.ordinal() < a.ordinal()){
				highest = a;
			}
		}
		return highest;
	}
	
}

