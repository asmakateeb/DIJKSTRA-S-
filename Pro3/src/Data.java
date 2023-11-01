import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

public class Data {
	// read countreies from file
	static LinkedList<Vertex> countreies;
	
	// read distance from file
	Data() {
		countreies = new LinkedList<>();
		readcountreies();
		readDistance();
	}

	public void readcountreies() {
		try {
			File file = new File("countries.txt");
			Scanner reader = new Scanner(file);
			while (reader.hasNextLine()) {
				// read line
				String line = reader.nextLine();
				// split line
				String splitLine[] = line.split("#");
				String country = splitLine[0];
				int x = Integer.parseInt(splitLine[1]);
				int y = Integer.parseInt(splitLine[2]);
				countreies.add(new Vertex(country, x, y+25));

			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void readDistance() {
		try {
			File file = new File("distance.txt");
			Scanner reader = new Scanner(file);
			while (reader.hasNextLine()) {
				// read line
				String line = reader.nextLine();
				// split line
				String splitLine[] = line.split("#");
				Vertex country1 = findVertex(splitLine[0].trim());
				Vertex country2 = findVertex(splitLine[1].trim());
				double dist = Double.parseDouble(splitLine[2].trim());
				
				// add edge for each country
				country1.addneighbourscountry(country2, dist);
				country2.addneighbourscountry(country1, dist);
			}

		} catch (Exception e) {
			System.out.println(e+"h");
		}
	}

	public Vertex findVertex(String country) {
		for (int x = 0; x < countreies.size(); x++) {
			Vertex c = countreies.get(x);
			if (c.getCountry().equals(country))
				return c;
		}
		return null;
	}
}
