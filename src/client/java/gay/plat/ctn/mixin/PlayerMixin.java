package gay.plat.ctn.mixin;

import gay.plat.ctn.CooldownTrickNotifier;
import gay.plat.ctn.config.CtnConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends Avatar {
    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public ItemStack lastItemInMainHand;

    @Inject(at = @At("HEAD"), method = "attack")
    private void onAttack(Entity entity, CallbackInfo info) {
        final CtnConfig config = CooldownTrickNotifier.configManager.getConfig();
        if (config.shouldPlaySound((Player)(Object)this) && !ItemStack.matches(this.lastItemInMainHand, this.getMainHandItem())) {
            assert Minecraft.getInstance().player != null;
            SoundEvent sound = BuiltInRegistries.SOUND_EVENT.getValue(Identifier.tryParse(config.sound));
            if (sound != null)
                Minecraft.getInstance().player.playSound(sound, config.volume, config.pitch);
        }
    }
}