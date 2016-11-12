
package cpsc433;

import java.util.Iterator;
import java.util.TreeSet;

import cpsc433.Predicate.ParamType;
import officeEntities.Group;
import officeEntities.NoSuchGroupException;
import officeEntities.NoSuchPersonException;
import officeEntities.NoSuchProjectException;
import officeEntities.NoSuchRoomException;
import officeEntities.Person;
import officeEntities.Project;
import officeEntities.Room;

/**
 * This is class extends {@link cpsc433.PredicateReader} just as required to in
 * the assignment. You can extend this class to include your predicate
 * definitions or you can create another class that extends
 * {@link cpsc433.PredicateReader} and use that one.
 * <p>
 * I have defined this class as a singleton.
 * 
 * <p>
 * Copyright: Copyright (c) 2003-16, Department of Computer Science, University
 * of Calgary. Permission to use, copy, modify, distribute and sell this
 * software and its documentation for any purpose is hereby granted without fee,
 * provided that the above copyright notice appear in all copies and that both
 * that copyright notice and this permission notice appear in supporting
 * documentation. The Department of Computer Science makes no representations
 * about the suitability of this software for any purpose. It is provided "as
 * is" without express or implied warranty.
 * </p>
 *
 * @author <a href="http://www.cpsc.ucalgary.ca/~kremer/">Rob Kremer</a>
 *
 */
public class Environment extends PredicateReader implements SisyphusPredicates {

	private static Environment instance = null;
	protected boolean fixedAssignments = false;

	protected Environment(String name) {
		super(name == null ? "theEnvironment" : name);
	}

	/**
	 * A getter for the global instance of this class. If an instance of this
	 * class does not already exist, it will be created.
	 * 
	 * @return The singleton (global) instance.
	 */
	public static Environment get() {
		if (instance == null)
			instance = new Environment(null);
		return instance;
	}

	// UTILITY PREDICATES

	/**
	 * The help text for the exit() predicate.
	 */
	public static String h_exit = "quit the program";

	/**
	 * The definition of the exit() assertion predicate. It will exit the
	 * program abruptly.
	 */
	public void a_exit() {
		System.exit(0);
	}

	/**
	 * Creates an instance of class Person with the given name, if one does not
	 * already exist.
	 */
	@Override
	public void a_person(String name) {
		if (!Person.exists(name))
			new Person(name);
	}

	/**
	 * Returns true if an instance of class Person with the given name exist.
	 */
	@Override
	public boolean e_person(String name) {
		return Person.exists(name);
	}

	/**
	 * If a person with the given name already exists, add "secretary" to their
	 * list of roles. Otherwise, create a new person object and add "secretary"
	 * to their list of roles.
	 */
	@Override
	public void a_secretary(String name) {
		try {
			Person.getEntityWithName(name).addAttribute("secretary");
		} catch (NoSuchPersonException e) {
			new Person(name, "secretary");
		}
	}

	/**
	 * Returns true if the named person exists and is a secretary.
	 */
	@Override
	public boolean e_secretary(String name) {
		try {
			return Person.getEntityWithName(name).hasAttribute("secretary");
		} catch (NoSuchPersonException e) {
			return false;
		}
	}

	/**
	 * If a person with the given name already exists, add "researcher" to their
	 * list of attributes. Otherwise, create a new person object and add
	 * "researcher" to their list of attributes.
	 */
	@Override
	public void a_researcher(String name) {
		try {
			Person.getEntityWithName(name).addAttribute("researcher");
		} catch (NoSuchPersonException e) {
			new Person(name, "researcher");
		}
	}

	/**
	 * Returns true if the named person exists and is a researcher.
	 */
	@Override
	public boolean e_researcher(String name) {
		try {
			return Person.getEntityWithName(name).hasAttribute("researcher");
		} catch (NoSuchPersonException e) {
			return false;
		}
	}

	/**
	 * If a person with the given name already exists, add "manager" to their
	 * list of attributes. Otherwise, create a new person object and add
	 * "manager" to their list of attributes.
	 */
	@Override
	public void a_manager(String name) {
		try {
			Person.getEntityWithName(name).addAttribute("manager");
		} catch (NoSuchPersonException e) {
			new Person(name, "manager");
		}
	}

