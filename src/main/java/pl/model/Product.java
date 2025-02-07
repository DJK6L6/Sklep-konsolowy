package pl.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private int id;
    private String name;
    private double price;
    private int quantity;

    @Override
    public String toString() {
        return new StringBuilder()
                .append("ID: ")
                .append(id)
                .append(", Name: ").
                append(name)
                .append(", Price: ")
                .append(String.format("%.2f zł", price))
                .append(", Quantity: ")
                .append(quantity)
                .toString();
    }
}