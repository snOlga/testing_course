package com.example.demo;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class SinTests {

	double CALC_ERROR = 0.001;

	@ParameterizedTest
	@CsvSource({
			"-90.00, -1.00", // check for negative max
			"0.00, 0.00", // for 0
			"0.0000001, 0.00",
			"90.00, 1.00", // positive max
			"450.00, 1.00", // positive periodic
			"-450.00, -1.00", // negative periodic
			"180, 0.00", // positive periodic zero
			"-180, 0.00", // negative periodic zero
			"-270.00, 1.00", // negative x -> positive y
			"270.00, -1.00", // positive x -> negative y
			"270.00, -1.00" // same input = same result
	})
	void test(double input, double expected) {
		double result = Sin.solve(input);
		if ((expected + CALC_ERROR) <= result || result <= (expected - CALC_ERROR))
			fail();
	}

	@Test
	void infinityTest() {
		double positiveInf = Double.POSITIVE_INFINITY;
		double negativeInf = Double.NEGATIVE_INFINITY;

		try {
			Double positiveResult = Sin.solve(positiveInf);
			Double negativeResult = Sin.solve(negativeInf);

			Assert.isTrue(
					Double.isNaN(positiveResult) &&
							Double.isNaN(negativeResult),
					"");
		} catch (Exception e) {
			fail();
		}
	}
}
