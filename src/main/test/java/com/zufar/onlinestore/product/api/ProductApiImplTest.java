package com.zufar.onlinestore.product.api;

import com.zufar.onlinestore.product.dto.ProductInfoDto;
import com.zufar.onlinestore.product.dto.ProductListWithPaginationInfoDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.UUID;

import static com.zufar.onlinestore.product.util.ProductPaginationDefaults.PAGE;
import static com.zufar.onlinestore.product.util.ProductPaginationDefaults.SIZE;
import static com.zufar.onlinestore.product.util.ProductPaginationDefaults.SORT_ATTRIBUTE;
import static com.zufar.onlinestore.product.util.ProductPaginationDefaults.SORT_DIRECTION;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ProductApiImplTest {

    @Mock
    private PageableProductsProvider pageableProductsProvider;

    @Mock
    private SingleProductProvider singleProductProvider;

    @InjectMocks
    private ProductApiImpl productApi;

    @Test
    void shouldReturnProductsForValidPaginationParameters() {
        when(pageableProductsProvider.getProducts(
                        PAGE.getIntValue(),
                        SIZE.getIntValue(),
                        SORT_ATTRIBUTE.getStringValue(),
                        SORT_DIRECTION.getStringValue()
                )
        ).thenReturn(mock(ProductListWithPaginationInfoDto.class));

        ProductListWithPaginationInfoDto result = productApi.getProducts(
                PAGE.getIntValue(),
                SIZE.getIntValue(),
                SORT_ATTRIBUTE.getStringValue(),
                SORT_DIRECTION.getStringValue()
        );

        assertNotNull(result);

        verify(pageableProductsProvider, times(1)).getProducts(
                PAGE.getIntValue(),
                SIZE.getIntValue(),
                SORT_ATTRIBUTE.getStringValue(),
                SORT_DIRECTION.getStringValue()
        );
    }

    @Test
    void shouldReturnProductForValidProductId() {
        UUID productId = UUID.randomUUID();

        when(singleProductProvider.getProductById(productId))
                .thenReturn(mock(ProductInfoDto.class));

        ProductInfoDto result = productApi.getProduct(productId);

        assertNotNull(result);

        verify(singleProductProvider, times(1)).getProductById(productId);
    }
}
