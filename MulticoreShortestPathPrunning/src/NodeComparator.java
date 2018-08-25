import java.io.Serializable;
import java.util.Comparator;
public class NodeComparator implements Comparator<Node>,Serializable{

	@Override
	public int compare(Node n1, Node n2) {
		// TODO Auto-generated method stub
		if(n1.getM_value() < n2.getM_value()){
			return -1;
		}
		if(n1.getM_value() > n2.getM_value()){
			return 1;
		}
		return 0;
	}

}
