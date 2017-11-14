package com.pacinetes.sigesemovil.Dominio;

/**
 * Created by nacho on 14/11/2017.
 */

public class Marca {
    DatosMarcaNombre marcasNombre;

    private class DatosMarcaNombre {
        public DatoTitle getValue() {
            return value;
        }

        public void setValue(DatoTitle value) {
            this.value = value;
        }

        DatoTitle value;

        private class DatoTitle {
            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            String title;
        }
    }
}
