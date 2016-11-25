/**
 * 
 */
package cpsc433;

import javax.swing.tree.DefaultMutableTreeNode;

import officeEntities.Person;

/**
 * A node in the or tree. Also known as a state.
 *
 */
public class ONode extends DefaultMutableTreeNode {
	SortedPeople unassigned;
	SortedPeople assigned;
	private int f_leaf_value;
	
	/**
	 * Constructor for an empty root node (no assignments).
	 */
	public ONode(SortedPeople unassigned){
		this.unassigned = unassigned;
		assigned = new SortedPeople();//empty
		f_leaf_value = 0;
	}
	

	/**
	 * Constructor for non-empty root node (input file contains room assignments).
	 * 
	 * @param unassigned
	 * @param assigned
	 */
	public ONode(SortedPeople unassigned, SortedPeople assigned){
		this.unassigned = unassigned;
		this.assigned = assigned;
		f_leaf_value = SearchControl.f_leaf(assigned.toArray());
	}
	/**
	 * Constructor for child node.
	 * 
	 * @param unassigned
	 * @param assigned
	 * @param newlyAssinged
	 */
	public ONode(SortedPeople unassigned, SortedPeople assigned, Person newlyAssinged){
		this.unassigned = unassigned;
		this.assigned = assigned;
		f_leaf_value = ((ONode) this.parent).get_f_leaf() + SearchControl.f_leaf(newlyAssinged, assigned.toArray());
		assigned.add(newlyAssinged);
	}
	
	public int get_f_leaf(){
		return f_leaf_value;
	}

}
