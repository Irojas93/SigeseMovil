package com.pacinetes.sigesemovil.Dominio;

import java.util.Date;

/**
 * Created by nacho on 25/11/2017.
 */

public class Cliente {

    Members members;

    public Members getMembers() {
        return members;
    }

    public void setMembers(Members members) {
        this.members = members;
    }

    public class Members {

        DatoString clienteNombre;
        DatoString clienteApellido;
        DatoDate clienteFechaNacimiento;
        DatoString personaTelefono;
        DatoString clienteDni;
        DatoString clienteTipoDocumento;
        DatoString personaDireccion;
        DatoLocalidad personaLocalidad;

        public DatoString getPersonaDireccion() {return personaDireccion;}
        public DatoString getClienteNombre() {return clienteNombre;}
        public DatoString getClienteApellido() {return clienteApellido;}
        public DatoDate getClienteFechaNacimiento() {return clienteFechaNacimiento;}
        public DatoString getPersonaTelefono() {return personaTelefono;}
        public DatoString getClienteDni() {return clienteDni;}
        public DatoString getclienteTipoDocumento() {return clienteTipoDocumento;}
        public DatoLocalidad getPersonaLocalidad() {return personaLocalidad;}

        public class DatoString {

            String value;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public class DatoDate {

            Date value;

            public Date getValue() {
                return value;
            }

            public void setValue(Date value) {
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
