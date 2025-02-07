package pl.gui.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.cart.ICartService;
import pl.db.IProductRepository;
import pl.gui.IGUI;
import pl.model.Product;
import pl.model.User;

import java.util.Scanner;

@RequiredArgsConstructor
@Component
public class GUI implements IGUI {
    private final Scanner scanner;
    private final IProductRepository productRepository;
    private final ICartService cartService;

    @Override
    public String showMenuAndReadChoice() {
        System.out.println("1. List products");
        System.out.println("2. Add product to cart");
        System.out.println("3. View cart");
        System.out.println("4. Buy");
        System.out.println("5. Exit");
        return scanner.nextLine();
    }

    @Override
    public void showProducts() {
        System.out.println("Available products:");
        for (Product product : this.productRepository.getAll()) {
            System.out.println(product);
        }
    }

    @Override
    public String readProductId() {
        System.out.println("Enter product ID:");
        return scanner.nextLine();
    }

    @Override
    public void showCart() {
        this.cartService.showCart();
    }

    @Override
    public void showResultMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String askLoginOrRegister() {
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        return scanner.nextLine();
    }

    @Override
    public User aksForCredentials() {
        User user = new User();
        System.out.println("Enter login:");
        user.setLogin(scanner.nextLine());
        System.out.println("Enter password:");
        user.setPassword(scanner.nextLine());
        return user;
    }

    @Override
    public void Buy() {
        showResultMessage(this.cartService.completePurchase() ? "Your purchase has been completed successfully!" : "Your cart is empty! You cannot proceed with the purchase.");
    }

    @Override
    public String askForLogin() {
        System.out.println("Enter your login:");
        return scanner.nextLine();
    }

    @Override
    public String askForPassword() {
        System.out.println("Enter your password:");
        return scanner.nextLine();
    }

    @Override
    public void addProductToCart(String productId) {
        showResultMessage(this.cartService.addToCart(productId) ? "Product added to cart!" : "Product not found!");
    }
}
