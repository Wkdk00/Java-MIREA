package p6;

import java.util.*;

interface FileComponent {
    void add(FileComponent component);
    void display(String indent);
}

class File implements FileComponent {
    private String name;

    public File(String name) {
        this.name = name;
    }

    @Override
    public void add(FileComponent component) {
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "📄 " + name);
    }
}

class Directory implements FileComponent {
    private String name;
    private List<FileComponent> children = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    @Override
    public void add(FileComponent component) {
        children.add(component);
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "📁 " + name);
        for (FileComponent child : children) {
            child.display(indent + "  "); // увеличиваем отступ
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Directory root = new Directory("root");
        Directory docs = new Directory("docs");
        Directory pictures = new Directory("pictures");

        docs.add(new File("readme.txt"));
        docs.add(new File("notes.pdf"));

        pictures.add(new File("photo.jpg"));
        pictures.add(new File("vacation.png"));

        root.add(docs);
        root.add(pictures);
        root.add(new File("config.xml"));

        root.display("");
    }
}

==================================================================================

  package p6;

import java.util.*;

class Student {
    private String name;
    public Student(String name) { this.name = name; }
    @Override
    public String toString() { return name; }
}

interface MyIterator {
    boolean hasNext();
    Student next();
}

class StudentList {
    private List<Student> students = new ArrayList<>();

    public void add(Student student) {
        students.add(student);
    }

    public MyIterator iterator() {
        return new StudentIterator();
    }

    private class StudentIterator implements MyIterator {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < students.size();
        }

        @Override
        public Student next() {
            if (!hasNext()) throw new NoSuchElementException();
            return students.get(index++);
        }
    }
}

public class Iter {
    public static void main(String[] args) {
        StudentList list = new StudentList();
        list.add(new Student("Георгий"));
        list.add(new Student("Иван"));
        list.add(new Student("Степан"));

        MyIterator it = list.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
