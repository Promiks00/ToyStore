/*
Информация о проекте
Необходимо написать проект, для розыгрыша в магазине игрушек. Функционал должен содержать добавление новых игрушек и задания веса для выпадения игрушек.

Как сдавать проект
Для сдачи проекта необходимо создать отдельный общедоступный репозиторий(Github, gitlub, или Bitbucket). Разработку вести в этом репозитории,
использовать пул реквесты на изменения. Программа должна запускаться и работать, ошибок при выполнении программы быть не должно.
Программа, может использоваться в различных системах, поэтому необходимо разработать класс в виде конструктора.
Напишите класс-конструктор у которого принимает минимум 3 строки, содержащие три поля id игрушки, текстовое название и частоту выпадения игрушки
Из принятой строки id и частоты выпадения(веса) заполнить минимум три массива.
Используя API коллекцию: java.util.PriorityQueue добавить элементы в коллекцию.
Организовать общую очередь.
Вызвать Get 10 раз и записать результат в файл
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;

public class ToyLottery {
    PriorityQueue<Toy> toyQueue;

    public ToyLottery(String[] ids, String[] names, int[] frequencies) {
        if (ids.length != names.length || ids.length != frequencies.length) {
            throw new IllegalArgumentException("Размеры массивов не совпадают.");
        }
        toyQueue = new PriorityQueue<>((t1, t2) -> t2.frequency - t1.frequency);

        for (int i = 0; i < ids.length; i++) {
            toyQueue.offer(new Toy(ids[i], names[i], frequencies[i]));
        }
    }

    public void getToys(int times) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("winners.txt"))) {
            for (int i = 0; i < times; i++) {
                Toy toy = toyQueue.poll();
                if (toy != null) {
                    writer.write(toy.id + " " + toy.name + "\n");
                    toy.frequency--;
                    if (toy.frequency > 0) {
                        toyQueue.offer(toy);
                    }
                }
            }
            System.out.println("Результаты розыгрыша записаны в файл 'winners.txt'.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String[] ids = {"1", "2", "3"};
        String[] names = {"Кукла", "Машинка", "Мяч"};
        int[] frequencies = {5, 3, 2};

        ToyLottery lottery = new ToyLottery(ids, names, frequencies);
        lottery.getToys(10);
    }
}