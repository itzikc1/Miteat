package miteat.miteat.Model.Entities;

/**
 * Created by Itzik on 07/07/2016.
 */
public class Gps {
    private String longitude;
    private String latitude;
    private Long time;
    private int id;

    public Gps(int id, String longitude, String latitude, long time) {
        this.id = id;
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;

    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
