

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class User {
    private int steps=0;
    private String data;
    private int nivel; //colocar na interface grafica uma barra de progresso
    private List<Badge> conquistas = new ArrayList<Badge>();

    public User(String data, int steps, Map<Integer,Integer> passosHora){
        this.data = data;
        this.steps = steps;
        nivel = LudificationNiveis.getInstance().getLevel(steps);
        conquistas = Conquistas.getInstance().getConquistas(passosHora);
    }

    public int getSteps() {
        return steps;
    }

    public String getData() {
        return data;
    }

    public int getNivel() {
        return nivel;
    }

    public List<Badge> getConquistas() {
        return conquistas;
    }

}
