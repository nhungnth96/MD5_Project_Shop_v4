package md5.end.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetailResponse {
    private Long productId;
    private String productName;
    private int quantity;
    private String price;
    private String amount;
}
