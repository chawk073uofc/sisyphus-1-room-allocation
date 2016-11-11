package officeEntities;

import java.util.ArrayList;

import cpsc433.Entity;

public class Project extends Entity {
	
	private static ArrayList<Project> projects =new ArrayList<Project>();
	
	private ArrayList<Person> members = new ArrayList<Person>();
	private Person projectHead; //TODO:change to array list
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
		projects.add(this);
	}
	
	public static Project getEntityWithName(String projectName) throws NoSuchProjectException{
		for(Project g : projects)
			if(g.equals(projectName)) return g;
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
	 * Assigns the person with the given name to be the head of this project. Creates a person 
	 * object with the given name if one does not already exist.
	 * @param personName
	 */
	public void setProjectHead(String personName) {
		try {
			projectHead = Person.getEntityWithName(personName);
		} catch (NoSuchPersonException e) {
			(new Person(personName)).addProject(this.getName());
		}
	}
	/**
	 * Returns true if the named person is the head of this project.
	 * @param personName
	 * @return
	 */
	public boolean hasProjectHead(String personName) {
		return projectHead.getName().equals(personName);
	}
	
	public boolean isLargeProject(){
		return largeProject;
	}
	
	public void setLargeProject(){
		largeProject = true;
	}

	/**
	 * Returns true if the named project exsists.
	 * @param groupName
	 * @return boolean
	 */
	public static boolean exists(String projName){
		for(Project p : projects)
			if(p.getName().equals(projName)) return true;
		return false;
	}
	
	
}
