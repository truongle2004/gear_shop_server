package com.example.jwt.application.usecase.product.impl;

import java.util.List;

import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.scope.SearchScope;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;

import com.example.jwt.application.usecase.product.SearchProductUseCase;
import com.example.jwt.dto.mapper.ImageMapper;
import com.example.jwt.dto.model.SearchResponseDto;
import com.example.jwt.entities.ProductEntity;
import com.example.jwt.utils.AppConstants;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SearchProductUseCaseImpl implements SearchProductUseCase {
    private EntityManager entityManager;
    private ImageMapper imageMapper;

    @Override
    public List<SearchResponseDto> execute(String query) {
        // Get a Hibernate Search session
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
                .fetch(AppConstants.LIMIT_SEARCH_SIZE);

        return searchResult
                .hits()
                .stream()
                .map(hit -> SearchResponseDto
                        .builder()
                        .id(hit.getId())
                        .title(hit.getTitle())
                        .price(hit.getPrice())
                        .images(hit
                                .getImages()
                                .stream()
                                .filter(image -> image.getPosition() == AppConstants.FIRST_IMAGE_POSITION)
                                .map(this.imageMapper::mapToDto)
                                .toList())
                        .build())
                .toList();
    }

}