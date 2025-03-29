package com.example.demo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class DemoApplicationTests {

	static double CALC_ERROR = 1;

	static Logarithm logMock;
	static Trigonometry trigMock;
	static BasicLogarithm basicLogarithm;
	static BasicTrigonometry basicTrigonometry;

	@BeforeAll
	static void mockAll() {
		logMock = Mockito.mock(Logarithm.class);
		trigMock = Mockito.mock(Trigonometry.class);
		basicLogarithm = Mockito.mock(BasicLogarithm.class);
		basicTrigonometry = Mockito.mock(BasicTrigonometry.class);
	}

	@ParameterizedTest
	@CsvSource({
			"-3.14,0.00,1.00,0.00,0.00,0.00,0.00",
			"2.00,0.00,0.00,0.69314718,1.00,0.430676558,0.6571538335"
	})
	void allMocked(double x,
			double mockSin,
			double mockSec,
			double mockLn,
			double mockLog2,
			double mockLog5,
			double expected) {
		Mockito.when(trigMock.sin(x)).thenReturn(mockSin);
		Mockito.when(trigMock.sec(x)).thenReturn(mockSec);
		Mockito.when(logMock.ln(x)).thenReturn(mockLn);
		Mockito.when(logMock.log2(x)).thenReturn(mockLog2);
		Mockito.when(logMock.log5(x)).thenReturn(mockLog5);

		FunctionSystem functionSystem = new FunctionSystem(trigMock, logMock);
		double result = functionSystem.count(x);

		Assert.isTrue(result <= (expected + CALC_ERROR) && result >= (expected - CALC_ERROR), "");
	}

	@ParameterizedTest
	@CsvSource({
			"-3.14,0.00,1.00,0.00,0.00,0.00,0.00",
			"2.00,0.00,0.00,0.69314718,0.69314718,1.60943791,0.6571538335"
	})
	void mockedTrigAndLn(double x,
			double mockSin,
			double mockSec,
			double mockLn,
			double mockLn2,
			double mockLn5,
			double expected) {
		Mockito.when(trigMock.sin(x)).thenReturn(mockSin);
		Mockito.when(trigMock.sec(x)).thenReturn(mockSec);
		Mockito.when(basicLogarithm.ln(x)).thenReturn(mockLn);
		Mockito.when(basicLogarithm.ln(2.00)).thenReturn(mockLn2);
		Mockito.when(basicLogarithm.ln(5.00)).thenReturn(mockLn5);

		FunctionSystem functionSystem = new FunctionSystem(trigMock, new Logarithm(basicLogarithm));
		double result = functionSystem.count(x);

		Assert.isTrue(result <= (expected + CALC_ERROR) && result >= (expected - CALC_ERROR), "");
	}

	@ParameterizedTest
	@CsvSource({
			"-3.14,0.00,1.00,0.00,0.00,0.00,0.00",
			"2.00,0.00,0.00,0.69314718,0.69314718,1.60943791,0.6571538335"
	})
	void mockedTrig(double x,
			double mockSin,
			double mockSec,
			double mockLn,
			double mockLn2,
			double mockLn5,
			double expected) {
		Mockito.when(trigMock.sin(x)).thenReturn(mockSin);
		Mockito.when(trigMock.sec(x)).thenReturn(mockSec);

		FunctionSystem functionSystem = new FunctionSystem(trigMock, new Logarithm(new BasicLogarithm()));
		double result = functionSystem.count(x);

		Assert.isTrue(result <= (expected + CALC_ERROR) && result >= (expected - CALC_ERROR), "");
	}

	@ParameterizedTest
	@CsvSource({
			"-3.14,0.00,1.00,0.00,0.00,0.00,0.00",
			"2.00,0.00,0.00,0.69314718,0.69314718,1.60943791,0.6571538335"
	})
	void mockedSin(double x,
			double mockSin,
			double mockSec,
			double mockLn,
			double mockLn2,
			double mockLn5,
			double expected) {
		Mockito.when(basicTrigonometry.sin(x)).thenReturn(mockSin);

		FunctionSystem functionSystem = new FunctionSystem(new Trigonometry(basicTrigonometry),
				new Logarithm(new BasicLogarithm()));
		double result = functionSystem.count(x);

		Assert.isTrue(result <= (expected + CALC_ERROR) && result >= (expected - CALC_ERROR), "");
	}

	@ParameterizedTest
	@CsvSource({
			"-3.14,0.00,1.00,0.00,0.00,0.00,0.00",
			"2.00,0.00,0.00,0.69314718,0.69314718,1.60943791,0.6571538335"
	})
	void noMock(double x,
			double mockSin,
			double mockSec,
			double mockLn,
			double mockLn2,
			double mockLn5,
			double expected) {

		FunctionSystem functionSystem = new FunctionSystem(new Trigonometry(new BasicTrigonometry()),
				new Logarithm(new BasicLogarithm()));
		double result = functionSystem.count(x);

		Assert.isTrue(result <= (expected + CALC_ERROR) && result >= (expected - CALC_ERROR), "");
	}

	@Test
	void csvTest() throws IOException {
		CSV csv = new CSV();
		FunctionSystem functionSystem = new FunctionSystem(new Trigonometry(new BasicTrigonometry()),
				new Logarithm(new BasicLogarithm()));
		String result = csv.csv(-2.5, 0.001, 0.0, functionSystem);

		BufferedWriter writer = new BufferedWriter(new FileWriter("file.csv"));
		writer.write(result);
		writer.close();
	}
}
