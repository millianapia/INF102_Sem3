package connectingToilets;

import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.LazyPrimMST;

import graphics.Svg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: xun007
 * @Team: rni006
 */
public class ConnectingToilets {

    private final static String TOILET_FILE = "connectingToilets/random_toilet_map.txt";

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

    /* Connecting the toilets, with the use of hashMaps to convert
    * from Toilet to Int so we can use the int values in the
    * algs4 Edges(algs4 need two ints)
     */
    public static Set<Edge> connectToilets(Set<Toilet> toilets) {
        Set<Edge> MST = new HashSet<>();
        List<Toilet> toiletList = new ArrayList<>();
        Iterator<Toilet> iterator = toilets.iterator();
        toiletList.addAll(toilets);

        HashMap<Toilet, Integer> toiletToInt = new HashMap<>();
        HashMap<Integer, Toilet> intToToilet = new HashMap<>();

        int counter = 0;
        while (iterator.hasNext()) {
            Toilet temp = iterator.next();

            toiletToInt.put(temp, counter);
            intToToilet.put(counter, temp);

            counter++;
        }

        EdgeWeightedGraph G = new EdgeWeightedGraph(toilets.size(), 0);

        /* Connects every toilet to eachother, then adds algs4
        edges to each of them, then put the edges into a graph
        * */
        for (int i = 0; i < intToToilet.size(); i++) {
            Toilet A = intToToilet.get(i);
            for (int j = 1; j < intToToilet.size(); j++) {
                Toilet B = intToToilet.get(j);
                double distance = distanceTo(A.getX(), A.getY(), B.getX(), B.getY());
                int aInt = toiletToInt.get(A);
                int bInt = toiletToInt.get(B);
                edu.princeton.cs.algs4.Edge tempEdge = new edu.princeton.cs.algs4.Edge(aInt, bInt, distance);
                G.addEdge(tempEdge);
            }
        }

            /* Adding the graph into the lazyPrimMST, which
            / calculates the minimum spanning tree of the 
            graph i made earlier
             */
        LazyPrimMST mst = new LazyPrimMST(G);


        // Converting from edges in the lazyPrimMST to toilet Edges
        for (edu.princeton.cs.algs4.Edge e : mst.edges()) {
            int aInt = e.either();
            double weight = e.weight();
            int bInt = e.other(e.either());
            Toilet A = intToToilet.get(aInt);
            Toilet B = intToToilet.get(bInt);
            Edge connection = new Edge(A, B, weight);
            MST.add(connection);
        }


        return MST;
    }


    // returns the distance from one object to another with coordinates
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
