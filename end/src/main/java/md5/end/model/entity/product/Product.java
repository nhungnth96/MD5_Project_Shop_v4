package md5.end.model.entity.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import md5.end.model.entity.order.CartItem;
import md5.end.model.entity.product.Brand;
import md5.end.model.entity.product.Category;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)

    private String name;



    @Column(name = "import_price", nullable = false)
    private double importPrice;



    @Column(name = "export_price", nullable = false)
    private double exportPrice;

    private int rating;


    @Lob
    private String description;


    private int stock;

    private int status; //  -- 1: còn hàng, 2: hết hàng, 3: ngừng kinh doanh

    private String created_at;

    private String updated_at;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "product")
    private List<ProductSpec> specifications;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "product")
    private List<ProductImage> images;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "product")
    private List<CartItem> cartItems;

}
