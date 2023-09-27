import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import pojo.UserFields;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest extends Base {

    @Test
    @DisplayName("Создание пользователя")
    public void success() {
        this.createUser();
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void alreadyExists() {
        this.success();
        Response response = given()
                .header("Content-Type", "application/json")
                .body(new UserFields(email, password, name))
                .post("/api/auth/register");
        response.then().statusCode(403);
        response.then().assertThat().body("success", equalTo(false));
        response.then().assertThat().body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Создание пользователя, без имени")
    public void withoutName() {
        Response response = given()
                .header("Content-Type", "application/json")
                .body(new UserFields(email, password, null))
                .post("/api/auth/register");
        response.then().statusCode(403);
        response.then().assertThat().body("success", equalTo(false));
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"));
    }
    @Test
    @DisplayName("Создание пользователя, без пароля")
    public void withoutPassword() {
        Response response = given()
                .header("Content-Type", "application/json")
                .body(new UserFields(email, null, name))
                .post("/api/auth/register");
        response.then().statusCode(403);
        response.then().assertThat().body("success", equalTo(false));
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"));
        response = given()
                .header("Content-Type", "application/json")
                .body(new UserFields(null, password, name))
                .post("/api/auth/register");
        response.then().statusCode(403);
        response.then().assertThat().body("success", equalTo(false));
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"));
    }
    @Test
    @DisplayName("Создание пользователя, без почты")
    public void withoutEmail() {
        Response response = given()
                .header("Content-Type", "application/json")
                .body(new UserFields(null, password, name))
                .post("/api/auth/register");
        response.then().statusCode(403);
        response.then().assertThat().body("success", equalTo(false));
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"));
    }


}
