package officeEntities;

import java.util.ArrayList;

import cpsc433.Entity;
//import officeEntities.Person.PersonAttributeType;

public class Person extends Entity 
{
	private static ArrayList<Person> people =new ArrayList<>(); //All instances of class Person currently instantiated.

	//All the possible attributes that a person can have.
	//public static enum PersonAttributeType {researcher, secretary, manager, project_head, hacker, smoker}	

	private ArrayList<String> attributes = new ArrayList<String>(); //The attributes that this instance of Person has.
	private ArrayList<Person> colleagues = new ArrayList<Person>(); //All the people this person works with.
	private ArrayList<Group> groups = new ArrayList<Group>(); //All of the groups this person is assigned to.
	private ArrayList<Project> projects = new ArrayList<Project>(); // All of the projects this person is assigned to.
	
	/**
	 * Constructor for class person. Creates a Person object with a given name. 
	 * @param name
	 */
	public Person(String name) {
		super(name);
		people.add(this);
	}
	/**
	 * Constructor for class person. Creates a Person object with a given name and then assigns the given attribute to that person. 
	 * @param name	name of person to be instantiated
	 * @param attribute 
	 */
	public Person(String name, String attribute){
		super(name);
		people.add(this);
		attributes.add(attribute);			
	}

	/**
	 * Adds a given attribute to a person's list of a if it is not already there. 
	 * @param attribute
	 */
	public void addAttribute(String attribute) {
		if(!attributes.contains(attribute))
			attributes.add(attribute);
	}

	/**
	 * Returns true if an Person object with the same name already exists. TODO: move to Entity??
	 * @param name	
	 * @return
	 */
	public static boolean exists(String name){
		for(Person p : people)
			if(p.equals(name)) return true;
		return false;
	}

	/**
	 * Returns an object representing a person with a given name , if such person exists.
	 * @param name
	 * @return
	 * @throws NoSuchPersonException
	 */
	public static Person getEntityWithName(String name) throws NoSuchPersonException{
		for(Person p : people)
			if(p.equals(name)) return p;
		//person by given name not found
		throw new NoSuchPersonException();
	}
	/**
	 * Returns true if the person has the given attribute (e.g. "secretary").
	 * @param attribute
	 * @return
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
	 * 
	 * @param value
	 */
	public void addColleague(Person colleague) {
		if (!colleagues.contains(colleague)){
			colleagues.add(colleague);
		}
	}
	public void addProject(String projectName) {
		try{
			projects.add(Project.getEntityWithName(projectName));
		}
		catch (NoSuchProjectException e){
			new Project(projectName, this);
		}
		
	}
	
	public boolean isColleague(Person colleague){
		return colleagues.contains(colleague);
	}
	/**
	 * String representation of person. String contains information about all the 
	 * person's colleagues and attributes.
	 */
	@Override
	public String toString(){
		String personStr = "";
		personStr += "person(" + this.getName() + ")\n";
		for(String att: attributes)
			personStr +=  att + "(" + this.getName() + ")\n";
		for(Person coll : colleagues)
			personStr += "works-with(" + this.getName() + ", " + coll.getName() + ")";
		
		return personStr;
	}
	/**
	 * Builds a string representing all the Person objects instantiated by calling the
	 * toString() method of each. 
	 * @return
	 */
	public static String peopleInfoString(){
		String peopleStr = "";
		for(Person p : people)
			peopleStr += p;
			
		return peopleStr;
	}
}
