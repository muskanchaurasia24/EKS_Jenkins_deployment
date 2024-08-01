package com.first;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AddControllerTest {
	

	@Test
	public void testAdd() {
		AddController addctrl = new AddController();
		double actual = addctrl.add(10,10);
		double expected = 20;
		assertEquals(expected,actual,0);
	   	}

	@Test
	public void testSub() {
		AddController addctrl = new AddController();
		double actual = addctrl.sub(30,10);
		double expected = 20;
		assertEquals(expected , actual,0);
	   	}
	

	@Test
	public void testMultiply() {
		AddController addctrl = new AddController();
		double actual = addctrl.mul(10,10);
		double expected = 100;
		assertEquals(expected , actual,0);
	   	
	}

	@Test
	public void testDivide() {
		AddController addctrl = new AddController();
		double actual = addctrl.div(10,10);
		double expected = 1;
		assertEquals(expected , actual,0);
	   	
	}

}
