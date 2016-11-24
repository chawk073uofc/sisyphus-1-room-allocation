package cpsc433;

import java.util.HashMap;
import java.util.Map;

import officeEntities.Person;

public class SortedPeople {
	//people are organized by the following "ranking" from highest to least
		//group-head
		//project-head
		//manager
		//secretary
		//smoker
		//hacker
		//researcher
		//person <--- a person who has not attributes
	
	private Map<String, Person> groupHeads= new HashMap<>(); //people who's highest rank is group head
	private Map<String, Person> projHeads= new HashMap<>(); //people who's highest rank is group head
	private Map<String, Person> managers= new HashMap<>(); //people who's highest rank is group head
	private Map<String, Person> secretaries= new HashMap<>(); //people who's highest rank is group head
	private Map<String, Person> hackers= new HashMap<>(); //people who's highest rank is group head
	private Map<String, Person> researchers= new HashMap<>(); //people who's highest rank is group head
	private Map<String, Person> smokers= new HashMap<>(); //people who's highest rank is group head
	private Map<String, Person> noAttributes= new HashMap<>(); //people who's highest rank is group head

	public SortedPeople(HashMap<String,Person> personHash) {
		for(Person p : personHash.values()){
			String rankingAttribute = p.getMostImportantAttribute();
				switch (rankingAttribute){
				case "group-head":
					groupHeads.put(p.getName(), p);
					break;
				case "project-head":
					projHeads.put(p.getName(), p);
					break;
				case "manager":
					managers.put(p.getName(), p);
					break;
				case "secretary":
					secretaries.put(p.getName(), p);
					break;
				case "smoker":
					smokers.put(p.getName(), p);
					break;
				case "hacker":
					hackers.put(p.getName(), p);
					break;
				case "researcher":
					researchers.put(p.getName(), p);
					break;
				default:
					noAttributes.put(p.getName(), p);
			}					
		}
	}
	/**
	 * Removes and returns the next highest-ranking person. 
	 */
	public Person next(){
		if(!groupHeads.isEmpty()){
			for(Person p: groupHeads.values())
				return p;
			}
		else if(!projHeads.isEmpty()){
			for(Person p: projHeads.values())
				return p;
			}
		else if(!managers.isEmpty()){
			for(Person p: managers.values())
				return p;
			}
		else if(!secretaries.isEmpty()){
			for(Person p: secretaries.values())
				return p;
			}
		else if(!smokers.isEmpty()){
			for(Person p: smokers.values())
				return p;
			}
		else if(!hackers.isEmpty()){
			for(Person p: hackers.values())
				return p;
			}
		else if(!researchers.isEmpty()){
			for(Person p: researchers.values())
				return p;
			}
		else if(!noAttributes.isEmpty()){
			for(Person p: noAttributes.values())
				return p;
			}
	
		return null;
	}
	
	public boolean isEmpty(){
		if (next()==null)
			return true;
		else
			return false;
	}
	
	public boolean hasNext(){
		return !isEmpty();
	}
}

