package newraceday;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import horse.Horse;
import race.Race;
import race.RaceId;
import race.RaceTime;

public class BasicRace implements Race {

	private RaceTime raceTime;
	private RaceId raceId;
	private Set<Horse> horses;

	public BasicRace(RaceId raceId, RaceTime raceTime) {
		this.raceId = raceId;
		this.raceTime = raceTime;
		this.horses = new HashSet<>();
	}

	@Override
	public RaceId getRaceId() {
		// TODO Auto-generated method stub
		return raceId;
	}

	@Override
	public boolean addHorse(Horse horse) {
		
		return horses.add(horse);
	}

	@Override
	public Collection<Horse> getHorses() {
		return Collections.unmodifiableCollection(horses);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BasicRace [raceTime=" + raceTime + ", raceId=" + raceId
				+ ", horses=" + horses + "]";
	}

}
