import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Driver class for scheduling problem
 * @author Nikhil
 *
 */
public class FinalSchedule {

	public static void main(String[] args) {

		ExamScheduler schedulerObj = new ExamScheduler();

		System.out.println("Enter the name of the student record file");
		try {
			Scanner scanObj = new Scanner(System.in);
			String fileName = null;
			if (scanObj.hasNext()) {
				fileName = scanObj.next();
			}
			scanObj = new Scanner(new File(fileName));

			while (scanObj.hasNextLine()) {
				// Read each line and store it
				String line = scanObj.nextLine();
				String[] delimitedLine = line.trim().split("([ ])+");

				// Populate course dictionary and the array
				schedulerObj.populateEverything(delimitedLine);
			}

			System.out.println("All the courses in the courseDictionary are:");
			schedulerObj.getCourseDictionary().inOrderTraversal();

			schedulerObj.getCourseGraph().printAdjMatrix();

			schedulerObj.printSchedule();

			scanObj.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
