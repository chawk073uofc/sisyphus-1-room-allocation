/**
<<<<<<< HEAD
 * I have added this comment.
=======
 * I have added this comment to test git.
 * I have added this comment to test git.	
>>>>>>> branch 'master' of https://gitlab.com/chrishawk9/sisDist.git
 */
package cpsc433;

import java.util.Iterator;
import java.util.TreeSet;

import cpsc433.Predicate.ParamType;
import officeEntities.Group;
import officeEntities.NoSuchGroupException;
import officeEntities.NoSuchPersonException;
import officeEntities.Person;
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

		// This is where we write to the output file?

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
		try {
			Person.getEntityWithName(personName).addGroup(groupName);
		} catch (NoSuchPersonException e) {
			(new Person(personName)).addGroup(groupName);
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
		try {
			Person.getEntityWithName(personName).addProject(projectName);
		} catch (NoSuchPersonException e) {
			(new Person(personName)).addProject(projectName);
		}

	}

	@Override
	public boolean e_project(String p, String prj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void a_heads_project(String p, String prj) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean e_heads_project(String p, String prj) {
		// TODO Auto-generated method stub
		return false;
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

		// try {
		// String collegueName = (String)iterator.next().getValue();
		// try{
		//
		// collegueToAdd = Person.getEntityWithName(collegueName);
		// } catch (NoSuchPersonException e){
		// collegueToAdd = new Person(collegueName);
		//
		// }
		// Person.getEntityWithName(personName).addColleague(collegueToAdd);
		// collegueToAdd.addColleague(Person.getEntityWithName(personName));
		// } catch (NoSuchPersonException e) {
		// Person newPerson = new Person(personName);
		// newPerson.addColleague(collegueToAdd);
		// collegueToAdd.addColleague(newPerson );

		// System.err.println(p2s.first().getValue());

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
	
	/**
	 * TODO:Branko
	 */
	@Override
	public void a_works_with(String p, String p2) {
		// TODO Auto-generated method stub

	}
	/**
	 * TODO:Branko
	 */
	@Override
	public boolean e_works_with(String p, String p2) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * TODO:Branko
	 */
	@Override
	public void a_assign_to(String p, String room) throws Exception {
		// TODO Auto-generated method stub

	}
	/**
	 * TODO:Branko
	 */
	@Override
	public boolean e_assign_to(String p, String room) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 *TODO:Brandon 
	 */
	@Override
	public void a_room(String r) {
		// TODO Auto-generated method stub

	}
	/**
	 *TODO:Brandon 
	 */
	@Override
	public boolean e_room(String r) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 *TODO:Brandon 
	 */
	@Override
	public void a_close(String room, String room2) {
		if (Room.exists(room) && Room.exists(room2)){
				Room.getEntityWithName(room).addcloseTo(Room.getEntityWithName(room2));
				Room.getEntityWithName(room2).addcloseTo(Room.getEntityWithName(room));
			}else if (!Room.exists(room) && Room.exists(room2)){
				new Room(room).addcloseTo(Room.getEntityWithName(room2));
				Room.getEntityWithName(room2).addcloseTo(Room.getEntityWithName(room));
			}else if (Room.exists(room) && !Room.exists(room2)){
				new Room(room2).addcloseTo(Room.getEntityWithName(room));
				Room.getEntityWithName(room).addcloseTo(Room.getEntityWithName(room2));
			}else{
				Room new_room = new Room(room);
				Room new_room2 = new Room(room2);
				new_room.addcloseTo(new_room2);
				new_room2.addcloseTo(new_room);
			}
		}
	/**
	 *TODO:Brandon 
	 */
			@Override
			public boolean e_close(String room, String room2) {
				if(Room.exists(room) && Room.exists(room2)){
					if(Room.getEntityWithName(room).iscloseTo(Room.getEntityWithName(room2)))
						return true;
				}
				return false;
			}

			/**
	 *TODO:Brandon 
	 */
	@Override
	public void a_close(String room, TreeSet<Pair<ParamType, Object>> set) {
		Iterator<Pair<ParamType, Object>> iterator = set.iterator();
		if(!Room.exists(room)){
			new Room(room);
		}
		String roomtoAdd;
		while((iterator.hasNext())){
			roomtoAdd = (String) iterator.next().getValue();
			if (Room.exists(roomtoAdd)){
				Room.getEntityWithName(room).addcloseTo(Room.getEntityWithName(roomtoAdd));
				Room.getEntityWithName(roomtoAdd).addcloseTo(Room.getEntityWithName(room));
			}else if (!Room.exists(roomtoAdd)){
				new Room(roomtoAdd).addcloseTo(Room.getEntityWithName(room));
				Room.getEntityWithName(room).addcloseTo(Room.getEntityWithName(roomtoAdd));
			}
		}
		}
	/**
	 *TODO:Brandon 
	 */
	@Override
	public boolean e_close(String room, TreeSet<Pair<ParamType, Object>> set) {
		if (!Room.exists(room)){
			return false;
		} 
		Iterator<Pair<ParamType, Object>> iterator = set.iterator();
		String roomtoAdd;
		while(iterator.hasNext()){
			String roomtoCheck = (String) iterator.next().getValue();
			if (!((Room.getEntityWithName(room)).iscloseTo(Room.getEntityWithName(roomtoCheck)))){
				return false;
			}
		}
		return true;
	}

	/**
	 *TODO:Brandon 
	 */
	@Override
	public void a_large_room(String r) {
		if(!Room.exists(r)){
			new Room(r, 2);
		}else {
			Room.getEntityWithName(r).setSize(2);
		}
	}
	/**
	 *TODO:Brandon 
	 */
	@Override
	public boolean e_large_room(String r) {
		if(Room.exists(r)){
			if(Room.getEntityWithName(r).getSize() == 2)
				return true;
		}
		return false;
	}
	/**
	 *TODO:Brandon 
	 */
	@Override
	public void a_medium_room(String r) {
		if(!Room.exists(r)){
			new Room(r, 1);
		}else{
			Room.getEntityWithName(r).setSize(1);
		}
	}
	/**
	 *TODO:Brandon 
	 */
	@Override
	public boolean e_medium_room(String r) {
		if(Room.exists(r)){
			if(Room.getEntityWithName(r).getSize() == 1)
				return true;
		}		
		return false;
	}
	/**
	 *TODO:Brandon 
	 */
	@Override
	public void a_small_room(String r) {
		if(!Room.exists(r)){
			new Room(r, 0);
		}else{
			Room.getEntityWithName(r).setSize(0);
		}
	}
	/**
	 *TODO:Brandon 
	 */
	@Override
	public boolean e_small_room(String r) {
		if(Room.exists(r)){
			if(Room.getEntityWithName(r).getSize() == 0)
				return true;
		}		
		return false;
	}
	/**
	 * TODO:Ali
	 */
	@Override
	public void a_group(String g) {
		// TODO Auto-generated method stub

	}
	/**
	 * TODO:Ali
	 */
	@Override
	public boolean e_group(String g) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * TODO:Ali
	 */
	@Override
	public void a_project(String p) {
		// TODO Auto-generated method stub

	}
	/**
	 * TODO:Ali
	 */
	@Override
	public boolean e_project(String p) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * TODO:Ali
	 */
	@Override
	public void a_large_project(String prj) {
		// TODO Auto-generated method stub
	}
	/**
	 * TODO:Ali
	 */
	@Override
	public boolean e_large_project(String prj) {
		// TODO Auto-generated method stub
		return false;
	}
}
