import java.util.ArrayList;

public class Monster implements Dice, Comparable<Monster>, Autowrap {

    private String name;
    private String size;
    private String type;
    private int proficiencyBonus;
    private int ac;
    private int hpAvg;
    public int hpVaried;
    public String speed;
    private int STR;
    private int DEX;
    private int CON;
    private int INT;
    private int WIS;
    private int CHA;
    private int HON;
    private int SAN;
    public ArrayList<String> passives = new ArrayList<>();
    public ArrayList<Boolean> savingThrows = new ArrayList<>();
    private int[] skillList;
    private int[] skillBonuses;
    private String senses;
    private String languages;
    private double CR;
    public ArrayList<Attack> attacks = new ArrayList<>();
    private int[] multiattack;

    public Monster() {}

    public Monster(String name,
                   String size,
                   String type,
                   int proficiencyBonus,
                   int ac,
                   int hpAvg,
                   String speed,
                   int STR,
                   boolean StrProficiency,
                   int DEX,
                   boolean DexProficiency,
                   int CON,
                   boolean ConProficiency,
                   int INT,
                   boolean IntProficiency,
                   int WIS,
                   boolean WisProficiency,
                   int CHA,
                   boolean ChaProficiency,
                   //int HON,
                   //int SAN,
                   int[] skills,
                   String senses,
                   String languages,
                   double cr)
    {

        this.name = name;
        this.size = size;
        this.type = type;
        this.ac = ac;
        this.proficiencyBonus = proficiencyBonus;
        this.hpAvg = hpAvg;
        this.speed = speed;
        this.STR = STR;
        savingThrows.add(StrProficiency);
        this.DEX = DEX;
        savingThrows.add(DexProficiency);
        this.CON = CON;
        savingThrows.add(ConProficiency);
        this.INT = INT;
        savingThrows.add(IntProficiency);
        this.WIS = WIS;
        savingThrows.add(WisProficiency);
        this.CHA = CHA;
        savingThrows.add(ChaProficiency);
        this.skillList=skills;
        this.skillBonuses=calculateSkillBonuses();
        this.senses=senses;
        this.languages=languages;
        this.CR = cr;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("\n");
        sb.append("-------------------------------\n");
        sb.append(size).append(" ").append(type).append("\n");
        sb.append("AC: ").append(ac).append("\n");
        sb.append("Hit Points: ").append(hpAvg).append("\n");
        sb.append("Speed: ").append(speed).append("\n\n");
        sb.append("STR: ").append(STR).append(" (").append(modifierString(STR)).append(")");
        sb.append(" DEX: ").append(DEX).append(" (").append(modifierString(DEX)).append(")");
        sb.append(" CON: ").append(CON).append(" (").append(modifierString(CON)).append(")");
        sb.append(" INT: ").append(INT).append(" (").append(modifierString(INT)).append(")");
        sb.append(" WIS: ").append(WIS).append(" (").append(modifierString(WIS)).append(")");
        sb.append(" CHA: ").append(CHA).append(" (").append(modifierString(CHA)).append(")");

        sb.append("\n\n");

        sb.append("Saving Throws:\n");
        sb.append("STR: ").append(savingThrowString(0, STR));
        sb.append(" DEX: ").append(savingThrowString(1, DEX));
        sb.append(" CON: ").append(savingThrowString(2, CON));
        sb.append(" INT: ").append(savingThrowString(3, INT));
        sb.append(" WIS: ").append(savingThrowString(4, WIS));
        sb.append(" CHA: ").append(savingThrowString(5, CHA));

        sb.append("\n\n");


        if (skillList.length>0){
            StringBuilder subSB = new StringBuilder();
            subSB.append("Skills: ");
            for( int n : skillList){
                subSB.append(Skills.values()[n]).append(" +").append(getSkillBonuses(n)).append(" ");
            }
            sb.append(subSB.toString()).append("\n");
        }
        sb.append("Senses: ").append(senses).append("\n");
        sb.append("Languages: ").append(languages).append("\n");
        sb.append("CR: ").append(CR).append("\n\n");
        if(passives.size()>0){
            sb.append("------ Passives -----\n");
            for(String s : passives){
                sb.append("* ").append(autoWrap(s)).append("\n");
            }
        }
        sb.append("\n");
        if(attacks.size()>0){
            sb.append("----- Attacks -----\n");
            for(Attack a : attacks){
                sb.append("* ").append(a.toString()).append("\n");
            }
        }
        sb.append("\n");

        return sb.toString();
    }

