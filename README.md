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

### Observable  Sample
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

# Reactive Sample

```

class StudyReactiveProgrammingJavaSE9 {
    public static void main(String[] args) throws InterruptedException {
        System.err.println("Application is running...");
        SubmissionPublisher<TradeEvent> publisher = new SubmissionPublisher<>();
        Flow.Subscriber<TradeEvent> slowSubscriber = new AlgoTrader();
        Flow.Subscriber<TradeEvent> fastSubscriber = new SignalProcessor();
        publisher.subscribe(slowSubscriber);
        publisher.subscribe(fastSubscriber);
        var tradeEvents = List.of(
                new TradeEvent("ORCL", 100.0, 50),
                new TradeEvent("IBM", 70.0, 250),
                new TradeEvent("MSF", 110.0, 500),
                new TradeEvent("ORCL", 102.0, 250),
                new TradeEvent("MSFT", 112.0, 2500)
        );
        tradeEvents.forEach(publisher::submit);
        TimeUnit.SECONDS.sleep(30);
    }
}

class AlgoTrader implements Flow.Subscriber<TradeEvent>{

    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(1);
    }

    @Override
    public void onNext(TradeEvent event) {
        try {TimeUnit.SECONDS.sleep(3);} catch(InterruptedException e) { e.printStackTrace(); }
        System.err.println("Slow Subscriber: " + event);
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("Error: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("Stream is completed");
    }
}

class SignalProcessor implements Flow.Subscriber<TradeEvent>{

    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(1);
    }

    @Override
    public void onNext(TradeEvent event) {
        System.err.println("Fast Subscriber: " + event);
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("Error: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("Stream is completed");
    }
}

```
