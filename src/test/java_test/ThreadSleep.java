
public class ThreadSleep {
    public static void main(String[] args) throws InterruptedException {
        // запоминаем текущее время в миллисекундах
        long start = System.currentTimeMillis();
        // останавливаем основной поток программы на 2000 миллисекунд (2 секунды)
        Thread.sleep(2000);
        System.out.println("Выполнение программы приостановлено на = " + (System.currentTimeMillis() - start));
    }
}