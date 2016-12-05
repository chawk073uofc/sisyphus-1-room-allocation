/**
 * 
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

/**
 * A node in the or tree. Also known as a state.
 *ghhdg
 */
public class ONode extends DefaultMutableTreeNode {
	private static int totalNodes = 0;
	private static int totalLeaves = 0;
	private static int totalAssigned = 0;
	private static boolean oneSolFound = false;
	
	
	private ArrayList<Person> unassigned = new ArrayList<Person>();
	private ArrayList<Person> assigned = new ArrayList<Person>();
	private ArrayList<Room> availableRooms = new ArrayList<Room>();
	private Room thisNodesRoom = null;
	private Person thisNodesPerson = null;
	private int f_leaf_value;
	private boolean checked = false;
	private ONode optimalReturnNode = null;
	
	
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
		//this.f_leaf_value = SearchControl.f_leaf ((Person[])assignedPpl.toArray());
	}
	/**
	 * Constructor for child node.
	 * @param availabelRooms TODO
	 * @param unassigned
	 * @param assigned
	 * @param newlyAssinged
	 */
	public ONode(ArrayList<Person> unassignedPpl, ArrayList<Person> assignedPpl, Person newlyAssigned, ArrayList<Room> availableRooms, Room thisNodesRoom, Person thisNodesPerson){
		//Check if placing the newlyAssigned person in his/her room has resulted in that room becoming full. If so, remove it from the list of available rooms
		//if(newlyAssigned.getRoom().isFull()){
		//	availabelRooms.remove(newlyAssigned.getRoom());
			//this.availableRooms = availabelRooms;
	//	}
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

	
	public void search(long deadLine){
		if(System.currentTimeMillis()>=deadLine){
			if(!oneSolFound){
				StringBuilder tempStr = new StringBuilder();
				// Attributes: complete, solved, utility=-407, 15/15 people assigned.
				tempStr.append("//Attributes: incomplete, utility=null, " + assigned.size() + "/" + Person.numberOfPeople() + " people assgined.\n");
				tempStr.append("//searched " + totalNodes + " nodes, including " + totalLeaves + " solutions found");
				SisyphusI.writeOutputFile(tempStr.toString());
			}
			System.exit(0);
		}
		//ONCE a solution is found
		if(this.isLeaf()){
			totalLeaves++;
			//System.out.println("This Node: " + this.hashCode() + "  can not be expanded further Pentalty:" + this.f_leaf_value); //print to file
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
			//set the return to and parent of return to as unchecked so when we climb we stop at them
			if(returnToIndex-1>0) {
				ONode returnToParent =(ONode) this.getPath()[returnToIndex-1];
				returnToParent.checked=false;
			 }
			//set the parent and grandparent of the solution node as unchecked so we can look in the proximity for a solution
			ONode parentNode = (ONode) this.getParent();
			ONode grandParentNode = (ONode) this.getParent();
			this.checked = true;
			parentNode.checked=false;
			grandParentNode.checked=false;
			//System.out.println("Setting return to at node: " + returnTo.hashCode() + " at level: " + returnToIndex);
			//thisNodesRoom.getOccupants().remove(thisNodesPerson.getName());
			//System.out.println("Removing 2"+ thisNodesPerson.getName() + " from room: " + thisNodesRoom.getName());
			
		}else{
			//if there are no kids and it is set to unchecked then expand it
			if(this.getChildCount()==0 && checked == false){
				checked = true;
				expandNode();
			}
			//BIG problem is when there is a project manager and it takes that room out of circulation and the null pointer gets thrown.
			ONode bestChild = SearchControl.f_select(this.children);
			//if(bestChild==null) return;
			bestChild.getNodesPerson().addRoomAssignment(bestChild.getNodesRoom());
			bestChild.search(deadLine);
		}
		//System.out.println("We are climbing up through node: " +  this.hashCode() + " #ofroomsavail:" + availableRooms.size());
		
		//if there are kids and it is unchecked then search its kids
		if(this.getChildCount()>0 && checked == false){
			this.search(deadLine);
		} else if(!this.isRoot()) {
			//thisNodesRoom.getOccupants().remove(thisNodesPerson.getName());
			//System.out.println("Removing 3"+ thisNodesPerson.getName() + " from room: " + thisNodesRoom.getName());
			this.removeFromParent();
		}
	}
	
	private void expandNode(){
		for(Room currRoom: availableRooms){
			currRoom.getOccupants().clear();
		}
		for(Person unassignedPerson: unassigned){
			unassignedPerson.clearRoomAssignment();
		}
		ArrayList<Person> newUnassigned = new ArrayList<Person>(unassigned);
		ArrayList<Person> newAssigned = new ArrayList<Person>(assigned);
			
		Person personToAssign = newUnassigned.remove(0);
			newAssigned.add(personToAssign);
			if(this.isRoot()){
				//System.out.println("Starting at Root HASH:" + this.hashCode());
			} else {
				//System.out.println("ExpandingNode: (" + this.thisNodesPerson.getName() + ":" + this.thisNodesRoom.getName() + ":" + this.f_leaf_value + ") HASH:" + this.hashCode() + " #ofroomsavail: " + availableRooms.size());
			}
			
			if(personToAssign.isBoss()){
				if(!Room.hasEmptyRoom(availableRooms)){
					//System.out.println("Dead end situation");
					System.exit(0);
				}
			}
			
			for (Room r : availableRooms){ // Create one child for each room
				ArrayList<Room> newAvailableRooms = new ArrayList<Room>(availableRooms);
				personToAssign.addRoomAssignment(r);
				//Check if placing the newlyAssigned person in his/her room has resulted in that room becoming full. If so, remove it from the list of available rooms

				ONode newNode = new ONode(newUnassigned, newAssigned, personToAssign, newAvailableRooms, r, personToAssign); // Create new node to add
				this.add(newNode);
				newNode.set_f_leaf(newNode.calc_f_leaf(personToAssign));
				//System.out.println("ChildAdded: (" + newNode.thisNodesPerson.getName() + ":" + newNode.thisNodesRoom.getName() + ":" + newNode.f_leaf_value + ") HASH:" + newNode.hashCode() + " #ofroomsavail: " + newAvailableRooms.size());
				if(r.isFull()){
					newAvailableRooms.remove(r);
				}
			}					
			
	}	
	
	private ArrayList<Person> getUnassignedList(){
		return unassigned;
	}
	
	
	@Override
	public boolean isLeaf(){
		return unassigned.isEmpty();
	}
	
	public Person getNodesPerson(){
		return thisNodesPerson;
	}
	public Room getNodesRoom(){
		return thisNodesRoom;
	}
	@Override
	public String toString(){
		String result = "";
		for(int k = 0; k<this.getLevel(); k++){
			result += "  ";
		}
		if(!this.isRoot()){
		result = result + this.getNodesPerson().getName() + ":" +  this.getNodesRoom().getName();
		}else {
			//System.out.println("Root");
		}
		
		//for (int i = 0; i < assigned.size(); i++){
	//		result = result + "(" + assigned.get(i).getName() + "," + assigned.get(i).getRoom().getName() + ")";
	//	}
		
		result = result + " Penalty: " + f_leaf_value;
		return result;
	}
	//asdfsdfdfs
	public int get_f_leaf(){
		return f_leaf_value;
	}
	
	public void set_f_leaf(int value){
		f_leaf_value = value;
	}
	
	public int calc_f_leaf(Person newlyAssigned){
		if(!this.isRoot()){
			Person list[] = new Person[assigned.size()];
			return ((ONode) this.parent).get_f_leaf() + SearchControl.f_leaf(newlyAssigned, assigned.toArray(list));
			//return ((ONode) this.parent).get_f_leaf() + SearchControl.f_leaf(newlyAssigned, (Person[]) assigned.toArray());
		}
	else { 
		return 0; }
	}

	
}
