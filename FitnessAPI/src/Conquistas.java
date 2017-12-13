import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Conquistas {

    private Map<Integer,Integer> passosHora;

    public List<Badge> getConquistas(Map<Integer,Integer> passosHora){
        this.passosHora = passosHora;
        List<Badge> badges = new ArrayList<Badge>();
        if(ifCaminhante()){ badges.add(new Badge(BadgeType.valueOf("Caminhante"))); }
        if(ifMaratonista()){ badges.add(new Badge(BadgeType.valueOf("Maratonista"))); }
        if(ifNightWalker()){ badges.add(new Badge(BadgeType.valueOf("NightWalker"))); }
        return badges;
    }


    private boolean ifMaratonista(){
        int sum=0;
        for(Map.Entry<Integer,Integer> entry : passosHora.entrySet()){
            sum+=entry.getValue();
            // regra três simples para converter km em passos
            if(sum>=54000){
                return true;
            }
        }
        return false;
    }

    private boolean ifCaminhante(){
        int sum=0;
        for(Map.Entry<Integer,Integer> entry : passosHora.entrySet()){
            sum+=entry.getValue();
            // regra três simples para converter km em passos
            if(sum>=1276){
                return true;
            }
        }
        return false;
    }

    private boolean ifNightWalker(){
        int sumDia=0;
        int sumNoite = 0;
        for(Map.Entry<Integer,Integer> entry : passosHora.entrySet()){
            if(entry.getValue()<=18 && entry.getValue()>=6){
                sumDia+=entry.getValue();
            }else{
                sumNoite+=entry.getValue();
            }
        }
        if(sumDia<sumNoite){
            return true;
        }else{
            return false;
        }
    }

}
