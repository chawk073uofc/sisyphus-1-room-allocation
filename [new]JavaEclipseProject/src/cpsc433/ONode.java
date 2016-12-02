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
	private ArrayList<Person> unassigned = new ArrayList<Person>();
	private ArrayList<Person> assigned = new ArrayList<Person>();
	private ArrayList<Room> availableRooms = new ArrayList<Room>();
	private Room thisNodesRoom = null;
	private Person thisNodesPerson = null;
	private int f_leaf_value;
	
	
	//TODO: remove from room list if full
	
	/**
	 * Constructor for an empty root node (no assignments).
	 * @param availableRooms TODO
	 * @param avilableRooms TODO
	 */
	public ONode(ArrayList<Person> unassignedPpl, ArrayList<Room> availableRooms){
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
	
	
	public void search(){
		if(this.isLeaf()){
			System.out.println("Node can not be expanded further");

			if (this.f_leaf_value > SisyphusI.getCurrentPenaltyScore()){
				SisyphusI.setAssignment(assigned, this.f_leaf_value);
			}
		}	
		else{	

			expandNode();
			//BIG problem is when there is a project manager and it takes that room out of circulation and the null pointer gets thrown.
			ONode bestChild = SearchControl.f_select(this.children);
			bestChild.getNodesPerson().addRoomAssignment(bestChild.getNodesRoom());
			bestChild.search();
		}
	}
	
	private void expandNode(){

///
			//assign this person to all no-full rooms 
			//int index = 0;

		
		
			ArrayList<Person> newUnassigned = new ArrayList(unassigned);
			ArrayList<Person> newAssigned = new ArrayList(assigned);
			
			Person personToAssign = newUnassigned.remove(0);
			newAssigned.add(personToAssign);
			if(this.isRoot()){
				System.out.println("Starting at Root");
			} else {
				System.out.println("ExpandingNode: (" + this.thisNodesPerson.getName() + ":" + this.thisNodesRoom.getName() + ":" + this.f_leaf_value + ") HASH:" + this.hashCode());
			}
			for (Room r : availableRooms){ // Create one child for each room
				ArrayList<Room> newAvailableRooms = new ArrayList(availableRooms);

				personToAssign.addRoomAssignment(r);
				//Check if placing the newlyAssigned person in his/her room has resulted in that room becoming full. If so, remove it from the list of available rooms
				if(r.isFull()){
					newAvailableRooms.remove(r);
				}
				ONode newNode = new ONode(newUnassigned, newAssigned, personToAssign, newAvailableRooms, r, personToAssign); // Create new node to add
				
				this.add(newNode);
				//oTree.insertNodeInto(newNode, this, index); // Insert the node
				newNode.set_f_leaf(newNode.calc_f_leaf(personToAssign));
				System.out.println("ChildAdded: (" + newNode.thisNodesPerson.getName() + ":" + newNode.thisNodesRoom.getName() + ":" + newNode.f_leaf_value + ") HASH:" + newNode.hashCode());
			//	childList.add(newNode); // Add the new node to the child list
			//	index++;
						
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
			System.out.println("Root");
		}
		
		//for (int i = 0; i < assigned.size(); i++){
	//		result = result + "(" + assigned.get(i).getName() + "," + assigned.get(i).getRoom().getName() + ")";
	//	}
		
		result = result + " Penalty: " + f_leaf_value;
		return result;
	}
	
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
		System.out.println("here");
		return 0; }
	}

	
}
