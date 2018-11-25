/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import domen.Korisnik;
import domen.Rec;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import konstante.Operacije;
import logika.Kontroler;
import transfer.KlijentskiZahtev;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Milica
 */
public class ObradaZahteva extends Thread{
    
    Socket s;

    public ObradaZahteva(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        
        while(true) {
            KlijentskiZahtev kz = primiZahtev();
            ServerskiOdgovor so = new ServerskiOdgovor();
            
            switch(kz.getOperacija()){
            
                case Operacije.ULOGUJ_SE:
                    
                    String korIme = (String) kz.getParametar();
                    Korisnik k= Kontroler.getInstanca().nadjiKorisnika(korIme);
                    so.setOdgovor(k);
                    //ovde treba samo jednom da se posalje
                    posaljiOdgovor(so);
                    break;
            
                case Operacije.UBACI_REC:
                    
                    Rec rec = (Rec) kz.getParametar();
                    ArrayList<Rec> lista= Kontroler.getInstanca().nadjiReci();
                    if(!lista.contains(rec)){
                    so= Kontroler.getInstanca().unesiNovuRec(rec);
                    } else{
                    so= Kontroler.getInstanca().izmeniReci(rec, lista);
                    
                    }
                    //ovde treba svima
                    ArrayList<Socket> listaKl= Kontroler.getInstanca().getListaKlijenata();
                    for (Socket sok : listaKl) {
                        posaljiOdgovorSvima(so, sok);
                    }
                    break;
                    
                    
            
            
            }
            //posaljiOdgovor(so);
        
        }
        
    }
    public KlijentskiZahtev primiZahtev() {
        KlijentskiZahtev kz = new KlijentskiZahtev();
        try {
            
            
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            kz= (KlijentskiZahtev) ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(ObradaZahteva.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ObradaZahteva.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return kz;
    }
    
    
    public void posaljiOdgovor(ServerskiOdgovor so) {
    
        
        try {
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(so);
        } catch (IOException ex) {
            Logger.getLogger(ObradaZahteva.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    //////
    public void posaljiOdgovorSvima(ServerskiOdgovor so, Socket soket) {
    
        
        try {
            ObjectOutputStream oos = new ObjectOutputStream(soket.getOutputStream());
            oos.writeObject(so);
        } catch (IOException ex) {
            Logger.getLogger(ObradaZahteva.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
}
