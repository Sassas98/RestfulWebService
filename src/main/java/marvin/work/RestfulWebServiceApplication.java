package marvin.work;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestfulWebServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(RestfulWebServiceApplication.class, args);
	}
	
}

/*
<dependency>   
			<groupId>org.springdoc</groupId> 
			<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
			<version>2.0.2</version>
</dependency>
 * */
//http://localhost:8080/v3/api-docs
//http://localhost:8080/swagger-ui/index.html#/product-service-controller/fileUpload
