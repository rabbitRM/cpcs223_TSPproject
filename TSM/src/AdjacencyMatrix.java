
import java.util.Scanner;

public class AdjacencyMatrix {

    public static void main(String[] args) {
        // scanner opject to read from the user
        Scanner input = new Scanner(System.in);

        // 2D array of integer to represent the graph 
        int[][] adjacencyMatrix;

        // integer variables to store the number of nodes from the user
        int nodes;

        // integer variables to store the random weight for each edge
        int random;

        // getting the number of nodes from the user
        System.out.print("Enter the number of nodes: ");
        nodes = input.nextInt();

        // creating the array based in the number of nodes 
        adjacencyMatrix = new int[nodes][nodes];

        // loop that go through the adjacency matrix to fill it
        //because it is a 2D array we need 2 nested loops
        for (int i = 0; i < nodes; i++) {
            for (int j = 0; j < nodes; j++) {

                // generating random integer for each edge
                random = 1 + (int) (Math.random() * 50);

                if (i == j) {
                    //because we dont want a self loop between a node and itself 
                    adjacencyMatrix[i][j] = 0;
                } else {
                    
                    // because the graph is undirected graph the weight of the edges
                    //from both sides will be equal , a->b and b->a
                    adjacencyMatrix[i][j] = random;
                    adjacencyMatrix[j][i] = random;
                }

            }
        }

        // printing the adjacency matrix
        System.out.println("\nThe Adjacency Matrix is: ");

        // loop that go through the adjacency matrix 
        for (int i = 0; i < nodes; i++) {
            for (int j = 0; j < nodes; j++) {

                System.out.print(adjacencyMatrix[i][j] + "\t");
            }

            System.out.println();
        }

    }  // END OF PROGRAM 
}
