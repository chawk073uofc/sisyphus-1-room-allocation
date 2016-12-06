/**
 * A node in the or tree. Also known as a state.
 * Each node stores a list of currently assigned people, unassigned people, available rooms.
 * Each node also has 2 variable that keep track of which person-room assignment this node handled thisNodesPerson, thisNodesRoom
 * Each node also has a checked boolean associated with them. This stores if this particular node has been check.
 * The f_leaf_value for each stored is also stored in each node. It stores the f-leaf value of the entire subtree before it.
 */
package cpsc433;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Random;

import javax.swing.tree.DefaultMutableTreeNode;

import officeEntities.Person;
import officeEntities.Room;


public class ONode extends DefaultMutableTreeNode {
	private static int totalNodes = 0;
	private static int totalLeaves = 0;
	private static boolean oneSolFound = false;
	public static boolean checkAllNodes = true;

	
	private ArrayList<Person> unassigned = new ArrayList<Person>();
	private ArrayList<Person> assigned = new ArrayList<Person>();
	private ArrayList<Room> availableRooms = new ArrayList<Room>();
	private Room thisNodesRoom = null;
	private Person thisNodesPerson = null;
	private int f_leaf_value;
	private boolean checked = false;
	
	/**
	 * Constructor for an empty root node (no assignments).
	 * @param availableRooms TODO
	 * @param avilableRooms TODO
	 */
	public ONode(ArrayList<Person> unassignedPpl, ArrayList<Room> availableRooms){
		totalNodes++;
		unassigned = unassignedPpl;
		this.availableRooms = availableRooms;
		f_leaf_value = 0;
	}

	/**
	 * Constructor for non-empty root node (input file contains room assignments).
	 * 
	 * @param unassigned
	 * @param assigned
	 */
	public ONode(ArrayList<Person> unassignedPpl, ArrayList<Person> assignedPpl, ArrayList<Room> availableRooms){
		totalNodes++;
		this.unassigned = unassignedPpl;
		this.assigned = assignedPpl;
		this.availableRooms = availableRooms;
	}
	
	/**
	 * Constructor for child node.
	 * @param availabelRooms TODO
	 * @param unassigned
	 * @param assigned
	 * @param newlyAssinged
	 */
	public ONode(ArrayList<Person> unassignedPpl, ArrayList<Person> assignedPpl, Person newlyAssigned, ArrayList<Room> availableRooms, Room thisNodesRoom, Person thisNodesPerson){
		totalNodes++;
		this.thisNodesRoom = thisNodesRoom;
		this.thisNodesPerson = thisNodesPerson;
		this.availableRooms = availableRooms;
		for (int i = 0; i < unassignedPpl.size(); i++){
			unassigned.add(unassignedPpl.get(i));
		}
		for (int i = 0; i < assignedPpl.size(); i++){
			assigned.add(assignedPpl.get(i));
		}
	}

