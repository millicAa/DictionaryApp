/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logika;

import db.DBBroker;
import domen.Korisnik;
import domen.Prevod;
import domen.Rec;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Milica
 */
public class Kontroler {

    private static Kontroler instanca;
    DBBroker db;
    LinkedList<String> lista;
    ArrayList<Socket> listaKlijenata;

    private Kontroler() {
        db = new DBBroker();
        lista = new LinkedList<>();
        listaKlijenata = new ArrayList<>();

    }

    public static Kontroler getInstanca() {

        if (instanca == null) {
            instanca = new Kontroler();
        }
        return instanca;
    }

    public ArrayList<Rec> nadjiReci() {

        db.ucitajDrajver();
        db.otvoriKonekciju();
        ArrayList<Rec> lista = db.vratiReci();
        for (Rec rec : lista) {
            ArrayList<Prevod> listaPrevoda = db.vratiPrevode(rec.getRecID());
            rec.setListaPrevoda(listaPrevoda);
        }
        db.zatvoriKon();
        return lista;
    }

    public Korisnik nadjiKorisnika(String korIme) {

        db.ucitajDrajver();
        db.otvoriKonekciju();
        Korisnik k = db.nadjiKorisnika(korIme);
        db.zatvoriKon();
        return k;

    }

    public ServerskiOdgovor unesiNovuRec(Rec rec) {

        ServerskiOdgovor so = new ServerskiOdgovor();
        db.ucitajDrajver();
        db.otvoriKonekciju();
        try {
            db.unesiRec(rec);
            rec.setRecID(db.vratiIDReci());//max
            for (Prevod p : rec.getListaPrevoda()) {
                db.unesiPrevod(p, rec);
            }
            db.commit();   //!!!!
            String nalepi = "";
            nalepi += "@" + rec.getRec();
            for (Prevod p : rec.getListaPrevoda()) {
                nalepi += p + ",";

            }
            nalepi += "@" + rec.getKorisnik();
            lista.addFirst(nalepi);
            so.setOdgovor(vrati3poslednjaElelementa());

        } catch (SQLException ex) {
            db.rollback();
            so.setOdgovor(new ArrayList<String>());

        }

        db.zatvoriKon();
        return so;

    }

    public ServerskiOdgovor izmeniReci(Rec rec, ArrayList<Rec> lista) {

        ServerskiOdgovor so = new ServerskiOdgovor();
        Rec izabranaRec = null;
        for (Rec r1 : lista) {
            if (r1.equals(rec)) {
                izabranaRec = r1;
            }

        }
        ArrayList<Prevod> listaZaUbacivanje = new ArrayList<>();
        ArrayList<Prevod> listaDuplikata = new ArrayList<>();
        ArrayList<Prevod> listaIzabranih = izabranaRec.getListaPrevoda();

        for (Prevod p1 : rec.getListaPrevoda()) {
            if (listaIzabranih.contains(p1)) {

                listaDuplikata.add(p1);
            } else {

                listaZaUbacivanje.add(p1);
            }
        }
        db.ucitajDrajver();
        db.otvoriKonekciju();

        try {
            for (Prevod prevod : listaZaUbacivanje) {
                db.unesiPrevod(prevod, izabranaRec);
            }
            db.commit();
            //prikaz novih reci pod b)
            String nalepi = "@novoZnacenje ";
            for (Prevod prevod : listaZaUbacivanje) {

                nalepi += prevod + ",";
            }
            nalepi += " @rec " + rec.getRec() + ", @korisnik " + rec.getKorisnik();
            nalepi += " @ostala znacenja: ";

            for (Prevod prevod : listaDuplikata) {
                nalepi += prevod + ",";
            }
            this.lista.add(nalepi);
            so.setOdgovor(vrati3poslednjaElelementa());

        } catch (SQLException ex) {
            db.rollback();
            so.setOdgovor(new ArrayList<String>());

        }
        return so;

    }

    public ArrayList<String> vrati3poslednjaElelementa() {

        ArrayList<String> lis = new ArrayList<>();
        int brojac = 0;
        for (String s : lista) {
            lis.add(s);
            brojac++;
            if (brojac > 2) {
                return lis;

            }
        }
        return lis;
    }

    public ArrayList<Socket> getListaKlijenata() {
        return listaKlijenata;
    }

}
