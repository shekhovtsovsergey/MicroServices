package com.shekhovtsov.core.repository.specifications;


import com.shekhovtsov.core.model.Product;
import java.util.function.Predicate;

/*public class ProductsSpecifications {

    public static Specification<Product> priceGreaterOrEqualsThan(Integer price) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> priceLessThanOrEqualsThan(Integer price) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> titleLike(String titlePart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", titlePart));
    }

}*/


/*

public class ProductsSpecifications {
    public static Predicate<Product> priceGreaterOrEqualsThan(Integer price) {
        return product -> product.getPrice() >= price;
    }
    public static Predicate<Product> priceLessThanOrEqualsThan(Integer price) {
        return product -> product.getPrice() <= price;
    }
    public static Predicate<Product> titleLike(String titlePart) {
        return product -> product.getTitle().contains(titlePart);
    }
}*/
