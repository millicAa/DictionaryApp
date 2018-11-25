/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import domen.Prevod;
import domen.Rec;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Milica
 */
public class ModelTabele extends AbstractTableModel {

    ArrayList<Rec> listaReci;

    public ModelTabele(ArrayList<Rec> listaReci) {
        this.listaReci = listaReci;
    }

    @Override
    public int getRowCount() {

        return listaReci.size();
    }

    @Override
    public int getColumnCount() {

        return 2;
    }

    @Override
    public Object getValueAt(int i, int i1) {

        Rec r = listaReci.get(i);

        switch (i1) {

            case 0:
                return r;
            case 1:
                ArrayList<Prevod> listaprevoda = r.getListaPrevoda();
                String nalepi = "";
                for (Prevod p : listaprevoda) {

                    nalepi += p;
                }
                return nalepi;

            default:
                return "poy";

        }
    }

    @Override
    public String getColumnName(int i) {

        switch (i) {

            case 0:
                return "Rec-SRB";
            case 1:
                return "Prevod-ENG";

            default:
                return "poy";

        }

    }
}