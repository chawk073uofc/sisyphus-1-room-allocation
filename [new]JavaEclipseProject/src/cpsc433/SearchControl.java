package cpsc433;

import officeEntities.Room;
import officeEntities.Room.RoomSize;
import officeEntities.Person;
import officeEntities.Project;
import java.util.Map;

public class SearchControl {
	//f_select
		//return child with best f_wert score
	//f_wert
		//return sum of penalties for violated penalties 
	//constraint1
		//return penalty if violated
	//constraint2
	//...
	
	// "implemented": 4, 11, 12, 14, 15, 16
	public int getTotalPenalty(Person... people){
		int penalty = 0;
		for (Person p : people){			
			// Check for colleague related conflicts //
			for (Person q : people){
				// if p and q aren't the same person we continue
				if (p.getName() != q.getName()){
					// if person p shares a room with person q
					if (p.getRoom().getName() == q.getRoom().getName()){
						penalty += -4; // SOFT CONSTRAINT 14 
						
						// if person p does not work with person q //
						if (!p.isColleague(q)){
							penalty += -3; // SOFT CONSTRAINT 15 
						}
						// if person p and q share a small room //
						if (p.getRoom().getSize() == RoomSize.SMALL){
							penalty += -25; // SOFT CONSTRAINT 16 
						}
						// if one person is a smoker and the other isn't //
						if ((p.hasAttribute("smoker") && !q.hasAttribute("smoker")) || (q.hasAttribute("smoker") && !p.hasAttribute("smoker"))){
							penalty += -50; // SOFT CONSTRAINT 11
						}
						// TODO: if a non-secretary hacker/non-hacker shares an office with a hacker/non-hacker (13)???
						
						// if one person is a secretary and the other isn't //
						if ((p.hasAttribute("secretary") && !q.hasAttribute("secretary")) || (q.hasAttribute("secretary") && !p.hasAttribute("secretary"))){
							penalty += -5; // SOFT CONSTRAINT 4
						}
						// here we check to see if p and q are members of the same project //
						for (Map.Entry<String, Project> p_entry : p.getProjects().entrySet()) {
							for (Map.Entry<String, Project> q_entry : q.getProjects().entrySet()){
								if (p_entry.getValue().getName() == q_entry.getValue().getName()){
									penalty += -7; // SOFT CONSTRAINT 12
									break;
								}
							}
						}	
					}	
				}
			}	
		}	
		return penalty;
	}
}

