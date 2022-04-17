package com.teamMaryo.wineCellar.models;

public interface Clientelable {
    void setClient(ClientModel client);
    void setId(Long id);
    ClientModel getClient();
}
