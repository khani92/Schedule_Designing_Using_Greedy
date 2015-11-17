public class RedBlackTree {

	/**
	 * Declaring the nil node. A nil node is a sentinel node that has its lc, p
	 * and rc pointers pointing to itself. the data store in the nil node can be
	 * any value that will not be store in the RB-Tree. Its generally -1 or
	 * null. We're using null for RB-Tree
	 */
	private static RedBlackNode nil = new RedBlackNode(null,
			RedBlackNode.BLACK, null, null, null);

	private int numOfComparisons = 0;

	// Initializes the nil node pointers with its values right when the class is
	// first loaded
	static {
		nil.setLc(nil);
		nil.setRc(nil);
		nil.setP(nil);
	}

	/**
	 * Root node for the RB Tree
	 */
	RedBlackNode root;
	private long size = 0;

	public RedBlackTree() {
		root = nil;
	}

	/**
	 * 
	 * @return int: number of values inserted into the tree.
	 */
	public long getSize() {

		return size;
	}

	/**
	 * BigTheta: No Case - BigTheta(N)
	 * 
	 * @param t
	 *            - a pointer to a node in the tree.
	 * @return the height of node t
	 */
	public int height(RedBlackNode t) {

		if (t == nil) {
			// Once we drop out of the tree we return -1 because the parent for
			// this tree will be a leaf and it will add 1 to max of the returned
			// value while its actual height is 0
			return -1;
		}

		int leftHeight = height(t.getLc());
		int rightHeight = height(t.getRc());
		return (leftHeight > rightHeight ? leftHeight : rightHeight) + 1;
	}

	/**
	 * BigTheta: No Case - BigTheta(N)
	 * 
	 * @return the height of the red black tree.
	 */
	public int height() {
		return (height(root));
	}

	/**
	 * The no argument inOrderTraversal() method calls the recursive
	 * inOrderTraversal(RedBlackNode) - passing the root. BigTheta No case:
	 * BigTheta(N)
	 */
	public void inOrderTraversal() {
		inOrderTraversal(root);
	}

	/**
	 * Perform an in-order traversal of the tree. The
	 * inOrderTraversal(RedBlackNode) method is recursive and displays the
	 * content of the tree. It makes calls on System.out.println(). This method
	 * would normally be private.
	 * 
	 * BigTheta No case: BigTheta(N)
	 * 
	 * @param t
	 *            : the root of the tree on the first call.
	 */
	public void inOrderTraversal(RedBlackNode t) {

		if (t.getLc() != nil) {
			inOrderTraversal(t.getLc());
		}

		System.out.println(t.toString());

		if (t.getRc() != nil) {
			inOrderTraversal(t.getRc());
		}
	}

	/**
	 * Perform a reverseInOrder traversal of the tree. The
	 * reverseInOrderTraversal(RedBlackNode) method is recursive and displays
	 * the content of the tree in reverse sorted order. This method would
	 * normally be private.
	 * 
	 * BigTheta No case - BigTheta(N)
	 * 
	 * @param t
	 */
	private void reverseInOrderTraversal(RedBlackNode t) {

		if (t.getRc() != nil) {
			reverseInOrderTraversal(t.getRc());
		}
		System.out.println(t.toString());

		if (t.getLc() != nil) {
			reverseInOrderTraversal(t.getLc());
		}
	}

	/**
	 * The no argument reverseInOrderTraversal() method calls the recursive
	 * reverseInOrderTraversal(RedBlackNode) - passing the root.
	 * 
	 * BigTheta No case - BigTheta(N)
	 */
	public void reverseInOrderTraversal() {
		reverseInOrderTraversal(root);
	}

	/**
	 * The insert() method places a data item into the tree. Precondition:
	 * memory is available for insertion BigTheta Best Case : BigTheta(1)
	 * BigTheta Worst Cast: BigTheta(log(N))
	 * 
	 * @param value
	 *            to be inserted
	 */
	public void insert(String value) {

		// y is the trailing pointer and is used when x falls off the tree. So
		// at the end y will have a leaf
		RedBlackNode y = nil;
		RedBlackNode x = root;

		while (x != nil) {
			y = x;
			if (value.compareTo(x.getCourseData().getCourseName()) < 0) {
				x = x.getLc();
			} else {
				x = x.getRc();
			}
		}

		// at this stage x will have fallen off the tree and y will be pointing
		// to the leaf
		CourseCode courseObj = new CourseCode(value, 0);
		RedBlackNode z = new RedBlackNode(courseObj, RedBlackNode.RED, y, nil,
				nil);

		if (y == nil) {
			root = z;
		} else if (z.getCourseData().getCourseName()
				.compareTo(y.getCourseData().getCourseName()) < 0) {
			y.setLc(z);
		} else if (z.getCourseData().getCourseName()
				.compareTo(y.getCourseData().getCourseName()) > 0) {
			y.setRc(z);
		}
		RBInsertFixup(z);

		// To keep a handy counter of number of items in the tree
		size++;
		// Setting the number with the Course Name
		z.getCourseData().setCodeNum((int)size-1);
	}

	/**
	 * Fixing up the tree so that Red Black Properties are preserved.
	 * 
	 * @param z
	 *            is the new node
	 */
	private void RBInsertFixup(RedBlackNode z) {

		while (z.getP().getColor() == RedBlackNode.RED) {

			if (z.getP() == (z.getP().getP().getLc())) {
				RedBlackNode y = z.getP().getP().getRc();

				if (y.getColor() == RedBlackNode.RED) {
					z.getP().setColor(RedBlackNode.BLACK);
					y.setColor(RedBlackNode.BLACK);
					z.getP().getP().setColor(RedBlackNode.RED);
					z = z.getP().getP();
				} else {
					if (z == z.getP().getRc()) {
						z = z.getP();
						leftRotate(z);// LEFT ROTATE z HERE
					}
					z.getP().setColor(RedBlackNode.BLACK);
					z.getP().getP().setColor(RedBlackNode.RED);
					rightRotate(z.getP().getP());// RIGHT ROTATE z.getP().getP()
				}
			} else {
				RedBlackNode y = z.getP().getP().getLc();

				if (y.getColor() == RedBlackNode.RED) {
					z.getP().setColor(RedBlackNode.BLACK);
					y.setColor(RedBlackNode.BLACK);
					z.getP().getP().setColor(RedBlackNode.RED);
					z = z.getP().getP();
				} else {
					if (z == z.getP().getLc()) {
						z = z.getP();
						rightRotate(z);
					}
					z.getP().setColor(RedBlackNode.BLACK);
					z.getP().getP().setColor(RedBlackNode.RED);
					leftRotate(z.getP().getP());
				}

			}
		}

		root.setColor(RedBlackNode.BLACK);
	}

	/**
	 * leftRotate() performs a single left rotation PreCondition: right[x] !=
	 * nil[T] and root's parent is nil[T] BigTheta: No case BigTheta(1)
	 * 
	 * @param x
	 */

	private void leftRotate(RedBlackNode x) {

		RedBlackNode y = x.getRc();
		x.setRc(y.getLc());
		if (y.getLc() != nil) {
			y.getLc().setP(x);
		}
		y.setP(x.getP());

		if (x.getP() == nil) {
			root = y;
		} else {
			if (x == x.getP().getLc()) {
				x.getP().setLc(y);
			} else {
				x.getP().setRc(y);
			}
		}
		y.setLc(x);
		x.setP(y);

	}

	/**
	 * rightRotate() performs a single right rotation PreCondition: left[x] !=
	 * nil[T] and root's parent is nil[T] BigTheta: No case BigTheta(1)
	 * 
	 * @param x
	 */
	private void rightRotate(RedBlackNode x) {

		RedBlackNode y = x.getLc();
		x.setLc(y.getRc());
		if (y.getRc() != nil) {
			y.getRc().setP(x);
		}
		y.setP(x.getP());

		if (x.getP() == nil) {
			root = y;
		} else {
			if (x == x.getP().getRc()) {
				x.getP().setRc(y);
			} else {
				x.getP().setLc(y);
			}
		}
		y.setRc(x);
		x.setP(y);
	}

	public static void main(String[] args) {

		RedBlackTree rbt = new RedBlackTree();

		for (int j = 1; j <= 5; j++)
			rbt.insert("" + j);

		// after 1..5 are inserted
		System.out.println("RBT in order");
		rbt.inOrderTraversal();
		System.out.println("RBT level order");
		rbt.levelOrderTraversal();
		System.out.println("RBT reverse in order");
		rbt.reverseInOrderTraversal();

		// is 3 in the tree

		if (rbt.contains("" + 3))
			System.out.println("Found 3");
		else
			System.out.println("No 3 found");

		// display the height
		System.out.println("The height is " + rbt.height());

	}

	/**
	 * This method displays the RedBlackTree in level order. It first displays
	 * the root. Then it displays all children of the root. Then it displays all
	 * nodes on level 3 and so on. It is not recursive. It uses a queue.
	 * 
	 * BigTheta: No case BigTheta(N)
	 */
	public void levelOrderTraversal() {

		Queue queue = new Queue();
		RedBlackNode p = null;
		queue.enQueue(root);

		while (!queue.isEmpty()) {
			p = (RedBlackNode) queue.deQueue();
			System.out.println(p);

			if (p.getLc() != nil) {
				queue.enQueue(p.getLc());
			}
			if (p.getRc() != nil) {
				queue.enQueue(p.getRc());
			}
		}
	}

	/**
	 * The method closeBy(v) returns a value close to v in the tree. If v is
	 * found in the tree it returns v.
	 * 
	 * BigTheta: Best case BigTheta(1) BigTheta: Worst case BigTheta(log(N))
	 * 
	 * @param v
	 * @return
	 */
	public CourseCode closeBy(String v) {

		numOfComparisons = 0;
		// y is the trailing pointer.
		RedBlackNode y = nil;
		// x is the leading pointer. At the end of the search this falls of the
		// tree
		RedBlackNode x = root;

		while (x != nil) {

			y = x;

			// To keep a count on the number of comparisons
			numOfComparisons++;

			// v is greater. Move right
			if (v.compareTo(x.getCourseData().getCourseName()) > 0) {
				x = x.getRc();
			}
			// v is smaller. Move left
			else if (v.compareTo(x.getCourseData().getCourseName()) < 0) {
				x = x.getLc();
			}
			// Found the string
			else if (v.compareTo(x.getCourseData().getCourseName()) == 0) {
				return x.getCourseData();
			}
		}

		// If the root is nil the while loop will never execute
		if (root == nil) {
			return (new CourseCode("", -1));
		}
		// If the code reaches here that means the String was not found. the
		// trailing pointer y was the last comparison made, so its the closest
		return (y.getCourseData());

	}

	/**
	 * The boolean contains() returns true if the String v is in the
	 * RedBlackTree and false otherwise. It counts each comparison it makes in
	 * the variable recentCompares.
	 * 
	 * BigTheta: Best case BigTheta(1) BigTheta: Worst case BigTheta(log(N))
	 * 
	 * @param v
	 *            : The String to be searched
	 * @return
	 */
	public boolean contains(String v) {

		boolean flag = false;
		CourseCode closestCourseObj = closeBy(v);

		if (closestCourseObj.getCourseName().compareTo(v) == 0) {
			flag = true;
		}

		return flag;
	}

	/**
	 * number of comparisons made in last call on the contains method. BigTheta:
	 * Best case BigTheta(1)
	 * 
	 * @return Number of comparisons made to reach the word
	 */
	public int getRecentCompares() {

		return numOfComparisons;
	}
}

