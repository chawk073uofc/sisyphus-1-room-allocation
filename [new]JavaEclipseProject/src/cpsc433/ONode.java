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
	ArrayList<Person> unassigned = new ArrayList<Person>();
	ArrayList<Person> assigned = new ArrayList<Person>();
	private int f_leaf_value;
	
	/**
	 * Constructor for an empty root node (no assignments).
	 */
	public ONode(ArrayList<Person> unassigned){
		this.unassigned = unassigned;
		f_leaf_value = 0;
	}
	

	/**
	 * Constructor for non-empty root node (input file contains room assignments).
	 * 
	 * @param unassigned
	 * @param assigned
	 */
	public ONode(ArrayList<Person> unassigned, ArrayList<Person> assigned){
		this.unassigned = unassigned;
		this.assigned = assigned;
		f_leaf_value = SearchControl.f_leaf((Person[]) assigned.toArray());
	}
	/**
	 * Constructor for child node.
	 * 
	 * @param unassigned
	 * @param assigned
	 * @param newlyAssinged
	 */
	public ONode(ArrayList<Person> unassigned, ArrayList<Person> assigned, Person newlyAssinged){
		this.unassigned = unassigned;
		this.assigned = assigned;
		//f_leaf_value = ((ONode) this.parent).get_f_leaf() + SearchControl.f_leaf(newlyAssinged, (Person[]) assigned.toArray());
		assigned.add(newlyAssinged);
	}
	
	@Override
	public boolean isLeaf(){
		return unassigned.isEmpty();
	}
	
	public int get_f_leaf(){
		return f_leaf_value;
	}

}
