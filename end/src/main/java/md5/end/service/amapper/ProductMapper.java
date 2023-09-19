package md5.end.service.amapper;

import md5.end.model.dto.request.ProductRequest;
import md5.end.model.dto.response.ProductResponse;
import md5.end.model.entity.product.Product;
import md5.end.model.entity.product.ProductImage;
import md5.end.model.entity.product.ProductSpec;
import md5.end.model.entity.product.Specification;
import md5.end.repository.IBrandRepository;
import md5.end.repository.ICategoryRepository;
import md5.end.repository.ISpecRepository;
import md5.end.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductMapper implements IGenericMapper<Product, ProductRequest, ProductResponse> {
    @Autowired
    ICategoryRepository categoryRepository;
    @Autowired
    IBrandRepository brandRepository;
    @Autowired
    ISpecRepository specRepository;

    @Override
    public Product getEntityFromRequest(ProductRequest productRequest) {
        Product product = new Product();
        product.setCreated_at(LocalDateTime.now().toString());
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setImportPrice(productRequest.getImportPrice());
        product.setExportPrice(productRequest.getExportPrice());
        product.setStock(productRequest.getStock());
        product.setStatus(productRequest.getStatus());
        product.setCategory(categoryRepository.findChildByParentId(productRequest.getParentCategoryId(),productRequest.getChildCategoryId()).get());
        product.getCategory().setParentCategory(categoryRepository.findParentById(productRequest.getParentCategoryId()).get());
        product.setBrand(brandRepository.findById(productRequest.getBrandId()).get());
        List<ProductSpec> specs = new ArrayList<>();
        if (productRequest.getSpecs() != null && !productRequest.getSpecs().isEmpty()) {
            for (Map.Entry<Long, String> entry : productRequest.getSpecs().entrySet()) {
                ProductSpec productSpec = new ProductSpec();

                // Thiết lập sản phẩm cho productSpec
                productSpec.setProduct(product);

                // Lấy specification từ ID
                Specification specification = specRepository.findById(entry.getKey()).orElse(null);
                productSpec.setSpecification(specification);

                // Đặt giá trị từ Map
                productSpec.setValue(entry.getValue());

                specs.add(productSpec);
            }
        }
        product.setSpecifications(specs);

        return product;


    }

    @Override
    public ProductResponse getResponseFromEntity(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        if (product.getCategory().getParentCategory() == null) {
            // Đây là danh mục cha
            productResponse.setParentCategoryName(product.getCategory().getName());
            productResponse.setChildCategoryName(null);
        } else {
            // Đây là danh mục con
            productResponse.setParentCategoryName(product.getCategory().getParentCategory().getName());
            productResponse.setChildCategoryName(product.getCategory().getName());
        }
        productResponse.setBrandName(product.getBrand().getName());
        productResponse.setProductName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setImportPrice(NumberFormat.getInstance().format(product.getImportPrice())+"₫");
        productResponse.setExportPrice(NumberFormat.getInstance().format(product.getExportPrice())+"₫");
        productResponse.setStock(product.getStock());
        if (product.getStatus() == 1) {
            productResponse.setStatus("On stock");
        } else if (product.getStatus() == 2) {
            productResponse.setStatus("Out of stock");
        } else {
            productResponse.setStatus("Stop business");
        }

        Map<String, String> specsMap = new HashMap<>();
        if (product.getSpecifications() != null && !product.getSpecifications().isEmpty()) {
            for (ProductSpec productSpec : product.getSpecifications()) {
                if (productSpec.getSpecification() != null) {
                    String specName = productSpec.getSpecification().getName();
                    String specValue = productSpec.getValue();
                    specsMap.put(specName, specValue);
                }
            }
        }
        productResponse.setSpecifications(specsMap);

        List<String> imageUrls = new ArrayList<>();
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            for (ProductImage productImage : product.getImages()) {
                if (productImage.getUrl() != null) {
                    imageUrls.add(productImage.getUrl());
                }
            }
        }
        productResponse.setImages(imageUrls);

        return productResponse;
    }
}
