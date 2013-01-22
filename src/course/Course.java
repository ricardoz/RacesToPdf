package course;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class Course {

	private static Map<String, Integer> courseToIdMap = createMap();;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Course c = new Course("Yarmouth");
		
		System.out.println(c);
	}

	final private String name;
	final private int id;
	private boolean allWeather;
	
	public Course(String name, int id){
		this.name = name;
		this.id = id;
		this.allWeather = name.indexOf("(A") > 0;
	}
	
	public Course(String name){
		this.name = name;
		courseToIdMap = createMap();
		this.id = courseToIdMap.get(name);
		this.allWeather = name.indexOf("(A") > 0;
	}
	
	public Course(int id){
		
		this.id=id;
		
		
		
		BiMap<Integer,String> bimap = HashBiMap.create(courseToIdMap).inverse();
		this.name= bimap.get(id);
		
		
	}
	
	
	
	public String toString(){
		return "Course: " + name + " Id: " +id;
	}

	public Integer getId() {
		return Integer.valueOf(id);
	}
	
	public static Set<String> getCourseNames(){
		return courseToIdMap.keySet();
	}
	
	private static Map<String, Integer> createMap(){
		Map<String,Integer> map = new HashMap<>();
		String courseId = "32:Aintree, 2:Ascot, 3:Ayr, 4:Bangor-On-Dee,5:Bath,6:Beverley,7:Brighton,8:Carlisle,9:Cartmel,10:Catterick,11:Cheltenham,12:Chepstow,13:Chester,15:Doncaster,17:Epsom,14:Exeter,18:Fakenham,1212:Ffos Las,19:Folkestone,20:Fontwell,21:Goodwood,1083:Great Leighs (A.W),22:Hamilton,23:Haydock,24:Hereford,25:Hexham,26:Huntingdon,27:Kelso,28:Kempton (A.W),1079:Kempton (A.W),30:Leicester,31:Lingfield (A.W),393:Lingfield (A.W),34:Ludlow,35:Market Rasen,16:Musselburgh,36:Newbury,37:Newcastle,38:Newmarket,174:Newmarket (July),39:Newton Abbot,40:Nottingham,41:Perth,44:Plumpton,46:Pontefract,47:Redcar,49:Ripon,52:Salisbury,54:Sandown,57:Sedgefield,61:Lingfield (A.W),394:Southwell (A.W),67:Stratford,73:Taunton,80:Thirsk,83:Towcester,84:Uttoxeter,85:Warwick,87:Wetherby,90:Wincanton,93:Windsor,95:Wolverhampton (A.W),513:Wolverhampton (A.W),101:Worcester,104:Yarmouth,107:York";
		String[] array = courseId.split(",");
		
		for (int i = 0; i < array.length; i++){
			//System.out.println("trying " + i + " g " + array[i]);
			String[] split = array[i].trim().split(":");
			//System.out.println("split 0 " + split[0] + " split 1 " + split[1]);
			
			map.put(split[1], Integer.parseInt(split[0]));
			
		}
		
		return map;
	}

	public boolean isAllWeather() {
		// TODO Auto-generated method stub
		return allWeather;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (allWeather ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (!(obj instanceof Course)) {
			return false;
		}
		Course other = (Course) obj;
		if (allWeather != other.allWeather) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
	
	

}
