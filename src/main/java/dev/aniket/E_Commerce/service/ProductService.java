package dev.aniket.E_Commerce.service;

import dev.aniket.E_Commerce.model.Product;
import dev.aniket.E_Commerce.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final Logger LOGGER = LoggerFactory.getLogger(Product.class);
    private ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Optional<Product> getProductById(Integer id) {
        return repository.findById(id);
    }

    public Product addProductInDB(Product product, MultipartFile imageFile) throws IOException {
        LOGGER.info(String.valueOf(imageFile));
        //add image to the product
        product.setImageName(imageFile.getOriginalFilename()); //TODO getOriginalFilename() -> its return the file name
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        //add the product
        return repository.save(product);
    }

    public byte[] getImageByProductId(Integer id) throws Exception {
        Optional<Product> product = repository.findById(id);
        if (product.isEmpty())
            throw new Exception("Product not found");
        //product is present
        return product.get().getImageData();
    }

    public String getContentTypeByProductId(Integer id) throws Exception {
        Optional<Product> product = repository.findById(id);

        if (product.isEmpty())
            throw new Exception("Product not found");
        //image content type
        return product.get().getImageType();
    }

    public Product updateProduct(Product product, MultipartFile imageFile) throws Exception {
        //check the product is available or not
        Optional<Product> isProductExist = getProductById(product.getId());
        if (isProductExist.isEmpty()) throw new Exception("Product is not found");

        //set the image to the product
        product.setImageType(imageFile.getContentType());
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageData(imageFile.getBytes());
        //update the product
        return repository.save(product);
    }

    public void deleteProductById(Integer id) throws Exception {
        //check the product is available or not
        Optional<Product> isProductExist = getProductById(id);
        if (isProductExist.isEmpty()) throw new Exception("Product is not found");

        //delete the product by id
        repository.deleteById(id);
    }

    public List<Product> searchProductsByKeyword(String keyword) {
        List<Product> products = repository.searchProducts(keyword);
//        System.out.println("Print: " + products);
        return products;
    }
}
