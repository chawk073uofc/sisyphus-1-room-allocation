package cpsc433;

import officeEntities.Room;
import officeEntities.Room.RoomSize;
import officeEntities.Person;
import officeEntities.Project;
import cpsc433.Environment;
import java.util.Map;

public class SearchControl {
	//f_select
		//return child with best f_wert score
	//f_leaf
		//return sum of penalties for violated penalties 
	//constraint1
		//return penalty if violated
	//constraint2
	//...
	
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
}

