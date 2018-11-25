/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;

/**
 *
 * @author Milica
 */
public class Korisnik implements Serializable{
    
    private int korisnikID;
    private String ime;
    private String prezime;
    private String korisnickoIme;

    public int getKorisnikID() {
        return korisnikID;
    }

    public void setKorisnikID(int korisnikID) {
        this.korisnikID = korisnikID;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }


    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public Korisnik() {
    }

    public Korisnik(int korisnikID, String ime, String prezime, String korisnickoIme) {
        this.korisnikID = korisnikID;
        this.ime = ime;
        this.prezime = prezime;
        
        this.korisnickoIme = korisnickoIme;
    }

    @Override
    public String toString() {
        return "@"+korisnickoIme; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
