package marvin.work;

import org.springframework.data.repository.CrudRepository;

import marvin.work.model.Product;

public interface ProductListRepository extends CrudRepository<Product, String> {
	
}
