package horse;

public class BasicHorse implements Horse {

	private HorseId horseId;
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((horseId == null) ? 0 : horseId.hashCode());
		result = prime * result
				+ ((horseName == null) ? 0 : horseName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BasicHorse)) {
			return false;
		}
		BasicHorse other = (BasicHorse) obj;
		if (horseId == null) {
			if (other.horseId != null) {
				return false;
			}
		} else if (!horseId.equals(other.horseId)) {
			return false;
		}
		if (horseName == null) {
			if (other.horseName != null) {
				return false;
			}
		} else if (!horseName.equals(other.horseName)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BasicHorse [horseId=" + horseId + ", horseName=" + horseName
				+ "]";
	}

	private HorseName horseName;

	public BasicHorse(HorseId horseId, HorseName horseName) {
		this.horseId = horseId;
		this.horseName = horseName;
	}

}
