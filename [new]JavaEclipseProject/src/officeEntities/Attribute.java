package officeEntities;
/**
 * <p>This enum represents all valid attributes that a person can have in the office allocation problem. 
 * Elements of the enum are listed in increasing order of the importance of the attribute when it comes to allocating offices. 
 * For example, the <code>GROUP_HEAD</code> attribute has a higher ordinal value than the <code>HACKER</code> attribute because 
 * it is belived that assigning group-heads offices before assigning hackers offices is belived to generate better solutions
 * faster for most problem instances. 
 * 
 * <p>More formally, for all persons <code>p1</code> and <code>p2</code>, <code>p1.getRankingAttribute().getOrdinal() > p2.getRankingAttribute().getOrdinal()</code>
 * if <code>p1</code> should be assigned an office earlier in the search process than <code>p2</code>. 
 */
public enum Attribute {
	/**Included to represent the ranking attribute of a person who has no <i>real</i> attributes*/
	PERSON("person"),
	RESEARCHER("researcher"),
	HACKER("hacker"),
	SMOKER("smoker"),
	SECRETARY("secretary"),
	MANAGER("manager"),
	PROJECT_HEAD("project-head"),
	GROUP_HEAD("group-head");
	
	private String displayName;
	Attribute(String displayName){this.displayName = displayName;}
	@Override public String toString() {return displayName;}
	

}
