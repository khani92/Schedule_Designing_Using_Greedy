public class ExamScheduler {

	private RedBlackTree courseDictionary = new RedBlackTree();
	private String[] courseArray = new String[40]; // a max of 40 courses
													// possible
	private Graph courseGraph = new Graph(40);
	private boolean[] markerArray;

	/**
	 * This method takes an array of course names and inserts those names into
	 * the RB tree. It also populates an array that keeps a list of courses that
	 * are taken by all students. The index of the array is the corresponding
	 * number it holds in the dictionary.This method also populates the graph
	 * 
	 * PreCondition: courseNames array should have courses starting from the 3
	 * element in the array. This is as per the guidelines for the data in the
	 * file per homework description
	 * 
	 * @param courseNames
	 */
	public void populateEverything(String[] courseNames) {

		// Populating course dictionary and array for fast access from
		// courseCode
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
	 * This methods return an array of SinglyLinkedList type. Each
	 * SinglyLinkedList in the array will the list of vertices colored by the
	 * same color PreCondition: courseDictionary is populated by all the courses
	 * taken by all the students. This method will only make sense if the course
	 * dictionary is already populated and the graph is ready to be used.
	 * 
	 * @return: It returns an array of SinglyLinkedList where each element in
	 *          the array is the list of nodes that can be colored with the same
	 *          color
	 */
	public SinglyLinkedList[] colorGraph() {

		SinglyLinkedList[] allColorList = new SinglyLinkedList[10];
		int effectiveVertices = (int) courseDictionary.getSize();

		// Marker array. Uncolored=false, colored=true
		markerArray = new boolean[effectiveVertices];

		int count = 0;
		// This iterates till all items in graph are colored

		while (getNextUncoloredLocation(markerArray, 0) != -1) {

			// Here the list returned is a list of all vertices colored by same
			// color. Repeat the process till the entire graph is colored
			SinglyLinkedList oneColorList = colorOneColor(courseGraph);
			allColorList[count] = (oneColorList);
			count++;
		}

		return allColorList;
	}

	/**
	 * This method return a linked list that has a list of vertices colored by
	 * the same color
	 * 
	 * @param g
	 * @return
	 */
	private SinglyLinkedList colorOneColor(Graph g) {

		SinglyLinkedList onecolorList = new SinglyLinkedList();

		// This gets the first uncolored vertex. Since the 2nd parameter is 0 it
		// starts from the start of the marker array
		int v = getNextUncoloredLocation(markerArray, 0);
		Node w;

		while (v < markerArray.length && v != -1) {
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

			v = getNextUncoloredLocation(markerArray, v + 1);
		}

		return onecolorList;
	}

	/**
	 * This method prints the not necessarily optimal schedule
	 */
	public void printSchedule() {
		SinglyLinkedList[] allColorList = colorGraph();
		System.out
				.println("RECOMMENDED SCHEDULE OF FINAL EXAMS (NOT NECESSARILY OPTIMAL)");
		int count = 0;
		while (allColorList[count] != null) {

			Node singleColorNode = allColorList[count].head;
			System.out.print("Final Exam Period " + (count + 1) + "=>");
			while (singleColorNode != null) {
				System.out.print(courseArray[singleColorNode.getData()] + " ");
				singleColorNode = singleColorNode.next;
			}
			System.out.println();
			count++;
		}
	}

	/**
	 * This is a private utility method that returns the location of the next
	 * uncolored vertex from the graph. If no vertex is uncolored it returns -1
	 * 
	 * @param array
	 * @return
	 */
	private int getNextUncoloredLocation(boolean[] array, int k) {
		if (k < array.length) {
			for (int i = k; i < array.length; i++) {
				if (array[i] == false) {
					return i;
				}
			}
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
