package net.drakkcore.Tools;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by Banner on 14.04.2016.
 */
public class ValueAccesor implements TweenAccessor<Value>{
    @Override
    public int getValues(Value target, int tweenType, float[] returnValues) {
        returnValues[0] = target.getVal();
        return 1;
    }

    @Override
    public void setValues(Value target, int tweenType, float[] newValues) {
        target.setVal(newValues[0]);
    }
}
