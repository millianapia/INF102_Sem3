package tree;

import edu.princeton.cs.algs4.*;

import java.util.*;

/**
 * @Author: xun007
 */
public class PrintTree {

    public static void main(String[] args) {
        StdOut.println("Enter file to be formated");
        Scanner inn = new Scanner(System.in);
        String inputFile = inn.nextLine();
        String inputContent = new In(inputFile).readAll();
        StdOut.println(formatStringToTree(inputContent));
    }

    public static String formatStringToTree(String inputContent) {
        //TODO: put unnecessary splitting in other methods
        StringBuilder outputContent = new StringBuilder();
        String[] splittedFileInput = inputContent.split("\n");
        int itemsInTotal = Integer.parseInt(splittedFileInput[0]);
        String[] splittedItems = splittedFileInput[1].split("\\s");
        String[] itemList = new String[splittedFileInput.length - 2];

        if (itemsInTotal < 2) {
            StdOut.println("'-" + splittedItems[0] + "  ");
            System.exit(0);
        }

        int k = 0;
        for (int i = 2; i < itemsInTotal - 1; i++) {
            itemList[k] = splittedFileInput[i].substring(4);
            k++;
        }

        int folderAmount = itemList.length - 1; //Because we do not count root as folder

        Node treeRootNode = new Node(null);
        treeRootNode.setId("'-" + splittedItems[0]);
        Node childNode = new Node(null);

        String[] folderSplit = itemList[0].split("\\s");
        int rootAmount = folderSplit.length;

        // For loop that adds nodes to root
        if (folderAmount > 1) {
            for (int i = 0; i < rootAmount; i++) {
                int itemNumber = Integer.parseInt(folderSplit[i]);
                childNode = addChild(treeRootNode, "'-" + splittedItems[itemNumber]);
            }
        }

        // For loop that adds nodes to children of root
        //TODO: fix it so "grandchildren" are present
        int treeRootNodeCounter = treeRootNode.getChildrenCount();
        if (folderAmount > 2) {
            int fileCounter = 1;
            for (int i = 0; i < treeRootNodeCounter; i++) {
                String[] fileSplit = itemList[fileCounter].split("\\s");
                for (int j = 0; j < fileSplit.length; j++) {
                    int fileNumber = Integer.parseInt(fileSplit[j]);
                    addChild(treeRootNode.getChild(i), "'-" + splittedItems[fileNumber]);
                }
                fileCounter++;

            }
        }
        printTree(treeRootNode, "  ");
        return outputContent.toString();
    }

    private static Node addChild(Node parent, String id) {
        Node node = new Node(parent);
        node.setId(id);
        parent.getChildren().add(node);
        return node;
    }


    //TODO: Change it to @StringBuilder format to please the gods
    private static void printTree(Node node, String appender) {
        System.out.println(appender + node.getId());
        for (Node each : node.getChildren()) {
            printTree(each, appender + appender);
        }

    }
}

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

    public Node getChild(int i) {
        return children.get(i);
    }

    public int getChildrenCount() {
        return children.size();
    }

    public Node getParent() {
        return parent;
    }

}