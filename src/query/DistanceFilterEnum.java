package query;

public enum DistanceFilterEnum {
	ALL, LESS_THAN_OR_EQUAL_TO, MORE_THAN_OR_EQUAL_TO, EQUAL_TO, BETWEEN;


	@Override
	public String toString(){
		switch (this){
			case ALL: return "all";
			case LESS_THAN_OR_EQUAL_TO: return "<=";
			case MORE_THAN_OR_EQUAL_TO: return ">=";
			case EQUAL_TO: return "==";
			case BETWEEN: return "between";
		}
		return "error";
	}
}
