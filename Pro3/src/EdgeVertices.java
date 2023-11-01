

public class EdgeVertices  {
	private Vertex targetNode;
	private double distanceBetweenVertices;

	public EdgeVertices(Vertex targetNode, double distanceBetweenVertices) {
		super();
		this.targetNode = targetNode;
		this.distanceBetweenVertices = distanceBetweenVertices;
	}

	public Vertex getTargetNode() {
		return targetNode;
	}

	public void setTargetNode(Vertex targetNode) {
		this.targetNode = targetNode;
	}

	public double getDistanceBetweenVertices() {
		return distanceBetweenVertices;
	}

	public void setDistanceBetweenVertices(double distanceBetweenVertices) {
		this.distanceBetweenVertices = distanceBetweenVertices;
	}



}
