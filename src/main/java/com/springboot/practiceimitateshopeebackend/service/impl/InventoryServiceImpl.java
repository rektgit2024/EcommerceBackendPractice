package com.springboot.practiceimitateshopeebackend.service.impl;

import com.springboot.practiceimitateshopeebackend.entity.Inventory;
import com.springboot.practiceimitateshopeebackend.entity.Product;
import com.springboot.practiceimitateshopeebackend.model.QuantityRequest;
import com.springboot.practiceimitateshopeebackend.repository.InventoryRepository;
import com.springboot.practiceimitateshopeebackend.repository.ProductRepository;
import com.springboot.practiceimitateshopeebackend.service.InventoryService;
import com.springboot.practiceimitateshopeebackend.utils.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    @Override
    public QuantityRequest addQuantity(QuantityRequest quantityRequest) {

        Optional<Product> product = productRepository.findById(quantityRequest.getProductId());
        boolean isNew = inventoryRepository.existsById(quantityRequest.getProductId());
        Inventory inv;
        try {
            if (!isNew) {
                inv = new Inventory();
                inv.setProduct(product.get());
                inv.setQuantity(quantityRequest.getQuantity());
                inventoryRepository.save(inv);
            }
            else {
                inv = product.get().getInventory();
                inv.setQuantity(inv.getQuantity() + quantityRequest.getQuantity());
                inventoryRepository.save(inv);
            }
        }catch (Exception e){
            throw new NoSuchElementException(StringUtils.PRODUCT_NOT_FOUND);
        }
        return QuantityRequest.builder()
                .productId(quantityRequest.getProductId())
                .quantity(quantityRequest.getQuantity())
                .build();

    }

    @Override
    public QuantityRequest decreaseQuantity(QuantityRequest quantityRequest) {

        Optional<Product> product = productRepository.findById(quantityRequest.getProductId());

        Inventory inv = product.get().getInventory();

        if(quantityRequest.getQuantity() > inv.getQuantity()){
            throw new IllegalArgumentException(StringUtils.OUT_OF_STOCK);
        }
        else {
            inv.setQuantity(inv.getQuantity() - quantityRequest.getQuantity());
            inventoryRepository.save(inv);
        }
        return QuantityRequest.builder()
                .productId(quantityRequest.getProductId())
                .quantity(quantityRequest.getQuantity())
                .build();
    }


}