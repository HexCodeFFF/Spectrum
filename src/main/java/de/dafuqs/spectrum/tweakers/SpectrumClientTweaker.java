package de.dafuqs.spectrum.tweakers;

import net.fabricmc.loader.impl.game.minecraft.launchwrapper.FabricClientTweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;

import java.io.File;
import java.util.List;

public class SpectrumClientTweaker extends FabricClientTweaker {
	
	@Override
	public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
	
	}
	
	@Override
	public void injectIntoClassLoader(LaunchClassLoader classLoader) {
		classLoader.registerTransformer(SpectrumASMTransformer.class.getName());
	}
	
	@Override
	public String getLaunchTarget() {
		return null;
	}
	
	@Override
	public String[] getLaunchArguments() {
		return new String[0];
	}
	
}
