package com.gameproject.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;


    private int stamina = 100;
    private int maxStamina = 100;

    private int fights;
    private int wins;
    private int loses;

    @Column(nullable = false)
    private Integer level = 1;   // folosim Integer

    @Column(nullable = false)
    private int xp = 0;

    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private GameCharacter character;

    // ===== constructors =====
    public Player() {
        this.level = 1;
        this.xp = 0;
    }
    // === PRE-PERSIST HOOK ===
    @PrePersist
    public void initDefaults() {
        if (level == null || level < 1) level = 1;
        if (xp < 0) xp = 0;
    }

    public Player(String name) {
        this.name = name;
        this.stamina = 100;
        this.maxStamina = 100;
        this.level = 1;
        this.xp = 0;
    }

    // ===== getters & setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getStamina() { return stamina; }
    public void setStamina(int stamina) { this.stamina = stamina; }

    public int getMaxStamina() { return maxStamina; }
    public void setMaxStamina(int maxStamina) { this.maxStamina = maxStamina; }

    public int getWins() { return wins; }
    public void setWins(int wins) { this.wins = wins; }

    public int getFights() { return fights; }
    public void setFights(int fights) { this.fights = fights; }

    public int getLoses() { return loses; }
    public void setLoses(int loses) { this.loses = loses; }

    public GameCharacter getCharacter() { return character; }
    public void setCharacter(GameCharacter character) { this.character = character; }

    public int getXp() { return xp; }
    public void setXp(int xp) { this.xp = xp; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
}
