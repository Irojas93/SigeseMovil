package com.pacinetes.sigesemovil.Servicio;

import java.util.List;

/**
 * Created by nacho on 25/11/2017.
 */

public class Clientes {
    List<RestLink> links;
    Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
    public List<RestLink> getLinks() {
        return links;
    }

    public void setLinks(List<RestLink> links) {
        this.links = links;
    }

    public class Result {
        List<RestLink> value;

        public List<RestLink> getValue() {
            return value;
        }

        public void setValue(List<RestLink> value) {
            this.value = value;
        }
    }
}
