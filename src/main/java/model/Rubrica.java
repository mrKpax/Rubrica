package model;

import java.util.Vector;

public class Rubrica {
    private Vector<Persona> contatti;

    public Rubrica() {
        this.contatti = new Vector<>();
    }

    public Vector<Persona> getContatti() {
        return contatti;
    }

    public void aggiungiPersona(Persona p) {
        if (p != null) {
            contatti.add(p);
        }
    }

    public void rimuoviPersona(int indice) {
        if (indice >= 0 && indice < contatti.size()) {
            contatti.remove(indice);
        }
    }

    public void aggiornaPersona(int indice, Persona p) {
        if (indice >= 0 && indice < contatti.size() && p != null) {
            contatti.set(indice, p);
        }
    }
}