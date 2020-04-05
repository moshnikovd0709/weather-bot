package weatherBot;

public class WeatherModel {
    private String location;
    private Double temperature;
    private String main;

    public String getName() {
        return location;
    }

    public void setName(String location) {
        this.location = location;
    }

    public Double getTemp() {
        return temperature;
    }

    public void setTemp(Double temperature) {
        this.temperature = temperature;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}
