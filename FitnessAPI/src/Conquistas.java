



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Conquistas {

/*  private Map<DateTime,Integer> passosHora;

    public void putHora(DateTime date,int steps){
        passosHora.put(date,steps);
    }*/

    private static Conquistas instance = null;

    public static Conquistas getInstance(){
        if(instance==null){
            instance = new Conquistas();
        }
        return instance;
    }

    //class não pode ser instanciada
    private Conquistas(){}

    public List<Badge> getConquistas(Map<Integer,Integer> passosHora){
        //this.passosHora = passosHora;
        List<Badge> badges = new ArrayList<Badge>();
        if(ifCaminhante(passosHora)){ badges.add(new Badge(BadgeType.valueOf("Caminhante"))); }
        if(ifMaratonista(passosHora)){ badges.add(new Badge(BadgeType.valueOf("Maratonista"))); }
        if(ifNightWalker(passosHora)){ badges.add(new Badge(BadgeType.valueOf("NightWalker"))); }
        return badges;
    }

    private boolean ifMaratonista(Map<Integer,Integer> passosHora){
        int sum=0;
        for(Map.Entry<Integer,Integer> entry : passosHora.entrySet()){
            sum=entry.getValue();
            // regra três simples para converter km em passos
            if(sum>=54000){
                return true;
            }
        }
        return false;
    }

    private boolean ifCaminhante(Map<Integer,Integer> passosHora){
        int sum=0;
        for(Map.Entry<Integer,Integer> entry : passosHora.entrySet()){
            sum=entry.getValue();
            // regra três simples para converter km em passos
            if(sum>=1276){
                return true;
            }
        }
        return false;
    }

    private boolean ifNightWalker(Map<Integer,Integer> passosHora){
        int sumDia=0;
        int sumNoite = 0;
        for(Map.Entry<Integer,Integer> entry : passosHora.entrySet()){
            if(entry.getKey()<=18 && entry.getKey()>=6){
                sumDia=entry.getValue();
            }else{
                sumNoite=entry.getValue();
            }
        }
        if(sumDia<sumNoite){
            return true;
        }else{
            return false;
        }
    }

}
