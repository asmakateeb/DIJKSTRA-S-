
import java.util.LinkedList;
import java.util.PriorityQueue;

class Graph {
	private boolean[] visited;
	private double[] distance;
	private int[] previousVertex;
	Graph(int numOfCountries) {
		visited = new boolean[numOfCountries];
		distance = new double[numOfCountries];
		previousVertex = new int[numOfCountries];
		for (int i = 0; i < numOfCountries; i++) {
			distance[i] = Double.MAX_VALUE;
			visited[i] = false;
		}
	}

	public double findShortestPath(Vertex from, Vertex to) {
		new Graph(distance.length);
		double dist = 0.0;
		if (from == to) {
			return dist;
		} else {

			EdgeComparator compare = new EdgeComparator();//sort
			PriorityQueue<EdgeVertices> minHeap = new PriorityQueue<>(compare);
			distance[findIndex(from.getCountry())] = 0;
			previousVertex[findIndex(from.getCountry())] = -1;
			minHeap.add(new EdgeVertices(from, 0.0)); //from , distance
			while (!minHeap.isEmpty()) {
				Vertex v = minHeap.poll().getTargetNode();
				int index = findIndex(v.getCountry());
				if (to.getCountry().equals(v.getCountry())) {
					dist = distance[findIndex(to.getCountry())];
					return dist;
				}
				if (visited[index])
					continue;
				visited[index] = true;
				LinkedList<EdgeVertices> adjacent = Data.countreies.get(index).getNeighbourscountry();
				computeRelaxtion(adjacent, index, minHeap);
			}

			dist = distance[findIndex(to.getCountry())];

		}

		return dist;
	}

	public void computeRelaxtion(LinkedList<EdgeVertices> adjacent, int index, PriorityQueue<EdgeVertices> minHeap) {
		for (EdgeVertices adj : adjacent) {
			double dist = adj.getDistanceBetweenVertices();
			Vertex vert = adj.getTargetNode();
			if (visited[findIndex(vert.getCountry())] == false) {
				if (distance[index] + dist < distance[findIndex(vert.getCountry())]) {
					distance[findIndex(vert.getCountry())] = distance[index] + dist;
					adj.setDistanceBetweenVertices(distance[index] + dist);
					previousVertex[findIndex(vert.getCountry())] = index;
					minHeap.add(adj);
				}
			}
		}
	}

	public int findIndex(String country) {
		for (int x = 0; x < Data.countreies.size(); x++) {
			Vertex c = Data.countreies.get(x);
			if (c.getCountry().equals(country))
				return x;
		}
		return -1;
	}

	public int[] getPreviousVertex() {
		return previousVertex;
	}

}
