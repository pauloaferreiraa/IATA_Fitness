

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Conquistas {

    private Map<DateTime,Integer> passosHora;

    public void putHora(DateTime date,int steps){
        passosHora.put(date,steps);
    }

    public List<Badge> getConquistas(Map<DateTime,Integer> passosHora){
        this.passosHora = passosHora;
        List<Badge> badges = new ArrayList<Badge>();
        if(ifCaminhante()){ badges.add(new Badge(BadgeType.valueOf("Caminhante"))); }
        if(ifMaratonista()){ badges.add(new Badge(BadgeType.valueOf("Maratonista"))); }
        if(ifNightWalker()){ badges.add(new Badge(BadgeType.valueOf("NightWalker"))); }
        return badges;
    }


    private boolean ifMaratonista(){
        int sum=0;
        for(Map.Entry<DateTime,Integer> entry : passosHora.entrySet()){
            sum=entry.getValue();
            // regra três simples para converter km em passos
            if(sum>=54000){
                return true;
            }
        }
        return false;
    }

    private boolean ifCaminhante(){
        int sum=0;
        for(Map.Entry<DateTime,Integer> entry : passosHora.entrySet()){
            sum=entry.getValue();
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
        for(Map.Entry<DateTime,Integer> entry : passosHora.entrySet()){
            if(entry.getKey().getHourOfDay()<=18 && entry.getKey().getHourOfDay()>=6){
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
