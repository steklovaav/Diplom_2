import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import pojo.*;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Base {
    final String email = "avsteklova@gmail.com";
    final String password = "password";
    final String name = "avsteklova";

    protected SuccessAuthResponse tokens;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    protected SuccessAuthResponse createUser() {
        return createUser(new UserFields(email, password, name));
    }

    protected SuccessAuthResponse createUser(UserFields userFields) {
        Response response = given()
                .header("Content-Type", "application/json")
                .body(userFields)
                .post("/api/auth/register");
        response.then().statusCode(200);
        response.then().assertThat().body("success", equalTo(true));
        this.tokens = response.body().as(SuccessAuthResponse.class);
        Assert.assertEquals(this.tokens.getUser().getEmail(), userFields.getEmail());
        Assert.assertEquals(this.tokens.getUser().getName(), userFields.getName());
        return this.tokens;
    }

    protected Response auth(Login login) {
        return given()
                .header("Content-Type", "application/json")
                .body(login)
                .post("/api/auth/login");
    }

    protected Ingredient[] ingredients() {
        Response response = given()
                .header("Content-Type", "application/json")
                .get("/api/ingredients");
        response.then().body("success", equalTo(true));
        return response.body().as(IngredientsResponse.class).getData();
    }

    protected Response createOrder(OrderRequest request, boolean auth) {
        RequestSpecification requestSpecification = given()
                .header("Content-Type", "application/json");
        if (auth) {
            requestSpecification = requestSpecification.header("authorization", tokens.getAccessToken());
        }
        return requestSpecification.body(request)
                .post("/api/orders");
    }


    @After
    public void clean() {
        if (this.tokens != null) {
            this.delete(tokens);
        }
    }

    protected void delete(SuccessAuthResponse tokens) {
        Response response = given().header("authorization", tokens.getAccessToken())
                .delete("/api/auth/user", Map.of());
        response.then().statusCode(202);
        response.then().assertThat().body("success", equalTo(true));
    }
}
