package com.gameproject.dto;

public class CreateCharacterRequest {

    private Long playerId;

    private String food;
    private String hobby;
    private String style;
    private String education;
    private String car;
    private String drink;

    public Long getPlayerId() { return playerId; }
    public void setPlayerId(Long playerId) { this.playerId = playerId; }

    public String getFood() { return food; }
    public void setFood(String food) { this.food = food; }

    public String getHobby() { return hobby; }
    public void setHobby(String hobby) { this.hobby = hobby; }

    public String getStyle() { return style; }
    public void setStyle(String style) { this.style = style; }

    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }

    public String getCar() { return car; }
    public void setCar(String car) { this.car = car; }

    public String getDrink() { return drink; }
    public void setDrink(String drink) { this.drink = drink; }
}
