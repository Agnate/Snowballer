package ca.agnate.Snowballer;

public enum Node {
    DAMAGE ("damage"),
    KNOCKBACK ("knockback"),
    EXTINGUISH("extinguish");

    private String perm;
    private String prefix = "snowballer.";

    private Node (String perm) {
	this.perm = perm;
    }

    public String toString () {
	return( prefix + this.perm );
    }
}
