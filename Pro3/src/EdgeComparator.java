import java.util.Comparator;

public class EdgeComparator implements Comparator<EdgeVertices> {
	
	@Override
	public int compare(EdgeVertices o1, EdgeVertices o2) {

		if (o1.getDistanceBetweenVertices() > o2.getDistanceBetweenVertices())
			return 1;
		if (o1.getDistanceBetweenVertices() < o2.getDistanceBetweenVertices())
			return -1;
		return 0;
	}
}