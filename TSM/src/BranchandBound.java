/* Title : Empirical Analysis of brute-force and branch-and-bound Algorithms to deliver the packages 
  
   Name                     ID
   Nujud Abdullah Almaleki  2105148 
   Rama Ahmad Alsefri  	    2105895
   Areej Omar Baeshen  	    2105759
   Furat Jamel Alfarsi 	    2009624

  control structure : the program will depend on the use of array and arraylist
  input requirements : the user will only enter the number of nodes and the program will automatically generate the weights of the 
  roads between nodes.
  output results : printing all the paths awith their cost, additionally showing the optimal cost and all the optimal paths 
 */

import java.util.*;

public class BranchandBound {

    // arraylist of integers to store all the possible solutions
    static ArrayList<Integer> solutions = new ArrayList<Integer>();

    // integers variable to store the optimal cost 
    static int final_res = Integer.MAX_VALUE;

    // method that find the minimum edge cost , haveing an end at vertex i 
    // which means chooseing the minimum cost in the row i 
    // Input : A 2D array AdjacencyMatrix [ 0 .. num_nodes –1 ] [0 .. num_nodes –1 ] store the weight of each edge , 
    // two nonnegative integer  , a vertex  i  and the number of nodes in the graph num_nodes      
    // Output :  The minimum edge cost , having an end at vertex i 
    static int firstMin(int adj[][], int i, int num_nodes) {

        // integer number help finding the fisrt minimum in method firstMin
        // initializing with the largest number to find the smallest
        int min = Integer.MAX_VALUE;

        // loop go through the edges connected to vertax i 
        for (int k = 0; k < num_nodes; k++) {

            // if the weight of the edge is smaller than min, 
            //and it is not a self edge 
            if (adj[i][k] < min && i != k) {
                // assign the weight to min 
                min = adj[i][k];
            }
        }

        return min;
    }

    // method that find the second minimum edge cost , haveing an end at vertex i 
    //chooseing the second minimum cost in the row i 
    // Input : A 2D array AdjacencyMatrix [ 0 .. num_nodes –1 ] [0 .. num_nodes –1 ] store the weight of each edge , 
    //two nonnegative integer  , a vertex  i  and the number of nodes in the graph num_nodes     
    // Output :  The second minimum edge cost , having an end at vertex i 
    static int secondMin(int adj[][], int i, int num_nodes) {

        // integer numbers help finding the second minimum in method secondMin
        // initializing with the largest number to find the second smallest
        int first = Integer.MAX_VALUE;
        int second = Integer.MAX_VALUE;

        // loop go through the edges connected to vertax i 
        // NOTE :we have to find the first minimum because finding the second minimum depend on it  
        for (int j = 0; j < num_nodes; j++) {

            // if it is a self edge continue 
            if (i == j) {
                continue;
            }

            // if the weight of the edge is smaller or equal than first
            if (adj[i][j] <= first) {

                // assign the value of first to second 
                second = first;

                // assign the weight to first 
                first = adj[i][j];

            } // if the weight of the edge is smaller or equal than second and it is not the first minimum
            else if (adj[i][j] <= second && adj[i][j] != first) {
                // assign the weight to second 
                second = adj[i][j];
            }
        }

        return second;
    }

    // the main method that will find all the possible paths , 
    // along with the optimal cost and the optimal paths takes as arguments :
    // curr_bound -> lower bound of the root node
    // curr_weight-> stores the weight of the path so far
    // level-> current level while moving in the search space tree
    // curr_path[] -> where the solution is being stored which would later be copied to solutions list
    // num_nodes -> number of nodes in the graph 
    // visited -> a 1D array stores boolean values to know if the node is visited 
    // Output :  Finding all the possible paths along with the optimal cost  
    static void BranchAndBound(int adj[][], int curr_bound, int curr_weight, int level, int curr_path[], int num_nodes, boolean visited[]) {

        // base case is when we have reached level N which
        // means we have covered all the nodes once
        if (level == num_nodes) {

            // check if there is an edge from last vertex in
            // path back to the first vertex
            if (adj[curr_path[level - 1]][curr_path[0]] != 0) {

                // curr_res has the total weight of the path
                int curr_res = curr_weight + adj[curr_path[level - 1]][curr_path[0]];

                // loop to print the path , and store it in solutions list
                for (int i = 0; i < num_nodes; i++) {
                    System.out.print(curr_path[i] + " -> ");
                    solutions.add(curr_path[i]);
                }

                // priting the last node which is the first node and the final weight
                System.out.println(curr_path[0] + " cost = " + curr_res);

                // store the final weight in solutions list 
                solutions.add(curr_res);

                // comparing between the cost of the current path and the final result so far 
                // and assign the smallest to final_res 
                final_res = Math.min(final_res, curr_res);
            }

            return;
        }

        // for any other level iterate for all vertices to
        // build the search space tree recursively
        for (int i = 0; i < num_nodes; i++) {

            // if the next vertex is not the same and not visited yet 
            if (adj[curr_path[level - 1]][i] != 0 && visited[i] == false) {

                // temporary integer variable to save the current value of the curr_bound
                // to help reseting later
                int temp = curr_bound;

                // adding the weight 
                curr_weight += adj[curr_path[level - 1]][i];

                // different computation of curr_bound for
                // level 2 from the other levels
                if (level == 1) {
                    curr_bound = curr_bound - ((firstMin(adj, curr_path[level - 1], num_nodes) + firstMin(adj, i, num_nodes)) / 2);
                } else {
                    curr_bound = curr_bound - ((secondMin(adj, curr_path[level - 1], num_nodes) + firstMin(adj, i, num_nodes)) / 2);
                }

                // save the node in curr_path[] , and mark it as visited 
                curr_path[level] = i;
                visited[i] = true;

                // recursive calling TSPRec for the next level
                BranchAndBound(adj, curr_bound, curr_weight, level + 1, curr_path, num_nodes, visited);

                // we have to prune the node by resetting 
                //all changes to curr_weight and curr_bound
                curr_weight -= adj[curr_path[level - 1]][i];
                curr_bound = temp;

                // reseting the visited array
                Arrays.fill(visited, false);
                for (int j = 0; j <= level - 1; j++) {
                    visited[curr_path[j]] = true;
                }
            }
        }
    }

