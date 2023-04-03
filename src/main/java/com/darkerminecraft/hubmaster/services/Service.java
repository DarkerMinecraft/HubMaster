package com.darkerminecraft.hubmaster.services;

import com.darkerminecraft.hubmaster.HubMaster;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class Service implements IService {

    public HubMaster plugin;
    @Getter
    private String name;

}
