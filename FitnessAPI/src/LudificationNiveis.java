public class LudificationNiveis {
    private int nivel1 = 1000;
    private int nivel2 = 5000;
    private int nivel3 = 10000;
    private int nivel4 = 20000;
    private int nivel5 = 30000;
    private int nivel6 = 50000;
    private int nivel7 = 70000;
    private static LudificationNiveis instance = null;

    public static LudificationNiveis getInstance(){
        if(instance==null){
            instance = new LudificationNiveis();
        }
        return instance;
    }


    public int getLevel(int steps){
        if(steps>=nivel7){ return 7; }
        if(steps>=nivel6){ return 6; }
        if(steps>=nivel5){ return 5; }
        if(steps>=nivel4){ return 4; }
        if(steps>=nivel3){ return 3; }
        if(steps>=nivel2){ return 2; }
        if(steps>=nivel1){ return 1; }
        return 0;
    }



}
