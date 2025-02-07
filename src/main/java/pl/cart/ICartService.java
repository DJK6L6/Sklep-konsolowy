package pl.cart;

public interface ICartService {
    boolean addToCart(String productId);

    void showCart();

    boolean completePurchase();
}
