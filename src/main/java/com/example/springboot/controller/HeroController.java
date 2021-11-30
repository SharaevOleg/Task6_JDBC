package com.example.springboot.controller;

import com.example.springboot.model.Hero;
import com.example.springboot.service.DbHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

import static com.example.springboot.service.DbHandler.getInstance;

@RestController
public class HeroController {

    private final DbHandler dbHandler = getInstance();

    public HeroController() throws SQLException {
    }

    // метод GET, который по урл '/api/hero/' взвращает json
    @GetMapping("/api/hero")
    public List<Hero> heroes() throws SQLException {
        return dbHandler.getAllHeroes();
    }

    // метод POST, который может принять модель Hero и вернуть ее уже с id 100
    @PostMapping("/api/hero")
    public ResponseEntity<String> hero(@Valid @RequestBody Hero hero) {
        hero.setId(100);
        System.out.println(hero);
        dbHandler.addHero(hero);
        return ResponseEntity.ok("valid");
    }

    // метод GET, возвращает Hero по id
    @GetMapping("/api/hero/{id}")
    public Hero hero(@PathVariable int id) {
        return dbHandler.getHero(id);
    }

    // метод удаляет Hero по id
    @DeleteMapping("/delete/{id}")
    public void deleteHeroById(@PathVariable int id) {
        dbHandler.deleteHero(id);
    }

}
