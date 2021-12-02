package com.ngshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class SmartHRMSApplicationTests {

	private Calculator c = new Calculator();

	@Test
	void contextLoads() {
	}

	@Test
	void testSum(){

		//expected result
		int expectedResult=17;

		//actual
		int actualResult = c.doSum(12,3,2);
		assertThat(actualResult).isEqualTo(expectedResult);
	}

	@Test
	void testProduct(){
		//expected
		int expectedResult=6;

		//actual
		int actualResult = c.doProduct(3,2);
		assertThat(actualResult).isEqualTo(expectedResult);
	}

	@Test
	void testCompareTwoNum(){
		//actual result
		Boolean actualResult = c.compareTwoNum(3,3);
		assertThat(actualResult).isTrue();
	}

}

