package com.pacinetes.sigesemovil.Dominio;

import java.io.Serializable;
import java.util.List;

/**
 * Created by nacho on 15/11/2017.
 */

public class IsisAction implements Serializable {
    String id;
    String memberType;
    List<IsisService> links;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public List<IsisService> getLinks() {
        return links;
    }

    public void setLinks(List<IsisService> links) {
        this.links = links;
    }
}
