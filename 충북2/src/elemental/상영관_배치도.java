package elemental;

import java.awt.Color;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import tools.Model;

public class 상영관_배치도 extends JFrame { 
	
	int[][] map = new int[9][9];
	int[][] prev = new int[9][9];
	int[] destinations = new int[6];
	int[] xdir = {0, 1, 0, -1};
	int[] ydir = {1, 0, -1, 0};
	boolean[][] visited = new boolean[9][9];
	JPanel jp = new JPanel();
	List<JPanel> nodeList = new ArrayList<JPanel>(); 
	
	public 상영관_배치도() {
		ui();
		getData();
		mapLoad(3);
		setVisible(true);
 	}
	
	private void mapLoad(int to) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				JPanel node = new JPanel();
				node.setBackground(getColor(map[i][j]));
				node.setBorder(new LineBorder(Color.black));
				nodeList.add(node);
				jp.add(node);
			}
		}
		
		
		for (int i = 0; i < 9; i++) {
			Arrays.fill(prev[i], -1);
		}
		
		Queue<Integer> queue = new LinkedList<Integer>();
		
		queue.add(72);
		visited[8][0] = true;
		nodeList.get(72).setBackground(Color.green);
		
		int dest = destinations[to-1];
		
		while(!queue.isEmpty()) {
			int current = queue.poll();
			int row = current/9;
			int col = current%9;
			
			if(current == dest) break;
			
			for (int i = 0; i < 4; i++) {
				int nextR = row + ydir[i];
				int nextC = col + xdir[i];
				
				if(nextC<0||nextC>8||nextR<0||nextR>8||visited[nextR][nextC]||map[nextR][nextC]==1)  continue;
				
				queue.add(nextR*9+nextC);
				prev[nextR][nextC] = current;
				visited[nextR][nextC] = true;
			}
		}
		
		List<Integer> route = new ArrayList<Integer>();
		
		int p = dest;
		while(p != 72) {
			route.add(p);
			int r = p/9;
			int c = p%9;
			p = prev[r][c];
		}
		Thread th = new Thread(()->{
			for (int i = route.size()-1; i > 0; i--) {
				nodeList.get(route.get(i)).setBackground(Color.red);
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			}
			
			for (int i = route.size()-1; i > 0; i--) {
				for (int j = 0; j < 5; j++) {
					nodeList.get(route.get(i)).setBackground(j%2==0?Color.RED:Color.white);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		th.start();
	}

	private Color getColor(int i) {
		return i==0? Color.white : i == 1 ? Color.DARK_GRAY : Color.blue;
	}

	private void getData() {
		try (var rs = Model.stmt.executeQuery("select * from srm")) {
			for (int i = 0; rs.next(); i++) {
				map[i/9][i%9] = rs.getInt(2);
				if(rs.getBoolean(3)) destinations[rs.getInt(3)-1] = i;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void ui() {
		setSize(600, 600);
		setDefaultCloseOperation(3);
		setLocationRelativeTo(null);
		
		jp.setLayout(new GridLayout(9, 9));
		add(jp);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(상영관_배치도::new);
	}
}
