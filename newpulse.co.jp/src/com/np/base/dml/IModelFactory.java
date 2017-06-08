package com.np.base.dml;

public interface IModelFactory<O, T, M> {
	O create(T tra, M mas);
}
