/**
 * 
 */
package race;

import java.util.Collection;

import horse.Horse;

/**
 * @author Miriam Martin
 *
 */
public interface Race {

	RaceId getRaceId();

	boolean addHorse(Horse horse);

	Collection<Horse> getHorses();
	
	@Override 
	public boolean equals(Object other);
	
	@Override
	public int hashCode();

}
