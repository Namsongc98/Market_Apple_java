package com.example.market_apple.Controller;

import com.example.market_apple.Service.ProductService;
import com.example.market_apple.Entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import com.example.market_apple.Dto.BaseResponse;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    // Tạo sản phẩm mới
    @PostMapping
    public ResponseEntity<BaseResponse<Product>> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.ok(BaseResponse.success(200, "Post Success", createdProduct));
    }

    @GetMapping("/page")
    public ResponseEntity<BaseResponse<Page<Product>>> getAllProductsFollowPage(@PageableDefault(size = 10) Pageable pageable) {
        Page<Product> products = productService.getAllProductsFollowPage(pageable);
        return   ResponseEntity.ok(BaseResponse.success(200, "Get Success", products));
    }

    // Lấy danh sách tất cả sản phẩm
    @GetMapping
    public ResponseEntity<BaseResponse<List<Product>>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(BaseResponse.success(200,"Get Success", products));
    }

    // Lấy sản phẩm theo ID
    @GetMapping("/product_id/{id}")
    public ResponseEntity<BaseResponse<Product>> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(BaseResponse.success(200, "Get Success", product ));
    }

    // Cập nhật sản phẩm
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Product>> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Product updatedProduct = productService.updateProduct(id, productDetails);
        return ResponseEntity.ok(BaseResponse.success(200, "Put Success", updatedProduct));
    }

    // Xóa sản phẩm
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(BaseResponse.success(204,"Product deleted successfully", null));
    }
}
