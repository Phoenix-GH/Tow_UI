package com.noname.akio.named.clientAPI.Response.Contracts;

import java.util.ArrayList;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")

public class ContractData
{
    private ArrayList<Contract> contracts;
    /**
     *
     * @return
     * The contracts
     */
    public ArrayList<Contract> getContracts() {
        return contracts;
    }

    /**
     *
     * @param contracts
     * The contracts
     */
    public void setContracts(ArrayList<Contract> contracts) {
        this.contracts = contracts;
    }
}