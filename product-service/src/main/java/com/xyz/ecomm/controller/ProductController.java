package com.xyz.ecomm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.ecomm.model.Product;
import com.xyz.ecomm.repository.ProductRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductRepository repository;

	@PostMapping
	public String addProduct(@RequestBody Product product) {
		repository.addProduct(product);
		return "Successfully inserted into DynamoDB table";
	}

	@GetMapping(value = "{productId}")
	public ResponseEntity<Product> getProductDetails(@PathVariable("productId") String productId) {
		Product product = repository.getProduct(productId);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@PutMapping(value = "{productId}")
	public void updateProductDetails(@RequestBody Product product) {
		repository.updateProduct(product);
	}

	@DeleteMapping(value = "{productId}")
	public void deleteStudentDetails(@PathVariable("productId") String productId) {
		Product product = new Product();
		product.setProductId(productId);
		repository.deleteProduct(product);
	}
	
	@GetMapping
	public List<Product> getAllProducts() {
		return  repository.getAllProducts();
		
	}

	
	/* Other  Search methods by Brand, Category or Department
	 *
	@GetMapping(value = "/brand/{brand}")
	public List<Product> findByBrand(@PathVariable("brand") String brand) {
		return  repository.findByBrand(brand);
		
	}	
	
	  @GetMapping (value = "/category/{category}")
	  public List<Product> findByCategory(@PathVariable("category") String category) { 
		  return repository.findByCategory(category);
	  
	  }
	  
	  @GetMapping(value = "/department/{department}") 
	  public List<Product> findByDepartment(@PathVariable("department") String department) { 
		  return repository.findByDepartment(department);
	  
	  }
	  
	  @GetMapping(value = "/searchBy")
		public List<Product> searchByBrand(@RequestParam String brand, @RequestParam String department, @RequestParam String category) {
			if( value != null && !value.isEmpty()) {
				return  repository.findByBrand(value);
			} else if ( value != null && !value.isEmpty()) {
				return repository.findByDepartment(value);
			} else if ( value != null && !value.isEmpty()) {
				return repository.findByCategory(value);
			} else {
			    return null;
			} 
		}
		
		@GetMapping(value = "/searchByBrand")
		public List<Product> searchByBrand(@RequestParam String value) {
			if( value != null && !value.isEmpty()) {
				return  repository.findByBrand(value);
			} 
			return null; 
		}
		
		@GetMapping(value = "/searchByDept")
		public List<Product> searchByDept(@RequestParam String value) {
			if ( value != null && !value.isEmpty()) {
				return repository.findByDepartment(value);	
			}
		return null; 
			
		}
		
		@GetMapping(value = "/searchByCategory")
		public List<Product> searchByCategory(@RequestParam String value) {
			if ( value != null && !value.isEmpty()) {
				return repository.findByCategory(value);
			}
			return null; 
		}
	  
	  */
	
	  @GetMapping(value = "/searchBy")
			public List<Product> searchByBrand(@RequestParam (required = false) String brand, @RequestParam (required = false) String department, @RequestParam (required = false) String category) {
				if( brand != null && !brand.isEmpty()) {
					return  repository.findByBrand(brand);
				} else if ( department != null && !department.isEmpty()) {
					return repository.findByDepartment(department);
				} else if ( category != null && !category.isEmpty()) {
					return repository.findByCategory(category);
				} else {
				    return new ArrayList<Product>();
				} 
			}
}