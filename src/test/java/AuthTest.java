import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.Login;
import pojo.SuccessAuthResponse;
import pojo.UserFields;

import static org.hamcrest.Matchers.equalTo;

public class AuthTest extends Base{
    final String wrongPassword = "неправильный пароль";

    @Test
    @DisplayName("Логин пользователя")
    public void success() {
        this.createUser();
        Response response = this.auth(new Login(email, password));
        response.then().statusCode(200);
        response.then().assertThat().body("success", equalTo(true));
        this.tokens = response.body().as(SuccessAuthResponse.class);
        Assert.assertEquals(this.tokens.getUser().getEmail(), email);
        Assert.assertEquals(this.tokens.getUser().getName(), name);
    }

    @Test
    @DisplayName("Логин c неверным логином и паролем")
    public void wrongPassword() {
        Response response = this.auth(new Login(email, wrongPassword));
        response.then().statusCode(401);
        response.then().assertThat().body("success", equalTo(false));
        response.then().assertThat().body("message", equalTo("email or password are incorrect"));
    }
}
