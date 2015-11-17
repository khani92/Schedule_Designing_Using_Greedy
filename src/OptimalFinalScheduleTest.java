import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class OptimalFinalScheduleTest {

	public static void main(String[] args) {
		OptimalFinalSchedule optimalSchedulerObj = new OptimalFinalSchedule();

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
				optimalSchedulerObj.populateEverything(delimitedLine);
			}

			System.out.println("All the courses in the courseDictionary are:");
			optimalSchedulerObj.getCourseDictionary().inOrderTraversal();

			optimalSchedulerObj.getCourseGraph().printAdjMatrix();

			optimalSchedulerObj.printOptimalSchedule();

			scanObj.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
