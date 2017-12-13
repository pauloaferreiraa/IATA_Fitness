import java.util.ArrayList;
import java.util.List;

public class User {
    private int steps=0;
    private String data;
    private int nivel; //colocar na interface grafica uma barra de progresso
    private List<Badge> conquistas = new ArrayList<Badge>();


    public User(String data,int steps){
        this.data = data;
        this.steps = steps;
        LudificationNiveis ln = LudificationNiveis.getInstance();
        this.nivel = ln.getLevel(steps);
    }

}
