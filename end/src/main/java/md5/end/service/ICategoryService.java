package md5.end.service;

import md5.end.exception.NotFoundException;
import md5.end.model.dto.request.CategoryRequest;
import md5.end.model.dto.response.CategoryResponse;
import md5.end.model.entity.product.Category;


import java.util.List;
import java.util.Optional;

public interface ICategoryService extends IGenericService<CategoryRequest, CategoryResponse> {
    List<CategoryResponse> findAllParent();
    List<CategoryResponse> findAllChildByParentId(Long parentId);
    CategoryResponse findParentById(Long parentId) throws NotFoundException;
    CategoryResponse findChildByParentId(Long parentId,Long childId) throws NotFoundException;
    CategoryResponse findByName(String name) throws NotFoundException;
}