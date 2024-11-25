import java.util.Scanner;

public class ServidorTodoListManager {

    static class Task {
        String description;
        int priority;
        boolean isCompleted;

        public Task(String description, int priority) {
            this.description = description;
            this.priority = priority;
            this.isCompleted = false;
        }

        public String toString() {
            return "Task: " + description + ", Priority: " + priority + ", Completed: " + (isCompleted ? "Yes" : "No");
        }

        public void markAsCompleted() {
            this.isCompleted = true;
        }
    }

    static class MaxHeap {
        Task[] heap;
        int size;
        int capacity;

        public MaxHeap(int capacity) {
            this.capacity = capacity;
            heap = new Task[capacity];
            size = 0;
        }

        void insert(Task task) {
            if (size >= capacity) {
                System.out.println("Heap is full!");
                return;
            }
            heap[size] = task;
            size++;
            heapifyUp(size - 1);
        }

        void heapifyUp(int index) {
            int parent = (index - 1) / 2;
            if (parent >= 0 && heap[parent].priority < heap[index].priority) {
                swap(parent, index);
                heapifyUp(parent);
            }
        }

        Task removeMax() {
            if (size == 0) {
                System.out.println("Heap is empty!");
                return null;
            }
            Task max = heap[0];
            heap[0] = heap[size - 1];
            size--;
            heapifyDown(0);
            return max;
        }

        void heapifyDown(int index) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int largest = index;

            if (leftChild < size && heap[leftChild].priority > heap[largest].priority) {
                largest = leftChild;
            }
            if (rightChild < size && heap[rightChild].priority > heap[largest].priority) {
                largest = rightChild;
            }

            if (largest != index) {
                swap(index, largest);
                heapifyDown(largest);
            }
        }

        void swap(int i, int j) {
            Task temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }

        void displayTasks() {
            if (size == 0) {
                System.out.println("No tasks available.");
                return;
            }
            System.out.println("To-Do List:");
            for (int i = 0; i < size; i++) {
                System.out.println(heap[i]);
            }
        }

        void removeCompletedTasks() {
            int i = 0;
            while (i < size) {
                if (heap[i].isCompleted) {
                    removeTaskAt(i);
                } else {
                    i++;
                }
            }
        }

        void removeTaskAt(int index) {
            if (index < 0 || index >= size) return;

            heap[index] = heap[size - 1];
            size--;
            heapifyDown(index);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MaxHeap taskHeap = new MaxHeap(10);

        System.out.println("---- To-Do List Manager Application ----\n");

        while (true) {
            System.out.println("\n1. Add Task");
            System.out.println("2. Remove Completed Tasks");
            System.out.println("3. Mark Task as Completed");
            System.out.println("4. Display All Tasks");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter task description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter task priority (higher number = higher priority): ");
                    int priority = scanner.nextInt();
                    taskHeap.insert(new Task(description, priority));
                    break;
                case 2:
                    taskHeap.removeCompletedTasks();
                    System.out.println("Completed tasks removed.");
                    break;
                case 3:
                    System.out.print("Enter the task description to mark as completed: ");
                    String taskDescription = scanner.nextLine();
                    markTaskAsCompleted(taskHeap, taskDescription);
                    break;
                case 4:
                    taskHeap.displayTasks();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void markTaskAsCompleted(MaxHeap heap, String description) {
        for (int i = 0; i < heap.size; i++) {
            if (heap.heap[i].description.equalsIgnoreCase(description)) {
                heap.heap[i].markAsCompleted();
                System.out.println("Task marked as completed: " + heap.heap[i]);
                return;
            }
        }
        System.out.println("Task not found.");
    }
}
