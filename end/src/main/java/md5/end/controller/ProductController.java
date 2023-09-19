package md5.end.controller;

import md5.end.exception.BadRequestException;
import md5.end.exception.NotFoundException;
import md5.end.model.dto.request.BrandRequest;
import md5.end.model.dto.request.ProductRequest;
import md5.end.model.dto.response.BrandResponse;
import md5.end.model.dto.response.ProductResponse;

import md5.end.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private IProductService productService;

    @PostMapping("")
    public ResponseEntity<ProductResponse> add(
            @Valid
            @RequestBody ProductRequest productRequest) throws BadRequestException, NotFoundException {
        return new ResponseEntity<>(productService.save(productRequest), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<ProductResponse>> getAll() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getOne(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> edit(
            @Valid
            @RequestBody ProductRequest productRequest,
            @PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(productService.update(productRequest, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponse> delete(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(productService.deleteById(id), HttpStatus.OK);

    }

    @PutMapping("/upload/{productId}")
    public ResponseEntity<ProductResponse> uploadImage(@PathVariable Long productId, @RequestParam(value = "files") List<MultipartFile> files) throws NotFoundException {
        return new ResponseEntity<>(productService.insertImage(productId,files), HttpStatus.OK);
    }

}