    // method that compute the lower bound of the root node and print all the possible paths with their costs 
    // and dispaly the optimal cost and optimal paths  
    // Input : A 2D array AdjacencyMatrix [ 0 .. num_nodes –1 ] [0 .. num_nodes –1 ] store the weight of each edge ,
    // a 1D array  visited [ 0 .. num_nodes -1 ] store boolean values to know if the node is visited , a nonnegative integer ,
    // the number of nodes in the graph num_nodes   
    // Output :  Printing all the possible paths , along with the optimal cost and the optimal paths  
    static void TSP(int adj[][], int num_nodes, boolean visited[]) {

        // array to save the current path
        int curr_path[] = new int[num_nodes];

        // Calculating the initial lower bound for the root node
        // using the formula 1/2 * (sum of first min + second min) for all edges
        //initialize the current bound with 0 
        int curr_bound = 0;

        // filling the current path array with -1 
        Arrays.fill(curr_path, -1);

        // loop to compute the initial bound 
        for (int i = 0; i < num_nodes; i++) {
            //computing the sum of the first and second minimum edges for each vertex
            curr_bound += (firstMin(adj, i, num_nodes) + secondMin(adj, i, num_nodes));
        }

        // rounding off the lower bound to an integer
        curr_bound = (curr_bound == 1) ? curr_bound / 2 + 1 : curr_bound / 2;

        // starting at vertex 0 , so mark it as visited , and save it in curr_path[]
        visited[0] = true;
        curr_path[0] = 0;

        // Calling the TSPRec and sending the adjacency matrix with the curr_bound also
        // curr_weight = 0 , level 1 and curr_path , num_nodes , visited
        BranchAndBound(adj, curr_bound, 0, 1, curr_path, num_nodes, visited);

        // printing the message
        System.out.println("This is the optimal path with cost : " + final_res);

        // loop that go through the list of solutions to find the optimal paths and print them
        // it starts from N , because the first cost will be in cell number N ,
        // the incrementing will be n+1 , also because the costs will be at every n+1 cell 
        // all this so we can minimize the useless iterations that will go through the nodes not the costs 
        for (int i = num_nodes; i < solutions.size(); i += num_nodes + 1) {

            // if the cost of the path is = to the optimal path
            if (solutions.get(i) == final_res) {

                // loop that print the nodes of the optimal path 
                // it start from i-n , because the starting nodes will always be at i- n cells
                for (int j = i - num_nodes; j < i; j++) {
                    // printing the nodes
                    System.out.print(solutions.get(j) + " -> ");
                }

                // printing the starting node as the last node because the starting node is the end node 
                System.out.println(solutions.get(i - (num_nodes)));

            }
        }

    }

    public static void main(String[] args) {

        // scanner opject to read from the user
        Scanner input = new Scanner(System.in);

        // integer variable to store the random weight for each edge 
        //generating  weights beween 1 and 50
        int random;

        // long variables to store the strating time and ending time 
        //of processing Brute force algorithm
        long startTime, endTime;

        // double variable to store diffrenece in time 
        //in nanoseconed between startTime and endTime variables
        double result;

        // Getting the number of nodes as input ( size rang 3-10 nods(area) )
        System.out.print("Enter the number of nodes : ");
        int num_nodes = input.nextInt();

        //creating the adjacency matrix with N*N size to represent the graph
        int adj[][] = new int[num_nodes][num_nodes];

        // loop that go through the adjacency matrix to fill it 
        //because it is a 2D array we need 2 nested loops
        for (int i = 0; i < num_nodes; i++) {

            for (int j = 0; j < num_nodes; j++) {
                // generating random integer for each edge
                random = 1 + (int) (Math.random() * 50);

                // because the graph is undirected graph the weight of the edges
                //from both sides will be equal , a->b and b->a
                if (i == j) {
                    //because we dont want a self loop between a node and itself 
                    adj[i][j] = 0;
                } else {

                    // because the graph is undirected graph the weight of the edges
                    //from both sides will be equal , a->b and b->a
                    adj[i][j] = random;
                    adj[j][i] = random;
                }
            }

        }
        
        // printing the adjacency matrix
        System.out.println("\nThe Adjacency Matrix is: ");

        // loop that go through the adjacency matrix
        for (int i = 0; i < num_nodes; i++) {
            for (int j = 0; j < num_nodes; j++) {

                System.out.print(adj[i][j] + "\t");
            }

            System.out.println();
        }

        // array of boolean to mark the nodes as visited or not
        boolean visited[] = new boolean[num_nodes];

        // printing a message
        System.out.println("\nAll the possible paths : ");

        // computing the starting time for the algorithm
        startTime = System.nanoTime();

        // calling the method that will help finding all the possible paths
        // along with the optimal solution and all the optimal paths
        TSP(adj, num_nodes, visited);

        // computing the ending time for the algorithm
        endTime = System.nanoTime();

        // computing the time it took the algorithm to process
        result = (double) (endTime - startTime) / 1000000000;

        //print a message
        System.out.println("It took me " + result + " ns");

    }

// END OF PROGRAM    
}
