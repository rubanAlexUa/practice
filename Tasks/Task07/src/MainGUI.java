import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Вікно програми з графіком опорів.
 * При запуску зчитує анотації через Reflection далі просто малює графік і
 * відповідає на кнопки.
 */
public class MainGUI extends JFrame {

    private Calc calc = Calc.getInstance();
    private StatsObserver stats = new StatsObserver();
    private SortObserver sort = new SortObserver();
    private JPanel chart;

    public MainGUI() {
        super("Графік провідників");

        if (stats.getClass().isAnnotationPresent(ObserverRole.class))
            System.out.println("stats роль: " + stats.getClass().getAnnotation(ObserverRole.class).value());
        if (sort.getClass().isAnnotationPresent(ObserverRole.class))
            System.out.println("sort роль: " + sort.getClass().getAnnotation(ObserverRole.class).value());

        setSize(700, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // панель графіку, перемальовується через repaint()
        chart = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                малюємо(g);
            }
        };
        chart.setBackground(Color.WHITE);

        JButton add = new JButton("Додати");
        JButton undo = new JButton("Відміна");
        JButton clear = new JButton("Очистити");

        add.setBackground(new Color(70, 130, 200));
        add.setForeground(Color.WHITE);
        undo.setBackground(new Color(200, 140, 40));
        undo.setForeground(Color.WHITE);
        clear.setBackground(new Color(200, 70, 70));
        clear.setForeground(Color.WHITE);

        add.addActionListener(e -> {
            calc.init(1 + Math.random() * 4,
                    Math.random() * 50, Math.random() * 50, Math.random() * 50);
            оновити();
        });
        undo.addActionListener(e -> {
            calc.removeLast();
            оновити();
        });
        clear.addActionListener(e -> {
            calc.clear();
            оновити();
        });

        JPanel низ = new JPanel();
        низ.add(add);
        низ.add(undo);
        низ.add(clear);

        add(chart, BorderLayout.CENTER);
        add(низ, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
    }

    /**
     * Передає список спостерігачам і оновлює графік.
     * Викликається після кожного натискання кнопки.
     */
    private void оновити() {
        List<Item> items = calc.getItems();
        stats.update(items);
        sort.update(items);
        chart.repaint();
    }

    /**
     * Малює стовпці для кожного Item.
     * Висота стовпця залежить від опору відносно максимального в колекції.
     * Червона лінія — середнє від StatsObserver.
     */
    private void малюємо(Graphics g) {
        List<Item> items = calc.getItems();
        int W = getWidth(), H = getHeight() - 80, pad = 40;

        g.setColor(new Color(245, 245, 245));
        g.fillRect(0, 0, W, H);

        // осі
        g.setColor(Color.DARK_GRAY);
        g.drawLine(pad, pad, pad, H - pad);
        g.drawLine(pad, H - pad, W - pad, H - pad);
        g.setFont(new Font("Arial", Font.PLAIN, 11));
        g.drawString("R (Ом)", 2, pad - 5);

        if (items.isEmpty()) {
            g.setColor(Color.GRAY);
            g.drawString("Поки що немає даних", W / 2 - 70, H / 2);
            return;
        }

        double maxR = items.stream().mapToDouble(Item::getResistance).max().orElse(1);
        int slot = (W - pad * 2) / items.size();
        int barW = Math.max(10, slot - 12);

        for (int i = 0; i < items.size(); i++) {
            double r = items.get(i).getResistance();
            int barH = (int) (r / maxR * (H - pad * 2));
            int x = pad + i * slot + (slot - barW) / 2;
            int y = H - pad - barH;

            g.setColor(new Color(70, 130, 200));
            g.fillRect(x, y, barW, barH);
            g.setColor(new Color(40, 90, 160));
            g.drawRect(x, y, barW, barH);

            g.setColor(Color.DARK_GRAY);
            g.setFont(new Font("Arial", Font.PLAIN, 10));
            g.drawString(String.format("%.0f", r), x + 2, y - 4);
            g.drawString("#" + (i + 1), x + barW / 2 - 6, H - pad + 14);
        }

        if (stats.avg > 0) {
            int avgY = H - pad - (int) (stats.avg / maxR * (H - pad * 2));
            g.setColor(Color.RED);
            g.drawLine(pad, avgY, W - pad, avgY);
            g.setFont(new Font("Arial", Font.BOLD, 11));
            g.drawString(String.format("avg: %.0f Ом", stats.avg), W - pad - 75, avgY - 4);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI().setVisible(true));
    }
}