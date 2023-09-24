package pojo;

public class IngredientsResponse {
    private boolean success;

    private Ingredient[] data;

    public IngredientsResponse() {
    }

    public IngredientsResponse(boolean success, Ingredient[] data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Ingredient[] getData() {
        return data;
    }

    public void setData(Ingredient[] data) {
        this.data = data;
    }
}
