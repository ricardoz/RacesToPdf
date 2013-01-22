package horse;

import horse.attribute.HorseAttribute;
import horse.attribute.HorseAttributeEnum;

public class HorseFactory {
	
	HorseAttributeMap<HorseAttributeEnum, HorseAttribute> haSet;
	
	public Horse getHorse(HorseAttributeMap<HorseAttributeEnum, HorseAttribute> horseAttributeMap){
		this.haSet = horseAttributeMap;
		
		Horse horse = new HorseImpl();
		
		//horse.setName();
		
		return null;
		
	}
}
