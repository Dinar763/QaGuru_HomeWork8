public enum Lang {
    EN("English"),
    RU("Русский");

    private final String description;

    Lang(String description) {
        this.description = description;
    }
    public String getDescription(){
        return description;
    }
}
