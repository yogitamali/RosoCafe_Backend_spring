package com.rosoCafe.springbootecommerce.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

import com.rosoCafe.springbootecommerce.entity.Product;
import com.rosoCafe.springbootecommerce.entity.ProductCategory;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer{
	
	private EntityManager entityManager;
	
	@Autowired
	public MyDataRestConfig(EntityManager theEntityManager ) {
		entityManager=theEntityManager;
	}
	
	
	
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		// TODO Auto-generated method stub
		//RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config);
	
		HttpMethod[] theUnsupoortedActions= {HttpMethod.PUT,HttpMethod.POST,HttpMethod.DELETE};
		//disable method put post delete for product
		config.getExposureConfiguration()
		.forDomainType(Product.class)
		.withItemExposure((metadata,httpMethods)->httpMethods.disable(theUnsupoortedActions));
		
		
	
	  config.getExposureConfiguration()
	  .forDomainType(ProductCategory.class)
	  .withItemExposure((metadata,httpMethods)->httpMethods.disable(theUnsupoortedActions));
	  
	  //call internal helper methods
	exposeIds(config);
	  
	  //call
	  }
	
	private void exposeIds(RepositoryRestConfiguration config) {
		//expose entity ids
		
		Set<EntityType<?>> entities =entityManager.getMetamodel().getEntities();
		
		
		//-create an arraylist of those entity type
		
		List<Class> entityClasses=new ArrayList<>();
		
		for(EntityType tempEntityType:entities) {
			entityClasses.add(tempEntityType.getJavaType());
		}
		
		
		//expose entity domain type
		
		
		Class[] domainTypes=entityClasses.toArray(new Class[0]);
		config.exposeIdsFor(domainTypes);
		
		
	}
	
	
	
	

	
	

}
