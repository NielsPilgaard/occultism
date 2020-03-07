/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.klikli_dev.occultism.registry;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.ritual.Ritual;
import com.github.klikli_dev.occultism.common.ritual.pentacle.DebugPentacle;
import com.github.klikli_dev.occultism.common.ritual.pentacle.Pentacle;
import com.github.klikli_dev.occultism.common.ritual.pentacle.PentacleCraftDjinni;
import com.github.klikli_dev.occultism.common.ritual.pentacle.PentacleSummonFoliotBasic;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.function.Supplier;

import static com.github.klikli_dev.occultism.util.StaticUtil.modLoc;

public class OccultismRituals {
    //region Fields
    public static final IForgeRegistry<Pentacle> PENTACLE_REGISTRY = RegistryManager.ACTIVE.getRegistry(Pentacle.class);
    public static final DeferredRegister<Pentacle> PENTACLES = new DeferredRegister<>(PENTACLE_REGISTRY, Occultism.MODID);
    public static final IForgeRegistry<Ritual> RITUAL_REGISTRY = RegistryManager.ACTIVE.getRegistry(Ritual.class);
    public static final DeferredRegister<Ritual> RITUALS = new DeferredRegister<>(RITUAL_REGISTRY, Occultism.MODID);

    public static final RegistryObject<DebugPentacle> PENTACLE_DEBUG = register("debug", DebugPentacle::new);
    public static final RegistryObject<PentacleSummonFoliotBasic> PENTALCE_SUMMON_FOLIOT_BASIC = register("summon_foliot_basic", PentacleSummonFoliotBasic::new);
    public static final RegistryObject<PentacleCraftDjinni> PENTALCE_CRAFT_DJINNI = register("craft_djinni", PentacleCraftDjinni::new);

    //endregion Fields

    //region Static Methods
    public static <T extends Pentacle> RegistryObject<T> register(final String name, final Supplier<? extends T> sup) {
        return PENTACLES.register(name, () -> {
            T pentacle = sup.get();
            ResourceLocation multiBlockId = modLoc("pentacle." + name);
            if (PatchouliAPI.instance.getMultiblock(multiBlockId) == null)
                pentacle.registerMultiblock(multiBlockId);
            return pentacle;
        });
    }
    //endregion Static Methods
}