    void display1() {
        System.out.println(name);
        System.out.println("---------------------------------");
        System.out.println(size + " " + type);
        System.out.println("AC: " + ac);
        System.out.println("Hit Points: " + hpAvg +", " + hpVaried);
        System.out.print("Speed: " + speed);
        System.out.println();
        System.out.println("STR: " + STR+ " DEX: " + DEX + " CON: " + CON + " INT: " + INT + " WIS: " + WIS + " CHA: " + CHA + " HON: " + HON + " SAN: " + SAN);
        System.out.println("Saving Throws: STR: " + savingThrow(0,STR) + ", DEX: " + savingThrow(1, DEX) + ", CON: " + savingThrow(2, CON) + ", INT: " +
                savingThrow(3, INT) + ", WIS: " + savingThrow(4, WIS) + ", CHA: " + savingThrow(5, CHA)
                //+", HON: " + savingThrow(6, HON) + ", SAN: " + savingThrow(7, SAN)
        );
        if (skillList.length>=1) {
            System.out.print("Skills: ");
            for(int n : skillList) {
                System.out.print(Skills.values()[n] + " +" + getSkillBonuses(n) + " ");
            }
        }
        System.out.println();
        System.out.println("Senses: " + senses);
        System.out.println("Languages: " + languages);
        System.out.println("Challenge Rating: " + CR);
        System.out.println();
    }

    void displayPassives() {
        System.out.println("---Passives---");
        for (String n : passives) {
            System.out.println(n+ "\n");
        }
    }

    void display2() {
        System.out.println("---Attacks---");
        for (Attack attack : attacks) {
            System.out.println("- " + attack.getName());
        }
        System.out.println();
    }

    boolean isMultiattackImplemented() {
        return isMethodImplemented(this, "multiattack");
    }

    public void fullDisplay() {
        display1();
        displayPassives();
        display2();
    }

    public void setName(String name) {
        this.name=name;
    }

