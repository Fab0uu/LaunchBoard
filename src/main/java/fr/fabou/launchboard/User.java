package fr.fabou.launchboard;

public class User {
    private String name;
    private String setting;

    public User(String name, String setting) {
        this.name = name;
        this.setting = setting;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    @Override
    public String toString() {
        return "Preset{name='" + name + "', setting='" + setting + "'}";
    }
}
