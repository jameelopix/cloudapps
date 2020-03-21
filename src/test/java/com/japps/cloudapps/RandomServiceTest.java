package com.japps.cloudapps;

import org.junit.jupiter.api.Test;

import cloudapp.service.RandomService;

//@SpringBootTest
public class RandomServiceTest {

	@Test
	public void testGenerateKey() {
		// System.out.println("RandomServiceTest.testGenerateKey()");
		System.out.println(RandomService.generateKey());
		System.out.println(RandomService.generateKey(RandomService.PREFIX_APP));
		System.out.println(RandomService.generateKey(RandomService.PREFIX_TABLE));
		System.out.println(RandomService.generateKey(RandomService.PREFIX_COLUMN));
	}

}
