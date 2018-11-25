/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import domen.Korisnik;
import domen.Prevod;
import domen.Rec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Milica
 */
public class DBBroker {

    Connection konekcija;

    public void ucitajDrajver() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void otvoriKonekciju() {

        String url = "jdbc:mysql://localhost:3306/predrok2017";
        String username = "root";
        String password = "";
        try {

            konekcija = DriverManager.getConnection(url, username, password);
            konekcija.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void zatvoriKon() {

        try {
            konekcija.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void commit() {
        try {
            konekcija.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void rollback() {
        try {
            konekcija.rollback();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Rec> vratiReci() {
        ArrayList<Rec> lista = new ArrayList<>();

        try {
            String upit = "select * from rec r join korisnik k on r.korisnikID = k.korisnikID";
            Statement s = konekcija.createStatement();
            ResultSet rs = s.executeQuery(upit);
            while (rs.next()) {
                int korisnikID = rs.getInt("korisnikID");
                String ime = rs.getString("ime");
                String prezime = rs.getString("prezime");
                String korisnickoIme = rs.getString("korisnickoIme");

                Korisnik k = new Korisnik(korisnikID, ime, prezime, korisnickoIme);

                int recID = rs.getInt("recID");
                String rec = rs.getString("rec");

                Rec r = new Rec(recID, rec, k, null);
                lista.add(r);

            }

        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }

    public ArrayList<Prevod> vratiPrevode(int recID) {
        ArrayList<Prevod> lista = new ArrayList<>();

        try {
            String upit = "select * from prevod p join korisnik k on p.korisnikID = k.korisnikID where p.recID=" + recID;
            Statement s = konekcija.createStatement();
            ResultSet rs = s.executeQuery(upit);
            while (rs.next()) {
                int korisnikID = rs.getInt("korisnikID");
                String ime = rs.getString("ime");
                String prezime = rs.getString("prezime");
                String korisnickoIme = rs.getString("korisnickoime");

                Korisnik k = new Korisnik(korisnikID, ime, prezime, korisnickoIme);

                int prevodID = rs.getInt("prevodID");
                String prevod = rs.getString("prevod");

                Prevod p = new Prevod(prevodID, k, prevod);
                lista.add(p);

            }

        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }

    public Korisnik nadjiKorisnika(String korIme) {
        Korisnik k = null;

        try {
            String upit = "select * from korisnik k where k.korisnickoime='" + korIme + "'";
            Statement s = konekcija.createStatement();
            ResultSet rs = s.executeQuery(upit);
            while (rs.next()) {
                int korisnikID = rs.getInt("korisnikID");
                String ime = rs.getString("ime");
                String prezime = rs.getString("prezime");
                String korisnickoIme = rs.getString("korisnickoime");

                k = new Korisnik(korisnikID, ime, prezime, korisnickoIme);

            }

        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return k;

    }

    public void unesiRec(Rec rec) throws SQLException {
        String sql = "INSERT INTO Rec(rec, korisnikID) values (?,?)";
        PreparedStatement ps = konekcija.prepareStatement(sql);
        ps.setString(1, rec.getRec());
        ps.setInt(2, rec.getKorisnik().getKorisnikID());
        ps.executeUpdate();

    }

    public int vratiIDReci() {
        int maks = 0;
        try {
            
            String upit = "select max(recID) as maks from rec";
            Statement s = konekcija.createStatement();
            ResultSet rs = s.executeQuery(upit);
            while(rs.next()) {
            
            maks=rs.getInt("maks");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return maks; 

    }

    public void unesiPrevod(Prevod p, Rec rec) throws SQLException {

        String sql = "INSERT INTO Prevod(prevod, korisnikID, recID) values (?,?,?)";
        PreparedStatement ps = konekcija.prepareStatement(sql);
        ps.setString(1, p.getPrevod());
        ps.setInt(2, p.getKorisnik().getKorisnikID());
        ps.setInt(3, rec.getRecID());
        ps.executeUpdate();
        
    }

}
