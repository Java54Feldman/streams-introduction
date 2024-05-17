package telran.streams.students;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ColledgeTests {
	private static final String NAME1 = "Shac";
	private static final String NAME2 = "Gershovich";
	private static final String NAME3 = "Feldman";
	private static final int HOURS1 = 100;
	private static final int HOURS2 = 100;
	private static final int HOURS3 = 150;
	private static final int[] MARKS1 = { 60, 70, 80 };
	private static final int[] MARKS2 = { 60, 60, 60 };
	Student st1 = new Student(NAME1, HOURS1, MARKS1);
	Student st2 = new Student(NAME2, HOURS2, MARKS2);
	Student st3 = new Student(NAME3, HOURS3, MARKS2);
	Colledge colledge = new Colledge(new Student[] {st1, st2, st3});

	@Test
	@DisplayName("Test of the sort students")
	void sortTest() {
		Student[] expected = {st1, st3, st2};
		assertArrayEquals(expected, sortStudents(colledge));
}
	@Test
	@DisplayName("Test of the summary statistic hours")
	void summaryStatisticsHoursTest() {
		IntSummaryStatistics iss = getHoursStatistics(colledge);
		assertEquals(100, iss.getMin());
		assertEquals(150, iss.getMax());
		assertEquals(350, iss.getSum());
	}
	@Test
	@DisplayName("Test of the summary statistic marks")
	void summaryStatisticsMarks() {
		IntSummaryStatistics iss = getMarksStatistics(colledge);
		assertEquals(60, iss.getMin());
		assertEquals(80, iss.getMax());
	}
	private static IntSummaryStatistics getHoursStatistics(Colledge col) {
		//returns IntSummaryStatistics of hours for all colledge's students
		return StreamSupport.stream(col.spliterator(), false)
				.collect(Collectors.summarizingInt(student -> student.hours()));
	}
	private static IntSummaryStatistics getMarksStatistics(Colledge col) {
		//returns summary statistics for marks of all colledge's students
		return StreamSupport.stream(col.spliterator(), false)
				.flatMapToInt(student -> Arrays.stream(student.marks()))
                .summaryStatistics();
	}

	private static Student[] sortStudents(Colledge col) {
		//consider getting stream from Iterable
		//returns array of students sorted in descending order of the average marks
		//in the case average marks are equaled there will be compared hours
		//one code line
		return StreamSupport.stream(col.spliterator(), false)
				.sorted((s1, s2) -> {
							int res = Double.compare(Arrays.stream(s2.marks()).average().orElseThrow(), 
													 Arrays.stream(s1.marks()).average().orElseThrow());
							return res == 0 ? Integer.compare(s2.hours(), s1.hours()) : res;	
						})
				.toArray(Student[]::new);
}
	
	@Test
	@DisplayName("Test of the Iterator")
	void testIterator() {
	    Student[] expected = {st1, st2, st3};
	    Iterator<Student> it = colledge.iterator();
	    int index = 0;
	    while (it.hasNext()) {
	        assertEquals(expected[index++], it.next());
	    }
	    assertEquals(expected.length, index);
	    assertThrowsExactly(NoSuchElementException.class, () -> it.next());
	}

}
