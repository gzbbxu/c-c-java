package com.zkk.lambda;
@FunctionalInterface
public interface MyPredicate<T> {
	public boolean test(T t);
}
