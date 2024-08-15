package com.codingshuttle.jpaTutorial.jpaTuts.controllers;

import com.codingshuttle.jpaTutorial.jpaTuts.entities.ProductEntity;
import com.codingshuttle.jpaTutorial.jpaTuts.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
@RequestMapping(path = "/products")

public class ProductController {
    private final int PAGE_SIZE=5;
    private final ProductRepository productRepository ;



    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //    @GetMapping()
    //   public List<ProductEntity> getAllProduct(@RequestParam(defaultValue = "id")String sortBy){
    //        //return productRepository.findBy(Sort.by(Sort.Direction.DESC,sortBy ,"price","quantity"));
    //
    //        return productRepository.findBy(Sort.by(Sort.Order.asc(sortBy), Sort.Order.asc("name")));
    //    }


    //    @GetMapping()
//   public List<ProductEntity> getAllProduct(@RequestParam(defaultValue = "id")String sortBy){
//        //return productRepository.findBy(Sort.by(Sort.Direction.DESC,sortBy ,"price","quantity"));
//
//        return productRepository.findBy(Sort.by(Sort.Order.asc(sortBy), Sort.Order.asc("name")));
//    }
    @GetMapping()
    public List<ProductEntity> getAllProduct(@RequestParam(defaultValue = "id")String sortBy,
                                             @RequestParam(defaultValue = "0")Integer pageNumber,
                                             @RequestParam(defaultValue = "") String title){

        return productRepository.findByTitleContainingIgnoreCase(title,
                PageRequest.of(pageNumber,PAGE_SIZE,Sort.by(sortBy)));
        //return productRepository.findBy(Sort.by(Sort.Direction.DESC,sortBy ,"price","quantity"));

        //return productRepository.findBy(Sort.by(Sort.Order.asc(sortBy), Sort.Order.asc("name")));
/*        Pageable pageable = PageRequest.of(pageNumber,PAGE_SIZE,Sort.by(sortBy));
        return productRepository.findAll(pageable).getContent();*/
    }
}
