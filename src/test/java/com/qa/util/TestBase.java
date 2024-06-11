package com.qa.util;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class TestBase {
	
	public final static int RESPONSE_CODE_200 = 200;
	public final static int RESPONSE_CODE_201 = 201;
	public final static int RESPONSE_CODE_400 = 400;
	public final static int RESPONSE_CODE_401 = 401;
	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "http://localhost:8080/";
	}

}

