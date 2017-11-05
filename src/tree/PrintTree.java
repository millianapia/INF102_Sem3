package tree;

import edu.princeton.cs.algs4.*;

import java.util.*;

/**
 * @Author: xun007
 * @Team: rni006
 */
public class PrintTree {

    public static void main(String[] args) {
        StdOut.println("Enter file to be formated");
        Scanner inn = new Scanner(System.in);
        String inputFile = inn.nextLine();
        String inputContent = new In(inputFile).readAll();
        StdOut.println(formatStringToTree(inputContent));
    }
    //formats string input to tree
    public static String formatStringToTree(String inputContent) {
        StringBuilder outputContent = new StringBuilder();
        String[] splitted = inputContent.split("\n");
        int amount = Integer.parseInt(splitted[0]);

        Node root =  new Node(null);
        root.setId("'-root");
        String[] files = Arrays.copyOfRange(splitted, 2, splitted.length);
        String[] splittedItems = splitted[1].split("\\s");
        for(int i = 0; i < amount ; i++){


            for(String line : files){
                if(line.charAt(0) == Integer.toString(i).charAt(0)){
                    String[] objectsInFile = line.split(":");
                    String parentId = objectsInFile[0].trim();
                    objectsInFile = objectsInFile[1].trim().split("\\s");
                    for(String file : objectsInFile){
                        Integer childrenId = Integer.parseInt(file);
                        if(i == 0){
                            addChild(root, "'-" + splittedItems[childrenId]);
                        } else {
                            addChild(findParent(root, splittedItems[Integer.parseInt(parentId)], new Node(null)), "'-" + splittedItems[childrenId]);
                        }
                    }
                }

            }
        }

        printTree(root, " ");
        return outputContent.toString();
    }

        // Finds parent of node found from root
    private static Node findParent(Node root, String id, Node found){
        for(Node child: root.getChildren()){
            if(child.getId().equals("'-"+id)){
                return child;
            } else {
                found = findParent(child, id, found);
            }
        }

        return found;
    }
    // Add a child to parent node
    private static Node addChild(Node parent, String id) {
        Node node = new Node(parent);
        node.setId(id);
        parent.getChildren().add(node);
        return node;
    }

    //Method that prints tree 
    private static void printTree(Node node, String appender) {
        System.out.println(appender + node.getId());
        for (Node each : node.getChildren()) {
            printTree(each, appender + appender);
        }

    }
}
// @Class node
class Node {
    private String id;
    private final List<Node> children = new ArrayList<>();
    private final Node parent;

    public Node(Node parent) {
        this.parent = parent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Node> getChildren() {
        return children;
    }

}