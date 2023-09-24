package pojo;

public class Order {
    private String[] ingredients;
    private String _id;
    private String status;
    private int number;
    private String createdAt;
    private String updatedAt;

    public Order() {
    }

    public Order(String[] ingredients, String _id, String status, int number, String createdAt, String updatedAt) {
        this.ingredients = ingredients;
        this._id = _id;
        this.status = status;
        this.number = number;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
