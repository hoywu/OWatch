package top.konoka.owatch;

public class MyData {
    private final String rawData;
    private String lastUpdated;
    private String temperature;
    private String humidity;

    public MyData(String rawData) {
        this.rawData = rawData;
        setLastUpdated(Utils.getMiddleString(rawData, "[", "]"));
        setTemperature(Utils.getMiddleString(rawData, "t:", ";"));
        setHumidity(Utils.getMiddleString(rawData, "h:", ";"));
    }

    private void setLastUpdated(String lastUpdated) {
        if (lastUpdated == null) {
            this.lastUpdated = "UnknownLastUpdateTime";
        } else {
            this.lastUpdated = lastUpdated.substring(0, lastUpdated.length() - 6);
        }
    }

    private void setTemperature(String temperature) {
        if (temperature == null) {
            this.temperature = "00.00";
        } else {
            this.temperature = temperature;
        }
    }

    private void setHumidity(String humidity) {
        if (humidity == null) {
            this.humidity = "00.00";
        } else {
            this.humidity = humidity;
        }
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }
}
