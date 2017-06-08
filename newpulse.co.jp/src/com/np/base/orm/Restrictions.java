package com.np.base.orm;

import java.util.List;

public class Restrictions {

	@SuppressWarnings("rawtypes")
	public static Criterion in(String name, List values) {
		return new InCriterion(name, values);
	}

	@SuppressWarnings("rawtypes")
	public static Criterion notIn(String name, List values) {
		return new NotInCriterion(name, values);
	}

	public static Criterion ne(String name, String value) {
		return new NotEqualsCriterion(name, value);
	}

	public static Criterion ne(String name, Integer value) {
		return new NotEqualsCriterion(name, value);
	}

	public static Criterion ne(String name, Long value) {
		return new NotEqualsCriterion(name, value);
	}

	public static Criterion eq(String name, String value) {
		return new EqualsCriterion(name, value);
	}

	public static Criterion eq(String name, Long value) {
		return new EqualsCriterion(name, value);
	}

	public static Criterion eq(String name, Integer value) {
		return new EqualsCriterion(name, value);
	}

	public static Criterion eq(String name, Double value) {
		return new EqualsCriterion(name, value);
	}

	public static Criterion gt(String name, String value) {
		return new GreateThanCriterion(name, value);
	}

	public static Criterion ge(String name, String value) {
		return new GreateEqualsCriterion(name, value);
	}

	public static Criterion lt(String name, String value) {
		return new LessThanCriterion(name, value);
	}

	public static Criterion le(String name, String value) {
		return new LessEqualsCriterion(name, value);
	}

	public static Criterion like(String name, String value) {
		return new LikeCriterion(name, "%" + value + "%");
	}

	public static Criterion isNotNull(String name) {
		return new IsNotNullCriterion(name);
	}

	public static Criterion isNull(String name) {
		return new IsNullCriterion(name);
	}

	public static Criterion between(String name, String from, String to) {
		return new BetweenCriterion(name, from, to);
	}

	@SuppressWarnings("rawtypes")
	public static Criterion notIn(String name, Criteria inClause) {
		return new NotInClauseCriterion(name, inClause);
	}

	@SuppressWarnings("rawtypes")
	public static Criterion in(String name, Criteria inClause) {
		return new InClauseCriterion(name, inClause);
	}

}
