package com.example.userorchestrationapi.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CryptoDTO {
    private String coin;
    private String wallet;
    private String network;

    // Getters and Setters

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }
}

