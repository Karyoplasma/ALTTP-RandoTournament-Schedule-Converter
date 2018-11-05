package core;

public enum ShortMonth {
	Jan("Jan", 1),
	Feb("Feb", 2),
	Mar("Mar", 3),
	Apr("Apr", 4),
	May("May", 5),
	Jun("Jun", 6),
	Jul("Jul", 7),
	Aug("Aug", 8),
	Sep("Sep", 9),
	Oct("Oct", 10),
	Nov("Nov", 11),
	Dec("Dec", 12);
	
	@SuppressWarnings("unused")
	private String name;
	private int value;
	
	private ShortMonth(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
	
}
