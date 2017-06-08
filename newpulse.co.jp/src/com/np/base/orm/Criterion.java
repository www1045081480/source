package com.np.base.orm;

import java.util.List;

public interface Criterion {

	String toSqlString();

	String getName();

	String getOp();

	void setName(String name);

	int numberOfValues();

	Object getValue(int index);
}

class EqualsCriterion extends BaseSingleValueCriterion {

	protected EqualsCriterion(String name, Object value) {
		super("=", name, value);
	}

}

class NotEqualsCriterion extends BaseSingleValueCriterion {

	protected NotEqualsCriterion(String name, Object value) {
		super("<>", name, value);
	}

}

class GreateEqualsCriterion extends BaseSingleValueCriterion {

	protected GreateEqualsCriterion(String name, Object value) {
		super(">=", name, value);
	}

}

class GreateThanCriterion extends BaseSingleValueCriterion {

	protected GreateThanCriterion(String name, Object value) {
		super(">", name, value);
	}

}

class LessEqualsCriterion extends BaseSingleValueCriterion {

	protected LessEqualsCriterion(String name, Object value) {
		super("<=", name, value);
	}

}

class LessThanCriterion extends BaseSingleValueCriterion {

	protected LessThanCriterion(String name, Object value) {
		super("<", name, value);
	}

}

class LikeCriterion extends BaseSingleValueCriterion {

	protected LikeCriterion(String name, Object value) {
		super("like", name, value);
	}
}

class BetweenCriterion extends BaseSingleValueCriterion {
	private Object from;
	private Object to;

	protected BetweenCriterion(String name, Object from, Object to) {
		super("between", name, new Object[] { from, to });
		this.from = from;
		this.to = to;
	}

	@Override
	public String toSqlString() {
		StringBuffer sb = new StringBuffer();

		sb.append(getName());

		sb.append(" ");
		sb.append(">=");
		sb.append(" ");

		sb.append(toSqlValue(from));

		sb.append(" AND ");

		sb.append(getName());

		sb.append(" ");
		sb.append("<=");
		sb.append(" ");

		sb.append(toSqlValue(to));

		return sb.toString();
	}

	@Override
	public int numberOfValues() {
		return 2;
	}

	@Override
	public Object getValue(int index) {
		if (index == 0)
			return from;
		else if (index == 1)
			return to;
		/*
		 * TODO
		 */
		throw new RuntimeException();
	}
}

class IsNotNullCriterion extends BaseSingleValueCriterion {

	protected IsNotNullCriterion(String name) {
		super("IS NOT NULL", name, null);
	}

	@Override
	public int numberOfValues() {
		return 0;
	}

}

class IsNullCriterion extends BaseSingleValueCriterion {

	protected IsNullCriterion(String name) {
		super("IS NULL", name, null);
	}

	@Override
	public int numberOfValues() {
		return 0;
	}

}

class InCriterion extends BaseSingleValueCriterion {
	@SuppressWarnings("rawtypes")
	private List values;

	@SuppressWarnings("rawtypes")
	protected InCriterion(String name, List values) {
		super("IN", name, values);
		this.values = values;
	}

	@Override
	public int numberOfValues() {
		return values.size();
	}

	@Override
	public String toSqlString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getName());

		sb.append(" ");
		sb.append(getOp());
		sb.append(" ");
		sb.append("(");
		boolean first = true;
		for (Object value : values) {
			if (first == false)
				sb.append(",");
			first = false;
			sb.append(toSqlValue(value));
		}
		sb.append(")");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public List getValues() {
		return values;
	}

	@SuppressWarnings({ "rawtypes" })
	public void setValues(List values) {
		this.values = values;
	}
}

class NotInCriterion extends BaseSingleValueCriterion {
	@SuppressWarnings("rawtypes")
	private List values;

	@SuppressWarnings("rawtypes")
	protected NotInCriterion(String name, List values) {
		super("NOT IN", name, values);
		this.values = values;
	}

	@Override
	public int numberOfValues() {
		return values.size();
	}

	@Override
	public String toSqlString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getName());

		sb.append(" ");
		sb.append(getOp());
		sb.append(" ");
		sb.append("(");
		boolean first = true;
		for (Object value : values) {
			if (first == false)
				sb.append(",");
			first = false;
			sb.append(toSqlValue(value));
		}
		sb.append(")");
		return sb.toString();
	}

	@SuppressWarnings("rawtypes")
	public List getValues() {
		return values;
	}

	@SuppressWarnings("rawtypes")
	public void setValues(List values) {
		this.values = values;
	}
}

class NotInClauseCriterion extends BaseSingleValueCriterion {
	@SuppressWarnings("rawtypes")
	private Criteria inClause;

	@SuppressWarnings("rawtypes")
	protected NotInClauseCriterion(String name, Criteria inClause) {
		super("NOT IN", name, inClause.toQuerySQL());
		this.inClause = inClause;
	}

	@Override
	public int numberOfValues() {
		return (getValue(0) == null) ? 0 : 1;
	}

	@Override
	public String toSqlString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getName());

		sb.append(" ");
		sb.append(getOp());
		sb.append(" ");
		sb.append("(");
		sb.append(inClause.toQuerySQL());
		sb.append(")");
		return sb.toString();
	}

}

class InClauseCriterion extends BaseSingleValueCriterion {
	@SuppressWarnings("rawtypes")
	private Criteria inClause;

	@SuppressWarnings("rawtypes")
	protected InClauseCriterion(String name, Criteria inClause) {
		super("IN", name, inClause.toQuerySQL());
		this.inClause = inClause;
	}

	@Override
	public int numberOfValues() {
		return (getValue(0) == null) ? 0 : 1;
	}

	@Override
	public String toSqlString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getName());

		sb.append(" ");
		sb.append(getOp());
		sb.append(" ");
		sb.append("(");
		sb.append(inClause.toQuerySQL());
		sb.append(")");
		return sb.toString();
	}

}

abstract class BaseSingleValueCriterion implements Criterion {
	private String op;
	private String name;
	private Object value;

	protected BaseSingleValueCriterion(String op, String name, Object value) {
		this.op = op;
		this.name = name;
		this.value = value;
	}

	protected String toSqlValue(Object value) {
		if (value == null)
			return null;
		StringBuffer sb = new StringBuffer();

		if (String.class.isInstance(value))
			sb.append("'");
		sb.append(value);
		if (String.class.isInstance(value))
			sb.append("'");
		return sb.toString();
	}

	@Override
	public String toSqlString() {
		StringBuffer sb = new StringBuffer();
		sb.append(name);

		sb.append(" ");
		sb.append(op);
		sb.append(" ");
		if (numberOfValues() == 1)
			sb.append(toSqlValue(value));

		return sb.toString();
	}

	@Override
	public String toString() {
		return toSqlString();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int numberOfValues() {
		return 1;
	}

	@Override
	public Object getValue(int index) {
		return value;
	}

	public String getOp() {
		return op;
	}

	public void setName(String name) {
		this.name = name;
	}

}