import java.util.HashMap;

public class MyDic {
	HashMap<String, String> map = new HashMap<String, String>();
	public MyDic() { }
	
	public String get(String eng) {
		return map.get(eng);
	}
	
	public void put(String eng, String kor) {
		map.put(eng, kor);
	}
}
	