	/**
	 * Main search method. This method is responsible for carrying out the search.
	 * This method constructs an or-tree by selecting using expandNode() and is the main function 
	 * responsible for tree construction and transversal.
	 * It starts as the root of the tree and calls expandNode() which generates child nodes by assigning a currently unassigned person
	 * to each available room. It then uses f-select to select a node to recursively call search on.
	 * 
	 * Once it reaches a leaf node there are two tree transversal modes it can run on:
	 * Exhaustive: If checkAllNodes each node in the tree will be checked and expanded. 
	 * 			   This method provides us with a generally good solution, quickly, for small to medium inputs.
	 * 			   However for large inputs this methods take very long.
	 * Optimized: In this mode, once the first solution is found we mark every node in its path to checked. Except for its
	 * 			  immediate parent and immediate grand-parent. We also calculate a return note, which is generally near the root.
	 * 			  The idea behind it is not to waste too much time looking at nodes around a solution, once a solution is found
	 * 			  we look at its immediate family but we do not spend time expanding all the nodes, instead we return near to the top to explore 
	 * 			  paths we could have taken.
	 * 
	 * This Optimized version was put into place after we observed that either solutions are really close to a 
	 * current solution, or they are really far apart, meaning it is in a completely different subsection of the tree we have yet to explore.
	 * This is the reason we look around the solution for a better one, and then go close to the top of the tree and take a different path all-together.
	 *
	 * @param long deadLine; is the time-stamp at which we need to finish the search
	 */
	public void search(long deadLine){
		//Perform a check to see if we have time. If we are out of time and a solution has not been written
		//write some stats to file
		if(System.currentTimeMillis()>=deadLine){
			if(!oneSolFound){
				StringBuilder tempStr = new StringBuilder();
				tempStr.append("//Attributes: incomplete, utility=null, " + assigned.size() + "/" + Person.numberOfPeople() + " people assgined.\n");
				tempStr.append("//searched " + totalNodes + " nodes, including " + totalLeaves + " solutions found");
				SisyphusI.writeOutputFile(tempStr.toString());
			}
			System.out.println("Time expired! Total Nodes Searched " + totalNodes + " total leaves/solutions: " +totalLeaves);
			System.exit(0);
			
		}
		//If this is the root then clear all of the rooms and their occupant lists
		//Also clears the people and their homeroom.
		if(this.isRoot()){
			for(Room currRoom: availableRooms){
				if(currRoom!=thisNodesRoom){
					currRoom.getOccupants().clear();
				}
			}
			for(Person unassignedPerson: unassigned){
				if(unassignedPerson!=thisNodesPerson){
					unassignedPerson.clearRoomAssignment();
				}
			}
		}
		
		//ONCE a solution is found
		if(this.isLeaf()){
			totalLeaves++;
			//save it if it is the best one
			if(this.f_leaf_value>SisyphusI.getCurrentPenaltyScore()){
				System.out.println("saving");
				oneSolFound = true;
				SisyphusI.setAssignment(this.assigned, this.f_leaf_value);
				SisyphusI.writeOutputFile(SisyphusI.prepareWriteString(totalNodes, totalLeaves));
			}
			//figure out how far to the tree to return, at the moment it is 90% of the way up
			int returnToIndex = (int)Math.round(this.getLevel()*0.1);
			ONode returnTo =(ONode) this.getPath()[returnToIndex];
			returnTo.checked=false;
			//set the parent and grandparent of the solution node as unchecked so we can look in the proximity for a better solution
			ONode parentNode = (ONode) this.getParent();
			if(parentNode.parent!=null){
				ONode grandParentNode = (ONode) parentNode.getParent();
				grandParentNode.checked=false;
			}
			this.checked = true;
			parentNode.checked=false;
			
		}else{
			//if there are no kids and it is set to unchecked then expand it
			if(this.getChildCount()==0 && checked == false){
				//if we are running on exhastive then do not set every node on the way down as checked
				if(!checkAllNodes){
					this.checked = true;
				} else {
					this.checked = false;
				}
				//expand the current node and add children to it
				expandNode();
			}
			//use f-select to pick the best child to recursively call search on
			ONode bestChild = SearchControl.f_select(this.children);
			bestChild.getNodesPerson().addRoomAssignment(bestChild.getNodesRoom());
			bestChild.search(deadLine);
		}
		//This code is for when we are climbing back up, stop at each node that has kids and is unchecked and recursively call search on it
		//if there are kids and it is unchecked then search its kids remove the && checked == false to check everynode.
		if(this.getChildCount()>0 && checked == false){
			this.search(deadLine);
		} else if(!this.isRoot()) {
			this.removeFromParent();
			//Remove the room assignment as we climb up to avoid duplicate assignments
			thisNodesRoom.getOccupants().remove(thisNodesPerson.getName());
			thisNodesPerson.clearRoomAssignment();
		}
	}
	/**
	 * This is our Altern function. 
	 * This will expand the current node and call fleaf to calculate pentalty values for the kids
	 */
	private void expandNode(){
		//Clear all of the rooms with the current person in it because we are assigning that person.
		for(Room otherRoom: availableRooms){
			if(otherRoom.hasPerson(thisNodesPerson) && otherRoom!=thisNodesRoom){
				otherRoom.getOccupants().remove(thisNodesPerson.getName());
			}
		}
		//Duplicate the assisnged and unassigned lists
		ArrayList<Person> newUnassigned = new ArrayList<Person>(unassigned);
		ArrayList<Person> newAssigned = new ArrayList<Person>(assigned);
			
		//select the next person from the unassigned list
		Person personToAssign = newUnassigned.remove(0);
			newAssigned.add(personToAssign);
			//if the current person is a boss and they have been pre assigned and there is no available room
			//then exit with an error. This should never arise unless the input is mistaken
			if(personToAssign.isBoss()){
				if(!Room.hasEmptyRoom(availableRooms)){
					System.out.println("Dead end situation");
					System.exit(0);
				}
			}
			
			//for each availible room, assign the person to each
			for (Room r : availableRooms){ // Create one child for each room
				ArrayList<Room> newAvailableRooms = new ArrayList<Room>(availableRooms);
				personToAssign.addRoomAssignment(r);
				//Check if placing the newlyAssigned person in his/her room has resulted in that room becoming full. If so, remove it from the list of available rooms
				if(r.isFull()){
					newAvailableRooms.remove(r);
				}
				ONode newNode = new ONode(newUnassigned, newAssigned, personToAssign, newAvailableRooms, r, personToAssign); // Create new node to add
				this.add(newNode);
				newNode.set_f_leaf(newNode.calc_f_leaf(personToAssign));
			}						
	}	
	
	
	@Override
	/**
	 * Checks isleaf boolean and returns the value
	 * @return true is current node is a leaf
	 */
	public boolean isLeaf(){
		return unassigned.isEmpty();
	}
	/**
	 * Finds this nodes person object
	 * @return Person object
	 */
	public Person getNodesPerson(){
		return thisNodesPerson;
	}
	/**
	 * Finds this nodes room object
	 * @return Room object
	 */
	public Room getNodesRoom(){
		return thisNodesRoom;
	}
	/**
	 * An overridden tostring method to print the nodes info. More for debugging purposes
	 * @return String 
	 */
	@Override
	public String toString(){
		String result = "";
		for(int k = 0; k<this.getLevel(); k++){
			result += "  ";
		}
		if(!this.isRoot()){
		result = result + this.getNodesPerson().getName() + ":" +  this.getNodesRoom().getName();
		}
		result = result + " Penalty: " + f_leaf_value;
		return result;
	}
	/**
	 * Returns this node's fleaf value
	 * @return int fLeafvalue
	 */
	public int get_f_leaf(){
		return f_leaf_value;
	}
	/**
	 * Setter method for fleaf value
	 * @param value
	 */
	public void set_f_leaf(int value){
		f_leaf_value = value;
	}
	/**
	 * Function that calculated the fleaf and returns it
	 * @param newlyAssigned
	 * @return int fleafvalue
	 */
	public int calc_f_leaf(Person newlyAssigned){
		if(!this.isRoot()){
			Person list[] = new Person[assigned.size()];
			return ((ONode) this.parent).get_f_leaf() + SearchControl.f_leaf(newlyAssigned, assigned.toArray(list));
		} else { 
		return 0; }
	}

	
}
