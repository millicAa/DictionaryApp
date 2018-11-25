/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import domen.Prevod;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Milica
 */
public class ModelTabele extends AbstractTableModel {

    ArrayList<Prevod> lista;

    public ModelTabele() {

        lista = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        Prevod p = lista.get(i);
        if (i1 == 0) {

            return p.getPrevod();
        } else {
            return "";

        }
    }

    @Override
    public String getColumnName(int i) {

        if (i == 0) {

            return "Prevod";
        } else {

            return "";
        }
    }

    public void dodajPrevod(Prevod p) {

        lista.add(p);
        fireTableDataChanged();
    }

    public ArrayList<Prevod> getLista() {
        return lista;
    }
    
    

}
