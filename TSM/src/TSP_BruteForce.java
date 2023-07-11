/* Title : Empirical Analysis of brute-force and branch-and-bound Algorithms to deliver the packages 
  
   Name                     ID
   Nujud Abdullah Almaleki  2105148 
   Rama Ahmad Alsefri  	    2105895
   Areej Omar Baeshen  	    2105759
   Furat Jamel Alfarsi 	    2009624

  control structure : the program will depend on the use of array and arraylist
  input requirements : the user will only enter the number of nodes and the program will automatically generate the weights of the 
  roads between nodes.
  output results : printing all the paths with their cost, additionally showing the optimal cost and all the optimal paths 
 */

import java.util.*;

public class TSP_BruteForce {

    
    //---------------------------------------------------------------------------------------------
    // method that print all the possible paths , along with finding the optimal cost and the ptimal paths
    // Input : A 2D array graph[ 0 .. num_nodes –1 ] [0 .. num_nodes –1 ] store the weight of each edge ,
    // two nonnegative integers the starting node s and  the number of nodes in the graph num_nodes    
    // Output : Printing all the possible paths , along with finding the optimal cost and the optimal paths 
    static void BruteForce(int graph[][], int s, int num_nodes) {

        // arraylist of integers to store all the cities except the source 
        ArrayList<Integer> vertex = new ArrayList<Integer>();

        // arraylist of integers to store all the possible solutions
        ArrayList<Integer> solutions = new ArrayList<Integer>();

        // integer variable help finding the optimal cost by initializing it with the largest value 
        int min_path = Integer.MAX_VALUE;

        // integer variable to store the cost of a path 
        int current_pathweight;

        // integer variable that represents the nodes in the path
        int k;

        //loop that fill the vertex list with the nodes excepting the starting node 
        for (int i = 0; i < num_nodes; i++) {
            // if the node is not the starting node
            if (i != s) {
                vertex.add(i);
            }
        }

        // loop that helps generate all the possible paths
        do {

            // initializing the variable for each path with 0 and the starting node
            current_pathweight = 0;
            k = s;

            // loop that make a path   
            for (int i = 0; i < vertex.size(); i++) {

                // adding the node to the solution list 
                solutions.add(k);

                // printing the node and the next node in the current path
                System.out.print(" " + k + " -> " + vertex.get(i) + " ");

                // adding the weight of the edge between the 2 nodes
                current_pathweight += graph[k][vertex.get(i)];

                // make the distenation node the source node 
                k = vertex.get(i);
            }

            // adding the weight of the edge between the last node and the start node
            current_pathweight += graph[k][s];

            // adding the last node in the path 
            solutions.add(k);

            // adding the final cost of the path to the solutions list
            solutions.add(current_pathweight);
            
            // printing the last node, start node and the cost of the path
            System.out.println(" " + k + " -> " + s + " = " + current_pathweight);

            // comparing between the cost of the current path and the minimum and assign the smallest to min_path 
            min_path = Math.min(min_path, current_pathweight);

        } while (findNextPermutation(vertex));

        // printing the message
        System.out.println("This is the optimal path with cost : " + min_path);

        // loop that go through the list of solutions to find the optimal paths and print them
        // it starts from N , because the first cost will be in cell number N ,
        // the incrementing will be n+1 , also because the costs will be at every n+1 cell 
        // all this so we can minimize the useless iterations that will go through the nodes not the costs 
        for (int i = num_nodes; i < solutions.size(); i += num_nodes + 1) {

            // if the cost of the path is = to the optimal path
            if (solutions.get(i) == min_path) {

                // loop that print the nodes of the optimal path 
                // it start from i-n , because the starting nodes will always be at i- n cells
                for (int j = i - (num_nodes); j < i; j++) {
                    // printing the nodes
                    System.out.print(solutions.get(j) + " -> ");
                }

                // printing the starting node as the last node because the starting node is the end node 
                System.out.println(solutions.get(i - (num_nodes)));
            }
        }
    }

    //---------------------------------------------------------------------------------------------
    // method to create the next Permutation
    // Input : An arraylist data <Integer>  that store all the cities except the source city , 
    // two nonnegative integers indices left and right .     
    // Output : Modified arraylist that contain the next permutation 
    public static ArrayList<Integer> swap(ArrayList<Integer> data, int left, int right) {

        // creating a temporary variable to hold the data
        int temp = data.get(left);
        
        // swapping the data 
        data.set(left, data.get(right));
        data.set(right, temp);

        // returning the list after changing it 
        return data;
    }

