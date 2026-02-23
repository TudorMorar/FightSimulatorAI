package com.gameproject.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "characters")
public class GameCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int power;
    private int health;
    private int luck;

    private String imageUrl;

    @Column(nullable = false)
    private Integer level = 1;   // folosim Integer pentru control

    @Column(nullable = false)
    private int xp = 0;

    @OneToOne
    @JoinColumn(name = "player_id")
    @JsonBackReference
    private Player player;

    // ===== constructors =====
    public GameCharacter() {
        this.level = 1;
        this.xp = 0;
    }

    public GameCharacter(String name, int power, int health, int luck) {
        this.name = name;
        this.power = power;
        this.health = health;
        this.luck = luck;
        this.level = 1;
        this.xp = 0;
    }

    // ===== safety defaults before INSERT =====
    @PrePersist
    public void initDefaults() {
        if (level == null || level == 0) level = 1;
        if (xp < 0) xp = 0;
    }

    // ===== getters & setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getPower() { return power; }
    public void setPower(int power) { this.power = power; }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }

    public int getLuck() { return luck; }
    public void setLuck(int luck) { this.luck = luck; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public int getXp() { return xp; }
    public void setXp(int xp) { this.xp = xp; }
}
