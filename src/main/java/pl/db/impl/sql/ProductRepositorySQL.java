package pl.db.impl.sql;

import org.springframework.stereotype.Component;
import pl.db.IProductRepository;
import pl.model.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductRepositorySQL implements IProductRepository {
    private final String GET_USER_BY_LOGIN_SQL = "SELECT * FROM products";
    private final String CHECK_QUANTITY_SQL = "SELECT quantity FROM products WHERE id = ?";
    private final String UPDATE_QUANTITY_SQL = "UPDATE products SET quantity = quantity - 1 WHERE id = ? AND quantity > 0";
    private final String GET_QUANTITY_SQL = "SELECT quantity FROM products WHERE id = ?";

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();

        try (PreparedStatement stmt = Constants.CONNECTION.prepareStatement(GET_USER_BY_LOGIN_SQL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving products from the database");
        }
        return products;
    }

    @Override
    public void decreaseProductQuantity(int id) {
        try (PreparedStatement checkStmt = Constants.CONNECTION.prepareStatement(CHECK_QUANTITY_SQL);
             PreparedStatement updateStmt = Constants.CONNECTION.prepareStatement(UPDATE_QUANTITY_SQL)) {
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int quantity = rs.getInt("quantity");

                if (quantity > 0) {
                    updateStmt.setInt(1, id);
                    updateStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while decreasing product quantity for product ID");
        }
    }

    @Override
    public int getQuantityById(int id) {
        try (PreparedStatement stmt = Constants.CONNECTION.prepareStatement(GET_QUANTITY_SQL)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("quantity");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while fetching quantity for product ID ");
        }
        return 0;
    }
}
