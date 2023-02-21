package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;

public class TC_VideoGameApi {
    @Test(priority=1)
	public void test_getAllVideoGames() {
		
		given()
		.when()
		.get("http://localhost:8080/app/videogames")
		.then()
		.statusCode(200);
	}
    @Test(priority=2)
    public void test_addNewVideoGame() throws InterruptedException {
    	
    	HashMap data = new HashMap();
    	data.put("id", "103");
    	data.put("name", "human-man");
    	data.put("releaseDate", "2019-09-20T08:55:58.510Z");
    	data.put("reviewScore", "7");
    	data.put("category", "Adventure");
    	data.put("rating", "Universal");
    	
    	Response res=
    	given()
    	   .contentType("application/json")
    	   .body(data)
    	.when()
    	.post("http://localhost:8080/app/videogames")
    	.then()
    	.statusCode(200)
    	.log()
    	.body()
    	.extract()
    	.response();
    	String jsonString =res.asString();
    	Assert.assertEquals(jsonString.contains("Record Added Successfully"), true);
    	Thread.sleep(5000);
    }
    @Test(priority=3)
    public void test_getVideoGame() {
    	
    	given()
    	.when()
    	.get("http://localhost:8080/app/videogames/103")
    	.then()
    	.statusCode(200)
    	.body("videoGame.id", equalTo("103"))
    	.body("videoGame.name", equalTo("human-man"));
    }
   @Test(priority=4)
    public void test_updateVideoGame() throws InterruptedException {
    	HashMap data = new HashMap();
    	data.put("id", "103");
    	data.put("name", "super-man");
    	data.put("releaseDate", "2019-08-20T08:55:58.510Z");
    	data.put("reviewScore", "10");
    	data.put("category", "Adventure");
    	data.put("rating", "Universal");
    	given()
    	.contentType("application/json")
    	.body(data)
    	.when()
    	.put("http://localhost:8080/app/videogames/103")
    	.then()
    	.statusCode(200)
    	.log().body()
    	.body("videoGame.id", equalTo("103"))
    	.body("videoGame.name", equalTo("super-man"));
    	Thread.sleep(2000);
    	
    }
    @Test(priority=5)
    public void test_deleteVideogame() {
    	Response res=
    	given()
    	.when()
    	.delete("http://localhost:8080/app/videogames/4")
    	.then()
    	.statusCode(200)
    	.log().body()
    	.extract().response();
    	String jsonString =res.asString();
    	Assert.assertEquals(jsonString.contains("Record Deleted Successfully"), true);
    	
    	
    }
}
