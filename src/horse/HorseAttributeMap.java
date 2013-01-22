package horse;

import horse.attribute.HorseAttribute;

import java.util.Map;

public interface HorseAttributeMap<K,V> extends Map<K,V> {
	public void addHorseAttribute(HorseAttribute ha);
}