	/**
	 * Returns true if the named person exists and is a manager.
	 */
	@Override
	public boolean e_manager(String name) {
		try {
			return Person.getEntityWithName(name).hasAttribute("manager");
		} catch (NoSuchPersonException e) {
			return false;
		}
	}

	/**
	 * If a person with the given name already exists, add "smoker" to their
	 * list of attributes. Otherwise, create a new person object and add
	 * "smoker" to their list of attributes.
	 */
	@Override
	public void a_smoker(String name) {
		try {
			Person.getEntityWithName(name).addAttribute("smoker");
		} catch (NoSuchPersonException e) {
			new Person(name, "smoker");
		}
	}

	/**
	 * Returns true if the named person exists and is a smoker.
	 */
	@Override
	public boolean e_smoker(String name) {
		try {
			return Person.getEntityWithName(name).hasAttribute("smoker");
		} catch (NoSuchPersonException e) {
			return false;
		}
	}

	/**
	 * If a person with the given name already exists, add "hacker" to their
	 * list of attributes. Otherwise, create a new person object and add
	 * "hacker" to their list of attributes.
	 */
	@Override
	public void a_hacker(String name) {
		try {
			Person.getEntityWithName(name).addAttribute("hacker");
		} catch (NoSuchPersonException e) {
			new Person(name, "hacker");
		}
	}

	/**
	 * Returns true if the named person exists and is a hacker.
	 */
	@Override
	public boolean e_hacker(String name) {
		try {
			return Person.getEntityWithName(name).hasAttribute("hacker");
		} catch (NoSuchPersonException e) {
			return false;
		}
	}

	/**
	 * Assigns the given group to the given person. If either does not exist,
	 * they are created.
	 */
	@Override
	public void a_group(String personName, String groupName) {
		Group groupObj = null;
		try {
			groupObj = Group.getEntityWithName(groupName);
		} catch (NoSuchGroupException e) {
			groupObj  = new Group(groupName);
		}
		
		try{
			Person personObj = Person.getEntityWithName(personName);
			groupObj.addMember(personObj);
			personObj.addGroup(groupName);
		} catch (NoSuchPersonException e) {
			Person personObj = new Person(personName);
			groupObj.addMember(personObj);
			personObj.addGroup(groupName);
		}
	}

	/**
	 * Returns true if the group with the name given has member by the name
	 * given.
	 * 
	 * @return
	 */
	@Override
	public boolean e_group(String personName, String groupName) {
		try {
			return Group.getEntityWithName(groupName).hasMember(personName);
		} catch (NoSuchGroupException e) {
			return false;
		}
	}

	/**
	 * Assigns the given person to head the given group. If either does not
	 * exist, they are created.
	 */
	@Override
	public void a_heads_group(String personName, String groupName) {
		try {
			Group.getEntityWithName(groupName).setGroupHead(personName);
		} catch (NoSuchGroupException e) {
			(new Group(groupName)).setGroupHead(personName);
		}
	}

	/**
	 * Returns true if the group with the name given exists and is headed by a
	 * person by the name given.
	 * 
	 * @return
	 */
	@Override
	public boolean e_heads_group(String personName, String groupName) {
		try {
			return Group.getEntityWithName(groupName).hasGroupHead(personName);
		} catch (NoSuchGroupException e) {
			return false;
		}
	}

	@Override
	public void a_project(String personName, String projectName) {
		Person personObj = null;
		try {
			personObj = Person.getEntityWithName(personName);
			new Project(projectName, personObj);
		} catch (NoSuchPersonException e) {
			personObj = new Person(personName);
			new Project(projectName, personObj);
		}

	}

	@Override
	public boolean e_project(String personName, String projName)  {
		try {
			Project projObj = Project.getEntityWithName(projName);
			return projObj.hasMember(personName);
		} catch (NoSuchProjectException e) {
			return false;
		}
	}

	@Override
	public void a_heads_project(String personName, String projName) {
		Project projectObj = null;
		try{
			projectObj = Project.getEntityWithName(projName);
		} catch (NoSuchProjectException e) {
			projectObj = new Project(projName);
		}
		
		projectObj.setProjectHead(personName);

	}

