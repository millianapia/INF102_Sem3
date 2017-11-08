package connectingToilets;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.StdOut;

import graphics.Svg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by knutandersstokke on 16 16.10.2017.
 */
public class ConnectingToilets {

    private final static String TOILET_FILE = "connectingToilets/bergen_toilet_map.txt";

    public static void main(String[] args) {
        Set<Toilet> toilets = readToiletsFromFile(TOILET_FILE);
        ToiletMap mapOfToilets = new ToiletMap(toilets, connectToilets(toilets));
        Svg.runSVG(Svg.buildSvgFromScienceEmployees(mapOfToilets));
    }

    public static Set<Toilet> readToiletsFromFile(String toiletFile) {
        List<String> lines = readLines(TOILET_FILE);
        if (lines == null) {
            System.out.print("An error ocurred trying to read " + TOILET_FILE + ". Check that the file exist.");
        }
        return lines.stream().map(ConnectingToilets::lineToToilet).collect(Collectors.toSet());

    }

    public static Set<Edge> connectToilets(Set<Toilet> toilets) {
        Set<Edge> MST = new HashSet<>();

        Iterator<Toilet> iterator = toilets.iterator();
        ArrayList<Toilet> toiletList = new ArrayList<>();
        while (iterator.hasNext()) {
            Toilet toiletA = iterator.next();
            Toilet toiletB = closestToilet(toiletA, toilets);
            double distance = distanceTo(toiletA.getX(), toiletA.getY(), toiletB.getX(), toiletB.getY());
            Edge connection = new Edge(toiletA, toiletB, distance);
            MST.add(connection);
        }
        return MST;
    }

    public static Toilet closestToilet(Toilet toiletA, Set<Toilet> toilets) {
        Iterator<Toilet> iterator = toilets.iterator();
        Toilet smallestToilet = null;
        double otherDistance = Integer.MAX_VALUE;
        for (int i = 0; i < toilets.size(); i++) {
            Toilet toiletB = iterator.next();
            double distance = distanceTo(toiletA.getX(), toiletA.getY(), toiletB.getX(), toiletB.getY());
            if (distance != 0.0) {
                if (distance < otherDistance) {
                    smallestToilet = toiletB;
                    otherDistance = distance;

                }
            }


        }
        return smallestToilet;
    }

    public static double distanceTo(double x, double y, double otherX, double otherY) {
        return Math.sqrt(Math.pow(x - otherX, 2) + Math.pow(y - otherY, 2));

    }

    private static Toilet lineToToilet(String line) {
        String[] fields = line.split(";");
        String name = fields[0];
        double x = Double.parseDouble(fields[1]);
        double y = Double.parseDouble(fields[2]);
        return new Toilet(name, x, y);
    }

    private static List<String> readLines(String fileName) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream resource = classloader.getResourceAsStream(fileName)) {
            if (resource == null) {
                System.out.println("File is missing!");
                return null;
            }
            return new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines().collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}