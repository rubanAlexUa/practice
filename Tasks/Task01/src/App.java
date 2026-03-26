public class App {
    public static void main(String[] args) {
        args = new String[] { "hello", "world", "123" };
        if (args.length != 0) {
            System.out.println("Argumenti: \n");
            for (int i = 0; i < args.length; i++) {
                System.out.println("Argument #" + i + " = " + args[i]);
            }
        } else {
            System.out.println("Neperedano zhodnih argumentiv ;(");
        }
    }
}
