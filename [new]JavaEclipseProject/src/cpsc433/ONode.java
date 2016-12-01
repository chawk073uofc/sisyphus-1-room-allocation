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
	public ONode(ArrayList<Person> unassignedPpl, ArrayList<Person> assignedPpl, Person newlyAssigned){
		for (int i = 0; i < unassignedPpl.size(); i++){
			unassigned.add(unassignedPpl.get(i));
		}
		for (int i = 0; i < assignedPpl.size(); i++){
			assigned.add(assignedPpl.get(i));
		}

		
		//f_leaf_value = calc_f_leaf(newlyAssigned);
		assigned.add(newlyAssigned);
	
	}
	
	@Override
	public boolean isLeaf(){
		return unassigned.isEmpty();
	}
	
	@Override
	public String toString(){
		String result = "";
		for(int k = 0; k<this.getLevel(); k++)
			result += "  ";
		
		for (int i = 0; i < assigned.size(); i++){
			result += "assigned-to(" + assigned.get(i).getName() + ", " + assigned.get(i).getRoom().getName() + "); "; 
			//result = result + assigned.get(i).getName();
		}
		result += " [" + f_leaf_value + "]";
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
