package com.example.jwt.application.usecase.product.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.jwt.application.usecase.product.GetByCategoryUseCase;
import com.example.jwt.dto.mapper.ProductMapper;
import com.example.jwt.dto.model.ProductDto;
import com.example.jwt.dto.response.ObjectResponse;
import com.example.jwt.entities.ImagesEntity;
import com.example.jwt.entities.ProductEntity;
import com.example.jwt.infra.repositories.ProductRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class GetByCategoryUseCaseImpl implements GetByCategoryUseCase {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final Logger logger = LoggerFactory.getLogger(GetByCategoryUseCaseImpl.class);

    @Override
    public ObjectResponse<ProductDto> execute(int pageNumber, int pageSize, String sortBy) {
        try {
            // TODO: remove hardcode sort by
            Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());

            Page<ProductEntity> pagedResult = this.productRepository.findAll(paging);

            List<ProductDto> content = pagedResult.getContent().stream()
                    .map(product -> {
                        // Create dto object to customize response
                        ProductDto productDto = new ProductDto();

                        productDto.setId(product.getId());

                        productDto.setTags(product.getTags());

                        productDto.setTitle(product.getTitle());

                        productDto.setHandle(product.getHandle());

                        productDto.setVendor(product.getVendorEntity().getName());

                        productDto.setPrice(product.getPrice());

                        productDto.setAvailable(product.getInventory().getQuantity() > 0);

                        // Filter images to include only those with position == 1 to show first of
                        List<ImagesEntity> images = product.getImages().stream().filter(
                                image -> image.getPosition() == 1).collect(Collectors.toList());

                        productDto.setImages(images);

                        return productDto;
                    })
                    .collect(Collectors.toList());

            ObjectResponse<ProductDto> objectResponse = new ObjectResponse<>();
            objectResponse.setContent(content);
            objectResponse.setPageNo(pagedResult.getNumber());
            objectResponse.setPageSize(pagedResult.getSize());
            objectResponse.setTotalElements(pagedResult.getTotalElements());
            objectResponse.setTotalPages(pagedResult.getTotalPages());
            objectResponse.setLast(pagedResult.isLast());

            return objectResponse;
        } catch (Exception e) {
            this.logger.error("Error gettin products by category", e);
            throw new RuntimeException(e);
        }
    }
}
