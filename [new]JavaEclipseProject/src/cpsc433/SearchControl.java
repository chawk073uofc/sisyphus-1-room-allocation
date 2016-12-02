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

public class SearchControl {
	//f_select
		//return child with best f_wert score
	//f_leaf
		//return sum of penalties for violated penalties 
	//constraint1
		//return penalty if violated
	//constraint2
	//...
	/**
	 * Returns the total penalty associated with a state (partial or complete building assignment)
	 * by determining which soft constraints are violated. 
	 * @param personName
	 * @param people
	 * @return
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
	 * @param array
	 * @return
	 */
	public static int f_leaf(Person[] people) {
		ArrayList<Person> peopleAL= new ArrayList<>(Arrays.asList(people));
		int penalty = 0;
		for(int i=0; i<people.length; i++){
			Person p = peopleAL.get(i);
			ArrayList<Person> peopleALCpy = new ArrayList<>();
			peopleALCpy.remove(i);
			Person[] everythingBut_p = (Person[]) peopleALCpy.toArray();
			penalty += f_leaf(p, everythingBut_p);
		}
		return penalty;
	}
	/**
	 * performs fselect and returns the child with the highest fleaf value
	 * 
	 * 
	 * @param children
	 * @return
	 */
	public static ONode f_select(Vector children) {
		int bestFLeaf = Integer.MIN_VALUE;
		ONode bestNode = null;
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

