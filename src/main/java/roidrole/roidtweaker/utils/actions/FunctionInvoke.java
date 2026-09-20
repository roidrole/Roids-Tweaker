package roidrole.roidtweaker.utils.actions;

import roidrole.roidtweaker.utils.DeferredLoader;
import roidrole.roidtweaker.utils.functions.QuadConsumer;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.ObjIntConsumer;

public abstract class FunctionInvoke implements DeferredLoader.IAction {

	public static FunctionInvoke from(IntConsumer function, int argument){
		return new IntConsumerInvoke(function, argument);
	}
	public static <T> FunctionInvoke from(Consumer<T> function, T argument){
		return new ConsumerInvoke<>(function, argument);
	}
	public static <T> FunctionInvoke from(Consumer<T> function, Collection<T> argument){
		return new ConsumerIterateInvoke<>(function, argument);
	}
	public static <T, U> FunctionInvoke from(BiConsumer<T, U> function, T[] argument, U argument2){
		return new BiConsumerIterateInvoke<>(function, argument, argument2);
	}
	public static <T, U> FunctionInvoke from(BiConsumer<T, U> function, T argument, U argument2){
		return new BiConsumerInvoke<>(function, argument, argument2);
	}
	public static <T> FunctionInvoke from(ObjIntConsumer<T> function, T argument1, int argument2){
		return new ObjIntConsumerInvoke<>(function, argument1, argument2);
	}
	public static <A, B, C, D> FunctionInvoke from(QuadConsumer<A, B, C, D> function, A argument1, B argument2, C argument3, D argument4){
		return new QuadConsumerInvoke<>(function, argument1, argument2, argument3, argument4);
	}

	private static class IntConsumerInvoke extends FunctionInvoke {
		private final IntConsumer function;
		private final int argument;

		private IntConsumerInvoke(IntConsumer function, int argument) {
			this.function = function;
			this.argument = argument;
		}

		@Override
		public void load() {
			function.accept(argument);
		}
	}
	private static class ConsumerInvoke<T> extends FunctionInvoke {
		private final Consumer<T> function;
		private final T argument;

		private ConsumerInvoke(Consumer<T> function, T argument) {
			this.function = function;
			this.argument = argument;
		}

		@Override
		public void load() {
			function.accept(argument);
		}
	}
	private static class ConsumerIterateInvoke<T> extends FunctionInvoke {
		private final Consumer<T> function;
		private final Collection<T> argument;

		private ConsumerIterateInvoke(Consumer<T> function, Collection<T> argument) {
			this.function = function;
			this.argument = argument;
		}

		@Override
		public void load() {
			for (T  t: argument){
				function.accept(t);
			}
		}
	}
	private static class BiConsumerInvoke<T, U> extends FunctionInvoke {
		private final BiConsumer<T, U> function;
		private final T argument;
		private final U argument2;

		private BiConsumerInvoke(BiConsumer<T, U> function, T argument, U argument2) {
			this.function = function;
			this.argument = argument;
			this.argument2 = argument2;
		}

		@Override
		public void load() {
			function.accept(argument, argument2);
		}
	}
	private static class BiConsumerIterateInvoke<T, U> extends FunctionInvoke {
		private final BiConsumer<T, U> function;
		private final T[] argument;
		private final U argument2;

		private BiConsumerIterateInvoke(BiConsumer<T, U> function, T[] argument, U argument2) {
			this.function = function;
			this.argument = argument;
			this.argument2 = argument2;
		}

		@Override
		public void load() {
			for (T  t: argument){
				function.accept(t, argument2);
			}
		}
	}
	private static class ObjIntConsumerInvoke<T> extends FunctionInvoke {
		private final ObjIntConsumer<T> function;
		private final T argument1;
		private final int argument2;

		private ObjIntConsumerInvoke(ObjIntConsumer<T> function, T argument1, int argument2) {
			this.function = function;
			this.argument1 = argument1;
			this.argument2 = argument2;
		}

		@Override
		public void load() {
			function.accept(argument1, argument2);
		}
	}
	private static class QuadConsumerInvoke<A, B, C, D> extends FunctionInvoke {
		private final QuadConsumer<A, B, C, D> function;
		private final A argument1;
		private final B argument2;
		private final C argument3;
		private final D argument4;

		private QuadConsumerInvoke(QuadConsumer<A, B, C, D> function, A argument1, B argument2, C argument3, D argument4) {
			this.function = function;
			this.argument1 = argument1;
			this.argument2 = argument2;
			this.argument3 = argument3;
			this.argument4 = argument4;
		}

		@Override
		public void load() {
			function.accept(argument1, argument2, argument3, argument4);
		}
	}
}
