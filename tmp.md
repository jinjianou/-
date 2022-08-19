


同步方法

```
Returns a new CompletableFuture that is already completed with the given
public static <U> CompletableFuture<U> completedFuture(U value)

//不带Async的都是
 public <U> CompletableFuture<U> thenApply(
        Function<? super T,? extends U> fn)
```



如

```
 CompletableFuture<String> message = CompletableFuture.completedFuture("message").thenApply(s -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("then apply....");
            return s;
        });

        System.out.println(message.getNow(null));
```



但同步套异步仍然无法实现同步问题，如

```
ExecutorService executor = Executors.newSingleThreadExecutor();
CompletableFuture<String> message = CompletableFuture.completedFuture("message").thenApply(s -> {
    CompletableFuture.runAsync(()->{
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("then apply....");},executor);

    return s;
});

System.out.println(message.getNow(null));
```

我们可能预期的输出

```
等待2s
then apply....
message
```

实际

@test

```
message
```

main

```
message
等待2s
then apply....
```
