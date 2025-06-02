package com.fgc.framedata_api.service;

import com.fgc.framedata_api.model.Character;
import com.fgc.framedata_api.repository.CharacterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;

    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public Character addCharacter(Character character) {
        return characterRepository.save(character);
    }

    public List<Character> getAllCharacters() {
        return characterRepository.findAll();
    }

    public Optional<Character> getCharacterById(Long id) {
        return characterRepository.findById(id);
    }

    public Optional<Character> updateCharacter(Long id, Character updatedCharacter) {
        return characterRepository.findById(id).map(existingChar -> {
            if (updatedCharacter.getGame() != null || !updatedCharacter.getGame().equals("")) {
                existingChar.setGame(updatedCharacter.getGame());
            };
            if (updatedCharacter.getName() != null || !updatedCharacter.getName().equals("")) {
                existingChar.setName(updatedCharacter.getName());
            };
           return characterRepository.save(existingChar);
        });
    }

    public void deleteCharacter(Long id) {
        characterRepository.deleteById(id);
    }
}