package com.example.demo.dtos;

import lombok.Data;

@Data
public class PlayerDTO {
    public Integer id;
    public String name;
    public int age;
    public String icon;
    public String nationality;
    public int winners;
    public int games;
    public String club;

    public PlayerDTO() {
    }

    public PlayerDTO(
            Integer id, String name, int age, String icon, String nationality, int winners, int games, String club) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.icon = icon;
        this.nationality = nationality;
        this.winners = winners;
        this.games = games;
        this.club = club;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getWinners() {
        return winners;
    }

    public void setWinners(int winners) {
        this.winners = winners;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }
}
