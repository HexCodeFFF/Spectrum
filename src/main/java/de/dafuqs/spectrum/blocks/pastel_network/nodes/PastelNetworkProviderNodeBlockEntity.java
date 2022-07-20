package de.dafuqs.spectrum.blocks.pastel_network.nodes;

import de.dafuqs.spectrum.registries.SpectrumBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class PastelNetworkProviderNodeBlockEntity extends PastelNetworkNodeBlockEntity {
	
	public PastelNetworkProviderNodeBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(SpectrumBlockEntities.PROVIDER_NODE, blockPos, blockState);
	}
	
}