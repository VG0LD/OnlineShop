package com.newsilkroad.data;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface ProductRepository
        extends
            JpaRepository<Product, Long>,
            JpaSpecificationExecutor<Product> {
    @Override
    default List<Product> findAll() {


        return null;
    }

    @Query("select p from Product as p where p.SubCategoryId = 1")
    List<Product> findAllVideoCardsList();
    @Query("select p FROM Product as p where p.SubCategoryId = 3")
    List<Product> findAllMotherBoardsList();
}
