package horse.attribute;

import horse.HorseAttributeMap;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class HorseAttributeConcurrentHashMap<HorseAttributeEnum, HorseAttribute> extends ConcurrentHashMap<HorseAttributeEnum, HorseAttribute>
		implements HorseAttributeMap<HorseAttributeEnum, HorseAttribute> {

	

	@Override
	public void addHorseAttribute(horse.attribute.HorseAttribute ha) {
		//put(ha.getHorseAttributeEnum(), ha);
		
	}

}
