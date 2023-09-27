package pojo;

public class OrderRequest {
    private String[] ingredients;

    public OrderRequest() {
    }

    public OrderRequest(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
}
