package com.paolo.fht.core;

import java.util.concurrent.Callable;

public abstract class FHTExecutor
	implements Callable<Void> {

    @Override
    public abstract Void call() throws Exception;
}