	@Override
	public boolean e_heads_project(String personName, String projName)  {
		try {
			Project projObj = Project.getEntityWithName(projName);
			return projObj.hasProjectHead(personName);
		} catch (NoSuchProjectException e) {
			return false;
		}
	}


	@Override
	public void a_works_with(String personName, TreeSet<Pair<ParamType, Object>> p2s) {
		Iterator<Pair<ParamType, Object>> iterator = p2s.iterator();
		Person collegueToAdd = null;
		try {
			String collegueName;
			while ((iterator.hasNext())) {
				//System.err.println(iterator.next());
				collegueName = (String) iterator.next().getValue();
				if (Person.exists(personName) && Person.exists(collegueName)) {
					Person.getEntityWithName(personName).addColleague(Person.getEntityWithName(collegueName));
					Person.getEntityWithName(collegueName).addColleague(Person.getEntityWithName(personName));

				} else if (Person.exists(personName) && !Person.exists(collegueName)) {
					(new Person(collegueName)).addColleague(Person.getEntityWithName(personName));
					Person.getEntityWithName(personName).addColleague(Person.getEntityWithName(collegueName));

				} else if (!Person.exists(personName) && Person.exists(collegueName)) {
					(new Person(personName)).addColleague(Person.getEntityWithName(collegueName));
					Person.getEntityWithName(collegueName).addColleague(Person.getEntityWithName(personName));
				} else if (!Person.exists(personName) && !Person.exists(collegueName)) {
					Person new_person = new Person(personName);
					Person new_collegue = new Person(collegueName);
					new_person.addColleague(new_collegue);
					new_collegue.addColleague(new_person);
				}
			}

		} catch (NoSuchPersonException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean e_works_with(String personName, TreeSet<Pair<ParamType, Object>> p2s) {
		Iterator<Pair<ParamType, Object>> iterator = p2s.iterator();
		Person collegueToAdd = null;
		try {
			String collegueName;
			while ((iterator.hasNext())) {
				//System.err.println(iterator.next());
				collegueName = (String) iterator.next().getValue();
				if (Person.exists(personName) && Person.exists(collegueName)) {
					if(!Person.getEntityWithName(personName).isColleague(Person.getEntityWithName(collegueName))){
						return false;
					}
				}
				else{
					return false;
				}
			}
		} catch (NoSuchPersonException e) {
			return false;
		}
		return true;
	}
	

	@Override
	public void a_works_with(String personName, String collegueName) {
		try {
				if (Person.exists(personName) && Person.exists(collegueName)) {
					Person.getEntityWithName(personName).addColleague(Person.getEntityWithName(collegueName));
					Person.getEntityWithName(collegueName).addColleague(Person.getEntityWithName(personName));

				} else if (Person.exists(personName) && !Person.exists(collegueName)) {
					Person new_collegue = new Person(collegueName);
					new_collegue.addColleague(Person.getEntityWithName(personName));
					Person.getEntityWithName(personName).addColleague(Person.getEntityWithName(collegueName));

				} else if (!Person.exists(personName) && Person.exists(collegueName)) {
					Person new_person = new Person(personName);
					new_person.addColleague(Person.getEntityWithName(collegueName));
					Person.getEntityWithName(collegueName).addColleague(Person.getEntityWithName(personName));
				} else if (!Person.exists(personName) && !Person.exists(collegueName)) {
					Person new_person = new Person(personName);
					Person new_collegue = new Person(collegueName);
					new_person.addColleague(new_collegue);
					new_collegue.addColleague(new_person);
				}

		} catch (NoSuchPersonException e) {
		}

	}

	@Override
	public boolean e_works_with(String personName, String collegueName) {
		try {
			return Person.getEntityWithName(personName).isColleague(Person.getEntityWithName(collegueName));
				
		} catch (NoSuchPersonException e) {
			return false;
		}
	}

	@Override
	public void a_assign_to(String personName, String room)  {
		
		try {
			if (Person.exists(personName) && Room.exists(room)) {
				Person.getEntityWithName(personName).addRoomAssignment(Room.getEntityWithName(room));
				Room.getEntityWithName(room).addOccupant(Person.getEntityWithName(personName));

			} else if (Person.exists(personName) && !Room.exists(room)) {
				Room newRoom = new Room(room);
				newRoom.addOccupant(Person.getEntityWithName(personName));
				Person.getEntityWithName(personName).addRoomAssignment(Room.getEntityWithName(room));

			} else if (!Person.exists(personName) && Room.exists(room)) {
				Person new_person = new Person(personName);
				new_person.addRoomAssignment(Room.getEntityWithName(room));
				Room.getEntityWithName(room).addOccupant(new_person);
			} else if (!Person.exists(personName) && !Person.exists(room)) {
				Person new_person = new Person(personName);
				Room newRoom = new Room(room);
				newRoom.addOccupant(new_person);
				new_person.addRoomAssignment(newRoom);
			}

	} catch (NoSuchPersonException e) {
		System.out.println("uhh");
	} catch (NoSuchRoomException e) {
		System.out.println("fail");
	}
		
	}

	@Override
	public boolean e_assign_to(String personName, String room) {
		try {
			return Room.getEntityWithName(room).hasPerson(Person.getEntityWithName(personName));
			
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 */
	@Override
	public void a_room(String roomName) {
			if (!Room.exists(roomName))
				new Room(roomName);
	}
	/**
	 */
	@Override
	public boolean e_room(String r) {
		return Room.exists(r);
	}

	/**
	 *
	 */
	@Override
	public void a_close(String room, String room2) {
		try{
			if (Room.exists(room) && Room.exists(room2)){
					Room.getEntityWithName(room).addCloseTo(Room.getEntityWithName(room2));
					Room.getEntityWithName(room2).addCloseTo(Room.getEntityWithName(room));
				}else if (!Room.exists(room) && Room.exists(room2)){
					new Room(room).addCloseTo(Room.getEntityWithName(room2));
					Room.getEntityWithName(room2).addCloseTo(Room.getEntityWithName(room));
				}else if (Room.exists(room) && !Room.exists(room2)){
					new Room(room2).addCloseTo(Room.getEntityWithName(room));
					Room.getEntityWithName(room).addCloseTo(Room.getEntityWithName(room2));
				}else{
					Room new_room = new Room(room);
					Room new_room2 = new Room(room2);
					new_room.addCloseTo(new_room2);
					new_room2.addCloseTo(new_room);
				}
		}catch (NoSuchRoomException e){
			System.out.println("FAIL");
		}
		}
	/**
	 *
	 */
		@Override
		public boolean e_close(String room, String room2) {
			try{
				if(Room.exists(room) && Room.exists(room2)){
					if(Room.getEntityWithName(room).isCloseTo(Room.getEntityWithName(room2)))
						return true;
				}
				}catch (NoSuchRoomException e){
					return false;
				}
			return false;
		}

	/**
	 *
	 */
	@Override
	public void a_close(String room, TreeSet<Pair<ParamType, Object>> set) {
		Iterator<Pair<ParamType, Object>> iterator = set.iterator();
		if(!Room.exists(room)){
			new Room(room);
		}
		String roomtoAdd;
		try{
			while((iterator.hasNext())){
				roomtoAdd = (String) iterator.next().getValue();
				if (Room.exists(roomtoAdd)){
					Room.getEntityWithName(room).addCloseTo(Room.getEntityWithName(roomtoAdd));
					Room.getEntityWithName(roomtoAdd).addCloseTo(Room.getEntityWithName(room));
				}else if (!Room.exists(roomtoAdd)){
					new Room(roomtoAdd).addCloseTo(Room.getEntityWithName(room));
					Room.getEntityWithName(room).addCloseTo(Room.getEntityWithName(roomtoAdd));
				}
			}
		}catch (NoSuchRoomException e){
			System.out.println("FAIL");
		}
		}
	/**
	 *
	 */
	@Override
	public boolean e_close(String room, TreeSet<Pair<ParamType, Object>> set) {
		if (!Room.exists(room)){
			return false;
		} 
		try{
		Iterator<Pair<ParamType, Object>> iterator = set.iterator();
		String roomtoAdd;
		while(iterator.hasNext()){
			String roomtoCheck = (String) iterator.next().getValue();
			if (!((Room.getEntityWithName(room)).isCloseTo(Room.getEntityWithName(roomtoCheck)))){
				return false;
			}
		}
		}catch (NoSuchRoomException e){
			return false;
		}
		return true;
	}

	/**
	 *
	 */
	@Override
	public void a_large_room(String roomName) {
		try{
			Room.getEntityWithName(roomName).setSize(Room.RoomSize.LARGE);
		}catch (NoSuchRoomException e){
			new Room(roomName, Room.RoomSize.LARGE);
		}
		
//		if(!Room.exists(roomName)){
//			new Room(roomName, Room.RoomSize.LARGE);
//		}else {
//			Room.getEntityWithName(roomName).setSize(Room.RoomSize.LARGE);
//		}
	}
	/**
	 * 
	 */
	@Override
	public boolean e_large_room(String roomName) {
		if(Room.exists(roomName)){
			try {
				return Room.getEntityWithName(roomName).getSize() == Room.RoomSize.LARGE;
			} catch (NoSuchRoomException e) {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * 
	 */
	@Override
	public void a_medium_room(String roomName) {
		try{
			Room.getEntityWithName(roomName).setSize(Room.RoomSize.MEDIUM);
		}catch (NoSuchRoomException e){
			new Room(roomName, Room.RoomSize.MEDIUM);
		}
	}
	/**
	 * 
	 */
	@Override
	public boolean e_medium_room(String roomName) {
		if(Room.exists(roomName)){
			try {
				return Room.getEntityWithName(roomName).getSize() == Room.RoomSize.MEDIUM;
			} catch (NoSuchRoomException e) {
				return false;
			}
		}
		return false;
	}
	/**
	 * 
	 */
	@Override
	public void a_small_room(String roomName) {
		try{
			Room.getEntityWithName(roomName).setSize(Room.RoomSize.SMALL);
		}catch (NoSuchRoomException e){
			new Room(roomName, Room.RoomSize.SMALL);
		}
	}
	/**
	 * 
	 */
	@Override
	public boolean e_small_room(String roomName) {
		if(Room.exists(roomName)){
			try {
				return Room.getEntityWithName(roomName).getSize() == Room.RoomSize.SMALL;
			} catch (NoSuchRoomException e) {
				return false;
			}
		}
		return false;
	}
	/**
	 * Creates a group with groupName.
	 * @param groupName
	 * @return void
	 */
	@Override
	public void a_group(String groupName) {
		if(!Group.exists(groupName))
			new Group(groupName);		
	}
		
	/**
	 * Returns true if a group with groupName exists.
	 * @param groupName
	 * @return boolean
	 */
	@Override
	public boolean e_group(String groupName) {
		return Group.exists(groupName);
	}
	
	/**
	 * Creates a project with given project name.
	 * @param projName
	 * @return void
	 */
	@Override
	public void a_project(String projName) {
		if(!Project.exists(projName))
			new Project(projName);
	}
	
	/**
	 * Returns true if a project with projName exists.
	 * @param projName
	 * @return boolean
	 */
	@Override
	public boolean e_project(String projName) {
		return Project.exists(projName);
	}
	
	/**
	 * Adds a large project with given name.
	 * If a project already exists with given name, 
	 * it will check to see if that project is large.
	 * If it is not large, it will set it to large.
	 * 
	 * @param projName
	 * @return void
	 */
	@Override
	public void a_large_project(String projName) {
		if(!Project.exists(projName)){					//Check if project with project name exsists
			new Project(projName, true);                   
		} else {              
			Project exsistingProject = null;
			try {
				exsistingProject = Project.getEntityWithName(projName);
			} catch (NoSuchProjectException e) {
				exsistingProject = new Project(projName);
			}
			if(!exsistingProject.isLargeProject()){
				exsistingProject.setLargeProject(); //if it is not large, set it to large
			}
		}
	}
	/**
	 * Returns true if a project with projName exists and is large.
	 * @param projName
	 * @return boolean
	 */
	@Override
	public boolean e_large_project(String projName) {
		if(Project.exists(projName)){
			Project exsistingProject = null;
			try {
				exsistingProject = Project.getEntityWithName(projName);
			} catch (NoSuchProjectException e) {
				e.printStackTrace();
			}
			return exsistingProject.isLargeProject();
		}
		return false;
	}
}
