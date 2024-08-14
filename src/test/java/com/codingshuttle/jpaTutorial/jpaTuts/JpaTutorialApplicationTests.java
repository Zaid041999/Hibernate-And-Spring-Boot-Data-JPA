package com.codingshuttle.jpaTutorial.jpaTuts;

import com.codingshuttle.jpaTutorial.jpaTuts.entities.ProductEntity;
import com.codingshuttle.jpaTutorial.jpaTuts.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class JpaTutorialApplicationTests {

	@Autowired
	ProductRepository productRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void testRepository(){


		ProductEntity entity = ProductEntity.builder()
				.sku("veggie122")
				.price(BigDecimal.valueOf(8.33))
				.title("Patato Veggie")
				.quantity(10)
				.build();
		ProductEntity savedProductEntity=productRepository.save(entity);
		System.out.println(savedProductEntity);
	}

	@Test
	void getRepository(){
//		List<ProductEntity> productEntities = productRepository.findAll();
//		for ( ProductEntity i : productEntities)
//		{
//			System.out.println(i);
//		}


//		List<ProductEntity> entities = productRepository.findByTitle("Lays");

//		List<ProductEntity> entities =
//				productRepository.findByCreatedAtAfter(LocalDateTime.of(2024,1,1,0,0,0));

//		List<ProductEntity> entities =
//				productRepository.findByQuantityAndPrice(10,BigDecimal.valueOf(8.33));
//		List<ProductEntity> entities =
//				productRepository.findByQuantityGreaterThanAndPriceLessThan(6,BigDecimal.valueOf(9.33));

//		List<ProductEntity> entities=
//				productRepository.findByTitleLike("%Bis%");

//		List<ProductEntity> entities =productRepository.findByTitleContaining("Bis");
//		System.out.println(entities);

	    List<ProductEntity> entities =productRepository.findByTitleContainingIgnoreCase("veg");
		System.out.println(entities);


	}
	@Test
	void getSingleFromRepository(){
		Optional<ProductEntity> productEntity =productRepository.findByTitleAndPrice("Lay",BigDecimal.valueOf(10.3));
		 productEntity.ifPresent(System.out::println);

	}

}
