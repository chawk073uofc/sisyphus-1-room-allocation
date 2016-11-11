package officeEntities;

import java.util.ArrayList;

import cpsc433.Entity;

public class Project extends Entity {
	
	private static ArrayList<Project> projects =new ArrayList<Project>();
	
	private ArrayList<Person> members = new ArrayList<Person>();
	private ArrayList<Person> projectHeads = new ArrayList<Person>();
	private boolean largeProject = false;

	public Project(String name) {
		super(name);
		projects.add(this);
	}
	
	public Project(String name, Boolean largeProject) {
		super(name);
		this.largeProject = largeProject;
		projects.add(this);
	}
	
	public Project(String projectName, Person person) {
		super(projectName);
		members.add(person);
	}
	
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
		
		try {
			if(!members.contains(Person.getEntityWithName(personName))){
				Person.getEntityWithName(personName).addProject(this.getName());
			}
			projectHeads.add(Person.getEntityWithName(personName));
		} catch (NoSuchPersonException e) {
			
			new Person(personName);
			setProjectHead(personName);
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
			projectHeads.contains(checkThisMember);
		} catch (NoSuchPersonException e) {
		}
		return false;
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
	 * Returns a string with all the information relating to this room.
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
