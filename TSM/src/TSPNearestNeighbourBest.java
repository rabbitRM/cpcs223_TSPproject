import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;
public class TSPNearestNeighbourBest
{
    private int numberOfNodes;
    static int  min_path = Integer.MAX_VALUE;
    static ArrayList<Integer> solutions = new ArrayList<Integer>(); 
    
 
    //--------------------------------------------------------
    public void tsp(int adjacencyMatrix[][] , int startVertex)
    {
        // getting the number of nodes , we substract 1 because we added additional value while creating
        numberOfNodes = adjacencyMatrix[1].length - 1;
        
        // array to save the visited nodes
        int[] visited = new int[numberOfNodes + 1];
        
        // we will start from the first node so we mark it as visited 
       
        visited[startVertex] = 1;
        

solutions.add(startVertex);
        
        int element, dst = 0, i;
        
        // initializing min with positive infinity 
        int min = Integer.MAX_VALUE;
        int current_cost=0 ;
        boolean minFlag = false;
        boolean done = true ;
        System.out.print(startVertex +" -> ");
     
     

        while (done)
        {
            element =  solutions.get(solutions.size()-1);
            
            i = 1;
            
            min = Integer.MAX_VALUE;
            while (i <= numberOfNodes)
            {
                if (adjacencyMatrix[element][i] > 1 && visited[i] == 0)
                {
                    if (min > adjacencyMatrix[element][i])
                    {
                        min = adjacencyMatrix[element][i];
                        dst = i;
                        minFlag = true;
                    }
                }
                i++;
            }
            if (minFlag)
            {
                current_cost+= min;
                visited[dst] = 1;
                

                solutions.add(dst);
                System.out.print(dst + " -> ");
                minFlag = false;
                continue;
            }
            
//            solutions.add(startVertex);
          

            done = false ;
        }
          solutions.add(current_cost);
          min_path = Math.min(min_path, current_cost);
          System.out.println( startVertex+ " cost = " +current_cost);
    }
 //--------------------------------------------------------------
    public static void main(String... arg)
    {
        
        int number_of_nodes;
       
       
            System.out.println("Enter the number of nodes in the graph");
            Scanner input = new Scanner(System.in);
            number_of_nodes = input.nextInt();
            
            
            
            int adjacency_matrix[][] = new int[number_of_nodes + 1][number_of_nodes + 1];
//            System.out.println("Enter the adjacency matrix");
            int random ;
            for (int i = 1; i <= number_of_nodes; i++)
            {
                for (int j = 1; j <= number_of_nodes; j++)
                {
                    random = 1000+(int)(Math.random()*9000); 
                    adjacency_matrix[i][j] = random;
                    adjacency_matrix[j][i] = random;
                }
                    
            }
            
            for (int i = 1; i <= number_of_nodes; i++)
            {
                for (int j = 1; j <= number_of_nodes; j++)
                {
                    if (adjacency_matrix[i][j] == 1 && adjacency_matrix[j][i] == 0)
                    {
                        adjacency_matrix[j][i] = 0;
                    }
                }
            }
            System.out.println("the citys are visited as follows");
            
            TSPNearestNeighbourBest tspNearestNeighbour = new TSPNearestNeighbourBest();
           int cost ;
            for (int i = 1; i <= number_of_nodes; i++) {
            tspNearestNeighbour.tsp(adjacency_matrix, i);
            }
            
            System.out.println("This is the optimal path with cost : " +min_path); 
            
           for ( int i = number_of_nodes; i < solutions.size(); ) {
        
            if(solutions.get(i) == min_path){
                for (int j = i-(number_of_nodes); j < i; j++) {
                    System.out.print(solutions.get(j) +" -> ");
                }
                System.out.println(solutions.get(i-(number_of_nodes)));
            }
               i+=number_of_nodes+1;
            
    }
             
        
            
       
        input.close();
    }
}