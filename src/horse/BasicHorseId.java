package horse;

public class BasicHorseId implements HorseId {

	private int id;

	public BasicHorseId(String horseIdAsString) {
		id = Integer.parseInt(horseIdAsString);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		if (!(obj instanceof BasicHorseId)) {
			return false;
		}
		BasicHorseId other = (BasicHorseId) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BasicHorseId [id=" + id + "]";
	}

}
