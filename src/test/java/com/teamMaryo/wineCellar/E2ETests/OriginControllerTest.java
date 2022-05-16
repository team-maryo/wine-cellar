package com.teamMaryo.wineCellar.E2ETests;

import com.teamMaryo.wineCellar.models.OriginModel;
import com.teamMaryo.wineCellar.repositories.OriginRepository;
import com.teamMaryo.wineCellar.services.OriginService;

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
public class OriginControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired 
    private OriginRepository repository;

    @Autowired 
    private OriginService service;

    //POST
    @Test
    public void TestOriginPost() {

        
        String address = "http://localhost:" + port + "/api/v1/origins"; 

       
        OriginModel newOrigin = new OriginModel();
        newOrigin.setNombre("Rias Baixas");
        newOrigin.setDescription("Fuerte");
        newOrigin.setUserId(1L);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic dXNlcjE6MTIz");
        HttpEntity<OriginModel> request = new HttpEntity<>(newOrigin, headers); 


		ResponseEntity<OriginModel> result = restTemplate.exchange(
            address,
            HttpMethod.POST,
            request,
            new ParameterizedTypeReference<OriginModel>(){}
        );

        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(result.getBody()).isEqualTo(newOrigin);
        
    }

    //GET
     @Test
    public void TestOriginsGet() {
        Iterable<OriginModel> origins = service.retrieveAll(1L);

        String url = "http://localhost:" + Integer.toString(port) + "/api/v1/origins";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic dXNlcjE6MTIz");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        //como le paso al response entity el user ?
        ResponseEntity<Iterable<OriginModel>> result = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<Iterable<OriginModel>>(){}
        );

        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(result.getBody()).isEqualTo(origins);
        
    }

    //GET por Id
    @Test
    public void TestOriginsGetbyId() {
        OriginModel origen = service.retrieve(1L,1L);

        String url = "http://localhost:" + Integer.toString(port) + "/api/v1/denominaciones/1";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic dXNlcjE6MTIz");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<OriginModel> result = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<OriginModel>(){}
        );

        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(result.getBody()).isEqualTo(origen);
        
    }

    // PUT
    @Test
    public void TestOriginPut(){

        Optional<OriginModel> originOptional = repository.findById(2L);
        then(originOptional.isPresent()).isEqualTo(true);

        OriginModel origin = originOptional.get();
        origin.setNombre("Chrome");
        origin.setDescription("Afrutado");


        String url = "http://localhost:" + Integer.toString(port) + "/api/v1/denominaciones/2";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic dXNlcjE6MTIz");
        HttpEntity<OriginModel> entity = new HttpEntity<>(origin,headers);

        ResponseEntity<OriginModel> result = restTemplate.exchange(
            url,
            HttpMethod.PUT,
            entity,
            new ParameterizedTypeReference<OriginModel>(){}
        );

        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(result.getBody()).isEqualTo(origin);
        
    }

    //DELETE
    @Test
    public void TestOriginsDelete(){

        Iterable<OriginModel> originsBefore = service.retrieveAll(1L);

        String url = "http://localhost:" + Integer.toString(port) + "/api/v1/denominaciones/2";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic dXNlcjE6MTIz");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<OriginModel> result = restTemplate.exchange(
            url,
            HttpMethod.DELETE,
            entity,
            new ParameterizedTypeReference<OriginModel>(){}
        );

        Iterable<OriginModel> originsAfter = service.retrieveAll(1L);

        then(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        then(!(originsAfter).equals(originsBefore));
        
    }
    
    } 




