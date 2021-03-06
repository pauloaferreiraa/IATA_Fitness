
public class Badge {
    private BadgeType name;
    private String desc;
    private String imagePath;

    public Badge(BadgeType name){
        this.name = name;
        this.desc = desc;
        this.imagePath = setImagePath(name);
    }

    public BadgeType getName(){
        return name;
    }

    public String getImagePath(){
        return imagePath;
    }

    private String setImagePath(BadgeType name){
        switch(name){
            case Caminhante:
                return "Imagens/Caminhante.png";
            case Maratonista:
                return "Imagens/Maratonista.png";
            case NightWalker:
                return "Imagens/NightWalker.png";
            case Peregrino:
                return "Imagens/Peregrino.png";
        }
        return null;
    }


    private String setDesc(BadgeType name){
        switch (name){
            case Maratonista:
                return "Caminhou mais de 42 km";
            case Caminhante:
                return "Caminhou o primeiro quilométro";
            case NightWalker:
                return "Prefere caminhar durante a noite";
            case Peregrino:
                return "Caminhou em todos os intervalos de tempo do dia";

        }
        return null;
    }

}
