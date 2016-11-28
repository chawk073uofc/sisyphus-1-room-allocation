package officeEntities;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import cpsc433.Entity;

/**
 * Class: Project
 * Extends: Entity
 * 
 * Represents the project entity. 
 * 
 */
public class Project extends Entity {
	/**
	 * static Map<String,Project> projects: Holds all of the project Items
	 * TreeSet<Person> members: Holds members for an instance of a project
	 * TreeSet<Person> projectHeads: Holds the list of project heads
	 * boolean largeProject: Holds the large-project attribute. Default: False
	 */
	private static Map<String,Project> projects =new HashMap<>();
	private Map<String, Person> members = new HashMap<>();
	private Map<String, Person> projectHeads = new HashMap<>();
	private boolean largeProject = false;

	
	/**
	 * Constructs the project and initializes the name.
	 * Checks to see if project already exists. If it does, it does not add it to the list.
	 * @param name
	 */
	public Project(String name) {
		super(name);
		if(!exists(name)) projects.put(name, this);
		
	}
	/**
	 * Constructs the project and initializes name and the large-project attribute
	 * Checks to see if project already exists. If it does, it does not add it to the list.
	 * @param name
	 * @param largeProject
	 */
	public Project(String name, Boolean largeProject) {
		super(name);
		this.largeProject = largeProject;
		if(!exists(name)) projects.put(name, this);
	}
	/**
	 * Constructs the project and initializes name and a adds a member.
	 * if project does not exist, it will create it and add the person as a member
	 * If project already exists then it will just add the person to the members list
	 * @param projectName
	 * @param person
	 */
	public Project(String projectName, Person person) {
		super(projectName);
		members.put(person.getName(), person);
		if(!exists(projectName)) {
			projects.put(projectName, this);
			person.addProject(projectName);
		} else {
			try{
				getEntityWithName(projectName).addMember(person.getName());
			} catch (Exception e) {
				
			}
		}
		
		
	}
	
	/**
	 * Will return the project object if a project with a given name exists
	 * @param projectName
	 * @return
	 * @throws NoSuchProjectException
	 */
	public static Project getEntityWithName(String projectName) throws NoSuchProjectException{
		Project pro = projects.get(projectName);
		if(pro == null)
			throw new NoSuchProjectException();
		return pro;
//		for(Project g : projects)
//			if(g.getName().equals(projectName)) return g;
//		throw new NoSuchProjectException();
	}
	
	/**
	 * Returns true if this project has a member by the name given.
	 * @param personName
	 * @return
	 */
	public boolean hasMember(String personName) {
		return members.containsKey(personName);//O(1)
	}
	/**
	 * Assigns the person with the given name to be in the project
	 * @param personName
	 * @return
	 */
	public boolean addMember(String personName) {
		if(!hasMember(personName)){
			try{
				Person personToAdd = Person.getEntityWithName(personName);
				members.put(personToAdd.getName(), personToAdd);
			} catch (NoSuchPersonException e) {
				Person createdPerson = new Person(personName);
				members.put(createdPerson.getName(), createdPerson);
			}
		}
		return false;
	}
	
	
	
	/**
	 * Assigns the person with the given name to be the head of this project. Creates a person 
	 * object with the given name if one does not already exist.
	 * @param personName
	 */
	public void setProjectHead(String personName) {
		Person personObj = null;
		try{
			personObj = Person.getEntityWithName(personName);
		} catch (NoSuchPersonException t){
			personObj = new Person(personName);
			personObj.addProject(this.getName());
		}
		if(!members.containsKey(personObj.getName())){
			members.put(personObj.getName(), personObj);
		}
		if(!projectHeads.containsKey(personObj.getName())){
			projectHeads.put(personObj.getName(), personObj);
		}
		//add project-head to person's attribute list
		personObj.addAttribute(Attribute.PROJECT_HEAD);
	}
	/**
	 * Returns true if the named person is the head of this project.
	 * @param personName
	 * @return
	 */
	public boolean hasProjectHead(String personName) {
		try{
			Person checkThisMember = Person.getEntityWithName(personName);
			return projectHeads.containsKey(checkThisMember.getName()); //O(1)
		} catch (NoSuchPersonException e) {
			return false;
		}
	}
	
	public boolean isLargeProject(){
		return largeProject;
	}
	
	public void setLargeProject(){
		largeProject = true;
	}

	/**
	 * Returns true if the named project exists.
	 * @param groupName
	 * @return boolean
	 */
	public static boolean exists(String projName){
		return projects.containsKey(projName);
	}
	
	/**
	 * Returns a string with all the information relating to this project.
	 * @return room_string 
	 */
	@Override
	public String toString(){
		String projStr = "";
		boolean includeQuotes = this.getName().contains(" ");
		if(includeQuotes){
			projStr += "project(\"" + this.getName() + "\")\n";
		} else {
			projStr += "project(" + this.getName() + ")\n";
		}
		if(this.largeProject){
			if(includeQuotes){
				projStr += "large-project(\"" + this.getName() + "\")\n";
			} else {
				projStr += "large-project(" + this.getName() + ")\n";
			}
		}
		for(Person projHead: projectHeads.values()){
			if(includeQuotes){
				projStr += "heads-project("+ projHead.getName() + ", \"" + this.getName() + "\")\n";
			} else {
				projStr += "heads-project("+ projHead.getName() + ", " + this.getName() + ")\n";
			}
		}
		for(Person member: members.values()){
			if(includeQuotes){
				projStr += "project("+member.getName() + ", \"" + this.getName() + "\")\n";
			} else {
				projStr += "project("+member.getName() + ", " + this.getName() + ")\n";
			}
		}
		projStr += "\n";
		return projStr;
	}
	/**
	 * Returns a string with all the information relating to all the projects.
	 * @return String
	 */
	public static String projectInfoString(){
		String projStr = "";
		for(Project proj : projects.values())
			projStr += proj;
		projStr += "\n";
		return projStr;
	}
	
	public Map<String, Person> getMembers(){
		return members;
	}
}
