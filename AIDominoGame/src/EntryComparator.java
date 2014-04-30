import java.util.AbstractMap.SimpleEntry;
import java.util.Comparator;


public class EntryComparator implements Comparator<SimpleEntry<Double,double[]>>{

	@Override
	public int compare(SimpleEntry<Double,double[]> arg0, SimpleEntry<Double,double[]> arg1) {
		// TODO Auto-generated method stub
		if((Double)arg0.getKey()-(Double)arg1.getKey() >= 0){
			return 1;
		}else
			return -1;
	}
}
