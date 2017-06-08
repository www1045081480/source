package com.np.base.dml;

public interface IMatchingProcedure {
	boolean doMatching(Object tra, Object mas);

	void doNoMatch(Object tra);

	void doSkipMaster(Object mas);

	Object doFinish();
}
