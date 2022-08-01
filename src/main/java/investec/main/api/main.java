package investec.main.api;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class main {

    private final String  url = "https://swapi.dev/api/people/";
    private String json;
    private String color;
    private String name;
    private int count;

    @Test
    public void test()
    {

        json = given().when().get(url).asString();

        JsonPath path = new JsonPath(json);

        //get the number of results
        count = path.getInt("results.size()");

        //loop through the number results
        for (int i = 0; i < count; i++) {
            // get the names
             name = path.getString("results.name["+i+"]");

             //check if the name exists
            if(name.equals("R2-D2"))
            {
                //get the color based on the name and exist the loop
                color = path.getString("results.skin_color["+i+"]");
                break;
            }
        }
        //assert Colors
        Assert.assertTrue(color.equals("white, blue"));
    }
}
