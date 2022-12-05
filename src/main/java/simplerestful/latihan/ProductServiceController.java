/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplerestful.latihan;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import Model2.Product;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author ThinkPad
 */
@ControllerAdvice
@RestController



public class ProductServiceController {
    private static final Map<String, Product> productRepo = new HashMap<>();
   static {
      Product honey = new Product();
      honey.setId("1");
      honey.setName("Honey");
      productRepo.put(honey.getId(), honey);
      
      Product almond = new Product();
      almond.setId("2");
      almond.setName("Almond");
      productRepo.put(almond.getId(), almond);
}
   public class ProductNotfoundException extends RuntimeException {
   private static final long serialVersionUID = 1L;
}

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
   public ResponseEntity<Object> delete(@PathVariable("id") String id) {
       if(!productRepo.containsKey(id))
       {
       return new ResponseEntity<>("product could not be found", HttpStatus.NOT_FOUND);
       }
   productRepo.remove(id);
   return new ResponseEntity<>("Product is deleted successsfully", HttpStatus.OK);
   
   }
 
   
 @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
   public ResponseEntity<Object> updateProduct(@PathVariable("id") String id, @RequestBody Product product) { 
       if (!productRepo.containsKey(product.getId())){
       return new ResponseEntity<>("product is null", HttpStatus.NOT_FOUND); 
       }
      productRepo.remove(id);
      product.setId(id);
      productRepo.put(id, product);
      
      return new ResponseEntity<>("Product is updated successsfully", HttpStatus.OK);

   }
   
   @RequestMapping(value = "/products", method = RequestMethod.POST)
   public ResponseEntity<Object> createProduct(@RequestBody Product product) {
      if (productRepo.containsKey(product.getId())){
      return new ResponseEntity<>("product is already exist with ID: " + product.getId(), HttpStatus.NOT_FOUND);
      }
      productRepo.put(product.getId(), product);
      return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
      
   }
   
   @RequestMapping(value = "/products")
   public ResponseEntity<Object> getProduct() {
       if (productRepo.isEmpty()){
       return new ResponseEntity<>("product is null", HttpStatus.NOT_FOUND);
       }
      return new ResponseEntity<>(productRepo.values(), HttpStatus.OK);
   }
}