/**
 * The RedBlackNode class acts as the blueprint for each node of our RB-Tree
 * 
 * @author Nikhil
 * 
 */
class RedBlackNode {

	public static final int BLACK = 0;
	public static final int RED = 1;

	private CourseCode courseData;
	private int color;
	private RedBlackNode p, lc, rc;

	/**
	 * 
	 * @param data
	 *            : A simple value held in the tree
	 * @param color
	 *            : Either RED or BLACK
	 * @param p
	 *            : Pointer to the parent
	 * @param lc
	 *            : Pointer to the left child (will be null only for the node
	 *            that represents all external nulls.
	 * @param rc
	 *            :Pointer to the right child (will be null only for the node
	 *            that represents all external nulls.
	 */

	public RedBlackNode(CourseCode data, int color, RedBlackNode p,
			RedBlackNode lc, RedBlackNode rc) {
		this.courseData = data;
		this.color = color;
		this.p = p;
		this.lc = lc;
		this.rc = rc;
	}

	/**
	 * The getCourseData() method returns the coursecode object in the node.
	 * 
	 * @return The data value in the node
	 */
	public CourseCode getCourseData() {
		return courseData;
	}

	/**
	 * The setCourseData() method sets the courseCode object in the node.
	 * 
	 * @return The data value in the node
	 */
	public void setCourseData(CourseCode courseData) {
		this.courseData = courseData;
	}

