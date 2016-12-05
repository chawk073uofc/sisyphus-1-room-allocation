package cpsc433;

import officeEntities.Group;
import officeEntities.Person;
import officeEntities.Project;
import officeEntities.Room;
import officeEntities.Room.RoomSize;
import cpsc433.SearchControl;

import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.tree.TreeModel;

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
	protected static String out;
	protected Environment env;
	private static ArrayList<Person> current_assignment;
	private static int current_penalty = -100000; // large negative value to ensure it's changed
	
	
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
			killShutdownHook();
		}
		else { // using interactive mode
			runInteractiveMode();
			killShutdownHook();
		}
	}

	public static void writeOutputFile(String srtingToWrite) {
		try{
		    PrintWriter writer = new PrintWriter(out);
		    writer.println(srtingToWrite);
		    writer.close();
		}catch(FileNotFoundException e){
			File outFile = new File(out);
			writeOutputFile(srtingToWrite);
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
	 * @param env An Environment  object.
	 * @param timeLimit A time limit in milliseconds.
	 */
	protected void doSearch(Environment env, long timeLimit) {
		long deadLine = System.currentTimeMillis() + timeLimit;
		//System.out.println("Would do a search for "+timeLimit+" milliseconds here, but it's not defined yet.");
		if(Person.numberOfPeople() > Room.buildingCapacity()){
			System.out.println("Number of people exceeds building capacity.");
		}
		else if(Person.numberOfPeople() > (Room.buildingCapacity() - Person.numberOfBosses())){
			System.out.println("Not enough rooms for bosses.");		
		} else if(Person.numberOfPeople()>0){ 
			//We (might) need to add a check to see if there are more managers/group heads/project leads than free rooms.
			if(Person.getAssignedPeople().isEmpty()){
				System.out.println("Beginning search.");
				ArrayList<Person> unassignedPpl = Person.getUnAssignedPeople();
				ArrayList<Person> sortedPeople = getSortedPersonList(unassignedPpl);

				ArrayList<Room> rooms = Room.getAvailableRooms();
				ArrayList<Room> sortedRooms = getSortedRoomList(rooms);
				ONode root = new ONode(sortedPeople, sortedRooms);
				OTree oTree = new OTree(root);
				//***Print number of people and rooms.
				System.out.println("Number of people:" + sortedPeople.size());
				System.out.println("Number of rooms:" + rooms.size());
				//***//
				if(sortedPeople.size()>9 && rooms.size() > 9) {
					ONode.checkAllNodes=false;
				}else{
					ONode.checkAllNodes=true;
				}
				root.search(deadLine);
				//asdfsadfsadfsadf
			}else if(Person.numberOfPeople()>0){
				ArrayList<Person> assignedPpl = Person.getAssignedPeople();
				ArrayList<Person> unassignedPpl = Person.getUnAssignedPeople();
				ArrayList<Room> availRooms = Room.getAvailableRooms();
				if(!unassignedPpl.isEmpty()){
					ArrayList<Person> sortedUnassignedPeople = getSortedPersonList(unassignedPpl);
					ArrayList<Room> sortedAvailRooms = getSortedRoomList(availRooms);
					
					ONode root = new ONode(sortedUnassignedPeople, assignedPpl, sortedAvailRooms);
					
					OTree oTree = new OTree(root);
					//***Print number of people and rooms.
					System.out.println("Number of assigned people:" + assignedPpl.size());
					System.out.println("Number of unassigned people:" + sortedUnassignedPeople.size());
					System.out.println("Number of avail rooms:" + sortedAvailRooms.size());
					//***//
					root.search(deadLine);
				} else {
					//everyone is already assigned.
					System.out.println("All assignments in the input file are already completed. Goodbye!");
					// DELETE AFTER //
					ArrayList<Person> test_ppl = Person.getAssignedPeople();
					Person list[] = new Person[0];
					for (Person p : test_ppl){
						System.out.println(SearchControl.f_leaf(p, list));
					}
					// ************ //
					writeOutputFile("All assignments in the input file are already completed.");
				}		
			}
		 }	
		}
	
	
	public static String prepareWriteString(int totalNodes, int totalLeaves){
		//System.out.println("### Printing Final Assignment ###");
		StringBuilder stringtowrite = new StringBuilder();
		for (Person p : current_assignment){
			//System.out.println("Person " + p.getName() + " is assigned to room: " + p.getRoom().getName());
			stringtowrite.append("assign-to(" + p.getName() + ", " + p.getRoom().getName() + ")\n");
		}
		stringtowrite.append("//utility=" +current_penalty+ ", " + Person.numberOfPeople() + "/" + Person.numberOfPeople() + " people assgined.\n");
		stringtowrite.append("//searched " + totalNodes + " nodes, including " + totalLeaves + " solution(s) found\n");
		System.out.println(stringtowrite.toString());
		return stringtowrite.toString();
		
	}
	
	public static void setAssignment(ArrayList<Person> assignment, int penalty){
		current_assignment = new ArrayList<>(assignment);
		current_penalty = penalty;
	}
	
	
	public static int getCurrentPenaltyScore(){
		return current_penalty;
	}
	
	protected void printResults() {
		//System.out.println("Would print results here, but the search isn't implemented yet.");
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

	/**
	 * @return
	 */
	private ArrayList<Person> getSortedPersonList(ArrayList<Person> unsortedPeople) {
		//ArrayList<Person> sortedPeople = new ArrayList<>((Person.getPersonList()).values());
		Collections.sort(unsortedPeople);
		Collections.reverse(unsortedPeople);
		return unsortedPeople;
	}

	/**
	 * @return
	 */
	private ArrayList<Room> getSortedRoomList(ArrayList<Room> unsortedRoom) {
		//ArrayList<Room> rooms = new ArrayList<>((Room.getRooms()).values());
		Collections.sort(unsortedRoom);
		Collections.reverse(unsortedRoom);
		return unsortedRoom;
	}
}
