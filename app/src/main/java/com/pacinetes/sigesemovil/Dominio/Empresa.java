package com.pacinetes.sigesemovil.Dominio;

/**
 * Created by nacho on 25/11/2017.
 */

public class Empresa {

    Members members;

    public Members getMembers() {
        return members;
    }

    public void setMembers(Members members) {
        this.members = members;
    }


    public class Members {

        DatoString empresaRazonSocial;
        DatoString personaCuitCuil;
        DatoString personaDireccion;
        DatoLocalidad personaLocalidad;
        DatoString personaMail;
        DatoString personaTelefono;

        public DatoString getEmpresaRazonSocial() {return empresaRazonSocial;}
        public DatoString getPersonaCuitCuil() {return personaCuitCuil;}
        public DatoString getPersonaDireccion() {return personaDireccion;}
        public DatoLocalidad getPersonaLocalidad() {return personaLocalidad;}
        public DatoString getPersonaMail() {return personaMail;}
        public DatoString getPersonaTelefono() {return personaTelefono;}


        public class DatoString {

            String value;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public class DatoLocalidad {
            DatoTitle value;

            public DatoTitle getValue() {
                return value;
            }

            public void setValue(DatoTitle value) {
                this.value = value;
            }

            public class DatoTitle {

                String title;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

            }
        }
    }
}
