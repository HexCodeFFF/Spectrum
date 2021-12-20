package de.dafuqs.spectrum.blocks.item_bowl;

import de.dafuqs.spectrum.Support;
import de.dafuqs.spectrum.networking.SpectrumS2CPackets;
import de.dafuqs.spectrum.particle.SpectrumParticleTypes;
import de.dafuqs.spectrum.registries.SpectrumBlockEntityRegistry;
import de.dafuqs.spectrum.registries.color.ColorRegistry;
import de.dafuqs.spectrum.sound.SpectrumSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ItemBowlBlockEntity extends BlockEntity {
	
	protected int INVENTORY_SIZE = 1;
	protected SimpleInventory inventory;
	
	public ItemBowlBlockEntity(BlockPos pos, BlockState state) {
		super(SpectrumBlockEntityRegistry.ITEM_BOWL, pos, state);
		this.inventory = new SimpleInventory(INVENTORY_SIZE);
	}
	
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		this.inventory = new SimpleInventory(INVENTORY_SIZE);
		this.inventory.readNbtList(nbt.getList("inventory", 10));
	}
	
	public void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		nbt.put("inventory", this.inventory.toNbtList());
	}
	
	// Called when the chunk is first loaded to initialize this be
	public NbtCompound toInitialChunkDataNbt() {
		NbtCompound nbtCompound = new NbtCompound();
		this.writeNbt(nbtCompound);
		return nbtCompound;
	}
	
	@Nullable
	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}
	
	public void updateInClientWorld() {
		world.updateListeners(pos, world.getBlockState(pos), world.getBlockState(pos), Block.NO_REDRAW);
	}
	
	public Inventory getInventory() {
		return this.inventory;
	}
	
	public static void clientTick(@NotNull World world, BlockPos blockPos, BlockState blockState, ItemBowlBlockEntity itemBowlBlockEntity) {
		ItemStack storedStack = itemBowlBlockEntity.getInventory().getStack(0);
		if(!storedStack.isEmpty()) {
			Optional<DyeColor> optionalItemColor = ColorRegistry.ITEM_COLORS.getMapping(storedStack.getItem());
			if(optionalItemColor.isPresent()) {
				int particleCount = Support.getIntFromDecimalWithChance(Math.max(0.1, (float) storedStack.getCount() / (storedStack.getMaxCount() * 2)), world.random);
				spawnParticles(world, blockPos, storedStack, particleCount);
			}
		}
	}
	
	public int decrementBowlStack(BlockPos particleTargetBlockPos, int amount) {
		ItemStack storedStack = this.inventory.getStack(0);
		if(storedStack.isEmpty()) {
			return 0;
		}
		
		int decrementAmount = Math.min(amount, storedStack.getCount());
		Item recipeRemainderItem = storedStack.getItem().getRecipeRemainder();
		if(recipeRemainderItem != null) {
			inventory.setStack(0, recipeRemainderItem.getDefaultStack());
		} else {
			inventory.getStack(0).decrement(decrementAmount);
		}
		
		if(decrementAmount > 0 && this.world instanceof ServerWorld serverWorld) {
			Optional<DyeColor> optionalItemColor = ColorRegistry.ITEM_COLORS.getMapping(storedStack.getItem());
			if(optionalItemColor.isPresent()) {
				ParticleEffect sparkleRisingParticleEffect = SpectrumParticleTypes.getSparkleRisingParticle(optionalItemColor.get());
				SpectrumS2CPackets.playParticle((ServerWorld) world, new Vec3d(pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5), sparkleRisingParticleEffect, 50, new Vec3d(0.4, 0.2, 0.4), new Vec3d(0.06, 0.16, 0.06));
				
				ParticleEffect fluidRisingParticleEffect = SpectrumParticleTypes.getFluidRisingParticle(optionalItemColor.get());
				SpectrumS2CPackets.playParticleWithFixedVelocity(serverWorld,
						new Vec3d(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D),
						fluidRisingParticleEffect, decrementAmount * 10, new Vec3d(0, 0, 0),
						new Vec3d((particleTargetBlockPos.getX() - this.pos.getX()) * 0.0045, 0, (particleTargetBlockPos.getZ() - this.pos.getZ()) * 0.0045));
				world.playSound(null, this.pos, SpectrumSoundEvents.ENCHANTER_DING, SoundCategory.BLOCKS, 1.0F, 0.7F + this.world.random.nextFloat() * 0.6F);
			}
			
			updateInClientWorld();
		}
		
		return decrementAmount;
	}
	
	public static void spawnParticles(World world, BlockPos blockPos, ItemStack itemStack, int amount) {
		if(amount > 0) {
			Optional<DyeColor> optionalItemColor = ColorRegistry.ITEM_COLORS.getMapping(itemStack.getItem());
			if (optionalItemColor.isPresent()) {
				ParticleEffect particleEffect = SpectrumParticleTypes.getSparkleRisingParticle(optionalItemColor.get());
				
				for (int i = 0; i < amount; i++) {
					float randomX = 0.1F + world.getRandom().nextFloat() * 0.8F;
					float randomZ = 0.1F + world.getRandom().nextFloat() * 0.8F;
					world.addParticle(particleEffect, blockPos.getX() + randomX, blockPos.getY() + 0.75, blockPos.getZ() + randomZ, 0.0D, 0.05D, 0.0D);
				}
			}
		}
	}
	
}
