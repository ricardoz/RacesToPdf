package horse.attribute;

public enum HorseAttributeEnum {
	NAME;

	public static HorseAttributeEnum getHorseAttributeEnum(HorseAttribute horseAttribute) {
		if (horseAttribute instanceof HorseName)	return NAME;
		return null;
		
		
	}
}
