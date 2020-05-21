package com.acme.common.util;

public interface Functions {

	public interface Func0<R> {

		R apply();
	}
	
	public interface Func1<T1, R> {

		R apply(T1 input1);
	}

	public interface Func2<T1, T2, R> {

		R apply(T1 input1, T2 input2);
	}

	public interface Func3<T1, T2, T3, R> {

		R apply(T1 input1, T2 input2, T3 input);
	}

	public interface Func4<T1, T2, T3, T4, R> {

		R apply(T1 input1, T2 input2, T3 input, T4 input4);
	}

	public interface Func5<T1, T2, T3, T4, T5, R> {

		R apply(T1 input1, T2 input2, T3 input, T4 input4, T5 input5);
	}

	public interface Func6<T1, T2, T3, T4, T5, T6, R> {

		R apply(T1 input1, T2 input2, T3 input, T4 input4, T5 input5, T6 input6);
	}

	public interface Func7<T1, T2, T3, T4, T5, T6, T7, R> {

		R apply(T1 input1, T2 input2, T3 input, T4 input4, T5 input5, T6 input6, T7 input7);
	}

	public interface Func8<T1, T2, T3, T4, T5, T6, T7, T8, R> {

		R apply(T1 input1, T2 input2, T3 input, T4 input4, T5 input5, T6 input6, T7 input7, T8 input8);
	}
	
	public interface Action0 {

		void accept();
	}
	
	public interface Action1<T1> {

		void accept(T1 input1);
	}

	public interface Action2<T1, T2> {

		void accept(T1 input1, T2 input2);
	}

	public interface Action3<T1, T2, T3> {

		void accept(T1 input1, T2 input2, T3 input);
	}

	public interface Action4<T1, T2, T3, T4> {

		void accept(T1 input1, T2 input2, T3 input, T4 input4);
	}

	public interface Action5<T1, T2, T3, T4, T5> {

		void accept(T1 input1, T2 input2, T3 input, T4 input4, T5 input5);
	}

	public interface Action6<T1, T2, T3, T4, T5, T6> {

		void accept(T1 input1, T2 input2, T3 input, T4 input4, T5 input5, T6 input6);
	}

	public interface Action7<T1, T2, T3, T4, T5, T6, T7> {

		void accept(T1 input1, T2 input2, T3 input, T4 input4, T5 input5, T6 input6, T7 input7);
	}

	public interface Action8<T1, T2, T3, T4, T5, T6, T7, T8> {

		void accept(T1 input1, T2 input2, T3 input, T4 input4, T5 input5, T6 input6, T7 input7, T8 input8);
	}
}
