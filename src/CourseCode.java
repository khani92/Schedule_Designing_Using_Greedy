/**
 * This is a wrapper class for Coursecode. This will hold the courseName and the
 * identifying number.
 * 
 * @author Nikhil
 * 
 */
public class CourseCode {

	private String courseName;
	private int codeNum;

	public CourseCode(String courseName, int codeNum) {
		super();
		this.courseName = courseName;
		this.codeNum = codeNum;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getCodeNum() {
		return codeNum;
	}

	public void setCodeNum(int codeNum) {
		this.codeNum = codeNum;
	}

}
