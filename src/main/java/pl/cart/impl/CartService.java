package pl.cart.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.cart.ICartService;
import pl.db.IProductRepository;
import pl.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CartService implements ICartService {
    private final IProductRepository productRepository;
    private final List<Product> cart = new ArrayList<>();

    @Override
    public boolean addToCart(String productId) {
        Product product = this.productRepository.getAll().stream()
                .filter(p -> String.valueOf(p.getId()).equals(productId))
                .findFirst()
                .orElse(null);

        if (product != null) {
            long countInCart = cart.stream()
                    .filter(p -> p.getId() == product.getId())
                    .count();

            int availableQuantity = this.productRepository.getQuantityById(product.getId());

            if (countInCart < availableQuantity) {
                cart.add(product);
                return true;
            } else {
                System.out.println("Cannot add more. Only " + availableQuantity + " left in stock.");
                return false;
            }
        }
        return false;
    }

    @Override
    public void showCart() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            Map<Integer, Long> productCount = cart.stream()
                    .collect(Collectors.groupingBy(Product::getId, Collectors.counting()));

            double totalCartPrice = productCount.entrySet().stream()
                    .mapToDouble(entry -> {
                        Product product = this.productRepository.getAll().stream()
                                .filter(p -> p.getId() == entry.getKey())
                                .findFirst()
                                .orElse(null);

                        if (product != null) {
                            long quantity = entry.getValue();
                            return product.getPrice() * quantity;
                        }
                        return 0;
                    })
                    .sum();

            productCount.forEach((id, quantity) -> {
                Product product = this.productRepository.getAll().stream()
                        .filter(p -> p.getId() == id)
                        .findFirst()
                        .orElse(null);
                if (product != null) {
                    double totalPrice = product.getPrice() * quantity;
                    System.out.println(product.getName() + " | Quantity: " + quantity + " | Total: " + String.format("%.2f", totalPrice) + " zł");
                }
            });
            System.out.println("Total cart price: " + String.format("%.2f", totalCartPrice) + " zł");
        }
    }

    @Override
    public boolean completePurchase() {
        if (cart.isEmpty()) {
            return false;
        }
        for (Product product : cart) {
            this.productRepository.decreaseProductQuantity(product.getId());
        }
        cart.clear();

        return true;
    }
}
