/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Psi Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Psi
 * 
 * Psi is Open Source and distributed under the
 * Psi License: http://psi.vazkii.us/license.php
 * 
 * File Created @ [10/03/2016, 18:54:52 (GMT)]
 */
package vazkii.psi.common.spell.trick;

import vazkii.psi.api.spell.EnumSpellStat;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellCompilationException;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellMetadata;
import vazkii.psi.api.spell.SpellParam;
import vazkii.psi.api.spell.SpellRuntimeException;
import vazkii.psi.api.spell.param.ParamNumber;
import vazkii.psi.api.spell.piece.PieceTrick;

public class PieceTrickSwitchTargetSlot extends PieceTrick {

	SpellParam pos;
	SpellParam shift;
	
	public PieceTrickSwitchTargetSlot(Spell spell) {
		super(spell);
	}

	@Override
	public void initParams() {
		addParam(pos = new ParamNumber(SpellParam.GENERIC_NAME_POSITION, SpellParam.BLUE, true, false));
		addParam(shift = new ParamNumber("psi.spellparam.shift", SpellParam.GREEN, true, false));
	}

	@Override
	public void addToMetadata(SpellMetadata meta) throws SpellCompilationException {
		if(paramSides.get(pos) != SpellParam.Side.OFF && paramSides.get(shift) != SpellParam.Side.OFF)
			throw new SpellCompilationException("psi.spellerror.exclusiveparams", x, y);
		
		meta.addStat(EnumSpellStat.COMPLEXITY, 1);
	}

	@Override
	public Object execute(SpellContext context) throws SpellRuntimeException {
		Double posVal = this.<Double>getParamValue(context, pos);
		Double shiftVal = this.<Double>getParamValue(context, shift);
		
		if(shiftVal != null) {
			context.shiftTargetSlot = true;
			context.targetSlot = shiftVal.intValue();
		} else if(posVal != null) {
			context.shiftTargetSlot = false;
			context.targetSlot = posVal.intValue();
		} else {
			context.shiftTargetSlot = true;
			context.targetSlot = 1;
		} 

		return null;
	}

}
