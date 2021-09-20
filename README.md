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

### Observable  
```
    Observable observable = new TradeEventObservable();
        Observer slowObserver = (o, tradeEvent) -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.err.println("Slow observer: " + tradeEvent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Observer fastObserver = (o, tradeEvent) -> {
            System.err.println("Fast observer: " + tradeEvent);
        };

        observable.addObserver(slowObserver);
        observable.addObserver(fastObserver);

        var tradeEvents = List.of(
                new TradeEvent("ORCL", 100.0, 50),
                new TradeEvent("IBM", 70.0, 250),
                new TradeEvent("MSFT", 110.0, 500),
                new TradeEvent("ORCL", 102.0, 250),
                new TradeEvent("MSFT", 112.0, 2500)
        );
        tradeEvents.forEach(observable::notifyObservers);

```
