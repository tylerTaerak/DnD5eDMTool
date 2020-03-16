public class DCAttack extends Attack{
    private int size;
    private String shape;
    private String DCEffect;
    private String save;
    private boolean halfDamage;


    //Add shape of spell as two parameters (size and shape) -- put 0 for size, null for shape for a single target effect
    public DCAttack(String name, int DC, int range, int size, String shape, int number, int sides, int bonuses,
             String damageType, String effect, String save, boolean halfdamage){
        super(name, DC, range, number, sides, bonuses, damageType, null);
        this.size = size;
        this.shape = shape;
        this.DCEffect = effect;
        this.save = save;
        this.halfDamage = halfdamage;
    }


    public String toString(){
        StringBuilder sb = new StringBuilder();
        if(getName()!=null){
            sb.append(getName()).append(": ");
        }

        if(!(size>0) && shape==null){
            sb.append("A creature within ").append(getRange()).append(" ft ");
        }
        else if (shape!=null) {
            char[] charShape = shape.toCharArray();
            charShape[0] = Character.toUpperCase(charShape[0]);
            shape = String.valueOf(charShape);
            sb.append(shape).append(" within ").append(getRange()).append(" ft ");
        }
        else {
            sb.append("Each creature in a ").append(size).append(" ft ").append(shape);
            if(getRange()>0){
                sb.append(" within ").append(getRange()).append(" ft ");
            }
            else {
                sb.append("centered on the monster ");
            }
        }

        if(getAttackBonus()>0){
            sb.append("must succeed on a DC ").append(getAttackBonus()).append(" ").append(save).append(" saving throw, or ");
        }

        if(getDamageNumber() != 0 && getDamageBonuses() != 0) {
            sb.append("take ").append(getDamageNumber()).append("d").append(getDamageSides()).
                    append(" + ").append(getDamageBonuses()).append(" ").append(getDamageType()).append(" damage");
            if(DCEffect!=null) {
                sb.append(", and ");
            }
        }

        if(DCEffect!=null) {
            sb.append("be ").append(DCEffect).append(". ");
        }

        if(halfDamage){
            sb.append("Targets take half damage on a successful save");
        }


        return autoWrap(sb.toString());
    }

    public void setDCEffect(String effect) {
        this.DCEffect=effect;
    }

    public void setSave(String save) {
        this.save=save;
    }

    public void setHalfDamage(boolean halfDamage) {
        this.halfDamage=halfDamage;
    }

    public int getSize() {
        return getSize(this.size);
    }

    public String getShape() {
        return getShape(this.shape);
    }

    public boolean getHalfDamage() {
        return getHalfDamage(this.halfDamage);
    }

    public String getSave() {
        return getSave(this.save);
    }

    public String getDCEffect() {
        return getDCEffect(this.DCEffect);
    }

    private String getName(String name) {
        return name;
    }

    private int getDifficultyClass(int difficultyClass) {
        return difficultyClass;
    }

    private int getRange(int range) {
        return range;
    }

    private int getSize(int size) {
        return size;
    }

    private String getShape(String shape) {
        return shape;
    }

    private int getDamageNumber(int damageNumber) {
        return damageNumber;
    }

    private int getDamageSides(int damageSides) {
        return damageSides;
    }

    private int getDamageBonuses(int damageBonuses) {
        return damageBonuses;
    }

    private String getDamageType(String damageType) {
        return damageType;
    }

    private boolean getHalfDamage(boolean halfDamage) {
        return halfDamage;
    }

    private String getSave(String save) {
        return save;
    }

    private String getDCEffect(String effect) {
        return effect;
    }
}