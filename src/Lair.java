import java.util.ArrayList;

public class Lair implements Autowrap {
    private String usualArea;
    ArrayList<DCAttack> lairActions;
    private ArrayList<String> regionalEffects;

    public Lair(String usualArea, ArrayList<DCAttack> lairActions, ArrayList<String> regionalEffects) {
        this.usualArea = usualArea;
        this.lairActions = lairActions;
        this.regionalEffects = regionalEffects;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("|-----------------------|\n");
        sb.append("|               Lair             |\n");
        sb.append("|-----------------------|\n\n");
        sb.append("Usually found in ").append(usualArea).append("\n\n");
        if(lairActions != null && lairActions.size() > 0){
            sb.append("----- Lair actions -----\n");
            for(DCAttack d : lairActions) {
                sb.append(d.toString()).append("\n");
            }
            sb.append("\n");
        }
        if(regionalEffects != null && regionalEffects.size()>0) {
            sb.append("----- Regional Effects -----\n");
            for(String s : regionalEffects){
                sb.append(autoWrap(s)).append("\n");
            }
        }


        return sb.toString();
    }

    void display() {
        System.out.println("Usually found in: " + usualArea);
        System.out.println("\n---Lair Actions---\n");
        if(lairActions!=null) {
            for (DCAttack k : lairActions) {
                System.out.print("* ");
                System.out.println("Must succeed on " + k.getSave() + " save against DC " + k.getAttackBonus() + " or take " +
                        k.getDamage(false) + " " + k.getDamageType() + " and " +
                        k.getEffect());
                if (k.getHalfDamage()) {
                    System.out.println("Take " + k.getDamage(false) / 2 + " " + k.getDamageType()
                            + " damage on a save");
                }
            }
        }
        System.out.println("\n---Regional Effects---\n");
        if(regionalEffects!=null) {
            for (String k : regionalEffects) {
                System.out.print("* ");
                System.out.println(k);
            }
        }
    }
}