    //---------------------------------------------------------------------------------------------
    // another method to help create the next Permutation
    // Input : An arraylist data  <Integer>  that store all the cities except the source city, 
    // two nonnegative integers indices left and right .     
    // Output : Modified arraylist that contain the next permutation 
    public static ArrayList<Integer> reverse(ArrayList<Integer> data, int left, int right) {

        // creating a temporary variable to hold the data
        int temp;
        
        // loop that go through the data list to reverse its contents 
        // as loong as the left index is smaller than the right index 
        while (left < right) {

            temp = data.get(left);
            
            // changing the data 
            // making the increment and decremnet post operation , 
            // so the real places in the time of processing do not change 
            data.set(left++, data.get(right));
            data.set(right--, temp);
        }
        return data;
    }

    //---------------------------------------------------------------------------------------------
    // method that finds wither if there is a next permutation  
    // Input : An arraylist data  <Integer>  that store all the cities except the source city  
    // Output : Return “true” if there is a next permutation  
    public static boolean findNextPermutation(ArrayList<Integer> data) {

        int last, nextGreater;

        // checking if there is a single node then there is no next Permutation
        if (data.size() <= 1) {
            return false;
        }

        // creating a variable by substrcting the size by 2 
        //because the minimum number of nodes will be 2 at this point
        last = data.size() - 2;

        // loop that go through the data list to find 2 integers in ascending order
        while (last >= 0) {

            // if there is 2 integers in ascending order
            if (data.get(last) < data.get(last + 1)) {
                
                // go out of the loop 
                break;
            }

            last--;
        }

        // if there is no more Permutation 
        //which means that the list is ordered in decreasing order
        if (last < 0) {
            return false;
        }

        // creating a variable by substrcting the size by 1
        //because we want it to search for the greater integer so 
        // by defualt it will be the next integer after last 
        nextGreater = data.size() - 1;

        // loop that go through data list to find 
        // the next greater integer after the integer in last 
        for (int i = data.size() - 1; i > last; i--) {

            // if this integer is greater than the integer in last 
            if (data.get(i) > data.get(last)) {

                // asign the place to the variable 
                nextGreater = i;
                
                // go out of the loop
                break;
            }
        }
        
        // calling the methods that will do the actual changing
        data = swap(data, nextGreater, last);
        data = reverse(data, last + 1, data.size() - 1);

        // if we proceed to this point 
        // this means there is a next Permutation
        return true;
    }

    public static void main(String args[]) {

        // scanner opject to read from the user
        Scanner input = new Scanner(System.in);

        // integer variable to store the random weight for each edge 
        //generating  weights beween 1 and 50
        int random;
        
        // integer variable to store the source city 
        int start;

        // long variables to store the strating time and ending time 
        //of processing Brute force algorithm
        long startTime, endTime;

        // double variable to store differenece of time 
        //in nanoseconed between startTime and endTime variables
        double result;

        // Getting the number of nodes as input ( size rang 3-10 nods(area) )
        System.out.print("Enter the number of nodes : ");
        int num_nodes = input.nextInt();

        // creating the array based in the number of nodes 
        int adjacency_matrix[][] = new int[num_nodes][num_nodes];

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
                    adjacency_matrix[i][j] = 0;
                } else {
                    
                    // because the graph is undirected graph the weight of the edges
                    //from both sides will be equal , a->b and b->a
                    adjacency_matrix[i][j] = random;
                    adjacency_matrix[j][i] = random;
                }
            }
        }

        // saving the value of the fisrt node 
        start = 0;

        // printing the adjacency matrix
        System.out.println("\nThe Adjacency Matrix is: ");

        // loop that go through the adjacency matrix
        for (int i = 0; i < num_nodes; i++) {
            for (int j = 0; j < num_nodes; j++) {

                System.out.print(adjacency_matrix[i][j] + "\t");
            }

            System.out.println();
        }

        // printing a message
        System.out.println("\nAll the possible paths : ");

        // computing the starting time for the algorithm
        startTime = System.nanoTime();

        // calling the method that will help finding all the possible paths
        // along with the optimal cost and all the optimal paths
        BruteForce(adjacency_matrix, start, num_nodes);
        
        // computing the ending time for the algorithm
        endTime = System.nanoTime();
        
        // computing the time it took the algorithm to process
        result = (double) (endTime - startTime) / 1000000000;
        
        //print a message
        System.out.println("It took me " + result + " ns");

    }

// END OF PROGRAM       
}
