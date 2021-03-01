package com.mystic.gamemode.usage;

import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameMode;

public class GameModeUsage {

    public static final GameMode UNLOCKABLE = ClassTinkerers.getEnum(GameMode.class, "UNLOCKABLE");

    public static GameMode getGameModeFromPlayerEntity(PlayerEntity playerEntity) {
        {
            if (playerEntity.isCreative()) {
                return GameMode.CREATIVE;
            } else if (!playerEntity.isInvulnerableTo(DamageSource.FALL) && playerEntity.canModifyBlocks() && !playerEntity.canFly()) {
                return GameMode.SURVIVAL;
            } else if (!playerEntity.isInvulnerableTo(DamageSource.FALL) && !playerEntity.canModifyBlocks() && !playerEntity.canFly()) {
                return GameMode.ADVENTURE;
            } else if (playerEntity.isSpectator()) {
                return GameMode.SPECTATOR;
            } else {
                return ClassTinkerers.getEnum(GameMode.class, "UNLOCKABLE");
            }
        }
    }

    public static GameMode getGameMode(){
        return ClassTinkerers.getEnum(GameMode.class, "UNLOCKABLE");
    }

    public static GameMode getGameModeFromId(int id){
        if(GameMode.byId(id) == GameMode.SURVIVAL){
            return  GameMode.SURVIVAL;
        }
        else if(GameMode.byId(id) == GameMode.CREATIVE){
            return  GameMode.CREATIVE;
        }
        else if(GameMode.byId(id) == GameMode.ADVENTURE){
            return  GameMode.ADVENTURE;
        }
        else if(GameMode.byId(id) == GameMode.SPECTATOR){
            return  GameMode.SPECTATOR;
        }
        else{
            return ClassTinkerers.getEnum(GameMode.class, "UNLOCKABLE");
        }
    }
}
