package com.teamMaryo.wineCellar.E2ETests;

import com.teamMaryo.wineCellar.models.PurchaseModel;
import com.teamMaryo.wineCellar.repositories.PurchaseRepository;
import com.teamMaryo.wineCellar.services.PurchaseService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PurchaseControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired 
    private PurchaseRepository repository;

    @Autowired 
    private PurchaseService service;

    //POST
    @Test
    public void TestPurchasePost() {

        
        String address = "http://localhost:" + port + "/api/v1/purchases"; 

       
        PurchaseModel newPurchase = new PurchaseModel();
        newPurchase.setWineId(1L);
        newPurchase.setCount(10);
        newPurchase.setUserId(1L);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic dXNlcjE6MTIz");
        HttpEntity<PurchaseModel> request = new HttpEntity<>(newPurchase, headers); 


		ResponseEntity<PurchaseModel> result = restTemplate.exchange(
            address,
            HttpMethod.POST,
            request,
            new ParameterizedTypeReference<PurchaseModel>(){}
        );

        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        
    }

    //GET
     @Test
    public void TestPurchaseGet() {
        Iterable<PurchaseModel> purchase = service.retrieveAll(1L);

        String url = "http://localhost:" + Integer.toString(port) + "/api/v1/purchases";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic dXNlcjE6MTIz");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Iterable<PurchaseModel>> result = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<Iterable<PurchaseModel>>(){}
        );

        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(result.getBody()).isEqualTo(purchase);
        
    }

    //GET por Id
     @Test
    public void TestPurchaseGetbyId() {
        PurchaseModel purchase = service.retrieve(1L,2L);

        String url = "http://localhost:" + Integer.toString(port) + "/api/v1/purchases/2";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic dXNlcjE6MTIz");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        //como le paso al response entity el user ?
        ResponseEntity<PurchaseModel> result = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<PurchaseModel>(){}
        );

        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(result.getBody()).isEqualTo(purchase);
        
    }

    // PUT
    @Test
    public void TestPurchasePut(){

        Optional<PurchaseModel> purchaseOptional = repository.findByUserIdAndPurchaseId(1L,1L);
        then(purchaseOptional.isPresent()).isEqualTo(true);

        PurchaseModel purchase = purchaseOptional.get();
        purchase.setCount(20);


        String url = "http://localhost:" + Integer.toString(port) + "/api/v1/purchases/1";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic dXNlcjE6MTIz");
        HttpEntity<PurchaseModel> entity = new HttpEntity<>(purchase,headers);

        ResponseEntity<PurchaseModel> result = restTemplate.exchange(
            url,
            HttpMethod.PUT,
            entity,
            new ParameterizedTypeReference<PurchaseModel>(){}
        );

        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(result.getBody()).isEqualTo(purchase);
        
    }

    //DELETE
    @Test
    public void TestPurchaseDelete(){

        Iterable<PurchaseModel> purchasesBefore = service.retrieveAll(1L);

        String url = "http://localhost:" + Integer.toString(port) + "/api/v1/purchases/2";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic dXNlcjE6MTIz");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<PurchaseModel> result = restTemplate.exchange(
            url,
            HttpMethod.DELETE,
            entity,
            new ParameterizedTypeReference<PurchaseModel>(){}
        );

        Iterable<PurchaseModel> purchasesAfter = service.retrieveAll(1L);

        then(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        then(!(purchasesAfter).equals(purchasesBefore));
        
    }
    
    } 

