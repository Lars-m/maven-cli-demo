
import calc.Calculator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.parsing.Parser;
import static org.hamcrest.Matchers.*;

public class ApiIntegrationTest {

  Calculator calc = new Calculator();

  public ApiIntegrationTest() {

  }

  @BeforeClass
  public static void setUpBeforeAll() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = 8080;
    RestAssured.basePath = "/calc";
    RestAssured.defaultParser = Parser.JSON;
  }

  @Test
  public void serverIsRunning() {
    given().when().get().then().statusCode(200);
  }

  @Test
  public void addOperationWrongArguments() {
    given().pathParam("n1", 2).pathParam("n2", 2.2).
            when().get("/api/calculator/add/{n1}/{n2}").
            then().
            statusCode(400).
            body("code", equalTo(400));
  }

  @Test
  public void addOperation() {
    given().pathParam("n1", 2).pathParam("n2", 2).
            when().get("/api/calculator/add/{n1}/{n2}").
            then().
            statusCode(200).
            body("result", equalTo(4), "operation", equalTo("2 + 2"));
  }

  @Test
  public void addOperationv2() {
    given().
            when().get("/api/calculator/add/2/2").
            then().
            statusCode(200).
            body("result", equalTo(4), "operation", equalTo("2 + 2"));
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

}
