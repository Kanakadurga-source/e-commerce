package com.spring.ecommerce.product;

import com.spring.ecommerce.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    public Integer createProduct(@Valid ProductRequest request) {
        var product =mapper.toProduct(request);
        return repository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProduct(List<ProductPurchaseRequest> purchaseRequests) {
        var productId=purchaseRequests.stream().map(ProductPurchaseRequest::productId).toList();
        var storeProduct = repository.findAllByIdInOrderById(productId);
        if (productId.size() != storeProduct.size()){
            throw new ProductPurchaseException("One or more Products does not exist");
        }
        var storeRequest = purchaseRequests
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for (int i=0; i< storeProduct.size();i++){
            var product = storeProduct.get(i);
            var productRequest = storeRequest.get(i);
            if (product.getAvailableQuantity()<productRequest.quantity()){
                throw new ProductPurchaseException("Insufficient stock quatity for Product with ID::"+ productRequest.productId());
            }
            var newAvailableQuatity = product.getAvailableQuantity()- productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuatity);
            repository.save(product);
            purchasedProducts.add(mapper.toProductPurchaseResponse(product,productRequest.quantity()));
        }

        return purchasedProducts;
    }

    public ProductResponse findById(Integer productId) {
        return repository.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(() ->new EntityNotFoundException("Product not with with this id::" +productId));
    }

    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .collect(Collectors.toList());
    }
}
