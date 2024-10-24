package com.newsilkroad.services;

import com.newsilkroad.data.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SamplePersonService {

    private final SamplePersonRepository repository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public SamplePersonService(@Qualifier("samplePersonRepository") SamplePersonRepository samplePersonRepository,
                               @Qualifier("categoryRepository") CategoryRepository categoryRepository,
                               @Qualifier("subCategoryRepository") SubCategoryRepository subCategoryRepository,
                               @Qualifier("productRepository") ProductRepository productRepository) {
        this.repository = samplePersonRepository;
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.productRepository = productRepository;
    }
    public Optional<SamplePerson> get(Long id) {
        return repository.findById(id);
    }

    public SamplePerson update(SamplePerson entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<SamplePerson> list(Pageable pageable) {
        return repository.findAll(pageable);
    }


    public Page<SamplePerson> list(Pageable pageable, Specification<SamplePerson> filter) {
        Page<SamplePerson> samplePeople = repository.findAll(filter, pageable);
        return samplePeople;
    }

    public int count() {
        return (int) repository.count();
    }

}
