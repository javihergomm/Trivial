package Trivial;

public abstract class Jugador {
    int puntuacion;
    String nombre;

    public Jugador(int puntuacion, String nombre) {
        this.puntuacion = puntuacion;
        this.nombre = nombre;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
