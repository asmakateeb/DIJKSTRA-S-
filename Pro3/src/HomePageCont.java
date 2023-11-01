import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class HomePageCont implements Initializable {
	// define views
	@FXML
	private AnchorPane mapContainer;
	private AnchorPane palestine_map;
	@FXML
	private ChoiceBox<String> srcCB;
	@FXML
	private ChoiceBox<String> desCB;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		setWorldMap();
		fillChoiceBoxes();

	}

	public void saji(MouseEvent e) {
		System.out.println(12);
	}

	private void fillChoiceBoxes() { //fill countries in choiceBoxes
		for (Vertex country : Data.countreies) {
			srcCB.getItems().add(country.getCountry());
			arrCircle.get(0).setFill(Color.GREEN);
			selectedCities[0] = 0;
			desCB.getItems().add(country.getCountry());
			arrCircle.get(1).setFill(Color.RED);
			selectedCities[1] = 1;
		}
		srcCB.getSelectionModel().select(0);
		desCB.getSelectionModel().select(1);
		srcCB.getSelectionModel().selectedIndexProperty()
				.addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {

					int index = srcCB.getSelectionModel().getSelectedIndex();
					if (selectedCities[0] != -1) {
						// set the previous circle white
						arrCircle.get(selectedCities[0]).setFill(Color.BLACK);

						// get the new city
						selectedCities[0] = index;
						// set the new circle green
						arrCircle.get(selectedCities[0]).setFill(Color.GREEN);

					} else {
						selectedCities[0] = index;
						arrCircle.get(selectedCities[0]).setFill(Color.GREEN);

					}
				});
		desCB.getSelectionModel().selectedIndexProperty()
				.addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
					int index = desCB.getSelectionModel().getSelectedIndex();
					if (selectedCities[1] != -1) {
						// set the previous circle white
						arrCircle.get(selectedCities[1]).setFill(Color.BLACK);

						// get the new city
						selectedCities[1] = index;
						// set the new circle green
						arrCircle.get(selectedCities[1]).setFill(Color.RED);

					} else {
						selectedCities[1] = index;
						arrCircle.get(selectedCities[1]).setFill(Color.RED);

					}
				});
	}

//-------------------------------------------------------------------------------------------
	// set map
	LinkedList<Circle> arrCircle;
	int[] selectedCities;

	public void setWorldMap() {
		try {
			// load map
			palestine_map = FXMLLoader.load(getClass().getResource("WorldMap.fxml"));
			mapContainer.getChildren().setAll(palestine_map);
			// display cities on the screen
			selectedCities = new int[2];
			selectedCities[0] = -1;
			selectedCities[1] = -1;
			arrCircle = new LinkedList<>();
			// read data from files
			Data readData = new Data();
			// get the list of cities
			LinkedList<Vertex> cities = readData.countreies;
			// set node on screen
			for (Vertex v : cities) {
				// create circle
				Circle circle = new Circle(v.getX(), v.getY(), 4);
				// set color of circle
				circle.setFill(Color.BLACK);
				// set action for circle
				circle.setOnMouseClicked(this::setCircleAction);
			

				
				Tooltip.install(circle, new Tooltip(v.getCountry()));

				// add to map
				palestine_map.getChildren().add(circle);
				// add circle to map
				arrCircle.add(circle);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void setCircleAction(MouseEvent e) {

		for (int x = 0; x < arrCircle.size(); x++) {
			Circle c = arrCircle.get(x);
			if (e.getSource() == c) {
				// set source
				if (e.getButton() == MouseButton.SECONDARY) {
					if (selectedCities[0] != -1) {
						// set the previous circle white
						arrCircle.get(selectedCities[0]).setFill(Color.BLACK);

						// get the new city
						selectedCities[0] = x;
						// set the new circle green
						arrCircle.get(selectedCities[0]).setFill(Color.GREEN);

						srcCB.getSelectionModel().select(x);
					} else {
						selectedCities[0] = x;
						arrCircle.get(selectedCities[0]).setFill(Color.GREEN);
						srcCB.getSelectionModel().select(x);
					}

				} // set destination
				else if (e.getButton() == MouseButton.PRIMARY) {
					if (selectedCities[1] != -1) {
						// set the previous circle white
						arrCircle.get(selectedCities[1]).setFill(Color.BLACK);

						// get the new city
						selectedCities[1] = x;
						// set the new circle green
						arrCircle.get(selectedCities[1]).setFill(Color.RED);
						desCB.getSelectionModel().select(x);
					} else {
						selectedCities[1] = x;
						arrCircle.get(selectedCities[1]).setFill(Color.RED);
						desCB.getSelectionModel().select(x);

					}
				}
			}
		}
	}

	// -------------------------------------------------------------------------------------------
	// find shortest path
	@FXML
	private TextField distanceTF;
	@FXML
	private TextArea path;

	public void runButton(ActionEvent e) {
		if (selectedCities[0] != -1 && selectedCities[1] != -1) {
			// find shortest path
			Graph graph = new Graph(arrCircle.size());
			Vertex src = Data.countreies.get(selectedCities[0]);
			Vertex dest = Data.countreies.get(selectedCities[1]);
			double distance = graph.findShortestPath(src, dest); // go to graph 
			distanceTF.setText("Distance : " + String.valueOf(roundNum(distance)) + " km");//distance

			// display lines
			showRoute(graph.getPreviousVertex(), graph.findIndex(dest.getCountry()));

		} else {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Please, you must SELECT 2 cities!!");
			alert.show();
		}
	}

	public void showRoute(int prevVertex[], int indexOfDestination) {
		// get previous vertex
		LinkedList<Vertex> cities = Data.countreies;
		Vertex vertex = null;
		LinkedList<Vertex> path = new LinkedList<>();
		int index = indexOfDestination;
		while (index != -1) {
			vertex = cities.get(index);
			path.add(vertex);
			index = prevVertex[index];
		}
		// display line
		displayLines(path);
		print(path);
	}

	public void print(LinkedList<Vertex> path) {  //path
		String route = "";
		for (int x = 1; x < path.size(); x++) {

			Vertex src = path.get(x - 1);
			route += src.getCountry() + "-";
			Vertex dest = path.get(x);
			route += dest.getCountry() + "->";

			route += String.valueOf(findDistance(src, dest)) + "\n";

		}
		this.path.setText(route);
	}

	public double findDistance(Vertex src, Vertex dest) {
		double distance = 0;
		int size = src.getNeighbourscountry().size();
		for (int x = 0; x < size; x++) {
			EdgeVertices edge = (EdgeVertices) src.getNeighbourscountry().get(x);
			if (edge.getTargetNode().getCountry().equals(dest.getCountry())) {
				distance = edge.getDistanceBetweenVertices();
				break;
			}
		}
		return roundNum(distance);

	}

	public double roundNum(double num) {
		num = num * 100;
		num = (int) num;
		num /= 100;
		return num;
	}

	private void displayLines(LinkedList<Vertex> path) {
		// display lines
		for (int x = 1; x < path.size(); x++) {
			Vertex start = path.get(x - 1);
			Vertex end = path.get(x);

			Line line = new Line(start.getX(), start.getY(), end.getX(), end.getY());
			//line.setStroke(Color.RED);
			palestine_map.getChildren().add(line);
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

	public void clearButton(ActionEvent e) {
		// re-set the map
		setWorldMap();
		// re-set textfield
		distanceTF.setText("Distance : 0 km");

	}

}
