package cpsc433;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import officeEntities.Attribute;
import officeEntities.Person;
/**
 * This class is essentially a custom data structure which allows the the group of office 
 * workers to be iterated over in an order that is convenient for the Or-tree search algorithm.
 * 
 * People are organized by the following "ranking" from highest to lowest. The rank of
 * a person represents the importance of allocating that person an office early in the
 * search process. The higher the rank, the earlier they will be assigned an office. Since individuals
 * may have many attributes, they are categorized according to the most important attribute they possess. 
 * 
 *	8)	group-head
 *	7)	project-head
 *	6)	manager
 *	5)	secretary
 *	4)	smoker
 *	3)	hacker
 *	2)	researcher
 *	1)	person <--- a person who has no attributes
 * 
 */
public class SortedPeople implements Iterator<Person>{
	
	
	private ArrayList<Person> groupHeads= new ArrayList<>(); //people who's highest rank is group head
	private ArrayList<Person> projHeads= new ArrayList<>(); //people who's highest rank is group head
	private ArrayList<Person> managers= new ArrayList<>(); //people who's highest rank is group head
	private ArrayList<Person> secretaries= new ArrayList<>(); //people who's highest rank is group head
	private ArrayList<Person> hackers= new ArrayList<>(); //people who's highest rank is group head
	private ArrayList<Person> researchers= new ArrayList<>(); //people who's highest rank is group head
	private ArrayList<Person> smokers= new ArrayList<>(); //people who's highest rank is group head
	private ArrayList<Person> noAttributes= new ArrayList<>(); //people who's highest rank is group head
	/**
	 * Constructor for class SortedPeopel. Creates an empty SortedPeople object
	 */
	public SortedPeople(){
		
	}
	/**
	 * Constructor for class SortedPeople. Sorts given HashMap of people by placing them in the
	 * appropriate category.
	 * @param personHash
	 */
	public SortedPeople(HashMap<String,Person> personHash) {
		for(Person p : personHash.values())
			add(p);
	}
	
	public ArrayList<Person> getSec(){
		return secretaries;
	}
	
	/**
	 * Adds a person to the SortedPeople data structure. 
	 * @param newlyAssinged
	 */
	public void add(Person p) {
		Attribute rankingAttribute = p.getRankingAttribute();
		switch (rankingAttribute){
		case GROUP_HEAD:
			groupHeads.add(p);
			break;
		case PROJECT_HEAD:
			projHeads.add(p);
			break;
		case MANAGER:
			managers.add(p);
			break;
		case SECRETARY:
			secretaries.add(p);
			break;
		case SMOKER:
			smokers.add(p);
			break;
		case HACKER:
			hackers.add(p);
			break;
		case RESEARCHER:
			researchers.add(p);
			break;
		default:
			noAttributes.add(p);
	}					
		
	}
	/**
	 * Removes and returns the next highest-ranking person. 
	 */
	public Person next(){
		if(!groupHeads.isEmpty()) return groupHeads.remove(0);
		else if(!projHeads.isEmpty()) return projHeads.remove(0);
		else if(!managers.isEmpty()) return managers.remove(0);
		else if(!secretaries.isEmpty())	return secretaries.remove(0);
		else if(!smokers.isEmpty()) return smokers.remove(0);
		else if(!hackers.isEmpty()) return hackers.remove(0);
		else if(!researchers.isEmpty()) return researchers.remove(0);
		else if(!noAttributes.isEmpty()) return noAttributes.remove(0);
		else return null;
	}
			
	
	/**
	 * Checks if all the lists are empty.
	 * @return true if all the lists are empty
	 */
	public boolean isEmpty(){
		if (this.size() == 0){ return true; }	
		else { return false; }
	}	

	/**
	 * Checks if there are people in the data structure.
	 * @return true if there is at least one person in one of the lists
	 */
	public boolean hasNext(){
		return !isEmpty();
	}
	public Person[] toArray() {
		Person[] personArr = new Person[this.size()];
		for(int i = 0; this.hasNext(); i++)
			personArr[i] = this.next();
		return personArr;
		}
	
	
	public int size(){
		return groupHeads.size() + projHeads.size() + managers.size() + secretaries.size()
				+ hackers.size() + researchers.size() + noAttributes.size();
	}
}

