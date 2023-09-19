package md5.end.service.amapper;

import md5.end.model.dto.request.CategoryRequest;
import md5.end.model.dto.response.CategoryResponse;
import md5.end.model.entity.product.Category;
import md5.end.model.entity.product.Product;
import md5.end.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper implements IGenericMapper<Category, CategoryRequest, CategoryResponse> {
    @Autowired
    ICategoryRepository categoryRepository;
    @Override
    public Category getEntityFromRequest(CategoryRequest categoryRequest) {
        Category category = new Category();
               category.setName(categoryRequest.getName());
        if (categoryRequest.getParentCategoryId() != null) {
            Category parentCategory = categoryRepository.findById(categoryRequest.getParentCategoryId()).orElse(null);
            category.setParentCategory(parentCategory);
        }
            return category;
    }

    @Override
    public CategoryResponse getResponseFromEntity(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        if (category.getParentCategory() == null) {
            categoryResponse.setName(category.getName());

            if (category.getChildCategories() != null && !category.getChildCategories().isEmpty()) {
                List<String> childCategoryNames = category.getChildCategories().stream()
                        .map(Category::getName)
                        .collect(Collectors.toList());
                categoryResponse.setChildCategoryNames(childCategoryNames);
            } else {
                categoryResponse.setChildCategoryNames(Collections.emptyList());
            }
        } else {
            categoryResponse.setName(category.getChildCategories() != null && !category.getChildCategories().isEmpty()
                    ? category.getChildCategories().get(0).getName()
                    : category.getName());
        }

        if (category.getProducts() != null && !category.getProducts().isEmpty()) {
            List<String> productNames = category.getProducts().stream()
                    .map(Product::getName)
                    .collect(Collectors.toList());
            categoryResponse.setProductNames(productNames);
        }  else {
            categoryResponse.setProductNames(Collections.emptyList());
        }


        return categoryResponse;


    }
}
