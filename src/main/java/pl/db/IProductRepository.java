package pl.db;

import pl.model.Product;

import java.util.List;

public interface IProductRepository {
    List<Product> getAll();

    void decreaseProductQuantity(int id);

    int getQuantityById(int id);
}
