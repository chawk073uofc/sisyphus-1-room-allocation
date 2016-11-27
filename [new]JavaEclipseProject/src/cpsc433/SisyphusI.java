package cpsc433;

import officeEntities.Group;
import officeEntities.Person;
import officeEntities.Project;
import officeEntities.Room;
import officeEntities.Room.RoomSize;
import cpsc433.SearchControl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * This is the main class for the SysiphusI assignment.  It's main function is to
 * interpret the command line.
 * 
 * <p>Copyright: Copyright (c) 2003-16, Department of Computer Science, University 
 * of Calgary.  Permission to use, copy, modify, distribute and sell this 
 * software and its documentation for any purpose is hereby granted without 
 * fee, provided that the above copyright notice appear in all copies and that
 * both that copyright notice and this permission notice appear in supporting 
 * documentation.  The Department of Computer Science makes no representations
 * about the suitability of this software for any purpose.  It is provided
 * "as is" without express or implied warranty.</p>
 *
 * @author <a href="http://www.cpsc.ucalgary.ca/~kremer/">Rob Kremer</a>
 *
 */
public class SisyphusI {

	/**
	 * Merely create a new SisypyusI object, and let the constructor run the program.
	 * @param args The command line argument list
	 */
	public static void main(String[] args) {
		new SisyphusI(args);
	}
	
	protected final String[] args;
	protected String out;
	protected Environment env;
	
	
	public SisyphusI(String[] args) {
		this.args = args;
		run();
	}

	protected void run() {
		env = getEnvironment();

		String fromFile = null;

		if (args.length>0) {
			fromFile = args[0];
			env.fromFile(fromFile);
		}
		else {
			printSynopsis();
		}

		out = fromFile+".out";

		createShutdownHook();

		if (args.length>1) { // using command-line arguments
			runCommandLineMode();
			printIODemoInfo();
			killShutdownHook();
		}
		else { // using interactive mode
			runInteractiveMode();
			printIODemoInfo();
			killShutdownHook();
		}
	}

	private void printIODemoInfo() {
		String people = Person.peopleInfoString();
		String rooms = Room.roomInfoString();
		String groups = Group.groupInfoString();
		String projects = Project.projectInfoString();
		try{
		    PrintWriter writer = new PrintWriter(out);
		    writer.println(people);
		    writer.println(rooms);
		    writer.println(groups);
		    writer.println(projects);
		    writer.close();
		}catch(FileNotFoundException e){
			File outFile = new File(out);
			printIODemoInfo();
		}
		
		
		
	}

	/**
	 * Return the environment object.  One should return an environment object that 
	 * makes sense for YOUR solution to the problem: the environment could contain 
	 * all the object instances required for the domain (like people, rooms, etc),
	 * as well as potential solutions and partial solutions.
	 * @return The global environment object.
	 */
	protected Environment getEnvironment() {
		return Environment.get();
	}
	
	protected void printSynopsis() {
		System.out.println("Synopsis: SisyphusI [<env-file> [<time-in-ms>]]");
	}

	/**
	 * If you want to install a shutdown hook, you can do that here.  A shutdown
	 * hook is completely optional, but can be useful if you search doesn't exit
	 * in a timely manner.
	 */
	protected void createShutdownHook() {}
	protected void killShutdownHook() {}
	
	/**
	 * Run in "Command line mode", that is, batch mode.
	 */
	protected void runCommandLineMode() {
		try {
			long timeLimit = new Long(args[1]).longValue(); 
			//timeLimit -= (System.currentTimeMillis()-startTime);
				System.out.println("Performing search for "+timeLimit+"ms");
				try {
					doSearch(env, timeLimit);
				} catch (Throwable e) {
					e.printStackTrace();
				}
		}
		catch (NumberFormatException ex) {
			System.out.println("Error: The 2nd argument must be a long integer.");
			printSynopsis();
			System.exit(-1);
		}
		printResults();
	}
	
	/**
	 * Perform the actual search
	 * @param env An Environment object.
	 * @param timeLimit A time limit in milliseconds.
	 */
	protected void doSearch(Environment env, long timeLimit) {
		System.out.println("Would do a search for "+timeLimit+" milliseconds here, but it's not defined yet.");
		if(Person.numberOfPeople() > Room.buildingCapacity())
			System.out.println("Number of people exceeds building capacity");
		else{
			System.out.println("Beginning search.");
			SortedPeople sortedPpl = new SortedPeople((HashMap<String, Person>) Person.getPersonList());
			
		 //NOT WORKING - should print all people in decending order of most important attribute
			while(sortedPpl.hasNext())
				System.out.println(sortedPpl.next());
			
			
		//ALSO NOT WORKING -should print all large then medium then small rooms
			TreeSet<Room> rooms = new TreeSet((Room.getRooms()).values());
			Iterator<Room> roomIterator = rooms.descendingIterator();
			while (roomIterator.hasNext())
				System.out.println(roomIterator.next());

			
			ONode root = new ONode(sortedPpl);
			OTree oTree = new OTree(root);
			//			Person p1 = new Person("A");
//			Person p2 = new Person("B");
//			Person p3 = new Person("C");
//			Room r1 = new Room("r1");
//			Room r2 = new Room("r2");
//			r1.addOccupant(p1);
//			r2.addOccupant(p2);
//			r2.addOccupant(p3);
//			r2.setSize(RoomSize.SMALL);
//			p1.addRoomAssignment(r1);
//			p2.addRoomAssignment(r2);
//			p3.addRoomAssignment(r2);
//			p3.addColleague(p2);
//			p2.addColleague(p3);
//			System.out.println(SearchControl.f_leaf(p2, p1, p3));

//			while (!groupHeadList.isEmpty()){
//				//...
//			}
			
			//While there are unassigned people and there is time left
				//for all group heads
				
					//Select random group head head
					
						//assign them a large room empty room (if one is available)
						//remove this room from pool a available rooms
						//calculate f_leaf for this node
				//for all managers 
					//same
				//for all project head
					//same
				//---if we run out of rooms before all managers, heads are assigned, cancel search
				//
			
		}
	}
	
	protected void printResults() {
		System.out.println("Would print results here, but the search isn't implemented yet.");
	}
	
	protected void runInteractiveMode() {
		final int maxBuf = 200;
		byte[] buf = new byte[maxBuf];
		int length;
		try {
			System.out.print("\nSisyphus I: query using predicates, assert using \"!\" prefixing predicates;\n !exit() to quit; !help() for help.\n\n> ");
			while ((length=System.in.read(buf))!=-1) {
				String s = new String(buf,0,length);
				s = s.trim();
				if (s.equals("exit")) break;
				if (s.equals("?")||s.equals("help")) {
					s = "!help()";
					System.out.println("> !help()");
				}
				if (s.length()>0) {
					if (s.charAt(0)=='!') 
						env.assert_(s.substring(1));
					else 
						System.out.print(" --> "+env.eval(s));
				}
				System.out.print("\n> ");
			}
		} catch (Exception e) {
			System.err.println("exiting: "+e.toString());
		}
	}
}
