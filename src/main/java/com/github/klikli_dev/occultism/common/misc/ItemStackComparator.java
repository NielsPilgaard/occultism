/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 * Some of the software architecture of the storage system in this file has been based on https://github.com/MrRiegel.
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

package com.github.klikli_dev.occultism.common.misc;

import com.github.klikli_dev.occultism.api.common.container.IItemStackComparator;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nonnull;

public class ItemStackComparator implements IItemStackComparator {
    //region Fields
    protected ItemStack filterStack;
    protected boolean matchNbt;
    //endregion Fields

    //region Initialization
    public ItemStackComparator(ItemStack stack) {
        this(stack, false);
    }

    public ItemStackComparator(ItemStack filterStack, boolean matchNbt) {
        this.filterStack = filterStack;
        this.matchNbt = matchNbt;
    }

    private ItemStackComparator() {
    }
    //endregion Initialization

    //region Getter / Setter
    public boolean getMatchNbt() {
        return this.matchNbt;
    }

    public void setMatchNbt(boolean matchNbt) {
        this.matchNbt = matchNbt;
    }
    //endregion Getter / Setter

    //region Overrides
    public ItemStack getFilterStack() {
        return this.filterStack;
    }

    public void setFilterStack(@Nonnull ItemStack filterStack) {
        this.filterStack = filterStack;
    }

    @Override
    public boolean matches(@Nonnull ItemStack stack) {
        if (stack.isEmpty())
            return false;

        if (this.matchNbt && !ItemStack.areItemStackTagsEqual(this.filterStack, stack))
            return false;
        return stack.getItem() == this.filterStack.getItem();
    }
    //endregion Overrides

    //region Static Methods
    public static ItemStackComparator loadFromNBT(CompoundNBT nbt) {
        ItemStackComparator comparator = new ItemStackComparator();
        comparator.readFromNBT(nbt);
        return !comparator.filterStack.isEmpty() ? comparator : null;
    }
    //endregion Static Methods

    //region Methods
    public void readFromNBT(CompoundNBT compound) {
        CompoundNBT nbt = compound.getCompound("stack");
        this.filterStack = ItemStack.read(nbt);
        this.matchNbt = compound.getBoolean("matchNbt");
    }

    public CompoundNBT writeToNBT(CompoundNBT compound) {
        compound.put("stack", this.filterStack.write(new CompoundNBT()));
        compound.putBoolean("matchNbt", this.matchNbt);
        return compound;
    }
    //endregion Methods
}