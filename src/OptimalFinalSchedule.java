public class OptimalFinalSchedule {

	private RedBlackTree courseDictionary = new RedBlackTree();
	private String[] courseArray = new String[40]; // a max of 40 courses
													// possible
	private Graph courseGraph = new Graph(40);
	private boolean[] markerArray;

	/**
	 * This method takes an array of course names and inserts those names into
	 * the RB tree. It also populates an array that keeps a list of courses that
	 * are taken by all students. The index of the array is the corresponding
	 * number it holds in the dictionary.
	 * 
	 * This method also populates the graph
	 * 
	 * @param courseNames
	 */
	public void populateEverything(String[] courseNames) {

		// Populating dictionary and array
		for (int i = 2; i < courseNames.length; i++) {

			if (!courseDictionary.contains(courseNames[i])) {
				courseDictionary.insert(courseNames[i]);
				courseArray[(int) (courseDictionary.getSize() - 1)] = courseNames[i];
			}
		}

		CourseCode courseObj1 = null;
		CourseCode courseObj2 = null;

		// Populate Graph here
		for (int i = 2; i < courseNames.length; i++) {

			// By now the course num should be in the dictionary. So closeBy
			// method will return the course object for that course not just the
			// closest member in the tree

			courseObj1 = courseDictionary.closeBy(courseNames[i]);
			for (int j = i + 1; j < courseNames.length; j++) {
				courseObj2 = courseDictionary.closeBy(courseNames[j]);

				courseGraph.addEdge(courseObj1.getCodeNum(),
						courseObj2.getCodeNum());
				courseGraph.addEdge(courseObj2.getCodeNum(),
						courseObj1.getCodeNum());
			}

		}
	}

	/**
	 * This method colors a graph starting from the starting vertex given
	 * 
	 * @param startingVertex
	 * @return
	 */
	public SinglyLinkedList[] colorGraph(int startingVertex) {

		// This will only work for graphs the need less than or equal to 10
		// colors
		SinglyLinkedList[] allColorList = new SinglyLinkedList[20];

		int effectiveVertices = (int) courseDictionary.getSize();

		// Marker array. Uncolored=false, colored=true
		markerArray = new boolean[effectiveVertices];

		int count = 0;
		// This iterates till all items in graph are colored
		while (getNextUncoloredLocation(markerArray, 0) != -1) {

			// returns a list of vertices colored by the same color
			SinglyLinkedList oneColorList = colorOneColor(courseGraph,
					startingVertex);
			allColorList[count] = (oneColorList);
			count++;
		}

		return allColorList;
	}

	/**
	 * This is the method that is called to color the vertices from the graph in
	 * the same color
	 * 
	 * @param g
	 * @param startingVertex
	 * @return
	 */
	private SinglyLinkedList colorOneColor(Graph g, int startingVertex) {

		SinglyLinkedList onecolorList = new SinglyLinkedList();

		// This gets the first uncolored vertex including the startingVertex
		// passed
		int v = getNextUncoloredLocation(markerArray, startingVertex);
		Node w;
		int verticesTraversed = 0;
		while (v != -1) {
			boolean found = false;

			if (onecolorList.isEmpty()) {
				markerArray[v] = true;
				onecolorList.insertAtEnd(v);
			} else {
				w = onecolorList.head;
				while (w != null) {
					if (g.getAdjMatrix()[v][(int) (w.getData())] == true
							|| g.getAdjMatrix()[(int) (w.getData())][v] == true) {
						found = true;
						// We found that an edge exists. So color cannot be same
						break;
					} else {
						w = w.next;
					}
				}
				if (found == false) {
					// Means one color list does not have an adjoining edge
					onecolorList.insertAtEnd(v);
					markerArray[v] = true;
				}
			}

			verticesTraversed++;
			if (verticesTraversed < markerArray.length) {
				v = getNextUncoloredLocation(markerArray, v + 1);
			} else {
				v = -1;
			}

		}

		return onecolorList;
	}

	/**
	 * PreCondition: this will work only for smaller graphs, i.e graphs that
	 * need less than 20 colors. Come to think of it, a graph that needs 20
	 * colors might practically be a really big graph
	 */
	public void printOptimalSchedule() {

		/**
		 * We'll color the graph starting from all different vertices and then store the
		 * result for each full colored graph, in a 2D array of SinglyLinkedList
		 * So essentially each row in this 2D matrix will have number of colors
		 * used to color the graph
		 */
		SinglyLinkedList[][] allPossibleColoring = new SinglyLinkedList[(int) courseDictionary
				.getSize()][10];
		SinglyLinkedList[] optimalColorList = new SinglyLinkedList[20];

		allPossibleColoring[0] = colorGraph(0);
		int min = getSizeOfArray(allPossibleColoring[0]);
		optimalColorList = allPossibleColoring[0];

		for (int i = 1; i < courseDictionary.getSize(); i++) {
			// Start from each vertex
			allPossibleColoring[i] = colorGraph(i);

			if (allPossibleColoring[i].length < min) {
				min = getSizeOfArray(allPossibleColoring[0]);
				optimalColorList = allPossibleColoring[i];
			}

		}

		System.out.println("RECOMMENDED SCHEDULE OF FINAL EXAMS (OPTIMAL)");
		int count = 0;
		while (optimalColorList[count] != null) {

			Node singleColorNode = optimalColorList[count].head;
			System.out.print("Final Exam Period " + (count + 1) + "=>");
			while (singleColorNode != null) {
				System.out.print(courseArray[singleColorNode.getData()] + " ");
				singleColorNode = singleColorNode.next;
			}
			System.out.println();
			count++;
		}
	}

	private int getSizeOfArray(SinglyLinkedList[] array) {
		int i = 0;
		while (array[i] != null) {
			i++;
		}
		return i;
	}

	/**
	 * This is a private utility method that returns the location of the next
	 * uncolored vertex from the graph. If no vertex is uncolored it returns -1
	 * 
	 * @param array
	 * @return
	 */
	private int getNextUncoloredLocation(boolean[] array, int vertex) {
		int count = 0;
		// This is for the time when the calling method send k that is greater
		// than array length
		vertex = vertex % array.length;
		for (int i = vertex; count < array.length; i++) {

			if (array[i % array.length] == false) {
				return i % array.length;
			}
			count++;
		}
		return -1;
	}

	public RedBlackTree getCourseDictionary() {
		return courseDictionary;
	}

	public void setCourseDictionary(RedBlackTree courseDictionary) {
		this.courseDictionary = courseDictionary;
	}

	public String[] getCourseArray() {
		return courseArray;
	}

	public void setCourseArray(String[] courseArray) {
		this.courseArray = courseArray;
	}

	public Graph getCourseGraph() {
		return courseGraph;
	}

	public void setCourseGraph(Graph courseGraph) {
		this.courseGraph = courseGraph;
	}

}
