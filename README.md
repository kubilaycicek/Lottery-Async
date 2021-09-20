# Lottery-Async

## Lottery Service
```
    public List<Integer> draw(int max, int size) {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return ThreadLocalRandom.current().ints(1, max)
                .distinct()
                .limit(size)
                .sorted()
                .boxed()
                .collect(Collectors.toList());
    }

```

### Lottery Async Service
```
 public CompletableFuture<List<Integer>> asyncDraw(int max, int size) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return ThreadLocalRandom.current().ints(1, max)
                    .distinct()
                    .limit(size)
                    .sorted()
                    .boxed()
                    .collect(Collectors.toList());
        });
    }

```