	/**
	 * The setData() method sets the data or key of the RedBlackNode.
	 * 
	 * @param data
	 *            : is an {@link String} holding a node's data value
	 */

	/**
	 * The getColor() method returns RED or BLACK.
	 * 
	 * @return the color value (RED or BLACK)
	 */
	public int getColor() {
		return color;
	}

	/**
	 * The setColor() method sets the color of the RedBlackNode.
	 * 
	 * @param color
	 *            : is either RED or BLACK
	 */
	public void setColor(int color) {
		this.color = color;
	}

	/**
	 * The getP() method returns the parent of the RedBlackNode.
	 * 
	 * @return The parent field
	 */
	public RedBlackNode getP() {
		return p;
	}

	/**
	 * The setP() method sets the parent of the RedBlackNode.
	 * 
	 * @param p
	 *            : establishes a parent pointer for this node
	 */
	public void setP(RedBlackNode p) {
		this.p = p;
	}

	/**
	 * The getLc() method returns the left child of the RedBlackNode.
	 * 
	 * @return The left child field
	 */
	public RedBlackNode getLc() {
		return lc;
	}

	/**
	 * The setLc() method sets the left child of the RedBlackNode.
	 * 
	 * @param lc
	 *            : establishes a left child for this node
	 */
	public void setLc(RedBlackNode lc) {
		this.lc = lc;
	}

