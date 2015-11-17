/**
 * This class implements the Graph ADT. The graph implemented below would be an
 * undirected and with unweighed edges
 * 
 * @author Nikhil
 * 
 */
public class Graph {

	private int vertices;
	boolean[][] adjMatrix;
	int[] labels;

	/**
	 * The constructor asks for the number of vertices in the graph and creates
	 * an adjacency matrix and labels array
	 * 
	 * @param v
	 */
	Graph(int v) {
		vertices = v;
		adjMatrix = new boolean[v][v];
		labels = new int[v];
	}

	/**
	 * Adds an edge to an undirected graph PreCondition: i,j are less than the
	 * number of vertices in the graph and are non negative
	 * 
	 * @param i
	 * @param j
	 */
	public void addEdge(int i, int j) {
		adjMatrix[i][j] = true;
		adjMatrix[j][i] = true;
	}

	/**
	 * Checks if an edge exists between a given set of vertices
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean isEdge(int i, int j) {
		return ((adjMatrix[i][j] == true || adjMatrix[j][i] == true) ? true
				: false);
	}

	/**
	 * Removes all the edges between two vertices in an undirected graph
	 * 
	 * @param i
	 * @param j
	 */
	public void removeEdge(int i, int j) {
		adjMatrix[i][j] = false;
		adjMatrix[j][i] = false;
	}

	public boolean[][] getAdjMatrix() {
		return adjMatrix;
	}

	/**
	 * Get all the neighbors for a given vertex
	 * 
	 * @param i
	 * @return
	 */
	public int[] getNeighbours(int vertex) {

		int count = 0;

		// Count number of neighbors first
		for (int i = 0; i < vertices; i++) {
			if (adjMatrix[vertex][i] == true) {
				count++;
			}
		}

		int[] neighborArray = new int[count];
		count = 0;

		for (int i = 0; i < vertices; i++) {
			if (adjMatrix[vertex][i] == true) {
				neighborArray[count] = i;
				count++;
			}
		}
		return neighborArray;
	}

	public int getSize() {
		return vertices;
	}

	/**
	 * This method is used to print the adjacency matrix. This method does not
	 * print the unused part of the matrix
	 */
	public void printAdjMatrix() {
		int countX = 0;
		int countY = 0;
		System.out.println("The adjacency matrix for the graph is: ");

		for (int i = 0; i < vertices; i++) {

			for (int j = 0; j < vertices; j++) {
				if (adjMatrix[i][j] == true) {

					countY = i;

					if (countX < j) {
						countX = j;
					}
				}
			}
		}

		// Time to print the adjacency matrix. Effective vertices start counting
		// from 0
		int effectiveVertices = Math.max(countX, countY);

		for (int i = 0; i <= effectiveVertices; i++) {
			for (int j = 0; j <= effectiveVertices; j++) {
				if (adjMatrix[i][j] == true) {
					System.out.print(1 + " ");
				} else {
					System.out.print(0 + " ");
				}
			}
			System.out.println();
		}
	}

	// Add traversal BFS-DFS methods if time permits

	public void breadthFirstSearch(int vertex) {
		boolean[] markerArray = new boolean[vertices];
		Queue queue = new Queue();

		queue.enQueue(vertex);

		while (!queue.isEmpty()) {

			int vertexNum;
			vertexNum = (int) queue.deQueue();
			if(markerArray[vertexNum]==false){
				System.out.println(vertexNum);
				markerArray[vertex] = true;
			}
			
			int[] neighbours = getNeighbours(vertexNum);

			for (int i = 0; i < neighbours.length; i++) {
				queue.enQueue(neighbours[i]);
			}
		}

	}
}
