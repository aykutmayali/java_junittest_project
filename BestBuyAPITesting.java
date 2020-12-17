package tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.*;

public class BestBuyAPITesting {
	
	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "http://localhost/";
		RestAssured.port = 3030;
	}
	
	@Test
	public void verifyGetProduct() {
		RestAssured.given().when().get("/products").then().statusCode(200);
	}
	
	@Test
	public void verifyGetProductWithLimit() {
		RestAssured.given().when().queryParam("$limit", 5).get("/products").then().log().all().statusCode(200);
	}
	
	@Test	
	public void verifyGetProductWithSpecificId(){
		//RestAssured.baseURI= "http://localhost/";
		//RestAssured.port = 3030;
		
		RestAssured.given().when().param("id",43900 ).get("/products").then().log().all();
	}
}
