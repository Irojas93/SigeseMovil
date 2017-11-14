package com.pacinetes.sigesemovil.Servicio;

import java.util.List;

/**
 * Created by nacho on 14/11/2017.
 */

public class RestLinks {
    List<RestLink> links;
    Object extensions;

    public List<RestLink> getLinks() {
        return links;
    }

    public void setLinks(List<RestLink> links) {
        this.links = links;
    }

    public Object getExtensions() {
        return extensions;
    }

    public void setExtensions(Object extensions) {
        this.extensions = extensions;
    }
}
