package officeEntities;

public enum Attribute {
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
