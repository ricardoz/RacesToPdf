/**
 * 
 */
package race;

/**
 * @author Miriam Martin
 *
 */
public class BasicRaceId implements RaceId {

	private int raceId;

	/* (non-Javadoc)
	 * @see race.RaceId#getRaceId()
	 */
	@Override
	public int getRaceId() {
		// TODO Auto-generated method stub
		return raceId;
	}
	
	public BasicRaceId(String idAsString){
		this.raceId = Integer.parseInt(idAsString);
	}
	
	public BasicRaceId(int id){
		this.raceId = id;
	}
	
	@Override
	public boolean equals(Object other){
		if (! (other instanceof RaceId)) return false;
		
		RaceId otherRaceId = (RaceId) other;
		
		return raceId == otherRaceId.getRaceId();
	}
	
	@Override
	public int hashCode(){		
		return raceId;
	}

}
