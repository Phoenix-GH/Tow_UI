package com.noname.akio.named.clientAPI.Response.Storages;

import java.util.ArrayList;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")

public class StorageData
{
    private ArrayList<Storage> storages;
    /**
     *
     * @return
     * The storages
     */
    public ArrayList<Storage> getStorages() {
        return storages;
    }

    /**
     *
     * @param storages
     * The storages
     */
    public void setContracts(ArrayList<Storage> storages) {
        this.storages = storages;
    }

}