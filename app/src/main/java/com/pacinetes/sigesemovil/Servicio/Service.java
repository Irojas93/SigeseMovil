package com.pacinetes.sigesemovil.Servicio;

import com.pacinetes.sigesemovil.Dominio.IsisService;

import java.util.List;

/**
 * Created by nacho on 15/11/2017.
 */

public class Service {
    List<IsisService> value;
    Object extensions;
    List<RestLink> links;

    public List<IsisService> getValue() {
        return value;
    }

    public void setValue(List<IsisService> value) {
        this.value = value;
    }

    public Object getExtensions() {
        return extensions;
    }

    public void setExtensions(Object extensions) {
        this.extensions = extensions;
    }

    public List<RestLink> getLinks() {
        return links;
    }

    public void setLinks(List<RestLink> links) {
        this.links = links;
    }
}
