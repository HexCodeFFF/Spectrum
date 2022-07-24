package de.dafuqs.spectrum.tweakers;

import de.dafuqs.spectrum.SpectrumCommon;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SpectrumASMTransformer implements IClassTransformer {

	private final String implementableDesc;
	private final String strippableDesc;
	
	private final ArrayList<String> workingPath = new ArrayList<>();

	public SpectrumASMTransformer() {
		implementableDesc = Type.getDescriptor(Implementable.class);
		strippableDesc = Type.getDescriptor(Strippable.class);
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		ClassReader cr = new ClassReader(bytes);
		ClassNode cn = new ClassNode();
		cr.accept(cn, 0);

		workingPath.add(transformedName);

		if (this.implement(cn)) {
			SpectrumCommon.logInfo("Adding runtime interfaces to " + transformedName);
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			cn.accept(cw);
			bytes = cw.toByteArray();
			cr = new ClassReader(bytes);
			cn = new ClassNode();
			cr.accept(cn, 0);
		}

		if (this.strip(cn)) {
			SpectrumCommon.logInfo("Stripping methods and fields from " + transformedName);
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
			cn.accept(cw);
			bytes = cw.toByteArray();
			cr = new ClassReader(bytes);
		}

		workingPath.remove(workingPath.size() - 1);

		return bytes;
	}

	private boolean implement(ClassNode cn) {
		if (cn.visibleAnnotations == null) {
			return false;
		}
		boolean interfaces = false;
		for (AnnotationNode node : cn.visibleAnnotations) {
			if (node.desc.equals(implementableDesc)) {
				if (node.values != null) {
					List<Object> values = node.values;
					for (int i = 0, e = values.size(); i < e;) {
						Object k = values.get(i++);
						Object v = values.get(i++);
						if ("value".equals(k) && v instanceof List && ((List<?>) v).size() > 0 && ((List<?>) v).get(0) instanceof String) {
							String[] value = ((List<?>) v).toArray(new String[0]);
							for (String s : value) {
								String clazz = s.trim();
								String cz = clazz.replace('.', '/');
								if (!cn.interfaces.contains(cz)) {
									try {
										if (!workingPath.contains(clazz)) {
											Class.forName(clazz, false, this.getClass().getClassLoader());
										}
										cn.interfaces.add(cz);
										interfaces = true;
									} catch (Throwable t) {
									}
								}
							}
						}
					}
				}
			}
		}
		return interfaces;
	}

	private boolean strip(ClassNode cn) {
		boolean altered = false;
		if (cn.visibleAnnotations != null) {
			for (AnnotationNode node : cn.visibleAnnotations) {
				if (node.desc.equals(strippableDesc)) {
					if (node.values != null) {
						List<Object> values = node.values;
						for (int i = 0, e = values.size(); i < e;) {
							Object k = values.get(i++);
							Object v = values.get(i++);
							if ("value".equals(k) && v instanceof List && ((List<?>) v).size() > 0 && ((List<?>) v).get(0) instanceof String) {
								String[] value = ((List<?>) v).toArray(new String[0]);
								for (String clazz : value) {
									String cz = clazz.replace('.', '/');
									if (cn.interfaces.contains(cz)) {
										try {
											if (!workingPath.contains(clazz)) {
												Class.forName(clazz, false, this.getClass().getClassLoader());
											}
										} catch (Throwable t) {
											cn.interfaces.remove(cz);
											altered = true;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if (cn.methods != null) {
			Iterator<MethodNode> iter = cn.methods.iterator();
			while (iter.hasNext()) {
				MethodNode mn = iter.next();
				if (mn.visibleAnnotations != null) {
					for (AnnotationNode node : mn.visibleAnnotations) {
						if (checkRemove(node, iter)) {
							altered = true;
							break;
						}
					}
				}
			}
		}
		if (cn.fields != null) {
			Iterator<FieldNode> iter = cn.fields.iterator();
			while (iter.hasNext()) {
				FieldNode fn = iter.next();
				if (fn.visibleAnnotations != null) {
					for (AnnotationNode node : fn.visibleAnnotations) {
						if (checkRemove(node, iter)) {
							altered = true;
							break;
						}
					}
				}
			}
		}
		return altered;
	}

	private boolean checkRemove(AnnotationNode node, Iterator<? extends Object> iter) {

		if (node.desc.equals(strippableDesc)) {
			if (node.values != null) {
				List<Object> values = node.values;
				for (int i = 0, e = values.size(); i < e;) {
					Object k = values.get(i++);
					Object v = values.get(i++);
					if ("value".equals(k) && v instanceof List && ((List<?>) v).size() > 0 && ((List<?>) v).get(0) instanceof String) {
						String[] value = ((List<?>) v).toArray(new String[0]);
						boolean needsRemoved = false;
						for (String clazz : value) {
							if (clazz.startsWith("mod:")) {
								needsRemoved = !FabricLoader.getInstance().isModLoaded(clazz.substring(4));
							} else try {
								if (!workingPath.contains(clazz)) {
									Class.forName(clazz, false, this.getClass().getClassLoader());
								}
							} catch (Throwable t) {
								needsRemoved = true;
							}
							if (needsRemoved) break;
						}
						if (needsRemoved) {
							iter.remove();
							return true;
						}
					}
				}
			}
		}
		return false;
	}

}