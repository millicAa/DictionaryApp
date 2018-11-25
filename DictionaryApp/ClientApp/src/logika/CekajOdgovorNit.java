/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logika;

import forme.GlavnaForma;
import java.util.ArrayList;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Milica
 */
public class CekajOdgovorNit extends Thread {

    GlavnaForma gf;

    public CekajOdgovorNit(GlavnaForma gf) {
        this.gf = gf;
    }

    @Override
    public void run() {
        while (true) {

            ServerskiOdgovor so = KomunikacijaSaServerom.getInstanca().primiOdgovor();

            ArrayList<String> poruke = (ArrayList<String>) so.getOdgovor();

            String nalepi = "";
            for (String s : poruke) {
                nalepi += s + System.lineSeparator();

            }
            gf.srediFormu(nalepi);

        }
    }

}
