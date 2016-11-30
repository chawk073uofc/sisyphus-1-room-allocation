/**
 * 
 */
package cpsc433;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import officeEntities.Person;

/**
 * A node in the or tree. Also known as a state.
 *
 */
public class ONode extends DefaultMutableTreeNode {
	private ArrayList<Person> unassigned = new ArrayList<Person>();
	private ArrayList<Person> assigned = new ArrayList<Person>();
	private int f_leaf_value;
	
	/**
	 * Constructor for an empty root node (no assignments).
	 */
	public ONode(ArrayList<Person> unassignedPpl){
		unassigned = unassignedPpl;
		f_leaf_value = 0;
	}
	

	/**
	 * Constructor for non-empty root node (input file contains room assignments).
	 * 
	 * @param unassigned
	 * @param assigned
	 */
	public ONode(ArrayList<Person> unassignedPpl, ArrayList<Person> assignedPpl){
		unassigned = unassignedPpl;
		assigned = assignedPpl;
		f_leaf_value = SearchControl.f_leaf((Person[]) assignedPpl.toArray());
	}
	/**
	 * Constructor for child node.
	 * 
	 * @param unassigned
	 * @param assigned
	 * @param newlyAssinged
	 */
	public ONode(ArrayList<Person> unassignedPpl, ArrayList<Person> assignedPpl, Person newlyAssinged){
		for (int i = 0; i < unassignedPpl.size(); i++){
			unassigned.add(unassignedPpl.get(i));
		}
		for (int i = 0; i < assignedPpl.size(); i++){
			assigned.add(assignedPpl.get(i));
		}
		//f_leaf_value = ((ONode) this.parent).get_f_leaf() + SearchControl.f_leaf(newlyAssinged, (Person[]) assigned.toArray());
		assigned.add(newlyAssinged);
	
	}
	
	@Override
	public boolean isLeaf(){
		return unassigned.isEmpty();
	}
	
	@Override
	public String toString(){
		String result = "";
		for (int i = 0; i < assigned.size(); i++){
			result = result + assigned.get(i).getName();
		}
		return result;
	}
	
	public int get_f_leaf(){
		return f_leaf_value;
	}

}
