package md5.end.repository;

import md5.end.model.entity.product.Brand;
import md5.end.model.entity.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ICategoryRepository extends JpaRepository<Category,Long> {
    @Query("select c from Category c where c.parentCategory = null")
    List<Category> findAllParent();

    @Query("select c from Category c where c.parentCategory.id = ?1")
    List<Category> findAllChildByParentId(Long id);

    @Query("select c from Category c where c.parentCategory = null and c.id =?1")
    Optional<Category> findParentById(Long id);

    @Query("select c from Category c where c.parentCategory.id = ?1 and c.id = ?2")
    Optional<Category> findChildByParentId(Long parentId,Long childId);

    Optional<Category> findByName(String name);
}
