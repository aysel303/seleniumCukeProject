package api_tests;

import static org.testng.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PetStoreTest {
	String url;
	Response response;
	
  @Test
  public void findByStaus() {
	  url = "https://petstore.swagger.io/v2/pet/findByStatus";
	  
	  //request URL with parameters
	  response = given().queryParam("status", "available").accept(ContentType.JSON)
	  .when().get(url);
	  
	  System.out.println(response.getContentType());
	  System.out.println(response.getStatusCode());
	  System.out.println(response.asPrettyString());
	  
	  //validation with RestAssured built in validation
	 response.then().statusCode(200)
	  .and().contentType("application/json");
	 
	 //validating with testNG assertion
	 assertEquals(response.getStatusCode(), 200);
	 assertEquals(response.contentType(), "application/json");
  }
  
  @Test
  public void findByInvalidUrl() {
	  
	  url = "https://petstore.swagger.io/v2/pet/findBy";
	  
	  response = given().queryParam("status", "avilable").accept(ContentType.JSON) 
			  .when().get(url);
	  
	  response.then().assertThat().statusCode(404)
	  .and().contentType("application/json");
	  
	  response.prettyPrint();
	  assertEquals(response.statusCode(), 404);
	  assertEquals(response.contentType(), "application/json");
  }
  
  @Test
  public void findById() {
	  int id = 227007;
	  url = "https://petstore.swagger.io/v2/pet/";
	  
	  response = given().accept(ContentType.JSON)
			  .when().get(url + id);
	  
	  assertEquals(response.statusCode(), 200);
	  assertEquals(response.contentType(), "application/json");
	  
	  int actualId =  response.path("id");
	  System.out.println("Actual ID is: " + actualId);
	  
	  String actualName = response.path("name");
	  System.out.println("Actual name is: " + actualName);
	  
	  String actualStatus = response.path("status");
	  System.out.println("Actual status is: " + actualStatus);
	  
	  assertEquals(actualId, id);
	  assertEquals(actualName, "booboo");
	  assertEquals(actualStatus, "available");
	  
	  int categoryId = response.path("category.id");
	  String categoryName = response.path("category.name");
	  
	  assertEquals(categoryId, 5);
	  assertEquals(categoryName, "dog");
	  
	  int tagsId = response.path("tags[0].id");
	  String tagsName = response.path("tags[0].name");
	  
	  System.out.println("Tag Id: " + tagsId);
	  System.out.println("Tag name: " + tagsName);
	  
	  assertEquals(tagsId, 1234);
	  assertEquals(tagsName, "booboo");
	  
  }
  
  @Test
  public void findByInvalidId() {
	  url = "https://petstore.swagger.io/v2/pet/";
	  int invalidId = 2792200;
	  
	  response = given().accept(ContentType.JSON)
	  .when().get(url + invalidId);
	  
	  assertEquals(response.statusCode(), 404);
	  assertEquals(response.contentType(), "application/json");
	  
	  assertEquals(response.path("message"), "Pet not found");

  }
  
  @Test
  public void addNewPet() {
	  url = "https://petstore.swagger.io/v2/pet/";
	  String requestBody = "{\n"
	  		+ "  \"id\": 55667799,\n"
	  		+ "  \"category\": {\n"
	  		+ "    \"id\": 0,\n"
	  		+ "    \"name\": \"string\"\n"
	  		+ "  },\n"
	  		+ "  \"name\": \"doggie\",\n"
	  		+ "  \"photoUrls\": [\n"
	  		+ "    \"string\"\n"
	  		+ "  ],\n"
	  		+ "  \"tags\": [\n"
	  		+ "    {\n"
	  		+ "      \"id\": 0,\n"
	  		+ "      \"name\": \"string\"\n"
	  		+ "    }\n"
	  		+ "  ],\n"
	  		+ "  \"status\"";
	  
	  
	  response = given().contentType("application/json").accept(ContentType.JSON).body("requestBody")
	  .when().post(url);
	  
	  assertEquals(response.statusCode(), 200);
	  assertEquals(response.contentType(), "application/json");
	  
	  String responseBody = response.body().asString();
	  assertEquals(responseBody, requestBody);
  }
  
  @Test
  public void addNewPetWithJsonFile() throws IOException {
	  url = "https://petstore.swagger.io/v2/pet/";
	 File requestBody = new File("./src/test/resources/jsonFiles/addNewPet.json");
	  
	  response = given().contentType("application/json").accept(ContentType.JSON)
			  .body(requestBody)
			  .when().post(url);
	  
	  assertEquals(response.statusCode(), 200);
	  assertEquals(response.contentType(), "application/json");
	  
	  String responseBody = response.body().asPrettyString();
	  response.body().prettyPrint();
	  
	  String content = new String(Files.readAllBytes(Paths.get("./src/test/resources/jsonFiles/addNewPet.json")));
	  assertEquals(responseBody, content);
  }
  
  @Test
  public void addNewPetWithChainValidation() throws IOException {
	  url = "https://petstore.swagger.io/v2/pet/";
	 File requestBody = new File("./src/test/resources/jsonFiles/addNewPetChainValidation.json");
	  
	  given().contentType("application/json").accept(ContentType.JSON)
			  .body(requestBody)
			  .when().post(url)
			  .then().assertThat().statusCode(200)
			  .and().assertThat().contentType("application/json")
			  .and().assertThat().body("id", equalTo(356098))
			  .and().assertThat().body("category.id", equalTo(2))
			  .and().assertThat().body("category.name", equalTo("kitten"))
			  .and().assertThat().body("name", equalTo("roar"))
			  .and().assertThat().body("tags[0].id", equalTo(12))
			  .and().assertThat().body("tags[0].name", equalTo("roar"));
	  
	 /* assertEquals(response.statusCode(), 200);
	  assertEquals(response.contentType(), "application/json");
	  
	  String responseBody = response.body().asPrettyString();
	  response.body().prettyPrint();
	  
	  String content = new String(Files.readAllBytes(Paths.get("./src/test/resources/jsonFiles/addNewPet.json")));
	  assertEquals(responseBody, content);*/
  }
  
  /*
	 * Scenario: As a user, I should not be able to perform POST request to invalid data structure
	 * Given I have the POST request URL and invalid request body 
	 * When I perform POST request to URL with request body 
	 * Then Response status code should be 400 invalid input 
	 * And content type is application.json
	 * And message should be "bad input"
	 */
  
  @Test
  public void addNewPetWithInvalidJsonData() throws IOException {
	  url = "https://petstore.swagger.io/v2/pet/";
	 File requestBody = new File("./src/test/resources/jsonFiles/addNewPetInvalid.json");
	  
	  response = given().contentType("application/json").accept(ContentType.JSON)
			  .body(requestBody)
			  .when().post(url);
	  response.body().prettyPrint();
	  response.then().assertThat().statusCode(500)
	  .and().assertThat().contentType("application/json")
	  .and().assertThat().body("message", equalTo("something bad happened"));
	  
	
  }
  
  @Test
  public void updatePet() {
	  url = "https://petstore.swagger.io/v2/pet/";
		 File requestBody = new File("./src/test/resources/jsonFiles/putRequestBody.json");
	  
		response =  given().accept(ContentType.JSON).contentType("application/json")
		 .body(requestBody)
		 .when().put(url).andReturn();
		
		response.getBody().prettyPrint();
	    
	    int scode = response.statusCode();
		String contentType = response.getContentType();
		System.out.println("content type :" + contentType);
		System.out.println("status code :" + scode);
		assertEquals(scode, 200);
		assertEquals(contentType, "application/json");
		
		//response.then().assertThat().statusCode(200)
		//.and().assertThat().contentType("application/json")
		//.and().assertThat().body("id", equalTo(356098))
		//.and().assertThat().body("photoUrls[0]", equalTo("www.amazon1.com"));
  }
  
  @Test
  public void deletePet() {
	  //myauth_key
	  url = "https://petstore.swagger.io/v2/pet/";
	  int id = 135;
	  response = given().header("api_key", "myauth_key").accept(ContentType.JSON)
	  .when()
	  .delete(url + id).andReturn();
	  
	  response.getBody().prettyPrint();
	  
	  
	  int scode = response.statusCode();
		String contentType = response.getContentType();
		System.out.println("content type :" + contentType);
		System.out.println("status code :" + scode);
		assertEquals(scode, 200);
		assertEquals(contentType, "application/json");
		
		assertEquals(response.getBody().path("message"), id + "");
  }
  
  @Test
  public void deletePetWithOath2() {
	
	  url = "https://petstore.swagger.io/v2/pet/";
	  int id = 135;
	  response = given().accept(ContentType.JSON).auth().oauth2("helil_primetech")
	  .when()
	  .delete(url + id);
	  response.body().prettyPrint();
  }
 }
  

