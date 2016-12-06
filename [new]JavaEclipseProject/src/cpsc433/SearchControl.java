package cpsc433;

import officeEntities.Room;
import officeEntities.Room.RoomSize;
import officeEntities.Person;
import officeEntities.Project;
import cpsc433.Environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * This class serves as the search control for the search process, along with the summing of penalty points.
 * Theoretical elements include fleaf and fselect.
 * The f_leaf function takes in assignments and determines the penalty score of the scenario (node).
 * The fselect function takes in child nodes, and returns the child node with the highest score.
 * 
 * @author Brandon Sieu
 *
 */

public class SearchControl {
	
	/**
	 * Returns the total penalty associated with a state (partial or complete building assignment)
	 * by determining which soft constraints are violated. 
	 * @param personName person to be inserted into the building assignment state
	 * @param people	existent people in the building assignment state
	 * @return penalty score of the building assignment
	 */
	public static int f_leaf(Person personName, Person... people){
		int penalty = 0;
		penalty += Environment.getPenalty1(personName);
		penalty += Environment.getPenalty2(personName);
		penalty += Environment.getPenalty3(personName);
		penalty += Environment.getPenalty5(personName);
		penalty += Environment.getPenalty6(personName);
		penalty += Environment.getPenalty7(personName);
		penalty += Environment.getPenalty8(personName);
		penalty += Environment.getPenalty9(personName);
		for (Person p : people){			
			penalty += Environment.getPenalty4(personName, p);
			penalty += Environment.getPenalty10(personName, p);
			penalty += Environment.getPenalty11(personName, p);
			penalty += Environment.getPenalty12(personName, p);
			penalty += Environment.getPenalty13(personName, p);
			penalty += Environment.getPenalty14(personName, p);
			penalty += Environment.getPenalty15(personName, p);
			penalty += Environment.getPenalty16(personName, p);
		}	
		return penalty;
	}
	
	/**
	 * Version of f_leaf to be called when the root node of the search tree is not empty
	 * (input file contains room assignments).
	 * @param people the array of person objects that reside within the root node
	 * @return penalty score of the building assignment
	 */
	public static int f_leaf(Person[] people) {
		ArrayList<Person> peopleAL= new ArrayList<>(Arrays.asList(people));	
		int penalty = 0;
		//Copies the array into an arraylist
		for(int i=0; i<people.length; i++){
			Person p = peopleAL.get(i);
			ArrayList<Person> peopleALCpy = new ArrayList<>();
			peopleALCpy.remove(i);
			Person[] everythingBut_p = (Person[]) peopleALCpy.toArray();
			//extracts a person each iteration, and calculates a total penalty by looping f_leaf
			penalty += f_leaf(p, everythingBut_p);
		}
		return penalty;
	}
	
	/**
	 * Performs f_select and returns the child with the highest f_leaf value.
	 * 
	 * @param children the vector of children nodes.
	 * @return the child node with the best f_leaf value.
	 */
	public static ONode f_select(Vector children) {
		int bestFLeaf = Integer.MIN_VALUE;
		ONode bestNode = null;
		//search for best f_leaf over vector of children
		for(Object objNode: children){
			ONode currNode = (ONode) objNode;
			if(currNode.get_f_leaf()>bestFLeaf){
				bestFLeaf = currNode.get_f_leaf();
				bestNode = currNode;
			}
		}
		return bestNode;
		
	}
}

