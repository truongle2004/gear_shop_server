package com.example.jwt.presentation.controller;

import java.util.List;

import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.scope.SearchScope;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.application.usecase.product.GetAllCategoryUseCase;
import com.example.jwt.application.usecase.product.GetByCategoryUseCase;
import com.example.jwt.application.usecase.product.GetProductByIdUseCase;
import com.example.jwt.dto.model.CategoryDto;
import com.example.jwt.dto.model.ProductDto;
import com.example.jwt.dto.model.SearchResponseDto;
import com.example.jwt.dto.response.ObjectResponse;
import com.example.jwt.entities.ImagesEntity;
import com.example.jwt.entities.ProductEntity;
import com.example.jwt.utils.AppConstants;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

@RestController
@CrossOrigin(origins = "${cors.allowed-origins}")
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {

    private GetAllCategoryUseCase getAllCategoryUseCase;
    private GetByCategoryUseCase getByCategoryUseCase;
    private GetProductByIdUseCase getProductByIdUseCase;
    private EntityManager entityManager;

    @GetMapping
    public ResponseEntity<ObjectResponse<ProductDto>> getAllProducts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "category", required = false) String category) {
        return new ResponseEntity<>(this.getByCategoryUseCase.execute(pageNo, pageSize, sortBy, category),
                HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return new ResponseEntity<>(this.getAllCategoryUseCase.execute(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable short id) {
        return new ResponseEntity<>(this.getProductByIdUseCase.execute(id), HttpStatus.OK);
    }

    @GetMapping("search")
    public List<SearchResponseDto> searchProduct(@RequestBody String query) {
        // Get a Hibernate Search session from the EntityManager
        SearchSession searchSession = Search.session(entityManager);

        // Initiate a search query on the index mapped to the
        SearchScope<ProductEntity> scope = searchSession.scope(ProductEntity.class);

        SearchResult<ProductEntity> searchResult = searchSession.search(scope)
                // define that only documents matching the given predicate should be returned
                .where(scope.predicate().match()
                        .field("title")
                        .matching(query)
                        .toPredicate())
                // build the query and get results
                .fetch(20);

        return searchResult.hits().stream().map(hit -> {
            SearchResponseDto response = new SearchResponseDto();
            response.setId(hit.getId());
            response.setPrice(hit.getPrice());
            response.setTitle(hit.getTitle());
            ImagesEntity image = hit.getImages().get(0);
            response.setImages(List.of(image));
            return response;
        }).toList();
    }
}
