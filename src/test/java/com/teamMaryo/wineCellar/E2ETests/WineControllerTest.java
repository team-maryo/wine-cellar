package com.teamMaryo.wineCellar.E2ETests;

import com.teamMaryo.wineCellar.models.WineModel;
import com.teamMaryo.wineCellar.repositories.WineRepository;
import com.teamMaryo.wineCellar.services.WineService;

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
public class WineControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired 
    private WineRepository repository;

    @Autowired 
    private WineService wineService;

    //POST
    @Test
    public void TestWinesPost() {

        
        String address = "http://localhost:" + port + "/api/v1/wines"; //tengo q añadir el wine Id al address y no se como;

       
        WineModel newWine = new WineModel();
        newWine.setNombre("prueba");
        newWine.setDescription("Muy rico");
        newWine.setQuantity(3L);
        newWine.setPrice(34);
        newWine.setLocation("Mi bodega");
        newWine.setYear(2005L);
        newWine.setRating(5L);
        newWine.setTipoId(2L);
        newWine.setOriginId(1L);
        newWine.setUserId(1L);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic UGVkcm86MTIzNDU=");
        HttpEntity<WineModel> request = new HttpEntity<>(newWine, headers); //Hago el request


		ResponseEntity<WineModel> result = restTemplate.exchange(
            address,
            HttpMethod.POST,
            request,
            new ParameterizedTypeReference<WineModel>(){}
        );

        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(result.getBody()).isEqualTo(newWine);
        
    }

    //GET
     @Test
    public void TestWinesGet() {
        Iterable<WineModel> wines = repository.findAll();

        String url = "http://localhost:" + Integer.toString(port) + "/api/v1/wines";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic UGVkcm86MTIzNDU=");
        HttpEntity<String> entity = new HttpEntity<>(headers);


        ResponseEntity<Iterable<WineModel>> result = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<Iterable<WineModel>>(){}
        );

        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(result.getBody()).isEqualTo(wines);
        
    }

    // PUT
    @Test
    public void TestWinesPut(){

        Optional<WineModel> wineOptional = repository.findById(2L);
        then(wineOptional.isPresent()).isEqualTo(true);

        WineModel wine = wineOptional.get();
        wine.setPrice(500);
        wine.setQuantity(34L);


         String url = "http://localhost:" + Integer.toString(port) + "/api/v1/wines/2";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic UGVkcm86MTIzNDU=");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Iterable<WineModel>> result = restTemplate.exchange(
            url,
            HttpMethod.PUT,
            entity,
            new ParameterizedTypeReference<Iterable<WineModel>>(){}
        );

        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(result.getBody()).isEqualTo(wine);
        
    }

    //DELETE
    @Test
    public void TestWinesDelete(){

        UserModel user = userService.getUserById("blancadepedr");

        String url = "http://localhost:"+Integer.toString(port)+"/api/v1/users/blancadepedr";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserModel> entity = new HttpEntity<>(user,headers);

        headers.setContentType(MediaType.APPLICATION_JSON);
        
        ResponseEntity<UserModel> result = restTemplate.exchange(
            url,
            HttpMethod.DELETE,
            entity,
            new ParameterizedTypeReference<UserModel>() {}
        );

        then(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        
    }
    
    } 



