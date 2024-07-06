package dev.aniket.E_Commerce.controller;

import dev.aniket.E_Commerce.model.Product;
import dev.aniket.E_Commerce.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173/")
public class ProductController {
    private final ProductService service;
    private final Logger LOGGER = LoggerFactory.getLogger(Product.class);

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer id) {
        Optional<Product> product = service.getProductById(id);

        //TODO product is not present in the DB
        if (product.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile) {
        System.out.println(product);
        try {
            Product afterAddProduct = service.addProductInDB(product, imageFile);
            return new ResponseEntity<>(afterAddProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Integer id,
                                                @RequestPart Product product,
                                                @RequestPart MultipartFile imageFile) {
        try {
            product.setId(id);
            Product updatedProduct = service.updateProduct(product, imageFile);
            if (updatedProduct != null)
                return new ResponseEntity<>("Update successfully", HttpStatus.OK);
            else
                return new ResponseEntity<>("Update failed", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        try {
            service.deleteProductById(id);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    http://localhost:8080/api/products/search?keyword=${value} //TODO this is my front-end url
    //this keyword is not coming for the url, we are receives using the @RequestParam
    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        LOGGER.info("Search with: " + keyword);
        List<Product> products = service.searchProductsByKeyword(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
