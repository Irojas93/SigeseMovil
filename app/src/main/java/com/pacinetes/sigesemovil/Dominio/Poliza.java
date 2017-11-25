package com.pacinetes.sigesemovil.Dominio;

import java.util.Date;

/**
 * Created by nacho on 21/11/2017.
 */

public class Poliza {


    Members members;

    public void setMembers(Members members) {
        this.members = members;
    }

    public Members getMembers() {
        return members;
    }

    public class Members {

        DatoString polizaNumero;
        DatoString polizaImporteTotal;
        DatoString polizaEstado;
        DatoDate polizaFechaVencimiento;
        DatoDate polizaFechaVigencia;
        DatoCompania polizaCompania;
        DatoCliente polizaCliente;
        DatoDate polizaFechaEmision;

        public DatoString getPolizaNumero() {
            return polizaNumero;
        }
        public DatoString getPolizaImporteTotal() {
            return polizaImporteTotal;
        }
        public DatoString getPolizaEstado() {
            return polizaEstado;
        }
        public DatoCompania getPolizaCompania(){return polizaCompania;}
        public DatoCliente getPolizaCliente(){return polizaCliente;}
        public DatoDate getPolizaFechaEmision() {return polizaFechaEmision;}
        public DatoDate getPolizaFechaVigencia() {return polizaFechaVigencia;}
        public DatoDate getPolizaFechaVencimiento() {return polizaFechaVencimiento;}

        public class DatoString {
            String value;
            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public class DatoCompania {

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

        public class DatoCliente {

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

        public class DatoDate {

            Date value;

            public Date getValue() {
                return value;
            }

            public void setValue(Date value) {
                this.value = value;
            }
        }
    }
}
