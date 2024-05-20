package telran.streams.students;

import java.util.Arrays;

public record Student(String name, int hours, int[] marks)
implements Comparable<Student> {
public double getAverageMark() {
	return Arrays.stream(marks).average().orElseThrow();
}
	@Override
	public int compareTo(Student o) {
		int res = Double.compare(o.getAverageMark(), getAverageMark());
		if (res == 0) {
			res = Integer.compare(o.hours, hours);
		}
		return res;
	}

}