    public void setSize(String size) {
        this.size=size;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setProficiencyBonus(int proficiencyBonus) {
        this.proficiencyBonus = proficiencyBonus;
    }

    public void setAc(int ac) {
        this.ac = ac;
    }

    public void setHpAvg(int hpAvg) {
        this.hpAvg=hpAvg;
    }

    public void setHpVaried(int hpVaried) {
        this.hpVaried = hpVaried;
    }

    public void setSpeed(String speed){
        this.speed = speed;
    }

    public void setSTR(int STR) {
        this.STR = STR;
    }

    public void setDEX(int DEX) {
        this.DEX = DEX;
    }

    public void setCON(int CON) {
        this.CON = CON;
    }

    public void setINT(int INT) {
        this.INT = INT;
    }

    public void setWIS(int WIS) {
        this.WIS= WIS;
    }

    public void setCHA(int CHA) {
        this.CHA = CHA;
    }

    public void setHON(int HON) {
        this.HON = HON;
    }

    public void setSAN(int SAN) {
        this.SAN = SAN;
    }

    protected void setSkillList(int[] skillList) {
        this.skillList = skillList;
    }

    public void setSenses(String senses) {
        this.senses = senses;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public void setSkillBonuses(Skills skill, int bonus) {
        skillBonuses[skill.ordinal()] = bonus;
    }

    public int getModifier(int score) {
        return modifier(score);
    }

    public String getName() {
        return getName(this.name);
    }

    public String getSize() {
        return getSize(this.size);
    }

    public String getType() {
        return getType(this.type);
    }

    public int getAc() {
        return getAc(this.ac);
    }

    public int getProficiencyBonus() {
        return getProficiencyBonus(this.proficiencyBonus);
    }

    public int getHpAvg() {
        return getHpAvg(this.hpAvg);
    }

    public String getSpeed() {
        return getSpeed(this.speed);
    }

    public int getSTR() {
        return getSTR(this.STR);
    }

    public int getDEX() {
        return getDEX(this.DEX);
    }

    public int getCON() {
        return getCON(this.CON);
    }

    protected int getINT() {
        return getINT(this.INT);
    }

    public int getWIS() {
        return getWIS(this.WIS);
    }

    public int getCHA() {
        return getCHA(this.CHA);
    }

    public int getHON() {
        return getHON(this.HON);
    }

    public int getSAN() {
        return getSAN(this.SAN);
    }

    public int[] getSkillList() {
        return getSkillList(this.skillList);
    }

    public String getSenses() {
        return getSenses(this.senses);
    }

    public String getLanguages() {
        return getLanguages(this.languages);
    }

    public double getCR() {
        return CR;
    }

    protected int getSkillBonuses(int index) {
        return getSkillBonus(this.skillBonuses, index);
    }

    public String getPassive(int index){
        return getPassive(this.passives, index);
    }

    public int[] getMultiattack() {
        return multiattack;
    }

    public void setMultiattack(int[] multiattack) {
        this.multiattack = multiattack;
    }

    public String multiattack() {
        StringBuilder multi = new StringBuilder();
        if(this.multiattack == null) {
            return null;
        }
        else {
            for (int value : this.multiattack) {
                multi.append(attacks.get(value));
            }
        }
        return multi.toString();
    }

    public String attack(Attack atk){
        String attack = atk.getName();
        if (atk.getRange()!=0) {
            attack+= " (range: " + atk.getRange() + " ft):";
        }
        attack+=" ";
        int roll = d(false,1,20, atk.getAttackBonus());
        if (!(atk instanceof DCAttack)){
            boolean isCritical = false;

            if (roll - atk.getAttackBonus() == 20) {
                attack+= "Critical Hit!!: ";
                isCritical=true;
            }
            if (roll - atk.getAttackBonus()!=1) {
                attack+= roll + " to hit against AC: ";
                if(atk.getDamage(true) != 0) {
                    attack+= atk.getDamage(isCritical) + " " + atk.getDamageType() + " damage\n";
                }
            }
            else{
                attack+="Critical Miss\n";
            }

            Object attackEffect = atk.getEffect();

            if (attackEffect instanceof DCAttack) {
                DCAttack DCattack = (DCAttack) attackEffect;
                DCattack.setName("");
                DCattack.setRange(0);
                attack+=attack(DCattack);
                attack+="\n";
            }
            else if (attackEffect instanceof DCAttack[]) {
                DCAttack[] effects = (DCAttack[]) atk.getEffect();
                for(DCAttack effect : effects) {
                    effect.setName("");
                    effect.setRange(0);
                    attack+=attack(effect);
                    attack+="\n";
                }
            }
            else if (attackEffect instanceof String) {
                String effect = (String) atk.getEffect();
                attack+=effect;
                attack+="\n";
            }

        }
        //DC attacks
        else{
            if(((DCAttack) atk).getShape()!=null) {
                attack+="Each creature in a " + ((DCAttack) atk).getSize() + " ft " + ((DCAttack) atk).getShape();
                if(atk.getRange()==0) {
                    attack+=" originating from the monster";
                }
                else {
                    attack+=" within range";
                }
            }
            else{
                attack+="Target";
            }
            if (atk.getAttackBonus()!=0) {
                attack+=" must succeed on a " + ((DCAttack) atk).getSave() + " save against DC " + atk.getAttackBonus();
            }
            if(atk.getDamage(true) != 0) {
                attack+= " or take " + atk.getDamage(false) + " " + atk.getDamageType() + " damage";
            }
            if (!((DCAttack) atk).getDCEffect().equals("") && atk.getDamage(false) != 0) {
                attack+= "\nand " + ((DCAttack) atk).getDCEffect();
            }
            else if (!((DCAttack) atk).getDCEffect().equals("")) {
                attack+= "\nor " + ((DCAttack) atk).getDCEffect()+"\n";
            }
            if(((DCAttack) atk).getHalfDamage()) {
                attack+="\nTake " + atk.getDamage(false)/2 + " " + atk.getDamageType()
                 + " damage on a save";
            }
        }
        return attack;
    }

    public void addSkillBonus(Skills skill, int bonus) {
        this.skillBonuses[skill.ordinal()]+=bonus;
    }

    private int savingThrow(int index, int score){
        int saveBonus;
        if (savingThrows.get(index)) {
            saveBonus = modifier(score) + this.proficiencyBonus;
        }
        else {
            saveBonus = modifier(score);
        }
        return saveBonus;
    }

    private String savingThrowString(int index, int score) {
        String s = "";
        if(savingThrow(index, score)<0) {
            s = Integer.toString(savingThrow(index, score));
        }
        else {
            s = "+"+savingThrow(index, score);
        }

        return s;
    }

    private int[] calculateSkillBonuses(){
        int[] skillBonuses = new int[18];
        for (int n = 0; n<skillBonuses.length; n++) {
                if (n == 0) {
                    int bonus0 = 0;
                    boolean isProficient0 = false;
                    for (int k:skillList) {
                        if (k == n) {
                            isProficient0 = true;
                            break;
                        }
                    }
                    if (isProficient0) {
                        bonus0 += getProficiencyBonus() + modifier(this.DEX);
                    }
                    else {
                        bonus0 += modifier(this.DEX);
                    }
                    skillBonuses[0] = bonus0;
                }
                else if (n==1) {
                    int bonus1 = 0;
                    boolean isProficient1 = false;
                    for (int k:skillList) {
                        if (k == n) {
                            isProficient1 = true;
                            break;
                        }
                    }
                    if (isProficient1) {
                        bonus1 += getProficiencyBonus() + modifier(this.WIS);
                    }
                    else {
                        bonus1 += modifier(this.WIS);
                    }
                    skillBonuses[1] = bonus1;
                }
                else if (n==2) {
                    int bonus2 = 0;
                    boolean isProficient2 = false;
                    for (int k:skillList) {
                        if (k == n) {
                            isProficient2 = true;
                            break;
                        }
                    }
                    if (isProficient2) {
                        bonus2 += getProficiencyBonus() + modifier(this.INT);
                    }
                    else {
                        bonus2 += modifier(this.INT);
                    }
                    skillBonuses[2] = bonus2;
                }
                else if (n==3) {
                    int bonus3 = 0;
                    boolean isProficient3 = false;
                    for (int k:skillList) {
                        if (k == n) {
                            isProficient3 = true;
                            break;
                        }
                    }
                    if (isProficient3) {
                        bonus3 += getProficiencyBonus() + modifier(this.STR);
                    }
                    else {
                        bonus3 += modifier(this.STR);
                    }
                    skillBonuses[3] = bonus3;
                }
                else if (n==4) {
                    int bonus4 = 0;
                    boolean isProficient4 = false;
                    for (int k:skillList) {
                        if (k == n) {
                            isProficient4 = true;
                            break;
                        }
                    }
                    if (isProficient4) {
                        bonus4 += getProficiencyBonus() + modifier(this.CHA);
                    }
                    else {
                        bonus4 += modifier(this.CHA);
                    }
                    skillBonuses[4] = bonus4;
                }
                else if (n==5) {
                    int bonus5 = 0;
                    boolean isProficient5 = false;
                    for (int k:skillList) {
                        if (k == n) {
                            isProficient5 = true;
                            break;
                        }
                    }
                    if (isProficient5) {
                        bonus5 += getProficiencyBonus() + modifier(this.INT);
                    }
                    else {
                        bonus5 += 0 ;
                    }
                    skillBonuses[5] = bonus5;
                }
                else if (n==6) {
                    int bonus6 = 0;
                    boolean isProficient6 = false;
                    for (int k:skillList) {
                        if (k == n) {
                            isProficient6 = true;
                            break;
                        }
                    }
                    if (isProficient6) {
                        bonus6 += getProficiencyBonus() + modifier(this.WIS);
                    }
                    else {
                        bonus6 += modifier(this.WIS);
                    }
                    skillBonuses[6] = bonus6;
                }
                else if (n==7) {
                    int bonus7 = 0;
                    boolean isProficient7 = false;
                    for (int k:skillList) {
                        if (k == n) {
                            isProficient7 = true;
                            break;
                        }
                    }
                    if (isProficient7) {
                        bonus7 += getProficiencyBonus() + modifier(this.CHA);
                    }
                    else {
                        bonus7 += modifier(this.CHA);
                    }
                    skillBonuses[7] = bonus7;
                }
                else if (n==8) {
                    int bonus8 = 0;
                    boolean isProficient8 = false;
                    for (int k:skillList) {
                        if (k == n) {
                            isProficient8 = true;
                            break;
                        }
                    }
                    if (isProficient8) {
                        bonus8 += getProficiencyBonus() + modifier(this.INT);
                    }
                    else {
                        bonus8 += modifier(this.INT);
                    }
                    skillBonuses[8] = bonus8;
                }
                else if (n==9) {
                    int bonus9 = 0;
                    boolean isProficient9 = false;
                    for (int k:skillList) {
                        if (k == n) {
                            isProficient9 = true;
                            break;
                        }
                    }
                    if (isProficient9) {
                        bonus9 += getProficiencyBonus() + modifier(this.WIS);
                    }
                    else {
                        bonus9 += modifier(this.WIS);
                    }
                    skillBonuses[9] = bonus9;
                }
                else if (n==10) {
                    int bonus10 = 0;
                    boolean isProficient10 = false;
                    for (int k:skillList) {
                        if (k == n) {
                            isProficient10 = true;
                            break;
                        }
                    }
                    if (isProficient10) {
                        bonus10 += getProficiencyBonus() + modifier(this.INT);
                    }
                    else {
                        bonus10 += modifier(this.INT);
                    }
                    skillBonuses[10] = bonus10;
                }
                else if (n==11) {
                    int bonus11 = 0;
                    boolean isProficient11 = false;
                    for (int k:skillList) {
                        if (k == n) {
                            isProficient11 = true;
                            break;
                        }
                    }
                    if (isProficient11) {
                        bonus11 += getProficiencyBonus() + modifier(this.WIS);
                    }
                    else {
                        bonus11 += modifier(this.WIS);
                    }
                    skillBonuses[11] = bonus11;
                }
                else if (n==12) {
                    int bonus12 = 0;
                    boolean isProficient12 = false;
                    for (int k:skillList) {
                        if (k == n) {
                            isProficient12 = true;
                            break;
                        }
                    }
                    if (isProficient12) {
                        bonus12 += getProficiencyBonus() + modifier(this.CHA);
                    }
                    else {
                        bonus12 += modifier(this.CHA);
                    }
                    skillBonuses[12] = bonus12;
                }
                else if (n==13) {
                    int bonus13 = 0;
                    boolean isProficient13 = false;
                    for (int k:skillList) {
                        if (k == n) {
                            isProficient13 = true;
                            break;
                        }
                    }
                    if (isProficient13) {
                        bonus13 += getProficiencyBonus() + modifier(this.CHA);
                    }
                    else {
                        bonus13 += modifier(this.CHA);
                    }
                    skillBonuses[13] = bonus13;
                }
                else if (n==14) {
                    int bonus14 = 0;
                    boolean isProficient14 = false;
                    for (int k:skillList) {
                        if (k == n) {
                            isProficient14 = true;
                            break;
                        }
                    }
                    if (isProficient14) {
                        bonus14 += getProficiencyBonus() + modifier(this.INT);
                    }
                    else {
                        bonus14 += modifier(this.INT);
                    }
                    skillBonuses[14] = bonus14;
                }
                else if (n==15) {
                    int bonus15 = 0;
                    boolean isProficient15 = false;
                    for (int k:skillList) {
                        if (k == n) {
                            isProficient15 = true;
                            break;
                        }
                    }
                    if (isProficient15) {
                        bonus15 += getProficiencyBonus() + modifier(this.DEX);
                    }
                    else {
                        bonus15 += modifier(this.DEX);
                    }
                    skillBonuses[15] = bonus15;
                }
                else if (n==16) {
                    int bonus16 = 0;
                    boolean isProficient16 = false;
                    for (int k:skillList) {
                        if (k == n) {
                            isProficient16 = true;
                            break;
                        }
                    }
                    if (isProficient16) {
                        bonus16 += getProficiencyBonus() + modifier(this.DEX);
                    }
                    else {
                        bonus16 += modifier(this.DEX);
                    }
                    skillBonuses[16] = bonus16;
                }
                else {
                    int bonus17 = 0;
                    boolean isProficient17 = false;
                    for (int k:skillList) {
                        if (k == n) {
                            isProficient17 = true;
                            break;
                        }
                    }
                    if (isProficient17) {
                        bonus17 += getProficiencyBonus() + modifier(this.WIS);
                    }
                    else {
                        bonus17 += modifier(this.WIS);
                    }
                    skillBonuses[17] = bonus17;
                }
            }
        return skillBonuses;
    }

    private int modifier(int score) {
        int mod;
        if (score>=10) {
            mod = (score-10)/2;
        }
        else{
            mod = (score-10)/2-1;
        }
        return mod;
    }

    private String modifierString(int score) {
        String s = "";
        if(modifier(score)<0) {
            s = Integer.toString(modifier(score));
        }
        else {
            s = "+"+modifier(score);
        }
        return s;
    }

    private int getSkillBonus(int[] skills, int index){
        return skills[index];
    }

    private String getName(String name) {
        return name;
    }

    private String getSize(String size) {
        return size;
    }

    private String getType(String type) {
        return type;
    }

    private int getProficiencyBonus(int proficiencyBonus) {
        return proficiencyBonus;
    }

    private int getAc(int ac) {
        return ac;
    }

    private int getHpAvg(int hpAvg) {
        return hpAvg;
    }

    private String getSpeed(String speed) {
        return speed;
    }

    private int getSTR(int STR) {
        return STR;
    }

    private int getDEX(int DEX){
        return DEX;
    }

    private int getCON(int CON) {
        return CON;
    }

    private int getINT(int INT) {
        return INT;
    }

    private int getWIS(int WIS) {
        return WIS;
    }

    private int getCHA(int CHA) {
        return CHA;
    }

    private int getHON(int HON) {
        return HON;
    }

    private int getSAN(int SAN) {
        return SAN;
    }

    private int[] getSkillList(int[] skillList) {
        return skillList;
    }

    private String getSenses(String senses) {
        return senses;
    }

    private String getLanguages(String languages) {
        return languages;
    }

    private String getPassive(ArrayList<String> passives, int index){
        return passives.get(index);
    }


    private static boolean isMethodImplemented(Object obj, String name) {
        try
        {
            Class<?> clazz = obj.getClass();

            return clazz.getMethod(name).getDeclaringClass().equals(clazz);
        }
        catch (SecurityException | NoSuchMethodException e)
        {
            return false;
        }
    }

    @Override
    public int compareTo(Monster m) {
        return this.name.compareTo(m.name);
    }

    public boolean equals(Monster m) {
        return this.name.equals(m.name);
    }
}