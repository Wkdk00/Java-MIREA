package p6;

import java.util.LinkedList;
import java.util.Queue;
import java.util.StringJoiner;

class StackOnQueue {
    private final Queue<Integer> queue1;
    private final Queue<Integer> queue2;

    public StackOnQueue() {
        queue1 = new LinkedList<>();
        queue2 = new LinkedList<>();
    }

    public void push(int x) {
        // Всегда добавляем в queue2
        queue2.offer(x);
        // Перемещаем все элементы из queue1 в queue2
        while (!queue1.isEmpty()) {
            queue2.offer(queue1.poll());
        }
        // Теперь нужно "перекинуть" всё обратно в queue1, чтобы она содержала стек
        while (!queue2.isEmpty()) {
            queue1.offer(queue2.poll());
        }
        // Теперь queue1 содержит элементы в порядке стека (первый — вершина)
        // queue2 остаётся пустой
    }

    public int pop() {
        if (empty()) {
            throw new RuntimeException("Stack is empty");
        }
        return queue1.poll();
    }

    public int top() {
        if (empty()) {
            throw new RuntimeException("Stack is empty");
        }
        return queue1.peek();
    }

    public boolean empty() {
        return queue1.isEmpty();
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for (Integer item : queue1) {
            sj.add(item.toString());
        }
        return sj.toString();
    }
}

public class Stack {
    public static void main(String[] args) {
        StackOnQueue stack = new StackOnQueue();
        stack.push(1);
        stack.push(2);
        System.out.println(stack.top()); // 2
        System.out.println(stack.pop()); // 2
        System.out.println(stack); // [2, 1]
        System.out.println(stack.empty());
    }
}


============================================================================

  package p6;

interface Button {
    void draw();
}

interface Checkbox {
    void draw();
}

// Конкретные продукты для Windows
class WinButton implements Button {
    public void draw() { System.out.println("Windows Button"); }
}

class WinCheckbox implements Checkbox {
    public void draw() { System.out.println("Windows Checkbox"); }
}

// Конкретные продукты для macOS
class MacButton implements Button {
    public void draw() { System.out.println("MacOS Button"); }
}

class MacCheckbox implements Checkbox {
    public void draw() { System.out.println("MacOS Checkbox"); }
}

// Абстрактная фабрика
interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

// Конкретные фабрики
class WinGUIFactory implements GUIFactory {
    public Button createButton() { return new WinButton(); }
    public Checkbox createCheckbox() { return new WinCheckbox(); }
}

class MacGUIFactory implements GUIFactory {
    public Button createButton() { return new MacButton(); }
    public Checkbox createCheckbox() { return new MacCheckbox(); }
}

public class Main {
    public static void main(String[] args) {
        GUIFactory factory;

        String os = "ndows";

        if (os == "Windows") {
            factory = new WinGUIFactory();
        } else {
            factory = new MacGUIFactory();
        }

        Button button = factory.createButton();
        Checkbox checkbox = factory.createCheckbox();

        button.draw();
        checkbox.draw();
    }
}
