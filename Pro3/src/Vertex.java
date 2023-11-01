import java.util.LinkedList;

public class Vertex {
	private String country;
	private int x;
	private int y;
	private LinkedList<EdgeVertices> neighbourscountry;

	public Vertex(String country, int x, int y) {
		super();
		this.country = country;
		this.x = x;
		this.y = y;
		neighbourscountry = new LinkedList<>();
	}

	public String getCountry() {
		return country;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public LinkedList<EdgeVertices> getNeighbourscountry() {
		return neighbourscountry;
	}

	public void addneighbourscountry(Vertex ncountry, double dist) {
		EdgeVertices edge = new EdgeVertices(ncountry, dist);
		neighbourscountry.add(edge);
	}
}
