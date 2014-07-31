package util.comparer;

import java.util.ArrayList;
import java.util.Map;

import model.Document;
import model.Vector;

public class VectorAngleComparer {
	public static double getAngle(Document source, Document toCompare) {
		double result = 0;
		ArrayList<Vector> mappedCoordinatedVectors = createMappedVectors(
				source, toCompare);
		Vector sourceVector = mappedCoordinatedVectors.get(0);
		Vector toCompareVector = mappedCoordinatedVectors.get(1);

		double top = multiplyVectors(sourceVector, toCompareVector);
		double bottom = bottomOperation(sourceVector, toCompareVector);

		result = top/bottom;
		return result;
	}

	private static double bottomOperation(Vector vector1,
			Vector vector2) {
		double resultVector1 = 0;
		for (Integer coordinate : vector1.coordinates) {
			resultVector1 += Math.pow(coordinate, 2);
		}
		
		double resultVector2 = 0;
		for (Integer coordinate : vector2.coordinates) {
			resultVector2 += Math.pow(coordinate, 2);
		}
			
		double result = 0;
		
		result = (Math.sqrt(resultVector1) * Math.sqrt(resultVector2));
		// case result 0 not allowed --> return 1
		result = result != 0 ? result : 1;
		return result;
	}

	private static double multiplyVectors(Vector vector1, Vector vector2) {
		double result = 0;
		for (int i = 0; i < vector1.coordinates.size(); i++) {
			result += (vector1.coordinates.get(i) * vector2.coordinates.get(i));
		}
		// case result 0 not allowed --> return 1
		return result;
	}

	private static ArrayList<Vector> createMappedVectors(Document source,
			Document toCompare) {
		ArrayList<Vector> vectors = new ArrayList<Vector>();

		ArrayList<Integer> sourceCoordinates = new ArrayList<Integer>();
		ArrayList<Integer> toCompareCoordinates = new ArrayList<Integer>();

		// Add source coordinates
		for (Map.Entry<String, Integer> entry : source.getParsed().entrySet()) {
			sourceCoordinates.add(entry.getValue());
			if (toCompare.getParsed().containsKey(entry.getKey()))
				toCompareCoordinates.add(toCompare.getParsed().get(
						entry.getKey()));
			else
				toCompareCoordinates.add(0);
		}
		// Add missing toCompare coordinates
		for (Map.Entry<String, Integer> entry : toCompare.getParsed()
				.entrySet()) {
			// key not already added
			if (!source.getParsed().containsKey(entry.getKey())) {
				toCompareCoordinates.add(entry.getValue());
				sourceCoordinates.add(0);
			}
		}

		Vector sourceVector = new Vector(sourceCoordinates);
		Vector toCompareVector = new Vector(toCompareCoordinates);
		vectors.add(sourceVector);
		vectors.add(toCompareVector);
		return vectors;
	}

}
