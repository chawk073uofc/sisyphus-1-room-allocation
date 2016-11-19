package officeEntities;

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
	 * static TreeSet<Project> projects: Holds all of the project Items
	 * TreeSet<Person> members: Holds members for an instance of a project
	 * TreeSet<Person> projectHeads: Holds the list of project heads
	 * boolean largeProject: Holds the large-project attribute. Default: False
	 */
	private static TreeSet<Project> projects =new TreeSet<Project>();
	private TreeSet<Person> members = new TreeSet<Person>();
	private TreeSet<Person> projectHeads = new TreeSet<Person>();
	private boolean largeProject = false;

	
	/**
	 * Constructs the project and initializes the name.
	 * Checks to see if project already exists. If it does, it does not add it to the list.
	 * @param name
	 */
	public Project(String name) {
		super(name);
		if(!exists(name)) projects.add(this);
		
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
		if(!exists(name)) projects.add(this);
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
		members.add(person);
		if(!exists(projectName)) {
			projects.add(this);
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
		for(Project g : projects)
			if(g.getName().equals(projectName)) return g;
		throw new NoSuchProjectException();
	}
	
	/**
	 * Returns true if this project has a member by the name given.
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
	 * Assigns the person with the given name to be in the project
	 * @param personName
	 * @return
	 */
	public boolean addMember(String personName) {
		
		if(!hasMember(personName)){
			try{
				Person personToAdd = Person.getEntityWithName(personName);
				members.add(personToAdd);
			} catch (NoSuchPersonException e) {
				Person createdPerson = new Person(personName);
				members.add(createdPerson);
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
		if(!members.contains(personObj)){
			members.add(personObj);
		}
		if(!projectHeads.contains(personObj)){
			projectHeads.add(personObj);
		}

	}
	/**
	 * Returns true if the named person is the head of this project.
	 * @param personName
	 * @return
	 */
	public boolean hasProjectHead(String personName) {
		try{
			Person checkThisMember = Person.getEntityWithName(personName);
			return projectHeads.contains(checkThisMember);
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
		for(Project p : projects)
			if(p.getName().equals(projName)) return true;
		return false;
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
		for(Person projHead: projectHeads){
			if(includeQuotes){
				projStr += "heads-project("+ projHead.getName() + ", \"" + this.getName() + "\")\n";
			} else {
				projStr += "heads-project("+ projHead.getName() + ", " + this.getName() + ")\n";
			}
		}
		for(Person member: members){
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
		for(Project proj : projects)
			projStr += proj;
		projStr += "\n";
		return projStr;
	}
	
	
}
