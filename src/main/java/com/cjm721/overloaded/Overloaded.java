package com.cjm721.overloaded;

import com.cjm721.overloaded.common.CommonProxy;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.versioning.ArtifactVersion;
import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion;
import net.minecraftforge.fml.common.versioning.InvalidVersionSpecificationException;
import net.minecraftforge.fml.common.versioning.VersionRange;

import java.util.HashSet;
import java.util.Set;

@Mod(modid = Overloaded.MODID, version = Overloaded.VERSION,
        acceptedMinecraftVersions = "[1.10.2, 1.11.2]",
        dependencies = "required-after:compatlayer@[0.2.5,)",
        useMetadata = true
        )
public class Overloaded {

    @Mod.Instance(Overloaded.MODID)
    public static Overloaded instance;

    public static final String MODID = "overloaded";
    public static final String VERSION = "${mod_version}";

    public static final String PROXY_CLIENT = "com.cjm721.overloaded.client.ClientProxy";
    public static final String PROXY_SERVER = "com.cjm721.overloaded.common.CommonProxy";

    @SidedProxy(clientSide = Overloaded.PROXY_CLIENT, serverSide = Overloaded.PROXY_SERVER)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
//        if(Loader.isModLoaded("forge")) {
//            for(ModContainer mod :Loader.instance().getModList()) {
//                if(!"forge".equals(mod.getModId()))
//                    continue;
//
//                try {
//                    VersionRange range = VersionRange.createFromVersionSpec("[12.18.3.2221,)");
//                    if(!range.containsVersion(mod.getProcessedVersion())) {
//                        Set<ArtifactVersion> set = new HashSet<>();
//                        set.add(new DefaultArtifactVersion("forge",range));
//                        throw new MissingModsException(set, "forge", "forge");
//                    }
//                } catch (InvalidVersionSpecificationException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        // If there is a certain version of 1.11 forge needed
//        else if (Loader.isModLoaded("Forge")) {
//            for(ModContainer mod :Loader.instance().getModList()) {
//                if(!"Forge".equals(mod.getModId()))
//                    continue;
//
//            }
//        }


        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }
}
