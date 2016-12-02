
package cpsc433;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import cpsc433.Predicate.ParamType;
import officeEntities.Attribute;
import officeEntities.Group;
import officeEntities.NoSuchGroupException;
import officeEntities.NoSuchPersonException;
import officeEntities.NoSuchProjectException;
import officeEntities.NoSuchRoomException;
import officeEntities.Person;
import officeEntities.Project;
import officeEntities.Room;
import officeEntities.Room.RoomSize;

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
	 * @param name the name of the person to create.
	 */
	@Override
	public void a_person(String name) {
		if (!Person.exists(name))
			new Person(name);
	}

	/**
	 * Returns true if an instance of class Person with the given name exist.
	 * @param name the name of the person who may or may not exist
	 * @return true if a person by the name given exists
	 */
	@Override
	public boolean e_person(String name) {
		return Person.exists(name);
	}

	/**
	 * If a person with the given name already exists, add Attribute.SECRETARY to their
	 * list of roles. Otherwise, create a new person object and add Attribute.SECRETARY
	 * to their list of roles.
	 * @param name the name of the secretary
	 */
	@Override
	public void a_secretary(String name) {
		try {
			Person.getEntityWithName(name).addAttribute(Attribute.SECRETARY);
		} catch (NoSuchPersonException e) {
			new Person(name, Attribute.SECRETARY);
		}
	}

	/**
	 * Returns true if the named person exists and is a secretary.
	 * @param name the name of the person who may or may not be a secretary
	 * @return true if the given person is a secretary
	 */
	@Override
	public boolean e_secretary(String name) {
		try {
			return Person.getEntityWithName(name).hasAttribute(Attribute.SECRETARY);
		} catch (NoSuchPersonException e) {
			return false;
		}
	}

	/**
	 * If a person with the given name already exists, add Attribute.RESEARCHER to their
	 * list of attributes. Otherwise, create a new person object and add
	 * Attribute.RESEARCHER to their list of attributes.
	 * @param name the name of of the researcher
	 */
	@Override
	public void a_researcher(String name) {
		try {
			Person.getEntityWithName(name).addAttribute(Attribute.RESEARCHER);
		} catch (NoSuchPersonException e) {
			new Person(name, Attribute.RESEARCHER);
		}
	}

	/**
	 * Returns true if the named person exists and is a researcher.
	 * @param name the name of the person who may or may not be a researcher
	 * @return true if the person with the name given is a researcher
	 */
	@Override
	public boolean e_researcher(String name) {
		try {
			return Person.getEntityWithName(name).hasAttribute(Attribute.RESEARCHER);
		} catch (NoSuchPersonException e) {
			return false;
		}
	}

	/**
	 * If a person with the given name already exists, add Attribute.MANAGER to their
	 * list of attributes. Otherwise, create a new person object and add
	 * Attribute.MANAGER to their list of attributes.
	 * @param name the name of the manager
	 */
	@Override
	public void a_manager(String name) {
		try {
			Person.getEntityWithName(name).addAttribute(Attribute.MANAGER);
		} catch (NoSuchPersonException e) {
			new Person(name, Attribute.MANAGER);
		}
	}

	/**
	 * Returns true if the named person exists and is a manager.
	 * @param name the name of a person who may or may not be a manager
	 * @return true if the given person is a manager
	 */
	@Override
	public boolean e_manager(String name) {
		try {
			return Person.getEntityWithName(name).hasAttribute(Attribute.MANAGER);
		} catch (NoSuchPersonException e) {
			return false;
		}
	}

	/**
	 * If a person with the given name already exists, add Attribute.SMOKER to their
	 * list of attributes. Otherwise, create a new person object and add
	 * Attribute.SMOKER to their list of attributes.
	 * @param name the name of the smoker
	 * 
	 */
	@Override
	public void a_smoker(String name) {
		try {
			Person.getEntityWithName(name).addAttribute(Attribute.SMOKER);
		} catch (NoSuchPersonException e) {
			new Person(name, Attribute.SMOKER);
		}
	}

	/**
	 * Returns true if the named person exists and is a smoker.
	 * @param name the name of the person who may or may not be a smoker
	 * @return true if the given person is a smoker
	 */
	@Override
	public boolean e_smoker(String name) {
		try {
			return Person.getEntityWithName(name).hasAttribute(Attribute.SMOKER);
		} catch (NoSuchPersonException e) {
			return false;
		}
	}

	/**
	 * If a person with the given name already exists, add Attribute.HACKER to their
	 * list of attributes. Otherwise, create a new person object and add
	 * Attribute.HACKER to their list of attributes.
	 * @param name the name of a smoker
	 */
	@Override
	public void a_hacker(String name) {
		try {
			Person.getEntityWithName(name).addAttribute(Attribute.HACKER);
		} catch (NoSuchPersonException e) {
			new Person(name, Attribute.HACKER);
		}
	}

	/**
	 * Returns true if the named person exists and is a hacker.
	 * @param name the name of the person who may or may not be a hacker
	 * @return true if the given person exists and is a hacker
	 */
	@Override
	public boolean e_hacker(String name) {
		try {
			return Person.getEntityWithName(name).hasAttribute(Attribute.HACKER);
		} catch (NoSuchPersonException e) {
			return false;
		}
	}

	/**
	 * Assigns the given group to the given person. If either does not exist,
	 * they are created.
	 * @param personName the name of the person
	 * @param groupName the name of the group
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
	 * @param personName the name of the person
	 * @param groupName the name of the group
	 * @return true if both person and group exist and the given person has been assigned to the given group
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
	 * @param personName the name of the person
	 * @param groupName the name of the group that the person heads
	 */
	@Override
	public void a_heads_group(String personName, String groupName) {
		Group groupObj;
		try {
			groupObj = Group.getEntityWithName(groupName);
		} catch (NoSuchGroupException e) {	
			groupObj = new Group(groupName);
		}
		groupObj.setGroupHead(personName);
	}

	/**
	 * Returns true if the group with the name given exists and is headed by a
	 * person by the name given.
	 * 
	 * @param personName the name of the person
	 * @param groupName the name of the group
	 * @return true if both person and group exist and the given person heads the given group
	 */
	@Override
	public boolean e_heads_group(String personName, String groupName) {
		try {
			return Group.getEntityWithName(groupName).hasGroupHead(personName);
		} catch (NoSuchGroupException e) {
			return false;
		}
	}
	/**
	 * Adds a group with a given name and adds the person named as a member
	 * If project exists, simply add person to it
	 * If person doesn't exist create it and add it
	 * @param personName the name of the person
	 * @param projectName the name of the project
	 * @return void
	 */
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
	/**
	 * Returns true if the project with the name given exists and the person given is in it
	 * @param personName the name of the person
	 * @param projName the name of the project
	 * @return true if both person and proj exist and the given person is in the the given proj
	 */
	@Override
	public boolean e_project(String personName, String projName)  {
		try {
			Project projObj = Project.getEntityWithName(projName);
			return projObj.hasMember(personName);
		} catch (NoSuchProjectException e) {
			return false;
		}
	}
	/**
	 * Adds a group with a given name and adds the person named as the project head
	 * If proj exists, simply add person to it and make him the head
	 * If person doesn't exist create it and add it
	 * @param personName the name of the person
	 * @param projectName the name of the project
	 * @return void
	 */
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
	/**
	 * Returns true if the project with the name given exists and the person given heads it
	 * @param personName the name of the person
	 * @param projName the name of the project
	 * @return true if both person and project exist and the given person heads the given project
	 */
	@Override
	public boolean e_heads_project(String personName, String projName)  {
		try {
			Project projObj = Project.getEntityWithName(projName);
			return projObj.hasProjectHead(personName);
		} catch (NoSuchProjectException e) {
			return false;
		}
	}

	/**
	 * Establishes that the given person works with all of the people in the given set, and vice versa.
	 * @param personName the name of the person
	 * @param p2s a set of people
	 */
	@Override
	public void a_works_with(String personName, TreeSet<Pair<ParamType, Object>> p2s) {
		Iterator<Pair<ParamType, Object>> iterator = p2s.iterator();
		try {
			String collegueName;
			while ((iterator.hasNext())) {
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

	/**
	 * Returns true if the given person works with all people in the given set.
	 * @param personName the name of the person
	 * @param p2s a set of people
	 * @return true if for all people in the set, the given person works with all of them
	 */
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

	/**
	 * Assigns a person to a room.
	 * @param personName the person to be added.
	 * @param room the room the person will be added to.
	 */
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
		System.out.println("FAIL");
	} catch (NoSuchRoomException e) {
		System.out.println("FAIL");
	}
		
	}

	/**
	 * Checks if the person resides in the room.
	 * @param personName the person to check.
	 * @param room the room to check.
	 * @return true if the person is assigned to the room.
	 */
	@Override
	public boolean e_assign_to(String personName, String room) {
		try {
			return Room.getEntityWithName(room).hasPerson(Person.getEntityWithName(personName));
			
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Adds the room.
	 * @param roomName the name for the room to be added.
	 */
	@Override
	public void a_room(String roomName) {
			if (!Room.exists(roomName))
				new Room(roomName);
	}
	
	/**
	 * Checks if the room exists.
	 * @param r the name for the room to be checked.
	 * @return true if the room exists.
	 */
	@Override
	public boolean e_room(String r) {
		return Room.exists(r);
	}

	/**
	 * Asserts a room to be close to another room.
	 * @param room a room to be assigned close to.
	 * @param room2 another room to be assigned close to.
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
	 * Checks if two rooms are close to each other.
	 * @param room a room to be checked close to.
	 * @param room2 the other room to be checked close to.
	 * @return true if the rooms are close to each other.
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
	 * Asserts the close to relation with a room and a set of rooms.
	 * @param room the host room to be close to the set of rooms.
	 * @param set the set of rooms to be close to the host room.
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
	 * Checks to see if a host room(room) is close to all of the rooms in a set of rooms.
	 * @param room the host room to check if is close to all the rooms in set.
	 * @param set the set of rooms to check against the host room.
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
	 * Asserts a room to be large.
	 * @param roomName name of the room.
	 */
	@Override
	public void a_large_room(String roomName) {
		try{
			Room.getEntityWithName(roomName).setSize(Room.RoomSize.LARGE);
		}catch (NoSuchRoomException e){
			new Room(roomName, Room.RoomSize.LARGE);
		}
	}
	
	/**
	 * Checks to see if a room is large.
	 * @param roomName name of the room to check.
	 * @return true if the room is large.
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
	 * Asserts a room to be medium.
	 * @param roomName name of the room.
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
	 * Checks to see if a room is medium.
	 * @param roomName name of the room to check.
	 * @return true if the room is medium.
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
	 * Asserts a room to be small.
	 * @param roomName name of the room.
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
	 * Checks to see if a room is small.
	 * @param roomName name of the room to check.
	 * @return true if the room is small.
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
	
	
	
	
	
	
	
	
	
	
	
	
	// THE SOFT CONSTRAINTS //
	
	// group heads should have a large room //
	public static int getPenalty1(Person p){
		Map<String, Group> p_groups = p.getGroups(); 
		for (Map.Entry<String, Group> entry : p_groups.entrySet()){
			if (entry.getValue().hasGroupHead(p.getName())){ // if person p is the head of a group
				if (p.getRoom().getSize() != RoomSize.LARGE){
					System.out.println("Penalty 1 on person: " + p.getName());
					return -40;
				}
			}
		}
		
		return 0;
	}
	
	// group heads should be close to all members of their group //
	public static int getPenalty2(Person p){
		Map<String, Group> p_groups = p.getGroups();
		for (Map.Entry<String, Group> entry : p_groups.entrySet()){
			if (entry.getValue().hasGroupHead(p.getName())){ // if person p is the head of a group
				for (Map.Entry<String, Person> entry_person : entry.getValue().getMembers().entrySet()){ // for every member of the group
					if (!p.getRoom().isCloseTo(entry_person.getValue().getRoom())){ // if p is not close to that person: penalty
						System.out.println("Penalty 2 on person: " + p.getName());
						return -2;
					}
				}
			}
		}	
		
		return 0;
	}
	
	// group heads should be close to at least one secretary in the group //
	public static int getPenalty3(Person p){
		Map<String, Group> p_groups = p.getGroups();
		for (Map.Entry<String, Group> entry : p_groups.entrySet()){
			if (entry.getValue().hasGroupHead(p.getName())){ // if person p is the head of a group
				for (Map.Entry<String, Person> entry_person : entry.getValue().getMembers().entrySet()){ // for every member of the group
					if (entry_person.getValue().hasAttribute(Attribute.SECRETARY)){ 
						if (p.getRoom().isCloseTo(entry_person.getValue().getRoom())){
							System.out.println("Penalty 3 on person: " + p.getName());
							return 0; // if a match is found, no penalty
						}
					}
				}
				return -30; // if a secretary is not found that's close, we return the penalty
			}
		}
		return 0; 
	}
	
	// if one person is a secretary and the other isn't //
	public static int getPenalty4(Person p, Person q){
		if (p.getRoom().getName() == q.getRoom().getName()){
			if ((p.hasAttribute(Attribute.SECRETARY) && !q.hasAttribute(Attribute.SECRETARY)) || (q.hasAttribute(Attribute.SECRETARY) && !p.hasAttribute(Attribute.SECRETARY))){
				System.out.println("Penalty 4 on person: " + p.getName());
				return -5;
			}
		}
		return 0;
	}
	
	// managers should be close to at least one secretary in their group //
	public static int getPenalty5(Person p){
		if (p.hasAttribute(Attribute.MANAGER)){
			Map<String, Group> p_groups = p.getGroups();
			for (Map.Entry<String, Group> entry : p_groups.entrySet()){
				for (Map.Entry<String, Person> entry_person : entry.getValue().getMembers().entrySet()){ // for every member of the group
					if (entry_person.getValue().hasAttribute(Attribute.SECRETARY)){ 
						if (p.getRoom().isCloseTo(entry_person.getValue().getRoom())){
							return 0; // if a match is found, no penalty
						}	
					}
				}
			}
			System.out.println("Penalty 5 on person: " + p.getName());
			return -20; // if a secretary is not found that's close, we return the penalty
		}
		return -0; 
	}
	
	// managers should be close to their groups head //
	public static int getPenalty6(Person p){
		if (p.hasAttribute(Attribute.MANAGER)){
			Map<String, Group> p_groups = p.getGroups();
			for (Map.Entry<String, Group> entry : p_groups.entrySet()){
				Map<String, Person> groupHeads = entry.getValue().getGroupHeads();
				for (Map.Entry<String, Person> groupHeads_entry : groupHeads.entrySet()){
					if (!p.getRoom().isCloseTo(groupHeads_entry.getValue().getRoom())){ // if the manager p isn't close to his groups head
						System.out.println("Penalty 6 on person: " + p.getName());
						return -20;
					}
				}
			}
		}
		return 0;
	}
	
	// managers should be close to all members of their group //
	public static int getPenalty7(Person p){
		if (p.hasAttribute(Attribute.MANAGER)){
			Map<String, Group> p_groups = p.getGroups();
			for (Map.Entry<String, Group> entry : p_groups.entrySet()){
				for (Map.Entry<String, Person> person_entry : entry.getValue().getMembers().entrySet()){ // for every person in the group
					if (!p.getRoom().isCloseTo(person_entry.getValue().getRoom())){ // if the manager p isn't close to the member
						System.out.println("Penalty 7 on person: " + p.getName());
						return -2;
					}
				}
			}
		}	
		return 0;
	}
	
	// the heads of projects should be close to all members of their project //
	public static int getPenalty8(Person p){
		Map<String, Project> p_projects = p.getProjects();
		for (Map.Entry<String, Project> entry : p_projects.entrySet()){ // for every project person p is in;
			if (entry.getValue().hasProjectHead(p.getName())){ // if person p is the head of the project
				for (Map.Entry<String, Person> entry_person : entry.getValue().getMembers().entrySet()){ // for every member of the project
					if (!p.getRoom().isCloseTo(entry_person.getValue().getRoom())){ // if the project head isn't close to the member
						System.out.println("Penalty 8 on person: " + p.getName());	
						return -5; 
						}
					}
			}
		}
		return 0;
	}
	
	// the heads of large projects should be close to at least one secretary in their group //
	public static int getPenalty9(Person p){
		Map<String, Project> p_projects = p.getProjects();
		for (Map.Entry<String, Project> entry : p_projects.entrySet()){ // for every project person p is in;
			if ((entry.getValue().hasProjectHead(p.getName())) && entry.getValue().isLargeProject()){ // if person p is the head of a large project
				// group section
				Map<String, Group> p_groups = p.getGroups();
				for (Map.Entry<String, Group> g_entry: p_groups.entrySet()){ 
					for (Map.Entry<String, Person> person_entry : g_entry.getValue().getMembers().entrySet()){ // for every person in the group
						if (person_entry.getValue().hasAttribute(Attribute.SECRETARY)){
							if (p.getRoom().isCloseTo(person_entry.getValue().getRoom())){ // if large project head p is close to a secretary
								return 0;
							}
						}
					}
				}
				System.out.println("Penalty 9 on person: " + p.getName());
				return -10; // If a secretary was not found
			}
		}	
		return 0;
	}
	
	// the heads of large projects should be close to the head of their group
	public static int getPenalty10(Person p, Person q){
		Map<String, Project> p_projects = p.getProjects();
		for (Map.Entry<String, Project> entry : p_projects.entrySet()){ // for every project person p is in;
			if ((entry.getValue().hasProjectHead(p.getName())) && entry.getValue().isLargeProject()){ // if person p is the head of a large project
				// group section
				Map<String, Group> p_groups = p.getGroups();
				for (Map.Entry<String, Group> g_entry: p_groups.entrySet()){ 
					for (Map.Entry<String, Person> groupHead_entry : g_entry.getValue().getGroupHeads().entrySet()){ // for every group head in the group
						if (!p.getRoom().isCloseTo(groupHead_entry.getValue().getRoom())){
							System.out.println("Penalty 10 on person: " + p.getName());
							return -10;
						}
					}
				}
			}
		}
		return 0;
	}
	
	// if one person is a smoker and the other isn't //
	public static int getPenalty11(Person p, Person q){
		if (p.getRoom().getName() == q.getRoom().getName()){
			if ((p.hasAttribute(Attribute.SMOKER) && !q.hasAttribute(Attribute.SMOKER)) || (q.hasAttribute(Attribute.SMOKER) && !p.hasAttribute(Attribute.SMOKER))){
				System.out.println("Penalty 11 on person: " + p.getName());
				return -50;
			}
		}
		return 0;
	}
	
	// if p and q are members of the same project //
	public static int getPenalty12(Person p, Person q){
		if (p.getRoom().getName() == q.getRoom().getName()){
			for (Map.Entry<String, Project> p_entry : p.getProjects().entrySet()) {
				for (Map.Entry<String, Project> q_entry : q.getProjects().entrySet()){
					if (p_entry.getValue().getName() == q_entry.getValue().getName()){
						System.out.println("Penalty 12 on person: " + p.getName());
						return -7;
					}
				}
			}	
		}
		return 0;
	}
	
	// if a non-secretary hacker/non-hacker shares an office with a hacker/non-hacker // TODO: possibly wrong
	public static int getPenalty13(Person p, Person q){
		if (p.getRoom().getName() == q.getRoom().getName()){
			if (!p.hasAttribute(Attribute.SECRETARY) && !q.hasAttribute(Attribute.SECRETARY)){
				if ((p.hasAttribute(Attribute.HACKER) && !q.hasAttribute(Attribute.HACKER)) || q.hasAttribute(Attribute.HACKER) && !p.hasAttribute(Attribute.HACKER)){
					System.out.println("Penalty 13 on person: " + p.getName());
					return -2;
				}
			}
		}
		return 0;
	}	

	// if person p shares a room with person q //
	public static int getPenalty14(Person p, Person q){
		if (p.getRoom().getName() == q.getRoom().getName()){ //  Make sure they're not the same person!
			if(p.getName() != q.getName()){
				System.out.println("Penalty 14 on person: " + p.getName());
				return -4;
			}
		}
		return 0;
	}
	
	// if person p does not work with person q //
	public static int getPenalty15(Person p, Person q){
		if (p.getRoom().getName() == q.getRoom().getName()){
			if(p.getName() != q.getName()){ //  Make sure they're not the same person!
				if (!p.isColleague(q)){
					System.out.println("Penalty 15 on person: " + p.getName());
					return -3;
				}
			}
		}
		return 0;
	}
	
	// if person p and q share a small room //
	public static int getPenalty16(Person p, Person q){
		if (p.getRoom().getName() == q.getRoom().getName()){
			if (p.getName() != q.getName()){ //  Make sure they're not the same person!
				if (p.getRoom().getSize() == RoomSize.SMALL){
					System.out.println("Penalty 16 on person: " + p.getName());
					return -25;
				}
			}
		}
		return 0;
	}
}
