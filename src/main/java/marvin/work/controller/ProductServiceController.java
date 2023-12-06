package marvin.work.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import marvin.work.exception.ProductNotFoundException;
import marvin.work.model.Product;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.websocket.server.PathParam;


@RestController
public class ProductServiceController {
	
	private static Map<String, Product> productRepo = new HashMap<>();
	static {
		Product miele = new Product();
		miele.setId("1");
		miele.setName("miele");
		productRepo.put(miele.getId(), miele);
		Product zucchero = new Product();
		zucchero.setId("2");
		zucchero.setName("zucchero");
		productRepo.put(zucchero.getId(), zucchero);
	}
	
	
	
	@GetMapping(value="/products")
	public ResponseEntity<Object> getProducts() {
		return new ResponseEntity<Object>(productRepo.values(), HttpStatus.OK);
	}
	
	@GetMapping(value="/products/{id}")
	public ResponseEntity<Object> getProduct(@PathVariable("id") String id) {
		System.out.println(id);
		return new ResponseEntity<Object>(productRepo.get(id), HttpStatus.OK);
	}
	
	@PostMapping(value="/products")
	public ResponseEntity<Object> addProduct(@RequestBody Product p){
		if(!productRepo.containsKey(p.getId())) {
			productRepo.put(p.getId(), p);
			return new ResponseEntity<Object>("Product created.", HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("Product already exists.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value="/products/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable("id") String id){
		if(productRepo.containsKey(id)) {
			productRepo.remove(id);
			return new ResponseEntity<Object>("Product successfully deleted.", HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("Product not exists.", HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="/products", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateProduct(@PathParam("id") String id,@RequestBody Product product){
		if(productRepo.containsKey(id)) {
			productRepo.get(id).setName(product.getName());
			return new ResponseEntity<Object>("Product updated.", HttpStatus.OK);
		} else throw new ProductNotFoundException("Prodotto "+id+" non trovato.");
	}
	
	//private static String path = "/Users/15mim/OneDrive/Desktop/";
	private static String path = "/Users/Marvin/Desktop/";
	
	@RequestMapping(value="/fileUpload", method = RequestMethod.POST, 
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> fileUpload(@RequestParam("file") MultipartFile file,
			@RequestParam("filename") String filename) throws IOException {
		File newFile = new File(path + filename);
		newFile.createNewFile();
		FileOutputStream fileOut = new FileOutputStream(newFile);
		fileOut.write(file.getBytes());
		fileOut.close();
		return new ResponseEntity<Object>("File saved.", HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value="/download")
	public ResponseEntity<Object> fileDownload(@PathParam("filename") String filename) throws FileNotFoundException{
		String fileN = path + filename;
		File file = new File(fileN);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Disposition", String.format("attachment; filename =\"%s\"", file.getName()));
		header.add("Cache-control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");
		@SuppressWarnings("rawtypes")
		ResponseEntity response = ResponseEntity.ok().headers(header).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/txt")).body(resource);
		return response;
	}
	
	/*
	@GetMapping(value="/test")
	public ResponseEntity<Object> getProductnew(@PathParam("id") String id) {
		return new ResponseEntity<Object>(productRepo.get(id), HttpStatus.OK);
	}
	*/
	
}
