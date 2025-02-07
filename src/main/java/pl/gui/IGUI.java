package pl.gui;

import pl.model.User;

public interface IGUI {
    String showMenuAndReadChoice();

    void showProducts();

    void showCart();

    String readProductId();

    void showResultMessage(String message);

    String askLoginOrRegister();

    void Buy();

    User aksForCredentials();

    String askForLogin();

    String askForPassword();

    void addProductToCart(String s);
}
