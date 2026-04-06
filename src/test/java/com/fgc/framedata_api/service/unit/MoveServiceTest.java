package com.fgc.framedata_api.service.unit;

import com.fgc.framedata_api.dto.MoveDTO;
import com.fgc.framedata_api.model.Character;
import com.fgc.framedata_api.model.Game;
import com.fgc.framedata_api.model.Move;
import com.fgc.framedata_api.model.MoveType;
import com.fgc.framedata_api.repository.CharacterRepository;
import com.fgc.framedata_api.repository.MoveRepository;
import com.fgc.framedata_api.request.CreateMoveRequest;
import com.fgc.framedata_api.request.UpdateMoveRequest;
import com.fgc.framedata_api.service.MoveService;
import com.fgc.framedata_api.utils.CustomExceptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MoveServiceTest {

    @Mock
    private MoveRepository moveRepository;

    @Mock
    private CharacterRepository characterRepository;

    @InjectMocks
    private MoveService moveService;

    private Character buildCharacter() {
        Game game = new Game();
        game.setId(1L);
        game.setName("Street Fighter 6");

        Character character = new Character();
        character.setId(1L);
        character.setName("Ryu");
        character.setGame(game);
        return character;
    }

    private Move buildMove(Character character) {
        Move move = new Move();
        move.setId(1L);
        move.setCharacter(character);
        move.setName("Hadoken");
        move.setInput("236P");
        move.setMoveType(MoveType.SPECIAL);
        move.setStartupFrames(13);
        move.setHasArmor(false);
        return move;
    }

    @Test
    void addMove_shouldThrow_whenCharacterNotFound() {
        when(characterRepository.findById(1L)).thenReturn(Optional.empty());

        CreateMoveRequest request = new CreateMoveRequest();
        request.setName("Hadoken");
        request.setInput("236P");
        request.setMoveType(MoveType.SPECIAL);

        try {
            moveService.addMove(1L, request);
        } catch (CustomExceptions.CharacterNotFoundException e) {
            assert e.getMessage().equals("Character not found with id: 1");
        }
    }

    @Test
    void addMove_shouldReturnMoveDTO_whenRequestIsValid() {
        Character character = buildCharacter();
        Move move = buildMove(character);

        when(characterRepository.findById(1L)).thenReturn(Optional.of(character));
        when(moveRepository.save(any(Move.class))).thenReturn(move);

        CreateMoveRequest request = new CreateMoveRequest();
        request.setName("Hadoken");
        request.setInput("236P");
        request.setMoveType(MoveType.SPECIAL);

        MoveDTO result = moveService.addMove(1L, request);
        assert result != null;
        assert result.getName().equals("Hadoken");
        assert result.getMoveType() == MoveType.SPECIAL;
    }

    @Test
    void addMove_shouldThrow_whenParentMoveNotFound() {
        Character character = buildCharacter();

        when(characterRepository.findById(1L)).thenReturn(Optional.of(character));
        when(moveRepository.findById(99L)).thenReturn(Optional.empty());

        CreateMoveRequest request = new CreateMoveRequest();
        request.setName("Follow-up");
        request.setInput("P");
        request.setMoveType(MoveType.TARGET_COMBO);
        request.setParentMoveId(99L);

        try {
            moveService.addMove(1L, request);
        } catch (CustomExceptions.MoveNotFoundException e) {
            assert e.getMessage().equals("Parent move not found with id: 99");
        }
    }

    @Test
    void getMoveById_shouldThrow_whenMoveNotFound() {
        when(moveRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            moveService.getMoveById(1L);
        } catch (CustomExceptions.MoveNotFoundException e) {
            assert e.getMessage().equals("Move not found with id: 1");
        }
    }

    @Test
    void getMoveById_shouldReturnMoveDTO_whenFound() {
        Character character = buildCharacter();
        Move move = buildMove(character);

        when(moveRepository.findById(1L)).thenReturn(Optional.of(move));

        MoveDTO result = moveService.getMoveById(1L);
        assert result != null;
        assert result.getId().equals(1L);
        assert result.getName().equals("Hadoken");
    }

    @Test
    void updateMove_shouldThrow_whenMoveNotFound() {
        when(moveRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            moveService.updateMove(1L, new UpdateMoveRequest());
        } catch (CustomExceptions.MoveNotFoundException e) {
            assert e.getMessage().equals("Move not found with id: 1");
        }
    }

    @Test
    void updateMove_shouldReturnUpdatedMoveDTO_whenRequestIsValid() {
        Character character = buildCharacter();
        Move move = buildMove(character);

        when(moveRepository.findById(1L)).thenReturn(Optional.of(move));
        when(moveRepository.save(any(Move.class))).thenReturn(move);

        UpdateMoveRequest request = new UpdateMoveRequest();
        request.setStartupFrames(14);

        MoveDTO result = moveService.updateMove(1L, request);
        assert result != null;
    }

    @Test
    void getAllMovesByCharacter_shouldThrow_whenCharacterNotFound() {
        when(characterRepository.existsById(1L)).thenReturn(false);

        try {
            moveService.getAllMovesByCharacter(1L);
        } catch (CustomExceptions.CharacterNotFoundException e) {
            assert e.getMessage().equals("Character not found with id: 1");
        }
    }

    @Test
    void getAllMovesByCharacter_shouldReturnList_whenCharacterExists() {
        Character character = buildCharacter();
        Move move = buildMove(character);

        when(characterRepository.existsById(1L)).thenReturn(true);
        when(moveRepository.findAllByCharacterId(1L)).thenReturn(List.of(move));

        List<MoveDTO> result = moveService.getAllMovesByCharacter(1L);
        assert result.size() == 1;
        assert result.get(0).getName().equals("Hadoken");
    }

    @Test
    void deleteMove_shouldThrow_whenMoveNotFound() {
        when(moveRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            moveService.deleteMove(1L);
        } catch (CustomExceptions.MoveNotFoundException e) {
            assert e.getMessage().equals("Move not found with id: 1");
        }
    }
}
