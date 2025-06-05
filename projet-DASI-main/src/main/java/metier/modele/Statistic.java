package metier.modele;

public class Statistic {
    private int nbSupports;
    private double averageDuration;
    private double averageRating;

    public Statistic() {
    }

    public Statistic(int nbSupports, double averageDuration, double averageRating) {
        this.nbSupports = nbSupports;
        this.averageDuration = averageDuration;
        this.averageRating = averageRating;
    }

    public int getNbSupports() {
        return nbSupports;
    }

    public void setNbSupports(int nbSupports) {
        this.nbSupports = nbSupports;
    }

    public double getAverageDuration() {
        return averageDuration;
    }

    public void setAverageDuration(double averageDuration) {
        this.averageDuration = averageDuration;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
}
