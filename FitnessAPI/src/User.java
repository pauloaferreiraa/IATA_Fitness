

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class User {
    private int steps=0;
    private String data;
    private int nivel; //colocar na interface grafica uma barra de progresso
    private List<Badge> conquistas = new ArrayList<Badge>();


    public User(String data,int steps, Map<DateTime,Integer> passosHora){
        this.data = data;
        this.steps = steps;
        nivel = LudificationNiveis.getInstance().getLevel(steps);
        conquistas = Conquistas.getInstance().getConquistas(passosHora);
    }
}
