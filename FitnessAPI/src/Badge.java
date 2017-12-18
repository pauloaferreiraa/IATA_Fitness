
public class Badge {
    private BadgeType name;
    private String desc;
    private String imagePath;

    public Badge(BadgeType name){
        this.name = name;
        this.desc = desc;
        this.imagePath = setImagePath(name);
    }

    private String setImagePath(BadgeType name){
        switch(name){
            case Caminhante:
                return "Imagens/Caminhante.png";
            case Maratonista:
                return "Imagens/Maratonista.png";
            case NightWalker:
                return "Imagens/NightWalker.png";
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

        }
        return null;
    }

}
