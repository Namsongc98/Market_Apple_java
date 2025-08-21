package com.example.market_apple.Service;

import java.util.List;
import com.example.market_apple.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product createProduct(Product product);
    Product updateProduct(Long id, Product productDetails);
    void deleteProduct(Long id);
    Product getProductById(Long id);
    List<Product> getAllProducts();
    Page<Product> getAllProductsFollowPage(Pageable pageable);
}
