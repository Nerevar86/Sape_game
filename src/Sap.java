import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sapgame.Box;
import sapgame.Coord;
import sapgame.Game;
import sapgame.Ranges;

public class Sap extends JFrame {
	private Game game;
	private JPanel panel;

	private JLabel label;
	private final int COLS = 9;
	private final int ROWS = 9;

	private final int BOMBS = 10;
	private final int IMAGE_SIZE = 50;

	public static void main(String[] args) {
		//всоздаем объект игры
		new Sap();
	}

	private Sap() {
		game = new Game(COLS, ROWS, BOMBS);
		game.start();
		//инициализируем картинки
		setImages();
		//инициализируем лейбл
		initLabel();
		//инициализируем панель
		initPanel();
		//инициализируем фрейм
		initFrame();

	}

	private void initLabel() {
		label = new JLabel("Приветствую, игрок!");
		add(label, BorderLayout.SOUTH);
	}

	private void initPanel() {
		//создаем панель окна
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				for (Coord coord : Ranges.getAllCoords()) {
					g.drawImage((Image) game.getBox(coord).image, coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
				}
			}
		};
		//устанавливаем адаптер мыши
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int x = e.getX() / IMAGE_SIZE;
				int y = e.getY() / IMAGE_SIZE;
				Coord coord = new Coord(x, y);
				if (e.getButton() == MouseEvent.BUTTON1) game.pressLeftButton(coord);
				if (e.getButton() == MouseEvent.BUTTON3) game.pressRightButton(coord);
				if (e.getButton() == MouseEvent.BUTTON2) game.start();
				label.setText(getMessage());
				panel.repaint();
			}
		});
		//устанавливаем размеры окна
		panel.setPreferredSize(new Dimension(Ranges.getSize().x * IMAGE_SIZE, Ranges.getSize().y * IMAGE_SIZE));
		add(panel);

	}

	private String getMessage() {

		switch (game.getState()) {
			case PLAYED:
				return "Сначала подумай, потом делай!";
			case BOMBED:
				return "Ты проиграл.БИГ БА-ДА-БУМ!";
			case WINNER:
				return "Поздравляю с победой!";
			default:
				return "";
		}
	}

	private void initFrame() {
		//закрыть программу
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		//заголовок программы
		setTitle("SAP GAME");
		//запрещаем менять замер окна
		setResizable(false);
		//устанавливаем видимость окна
		setVisible(true);
		//устанавливаем икноку приложения
		setIconImage(getImage("icon"));
		//вызываем для отображения
		pack();
		//устанавливаем окно по центру
		setLocationRelativeTo(null);

	}

	private Image getImage(String name) {
		String filename = "img/" + name + ".png";
		ImageIcon icon = new ImageIcon(getClass().getResource(filename));
		return icon.getImage();

	}


	private void setImages() {
		for (Box box : Box.values()) {
			box.image = getImage(box.name()
									.toLowerCase());
		}
	}
}



