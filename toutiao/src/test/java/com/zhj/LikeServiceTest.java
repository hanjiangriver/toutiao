package com.zhj;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ToutiaoApplication.class)
public class LikeServiceTest {

	@Test
	public void testLike(){
		System.out.println("testLike");
		
	}
	@Test(expected=IllegalArgumentException.class)
	//@Test
	public void testLikeB(){
		throw new IllegalArgumentException("dsfaf");
		//System.out.println("testLikeB");
	}
	@Before
	public void setup(){
		System.out.println("setup");
	}
	@After
	public void tearDown(){
		System.out.println("tearDown");
	}
	
	@BeforeClass
	public static void beforeClass(){
		System.out.println("beforeClass");
	}
	@AfterClass
	public static void afterClass(){
		System.out.println("afterClass");
	}
	
}
