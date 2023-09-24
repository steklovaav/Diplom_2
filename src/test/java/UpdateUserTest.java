import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import pojo.Login;
import pojo.SuccessAuthResponse;
import pojo.UserFields;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UpdateUserTest extends Base {
    final String newEmail = "newemailavsteklova@gmail.com";
    final String newPassword = "newPassword";
    final String newName = "newnameavsteklova";

    private SuccessAuthResponse secondUserToken;

    @Test
    @DisplayName("Смена всех полей профиля пользователя")
    public void success() {
        this.createUser();
        Response response = given()
                .header("Content-Type", "application/json")
                .header("authorization", tokens.getAccessToken())
                .body(new UserFields(newEmail, newPassword, newName))
                .patch("/api/auth/user");
        response.then().statusCode(200);
        response.then().assertThat().body("success", equalTo(true));
        response.then().assertThat().body("user.name", equalTo(newName));
        response.then().assertThat().body("user.email", equalTo(newEmail));

        response = this.auth(new Login(newEmail, newPassword));
        response.then().statusCode(200);
        response.then().assertThat().body("success", equalTo(true));
        this.tokens = response.body().as(SuccessAuthResponse.class);
        Assert.assertEquals(this.tokens.getUser().getEmail(), newEmail);
        Assert.assertEquals(this.tokens.getUser().getName(), newName);
    }

    @Test
    @DisplayName("Попытка сменить данные пользователя без авторизации")
    public void unauthorized() {
        this.createUser();
        Response response = given()
                .header("Content-Type", "application/json")
                .body(new UserFields(newEmail, newPassword, newName))
                .patch("/api/auth/user");
        response.then().statusCode(401);
        response.then().assertThat().body("success", equalTo(false));
        response.then().assertThat().body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Попытка передать почту, которая уже используется")
    public void emailAlreadyExists() {
        secondUserToken = this.createUser(new UserFields(newEmail, newPassword, newName));
        this.createUser();
        Response response = given()
                .header("Content-Type", "application/json")
                .header("authorization", secondUserToken.getAccessToken())
                .body(new UserFields(email, newPassword, newName))
                .patch("/api/auth/user");
        response.then().statusCode(403);
        response.then().assertThat().body("success", equalTo(false));
        response.then().assertThat().body("message", equalTo("User with such email already exists"));
    }

    @After
    public void clean() {
        if (this.secondUserToken != null) {
            this.delete(secondUserToken);
        }
        super.clean();
    }
}
