package com.teamMaryo.wineCellar.E2ETests;

import com.teamMaryo.wineCellar.models.TipoModel;
import com.teamMaryo.wineCellar.repositories.TypeRepository;
import com.teamMaryo.wineCellar.services.TipoService;

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
public class TipoControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired 
    private TypeRepository repository;

    @Autowired 
    private TipoService service;

    //POST
    @Test
    public void TestTiposPost() {

        
        String address = "http://localhost:" + port + "/api/v1/tipos"; 

       
        TipoModel newTipo = new TipoModel();
        newTipo.setNombre("prueba");
        newTipo.setUserId(1L);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic dXNlcjE6MTIz");
        HttpEntity<TipoModel> request = new HttpEntity<>(newTipo, headers); //Hago el request


		ResponseEntity<TipoModel> result = restTemplate.exchange(
            address,
            HttpMethod.POST,
            request,
            new ParameterizedTypeReference<TipoModel>(){}
        );

        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(result.getBody()).isEqualTo(newTipo);
        
    }

    //GET
     @Test
    public void TestWinesGet() {
        Iterable<TipoModel> tipos = service.retrieveAll(1L);

        String url = "http://localhost:" + Integer.toString(port) + "/api/v1/tipos";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic dXNlcjE6MTIz");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        //como le paso al response entity el user ?
        ResponseEntity<Iterable<TipoModel>> result = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<Iterable<TipoModel>>(){}
        );

        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(result.getBody()).isEqualTo(tipos);
        
    }

    //GET por Id
     @Test
    public void TestTiposGetbyId() {
        TipoModel tipo = service.retrieve(1L,2L);

        String url = "http://localhost:" + Integer.toString(port) + "/api/v1/tipos/2";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic dXNlcjE6MTIz");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<TipoModel> result = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<TipoModel>(){}
        );

        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(result.getBody()).isEqualTo(tipo);
        
    }

    // PUT
    @Test
    public void TestTiposPut(){

        Optional<TipoModel> tipoOptional = repository.findById(2L);
        then(tipoOptional.isPresent()).isEqualTo(true);

        TipoModel tipo = tipoOptional.get();
        tipo.setNombre("Chrome");


        String url = "http://localhost:" + Integer.toString(port) + "/api/v1/tipos/2";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic dXNlcjE6MTIz");
        HttpEntity<TipoModel> entity = new HttpEntity<>(tipo,headers);

        ResponseEntity<TipoModel> result = restTemplate.exchange(
            url,
            HttpMethod.PUT,
            entity,
            new ParameterizedTypeReference<TipoModel>(){}
        );

        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(result.getBody()).isEqualTo(tipo);
        
    }

    //DELETE
    @Test
    public void TestTiposDelete(){

        Iterable<TipoModel> tiposBefore = service.retrieveAll(1L);

        String url = "http://localhost:" + Integer.toString(port) + "/api/v1/tipos/2";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic dXNlcjE6MTIz");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<TipoModel> result = restTemplate.exchange(
            url,
            HttpMethod.DELETE,
            entity,
            new ParameterizedTypeReference<TipoModel>(){}
        );

        Iterable<TipoModel> tiposAfter = service.retrieveAll(1L);

        then(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        then(!(tiposAfter).equals(tiposBefore));
        
    }
    
    } 




