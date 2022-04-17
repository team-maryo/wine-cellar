package com.teamMaryo.wineCellar.models;

import jakarta.validation.constraints.Size;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="CLIENT")
public class ClientModel {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Size(min=0, max=64)
    @Column(name="CLIENT_NAME")
	private String clientName;

    @Column(name="PASSWORD")
	private String password;

    @Size(min=0, max=64)
    @Column(name="FIRST_NAME")
    private String firstName;

    @Size(min=0, max=64)
    @Column(name="LAST_NAME")
    private String lastName;

    @Size(min=0, max=255)
    @Column(name="EMAIL")
	private String email;

    public ClientModel() {}

    public ClientModel(Long id, @Size(min = 0, max = 64) String clientName, String password,
            @Size(min = 0, max = 64) String firstName, @Size(min = 0, max = 64) String lastName,
            @Size(min = 0, max = 255) String email) {
        this.id = id;
        this.clientName = clientName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

