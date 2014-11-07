package percolation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Percolation extends JFrame{
	int[] id, sz;
	boolean[] blank;
	int num, vis;
	NewPanel np;
	private int n;
	private moveThread mt;
	private Thread t;
	private int s;
	
	public Percolation(){
		super("Percolation");
		setSize(600, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		s = 50;
		id = new int[s * s + 2];
		sz = new int[s * s + 2];
		num = 0;
		vis = 1;
		blank = new boolean[s*s];
		n = 0;
		
		for (int i = 0; i < s; i++){
			for (int j = 0; j < s; j++){
				id[i*s+j] = i*s+j;
				blank[i*s+j] = false;
				sz[i*s+j] = 1;
			}
		}
		
		id[s*s] = s*s;
		id[s*s+1] = s*s+1;
		
		for (int i = 0; i < s; i++){
			id[i] = s*s;
			id[s*s-i-1] = s*s+1;
		}
		 mt = new moveThread(n);
		mt.start();
		np = new NewPanel();
		 t = new Thread(np);
		t.start();
		add(np);
		setVisible(true);
		
	}
	
	public boolean find(int p, int q){
		return root(p) ==  root(q);
	}
	
	public int root(int i){
		while (id[i] != i && id[i] != 100 && i != 101){
			if (i>(s-1)){
			id[i] = id[id[i]];
			i = id[i];
			}
			else {
				id[i] = s*s;
				i = s*s;
			}
		}
		if ( i < s ){
			return s*s;
		}
		else {
			return id[i];
		}
	}
	
	public void union(int p, int q){
		int pid = id[p];
		int qid = id[q];
		if (pid == qid) return;
		if ( pid == s*s && q!= s*s && q != s*s+1){
			id[q] = pid;
			return;
		}
		else if (qid == s*s && p != s*s && p != s*s+1){
			id[p] = qid;
			return;
		}
		else if ((pid < qid ) && q!= s*s && q != s*s+1){
			id[q] = pid;
		}
		else if ((pid > qid) && p != s*s && p != s*s+1){
			id[p] = qid;
		}
	}
	
	class NewPanel extends JPanel implements Runnable{
		JButton jb;
		public NewPanel(){
		}
		
		@Override
		protected void paintComponent(Graphics g){
			super.paintComponent(g);

			for (int i = 0; i < s; i++){
				for (int j = 0; j < s; j++){
					g.setColor(Color.BLACK);
					g.drawRect(i*500/s, j*500/s, 500/s, 500/s);
					g.fillRect(i*500/s, j*500/s, 500/s, 500/s);
				}
			}
			vis = 0;
			if (num < (s*s-1)){
				g.setColor(Color.WHITE);
				if (!blank[n]){
					num++;
					blank[n] = true;
//					g.drawRect((n%s)*500/s, (n/10)*500/s, 500/s-2, 500/s-2);
//					g.fillRect((n%s)*500/s, (n/10)*500/s, 500/s-2, 500/s-2);
//					g.setColor(Color.BLACK);
//					g.drawString("" + n, (n%s)*500/s+100/s, (n/s)*500/s+250/s);
							

					if (n/s > 0) {
						if (blank[n-s]){
							union(n, n-s);
						}
					}
					if (n%s > 0) {
						if (blank[n-1]){
							union(n, n-1);
						}
					}
					if (n%s < (s-1)) {
						if (blank[n+1]){
							union(n,n+1);
						}
					}
					if (n/s < (s-1)) {
						if (blank[n+s]){
							union(n, n+s);
						}
					}
				}
				for (int i = 0; i < s*s; i++){
					if (blank[i]){
						if (i/s > 0) {
							if (blank[i-s]){
								union(i, i-s);
							}
						}
						if (i%s > 0) {
							if (blank[i-1]){
								union(i, i-1);
							}
						}
						if (i%s < (s-1)) {
							if (blank[i+1]){
								union(i,i+1);
							}
						}
						if (i/s < (s-1)) {
							if (blank[i+s]){
								union(i, i+s);
							}
						}
					}
					if (root(i) == s*s && blank[i]){
						g.setColor(Color.BLUE);
						g.drawRect((i%s)*500/s, (i/s)*500/s, 500/s-2, 500/s-2);
						g.fillRect((i%s)*500/s, (i/s)*500/s, 500/s-2, 500/s-2);
//						g.setColor(Color.BLACK);
//						g.drawString("" + i, (i%s)*500/s+100/s, (i/s)*500/s+250/s);
						
					}
					else if (root(i) != s*s && blank[i]){
						g.setColor(Color.WHITE);
						g.drawRect((i%s)*500/s, (i/s)*500/s, 500/s-2, 500/s-2);
						g.fillRect((i%s)*500/s, (i/s)*500/s, 500/s-2, 500/s-2);
//						g.setColor(Color.BLACK);
//						g.drawString("" + i, (i%s)*500/s+100/s, (i/s)*500/s+250/s);
						
					}
				}
				JFrame jframe = new JFrame();
			}
		}
		
		public void run(){
			while(true){
				repaint();
//				try {
//					Thread.sleep(10);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				for (int i = s*(s-1); i < s*s; i++){
					if (root(i) == s*s ){
						System.out.println("number of blanks: " + num);
						t.interrupt();
						mt.interrupt();
						JFrame jframe = new JFrame();
						JOptionPane.showMessageDialog(jframe, "" + num);
						return;
					}
				}
			}
		}
	}
	
	class moveThread extends Thread{
		private Random rand;
		public moveThread(int n){
			super();
			rand = new Random();
		}
		public void run(){
			while(true){
				n = rand.nextInt(s*s);
//				System.out.println("n in run " + n);
				try {
					sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for (int i = s*(s-1); i < s*s; i++){
					if (root(i) == s*s ){
						t.interrupt();
						mt.interrupt();
						return;
					}
				}
			}
		}
	}
	
	public static void main(String[] args){
		Percolation p = new Percolation();
	}
}
