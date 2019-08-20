package com.xyz.ecomm.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.xyz.ecomm.model.Product;

@Repository
public class ProductRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRepository.class);

	@Autowired
	private DynamoDBMapper mapper;


	public void addProduct(Product product) {
		mapper.save(product);
	}

	
	public Product getProduct(String productId) {
		return mapper.load(Product.class, productId);
	}


	public void updateProduct(Product product) {
		try {
			mapper.save(product, buildDynamoDBSaveExpression(product));
		} catch (ConditionalCheckFailedException exception) {
			LOGGER.error("invalid data - " + exception.getMessage());
		}
	}
	
		
	public void deleteProduct(Product product) {
		mapper.delete(product);
	}

	
	public DynamoDBSaveExpression buildDynamoDBSaveExpression(Product Product) {
		DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
		Map<String, ExpectedAttributeValue> expected = new HashMap<>();
		expected.put("productId", new ExpectedAttributeValue(new AttributeValue(Product.getProductId()))
				.withComparisonOperator(ComparisonOperator.EQ));
		saveExpression.setExpected(expected);
		return saveExpression;
	}

	/* Projection Expression - To get the limited attributes ; note 'name' is a keyword
	public List<Product> getAllProducts(){
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withProjectionExpression("productId,prodname,price");
		List<Product> scanResult = mapper.scan(Product.class, scanExpression);
		return scanResult;
	}
	*/
	
	public List<Product> getAllProducts(){
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		List<Product> scanResult = mapper.scan(Product.class, scanExpression);
		return scanResult;
	}
	
	public List<Product> findByBrand(String brand){
		
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
	    eav.put(":val1", new AttributeValue().withS(brand));
	    
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withFilterExpression("brand = :val1").withExpressionAttributeValues(eav);
		List<Product> scanResult = mapper.scan(Product.class, scanExpression);
		
		return scanResult;		
	}
	
	public List<Product> findByCategory(String category){
		
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
	    eav.put(":val1", new AttributeValue().withS(category));
	    
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withFilterExpression("category = :val1").withExpressionAttributeValues(eav);
		List<Product> scanResult = mapper.scan(Product.class, scanExpression);
		
		return scanResult;		
	}
	
	public List<Product> findByDepartment(String department){
		
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
	    eav.put(":val1", new AttributeValue().withS(department));
	    
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withFilterExpression("department = :val1").withExpressionAttributeValues(eav);
		List<Product> scanResult = mapper.scan(Product.class, scanExpression);
		
		return scanResult;		
	}
}