/**
 * 
 */
package cpsc433;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;

import officeEntities.Person;
import officeEntities.Room;

/**
 * A node in the or tree. Also known as a state.
 *
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
		unassigned = unassignedPpl;
		assigned = assignedPpl;
		this.availableRooms = availableRooms;
		f_leaf_value = SearchControl.f_leaf((Person[]) assignedPpl.toArray());
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

		
		//f_leaf_value = calc_f_leaf(newlyAssigned);
		//assigned.add(newlyAssigned);
	//
	}
	
	private boolean isChecked(){
		return checked;
	}
	private void setChecked(boolean checked){
		this.checked = checked;
	}
	
	private void clearOtherRooms(){
		if(!this.isRoot()){
			for(Room currRoom: availableRooms){
				if(!currRoom.equals(this.getNodesRoom())){
					//System.out.println("Removing 1"+ this.thisNodesPerson.getName() + " from room: " + currRoom.getName());
					currRoom.getOccupants().remove(this.thisNodesPerson.getName());
				}
			}
		} 
	}
	
	public void search(long deadLine){
		
		if(System.currentTimeMillis()>=deadLine){
			if(!oneSolFound){
				StringBuilder tempStr = new StringBuilder();
				// Attributes: complete, solved, utility=-407, 15/15 people assigned.
				tempStr.append("//Attributes: incomplete, unsolved, utility=null, " + assigned.size() + "/" + Person.numberOfPeople() + " people assgined.\n");
				tempStr.append("//searched " + totalNodes + " nodes, including " + totalLeaves + " leaves\n");
				SisyphusI.writeOutputFile(tempStr.toString());
			}
			System.exit(0);
		}
		
		if(this.isLeaf()){
			totalLeaves++;
			//System.out.println("This Node: " + this.hashCode() + "  can not be expanded further Pentalty:" + this.f_leaf_value); //print to file
			if(this.f_leaf_value>SisyphusI.getCurrentPenaltyScore()){
				//System.out.println("saving");
				oneSolFound = true;
				SisyphusI.setAssignment(this.assigned, this.f_leaf_value, totalNodes, totalLeaves);
			}
			this.checked = true;
			thisNodesRoom.getOccupants().remove(thisNodesPerson.getName());
			//System.out.println("Removing 2"+ thisNodesPerson.getName() + " from room: " + thisNodesRoom.getName());
		}else{
			if(this.getChildCount()==0 && checked == false){
				checked = true;
				expandNode();
			}
			//BIG problem is when there is a project manager and it takes that room out of circulation and the null pointer gets thrown.
			ONode bestChild = SearchControl.f_select(this.children);
			bestChild.getNodesPerson().addRoomAssignment(bestChild.getNodesRoom());
			
			bestChild.search(deadLine);
		}
		
		if(this.isRoot()){
			//System.out.println("##ROOT##");
			for(Room currRoom: availableRooms){
				currRoom.getOccupants().clear();
			}
		}
		//System.out.println("We are climbing up through node: " +  this.hashCode() + " #ofroomsavail:" + availableRooms.size());
		if(this.getChildCount()>0){
			//System.out.println("Now checking other children of: " + this.hashCode());
			this.search(deadLine);
		} else if(!this.isRoot()) {
			thisNodesRoom.getOccupants().remove(thisNodesPerson.getName());
			//System.out.println("Removing 3"+ thisNodesPerson.getName() + " from room: " + thisNodesRoom.getName());
			this.removeFromParent();
		}
		


	}
	
	
	
	private void expandNode(){
		clearOtherRooms();
		ArrayList<Person> newUnassigned = new ArrayList(unassigned);
		ArrayList<Person> newAssigned = new ArrayList(assigned);
			
		Person personToAssign = newUnassigned.remove(0);
			newAssigned.add(personToAssign);
			if(this.isRoot()){
				//System.out.println("Starting at Root HASH:" + this.hashCode());
			} else {
				//System.out.println("ExpandingNode: (" + this.thisNodesPerson.getName() + ":" + this.thisNodesRoom.getName() + ":" + this.f_leaf_value + ") HASH:" + this.hashCode() + " #ofroomsavail: " + availableRooms.size());
			}
			for (Room r : availableRooms){ // Create one child for each room
				ArrayList<Room> newAvailableRooms = new ArrayList(availableRooms);

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
