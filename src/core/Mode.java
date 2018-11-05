package core;

public enum Mode {

	DefeatGanon("Defeat Ganon"),
	AllDungeons("All Dungeons"),
	Standard("Standard"),
	Open("Open"),
	Inverted("Inverted"),
	UncleAssured("Uncle-assured"),
	Randomized("Randomized"),
	Swordless("Swordless"),
	Normal("Normal"),
	Hard("Hard"),
	Expert("Expert"),
	Keysanity("Keysanity"),
	Retro("Retro"),
	Enemizer("Enemizer"),
	None("None"),
	AllOverride("All");
	
	private String mode;

	private Mode(String mode) {
		this.mode = mode;
	}
	
	public String getMode() {
		return mode;
	}
	
	
	@Override
	public String toString() {
		return mode;
	}
}
