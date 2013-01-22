package horse.attribute;

public class HorseName implements HorseAttribute {
	
	String name;
	HorseAttributeEnum haEnum;
	
	HorseName(String name){
		this.name = name;
		haEnum = HorseAttributeEnum.getHorseAttributeEnum(this);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public HorseAttributeEnum getHorseAttributeEnum() {
		// TODO Auto-generated method stub
		return haEnum;
	}

}
