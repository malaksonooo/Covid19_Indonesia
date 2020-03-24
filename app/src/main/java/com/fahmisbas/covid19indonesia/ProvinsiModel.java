package com.fahmisbas.covid19indonesia;

public class ProvinsiModel {

    ProvinsiModel(){

    }

    int kodeProvinsi;
    String namaProvinsi,kasusPositif,kasusSembuh,kasusMeninggal;

    public int getKodeProvinsi() {
        return kodeProvinsi;
    }

    public void setKodeProvinsi(int kodeProvinsi) {
        this.kodeProvinsi = kodeProvinsi;
    }

    public String getNamaProvinsi() {
        return namaProvinsi;
    }

    public void setNamaProvinsi(String namaProvinsi) {
        this.namaProvinsi = namaProvinsi;
    }

    public String getKasusPositif() {
        return kasusPositif;
    }

    public void setKasusPositif(String kasusPositif) {
        this.kasusPositif = kasusPositif;
    }

    public String getKasusSembuh() {
        return kasusSembuh;
    }

    public void setKasusSembuh(String kasusSembuh) {
        this.kasusSembuh = kasusSembuh;
    }

    public String getKasusMeninggal() {
        return kasusMeninggal;
    }

    public void setKasusMeninggal(String kasusMeninggal) {
        this.kasusMeninggal = kasusMeninggal;
    }
}
