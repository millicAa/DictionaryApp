/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import forme.ServerskaForma;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import logika.Kontroler;

/**
 *
 * @author Milica
 */
public class PokreniServer extends Thread {

    ServerskaForma sf;

    public PokreniServer(ServerskaForma sf) {
        this.sf = sf;
    }

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(9000);
            sf.serverPokrenut();
            System.out.println("Server je pokrenut");
            NitZaZatvaranje nz = new NitZaZatvaranje(ss, this);
            nz.start();
            while (!isInterrupted()) {
                Socket s = ss.accept();
                //
                Kontroler.getInstanca().getListaKlijenata().add(s);
                //
                System.out.println("Klijent se nakacio");
                ObradaZahteva oz = new ObradaZahteva(s);
                oz.start();
            }
            
            
        } catch (IOException ex) {   //ulazi ovde na ss.close()
            System.out.println("Server je zaustavljen");
            sf.serverNijePokrenut();
        }
    }

}