	/**
	 * The getRc() method returns the right child of the RedBlackNode.
	 * 
	 * @return The right child field
	 */
	public RedBlackNode getRc() {
		return rc;
	}

	/**
	 * The setRc() method sets the right child of the RedBlackNode.
	 * 
	 * @param rc
	 *            : establishes a right child for this node.
	 */
	public void setRc(RedBlackNode rc) {
		this.rc = rc;
	}

	/**
	 * The toString() methods returns a string representation of the
	 * RedBlackNode.
	 */
	public String toString() {
		return getCourseData().getCourseName() + " --> "
				+ getCourseData().getCodeNum();
	}
}

/**
 * The Queue is a first in first out data structure. This Queue holds Java
 * Object references. It grows dynamically as long as memory is available.
 * 
 * @author Nikhil
 */
class Queue {

	// array to implement the queue
	private Object[] array;

	// items variable to keep track of number of items in the array/queue i.e.
	// size of the data structure
	private int items = 0;
	private int front, rear = 0;

	/**
	 * Create a default empty queue that lives in a small array. PreCondition:
	 * Memory is available. PostCondition: Array created and indexes
	 * established.
	 */
	public Queue() {
		array = new Object[10];
	}

	/**
	 * Boolean method returns true on empty queue, false otherwise. Pre: None
	 * BigTheta No case: Big-Theta (1)
	 * 
	 * @return Returns true if queue is empty.
	 */
	public boolean isEmpty() {
		if (items == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Boolean method returns true if queue is currently at capacity, false
	 * otherwise. If isfull() returns true and additional enqueue calls are
	 * made, the queue will expand in size. Pre: None BigTheta No case:
	 * Big-Theta (1)
	 * 
	 * @return Returns true if queue is at current capacity.
	 */
	public boolean isFull() {
		if (items == array.length) {
			return true;
		}
		return false;
	}

	/**
	 * Object method removes and returns reference in front of queue.
	 * PreCondition: Queue should not be empty. PostCondition: The top/front
	 * item is removed from the queue. BigTheta No case: Big-Theta (1)
	 * 
	 * @return Object from the front of the queue.
	 */
	public Object deQueue() {
		Object data = null;
		if (!isEmpty()) {
			// decrease the items variable to keep the correct count of items
			items--;
			if (front == rear) {
				data = array[front];
				array[front] = null;
			}

			else if (front != rear) {
				data = array[front];
				array[front] = null;
				front = getNext(front);
			}
		}
		return data;
	}

	/**
	 * This method returns the next inderx based on the current index which is
	 * passed as a paramter. It uses modulo operator to implement circular queue
	 * 
	 * @param i
	 *            : current index
	 * @return
	 */
	private int getNext(int i) {
		return (i + 1) % array.length;
	}

	/**
	 * Add an object reference to the rear of the queue. Pre-condition Memory is
	 * available for doubling queue capacity when full. Post-condition: queue
	 * now contains x in the rear. BigTheta Best case: Queue is not full
	 * Big-Theta (1) BigTheta Worst case: Queue is full Big-Theta (N)
	 * 
	 * @param x
	 *            : Is an object to be added to the rear of the queue.
	 */
	public void enQueue(Object x) {
		if (isEmpty()) {
			array[front] = x;
		}

		else if (!isFull()) {
			rear = getNext(rear);
			array[rear] = x;
		} else if (isFull()) {
			// Now copy the array to a bigger sized array
			Object[] temp = array;
			// Create a new and bigger array of double size
			array = new Object[temp.length * 2];
			// Copies the small array into the big one
			int count = front;
			for (int i = 0; i < temp.length; i++) {
				array[i] = temp[count];
				count = (count + 1) % temp.length;
			}
			front = 0;
			rear = temp.length;
			array[rear] = x;
			temp = null;
		}
		// increases the count of items
		items++;
	}

	/**
	 * Method getFront returns the front of the queue without removing it.
	 * Pre-condition: queue not empty BigTheta No case: Big-Theta (1)
	 * 
	 * @return: The queue front without removal.
	 */
	public Object getFront() {
		Object data = null;
		if (!isEmpty()) {
			data = array[front];
		}
		return data;
	}

	/**
	 * The toString method returns a String representation of the current queue
	 * contents. BigTheta Best case: Big-Theta (1)
	 * 
	 * @return: a string representation of the queue. It shows the front of the
	 *          queue first. It then shows the second and third and so on.
	 */
	public String toString() {
		StringBuffer s = new StringBuffer();
		for (int i = front; i != getNext(rear); getNext(i)) {
			s.append(array[i] + "  ");
		}
		return s.toString();
	}

	/**
	 * main is for testing the queue routines.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Queue queue = new Queue();

		for (int i = 0; i < 10; i++) {
			queue.enQueue(i);
		}
		System.out.println(queue.isEmpty());
		System.out.println(queue.isFull());

		System.out.println(queue.deQueue());
		System.out.println(queue.deQueue());

		for (int i = 0; i < 80; i++) {
			queue.enQueue(i);
		}
		queue.enQueue(111);
		queue.enQueue(12232);

		queue.enQueue(1212);
		System.out.println(queue.getFront());
	}
}
