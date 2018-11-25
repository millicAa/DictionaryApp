/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import com.sun.org.apache.xpath.internal.operations.Equals;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Milica
 */
public class Rec implements Serializable{
    
    private int recID;
    private String rec;
    private Korisnik korisnik;
    private ArrayList<Prevod> listaPrevoda;

    public int getRecID() {
        return recID;
    }

    public void setRecID(int recID) {
        this.recID = recID;
    }

    public String getRec() {
        return rec;
    }

    public void setRec(String rec) {
        this.rec = rec;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public ArrayList<Prevod> getListaPrevoda() {
        return listaPrevoda;
    }

    public void setListaPrevoda(ArrayList<Prevod> listaPrevoda) {
        this.listaPrevoda = listaPrevoda;
    }

    public Rec() {
        listaPrevoda= new ArrayList<>();
                
    }

    public Rec(int recID, String rec, Korisnik korisnik, ArrayList<Prevod> listaPrevoda) {
        this.recID = recID;
        this.rec = rec;
        this.korisnik = korisnik;
        this.listaPrevoda = listaPrevoda;
    }

    @Override
    public String toString() {
        return rec +"("+korisnik+")";
        
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Rec other = (Rec) obj;
        if (!Objects.equals(this.rec, other.rec)) {
            return false;
        }
        return true;
    }
    

    
    
    
}
