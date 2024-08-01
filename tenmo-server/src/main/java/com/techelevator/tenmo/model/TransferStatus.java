package com.techelevator.tenmo.model;

public class TransferStatus {
    private int transferStatusId;
    private String name;

    public TransferStatus(int transferStatusId, String name) {
        this.transferStatusId = transferStatusId;
        this.name = name;
    }

    public TransferStatus() {
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
