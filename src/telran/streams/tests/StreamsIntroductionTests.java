package telran.streams.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static telran.streams.StreamIntroductionsMethods.*

;

import java.util.Arrays;
import java.util.NoSuchElementException;class StreamsIntroductionTests {

	@Test
	void sumIfTest() {
		int[] ar = {1,2,3,4,5,6};
		assertEquals(12, sumIf(ar, n -> n % 2 == 0));
	}
	@Test
	void sumDistinctTest() {
		int[] ar = {1,2,10,1,1};
		assertEquals(13, sumDistinct(ar));
	}
	@Test
	void maxIfTest() {
		int[] ar = {1,7,-3,23,13,101};
		assertEquals(101, maxIf(ar, n -> n % 2 != 0));
		assertThrowsExactly(NoSuchElementException.class, () -> maxIf(ar, n -> n % 2 == 0));
	}
	@Test
	void sortDistinctTest() {
		int[] ar = {1,10,1,1,2};
		int[] expected = {1,2,10};
		assertArrayEquals(expected, sortDistinct(ar));
	}
	@Test
	void forEachIfTest() {
		int[] ar = {1,10,1,1,2};
		forEachIf(ar, n -> n % 2 != 0, System.out::println);
	}
	@Test
	void getRandomArrayTest() {
		int ar[] = getRandomArray(10, 12, 1000);
		assertEquals(1000, ar.length);
		assertTrue(Arrays.stream(ar).allMatch(n -> n >= 10 && n < 12));
		assertEquals(21, sumDistinct(ar));
	}
}
