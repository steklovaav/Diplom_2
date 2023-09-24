import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pojo.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.equalTo;

public class OrderTest extends Base {

    private Ingredient[] ingredients;
    private final int firstIngredient = 0;
    private final int secondIngredient = 1;

    @Before
    public void getIngredients() {
        ingredients = this.ingredients();
    }

    @Test
    @DisplayName("Успешный заказ")
    public void successOrder() {
        this.createUser();
        Response response = this.createOrder(new OrderRequest(new String[]{ingredients[firstIngredient].get_id(), ingredients[secondIngredient].get_id()}), true);
        response.then().statusCode(200);
        response.then().assertThat().body("success", equalTo(true));
        response.body().as(OrderReponse.class);
    }

    @Test
    @DisplayName("Заказ без авторизации")
    public void noAuth() {
        this.createUser();
        Response response = this.createOrder(new OrderRequest(new String[]{ingredients[firstIngredient].get_id(), ingredients[secondIngredient].get_id()}), false);
        response.then().statusCode(200);
        response.then().assertThat().body("success", equalTo(true));
        response.body().as(OrderReponse.class);
    }

    @Test
    @DisplayName("Заказ с невалидным ингридиентом")
    public void notValid() {
        this.createUser();
        Response response = this.createOrder(new OrderRequest(new String[]{"невалидный хеш ингридиента"}), false);
        response.then().statusCode(500);
    }

    @Test
    @DisplayName("Заказ с пустым списком ингридиентов")
    public void empty() {
        this.createUser();
        Response response = this.createOrder(new OrderRequest(new String[]{}), true);
        response.then().statusCode(400);
        response.then().assertThat().body("success", equalTo(false));
        response.then().assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Успешное получение списка заказов")
    public void successOrderList() {
        this.successOrder();

        Response response = given()
                .header("Content-Type", "application/json")
                .header("authorization", tokens.getAccessToken())
                .get("/api/orders");
        response.then().statusCode(200);
        response.then().assertThat().body("success", equalTo(true));
        OrderListResponse list = response.body().as(OrderListResponse.class);
        Assert.assertEquals(list.getOrders().length, 1);
        Order order = list.getOrders()[0];
        Assert.assertThat(order.getIngredients(), arrayContaining(ingredients[firstIngredient].get_id(), ingredients[secondIngredient].get_id()));
    }

    @Test
    @DisplayName("Список заказов без авторизации")
    public void noAuthOrderList() {
        this.createUser();
        Response response = given()
                .header("Content-Type", "application/json")
                .get("/api/orders");
        response.then().statusCode(401);
        response.then().assertThat().body("success", equalTo(false));
        response.then().assertThat().body("message", equalTo("You should be authorised"));
        response.body().as(OrderListResponse.class);
    }
}
