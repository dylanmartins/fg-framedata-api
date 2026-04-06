package com.fgc.framedata_api.request;

import com.fgc.framedata_api.model.MoveType;

public class CreateMoveRequest {

    private Long parentMoveId;
    private String name;
    private String input;
    private MoveType moveType;
    private Integer superArtLevel;
    private Integer damage;
    private Integer chipDamage;
    private Integer startupFrames;
    private Integer activeFrames;
    private Integer recoveryFrames;
    private Integer onHitAdvantage;
    private Integer onBlockAdvantage;
    private Integer onCounterHitAdvantage;
    private Integer onPunishCounterAdvantage;
    private Integer driveRushOnHit;
    private Integer driveRushOnBlock;
    private Boolean hasArmor = false;
    private Integer armorHits;
    private String cancelOptions;
    private String notes;

    public Long getParentMoveId() { return parentMoveId; }
    public void setParentMoveId(Long parentMoveId) { this.parentMoveId = parentMoveId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getInput() { return input; }
    public void setInput(String input) { this.input = input; }

    public MoveType getMoveType() { return moveType; }
    public void setMoveType(MoveType moveType) { this.moveType = moveType; }

    public Integer getSuperArtLevel() { return superArtLevel; }
    public void setSuperArtLevel(Integer superArtLevel) { this.superArtLevel = superArtLevel; }

    public Integer getDamage() { return damage; }
    public void setDamage(Integer damage) { this.damage = damage; }

    public Integer getChipDamage() { return chipDamage; }
    public void setChipDamage(Integer chipDamage) { this.chipDamage = chipDamage; }

    public Integer getStartupFrames() { return startupFrames; }
    public void setStartupFrames(Integer startupFrames) { this.startupFrames = startupFrames; }

    public Integer getActiveFrames() { return activeFrames; }
    public void setActiveFrames(Integer activeFrames) { this.activeFrames = activeFrames; }

    public Integer getRecoveryFrames() { return recoveryFrames; }
    public void setRecoveryFrames(Integer recoveryFrames) { this.recoveryFrames = recoveryFrames; }

    public Integer getOnHitAdvantage() { return onHitAdvantage; }
    public void setOnHitAdvantage(Integer onHitAdvantage) { this.onHitAdvantage = onHitAdvantage; }

    public Integer getOnBlockAdvantage() { return onBlockAdvantage; }
    public void setOnBlockAdvantage(Integer onBlockAdvantage) { this.onBlockAdvantage = onBlockAdvantage; }

    public Integer getOnCounterHitAdvantage() { return onCounterHitAdvantage; }
    public void setOnCounterHitAdvantage(Integer onCounterHitAdvantage) { this.onCounterHitAdvantage = onCounterHitAdvantage; }

    public Integer getOnPunishCounterAdvantage() { return onPunishCounterAdvantage; }
    public void setOnPunishCounterAdvantage(Integer onPunishCounterAdvantage) { this.onPunishCounterAdvantage = onPunishCounterAdvantage; }

    public Integer getDriveRushOnHit() { return driveRushOnHit; }
    public void setDriveRushOnHit(Integer driveRushOnHit) { this.driveRushOnHit = driveRushOnHit; }

    public Integer getDriveRushOnBlock() { return driveRushOnBlock; }
    public void setDriveRushOnBlock(Integer driveRushOnBlock) { this.driveRushOnBlock = driveRushOnBlock; }

    public Boolean getHasArmor() { return hasArmor; }
    public void setHasArmor(Boolean hasArmor) { this.hasArmor = hasArmor; }

    public Integer getArmorHits() { return armorHits; }
    public void setArmorHits(Integer armorHits) { this.armorHits = armorHits; }

    public String getCancelOptions() { return cancelOptions; }
    public void setCancelOptions(String cancelOptions) { this.cancelOptions = cancelOptions; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
