package horse;

public class BasicHorseName implements HorseName {

	private String horseName;

	public BasicHorseName(String horseName) {
		this.horseName = horseName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		if (!(obj instanceof BasicHorseName)) {
			return false;
		}
		BasicHorseName other = (BasicHorseName) obj;
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
		return "BasicHorseName [horseName=" + horseName + "]";
	}
	
	

}
