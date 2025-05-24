package gay.plat.ctn.mixin;

import gay.plat.ctn.config.CooldownTrickNotifierConfig;
import gay.plat.ctn.events.CooldownTrickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Optional;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin{
	@Unique
	AttributeModifiersComponent prevAttributeModifiersComponent = new AttributeModifiersComponent(new ArrayList<>(),false);

	@Inject(at = @At("HEAD"), method = "tick")
	private void onTick(CallbackInfo info) {
		final PlayerEntity player = (PlayerEntity)(Object)this;
		if (player.getWorld().isClient() && CooldownTrickNotifierConfig.shouldPlaySound(player))
			prevAttributeModifiersComponent = player.getMainHandStack().getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT);
	}

	@Inject(at = @At("HEAD"), method = "attack")
	private void onAttack(Entity target, CallbackInfo info) {
		final PlayerEntity player = (PlayerEntity)(Object)this;
		if (player.getWorld().isClient() && CooldownTrickNotifierConfig.shouldPlaySound(player)) {
			AttributeModifiersComponent attributeModifiersComponent = player.getMainHandStack().getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT);
			if (attributeModifiersComponent != prevAttributeModifiersComponent) {
				CooldownTrickCallback.EVENT.invoker().interact(player, target, player.getWorld(), player.getMainHandStack());

				assert MinecraftClient.getInstance().player != null;
				Optional.ofNullable(CooldownTrickNotifierConfig.soundID)
					.map(Identifier::tryParse)
					.map(Registries.SOUND_EVENT::get)
					.ifPresent(sound -> MinecraftClient.getInstance().player.playSound(sound, CooldownTrickNotifierConfig.volume, CooldownTrickNotifierConfig.pitch));
			}
		}
	}
}