package dtomodels.eventDTO;



public class EventSearchDTO
{
    private int Radius;
    private double CenterLatitude;
    private double CenterLongitude;

    public int getRadius() {
        return Radius;
    }

    public void setRadius(int radius) {
        this.Radius = radius;
    }

    public double getCenterLatitude() {
        return CenterLatitude;
    }

    public void setCenterLatitude(double centerLatitude) {
        this.CenterLatitude = centerLatitude;
    }

    public double getCenterLongitude() {
        return CenterLongitude;
    }

    public void setCenterLongitude(double centerLongitude) {
        this.CenterLongitude = centerLongitude;
    }
}
