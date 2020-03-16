public interface Dice {
    default int d( boolean critical, int number, int sides, int bonuses) {
        if (critical) {
            int damage = number * sides + bonuses;
            for(int n = 0; n<number; n++) {
                damage += (int) (Math.random() * sides + 1);
            }
            return damage;
        }
        else{
            int rollSum=0;
            for(int n=0; n<number; n++) {
                int roll = (int) (Math.random() * sides + 1);
                rollSum+=roll;
            }
            return rollSum+bonuses;
        }
    }
}
