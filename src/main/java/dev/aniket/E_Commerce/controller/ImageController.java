package dev.aniket.E_Commerce.controller;

import dev.aniket.E_Commerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class ImageController {
    private final ProductService service;

    @Autowired
    public ImageController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) {
        try {
            System.out.println("Try to get the image");
            byte[] imageDate = service.getImageByProductId(id);
            return new ResponseEntity<>(imageDate, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
