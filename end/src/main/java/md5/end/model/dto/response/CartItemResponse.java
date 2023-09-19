package md5.end.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CartItemResponse {
    private Long id;
    private String productName;
    private String productPrice;
    private int quantity;
    private String amount;
}
