public class Attack implements Dice, Autowrap {
    private boolean critical = false;
    private int damageNumber;
    private int damageSides;
    private int damageBonuses;
    private String name;
    private int attackBonus;
    private int range;
    private String damageType;
    private Object effect;

    Attack(){}

    //Add DCAttack, DCAttack[], or String effect to reduce pointless recurrences of one attack, else, call this parameter null
    public Attack(String name, int attackBonus, int range, int number, int sides, int bonuses, String damageType, Object effect) {
        this.name = name;
        this.attackBonus = attackBonus;
        this.range = range;
        this.damageNumber = number;
        this.damageSides = sides;
        this.damageBonuses = bonuses;
        this.damageType = damageType;
        this.effect = effect;
}

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(": ").append(attackBonus).append(" to hit; Range: ").append(range).append(": ");
        if(damageNumber!=0) {
            sb.append(damageNumber).append("d").append(damageSides);
            if(damageBonuses!=0) {
                sb.append(" + ").append(damageBonuses);
            }
        }
        else if (damageBonuses!=0){
            sb.append(damageBonuses);
        }
        if(damageType!=null) {
            sb.append(" ").append(damageType).append(" damage");
        }
        if(effect!=null) {
            if(effect instanceof DCAttack) {
                sb.append("\n").append(effect.toString());
            }
            else {
                sb.append("\n").append(autoWrap(effect.toString()));
            }
        }


        return sb.toString();
    }

    public void setName(String name) {
        this.name=name;
    }

    public void setAttackBonus(int bonus) { this.attackBonus=bonus; }

    public void setRange(int range) { this.range=range; }

    public void setDamageNumber(int number){
        this.damageNumber=number;
    }

    public void setDamageSides(int sides) { this.damageSides=sides; }

    public void setDamageBonuses(int bonuses) { this.damageBonuses=bonuses; }

    public void setDamageType(String type) { this.damageType=type; }

    public void setEffect(Object effect) { this.effect = effect; }

    public void setCritical(boolean critical) { this.critical = critical; }

    public String getName() { return getName(this.name); }

    public int getAttackBonus() { return getAttackBonus(this.attackBonus); }

    public int getRange() { return getRange(this.range); }

    public int getDamage(boolean isCritical) { return d(isCritical, damageNumber, damageSides, damageBonuses); }

    public int getDamageNumber() { return this.getDamageNumber(this.damageNumber); }

    public int getDamageSides() { return this.getDamageSides(this.damageSides); }

    public int getDamageBonuses() { return this.getDamageBonuses(this.damageBonuses); }

    public String getDamageType() { return this.getDamageType(this.damageType); }

    public Object getEffect() { return getEffect(this.effect); }

    public boolean getCritical() { return getCritical(this.critical); }

    private String getName(String name) { return name; }

    private int getAttackBonus(int bonus) { return bonus; }

    private int getRange(int range) { return range; }

    private int getDamageNumber(int damageNumber) { return damageNumber; }

    private int getDamageSides(int damageSides) { return damageSides; }

    private int getDamageBonuses(int damageBonuses) { return damageBonuses; }

    private String getDamageType(String type){ return type; }

    private Object getEffect(Object effect) { return effect; }

    private boolean getCritical(boolean critical) { return critical; }
}
