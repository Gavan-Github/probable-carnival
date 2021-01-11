import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;
public class MYWZQ extends JFrame
implements ActionListener
{
	JMenuBar jmb=new JMenuBar();
	JMenu jmyx=new JMenu("游戏");
	JMenu jmgy=new JMenu("关于");
	JMenuItem jmikj=new JMenuItem("开局");
	JMenuItem jmihq=new JMenuItem("悔棋");
	JMenuItem jmitc=new JMenuItem("退出");
	JMenuItem jmigy=new JMenuItem("关于我的五子棋");
	MJPanel mp=new MJPanel(this);
	public MYWZQ()
	{
		this.setJMenuBar(jmb);
		this.addKeyListener(
			new KeyAdapter()
			{
				public void keyReleased(KeyEvent e)
				{
					//e.getKeyChar()字符
					//e.getKeyCode()键盘的值，如a是65
					if(e.getKeyCode()==KeyEvent.VK_R)
					{
						jmihq.doClick();	
					}
				}
			}
			);
		jmb.add(jmyx);jmb.add(jmgy);
		jmyx.add(jmikj);jmyx.add(jmihq);jmyx.add(jmitc);
		jmgy.add(jmigy);
		jmikj.addActionListener(this);
		jmihq.addActionListener(this);
		jmitc.addActionListener(this);
		jmigy.addActionListener(this);
		this.setTitle("我自己的五子棋");
		this.add(mp);
		jmikj.setEnabled(false);
		jmihq.setEnabled(false);
		this.setBounds(100,100,60+mp.hl*mp.jj,130+mp.hl*mp.jj);
		this.setResizable(false);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jmitc)
		{
			System.exit(0);
		}else if(e.getSource()==jmihq)
		{
			mp.gamedata[mp.x][mp.y]=0;
			mp.who=!mp.who;
			jmihq.setEnabled(false);
			mp.repaint();
		}else if(e.getSource()==jmikj)
		{
			mp.who=true;
			mp.over=false;
			jmihq.setEnabled(false);
			mp.gamedata=new int[mp.hl+1][mp.hl+1];
			mp.t.cancel();
			mp.t=null;mp.mt=null;
			mp.repaint();
		}else if(e.getSource()==jmigy)
		{
			new GY(this);
		}
	}
	public static void main(String args[])
	{
		new MYWZQ();
	}
}



class MJPanel extends JPanel
{
	int hl=20;
	int jj=40;
	int d=25;
	int x=-1;
	int y=-1;
	int gamedata[][]=new int[hl+1][hl+1];
	int sl=0;
	boolean who=true;
	boolean over=false;
	String msg="游戏结束";
	Timer t=new Timer();
	MyTimerTask mt=new MyTimerTask(this);
	MYWZQ wzq;
	public MJPanel(MYWZQ wzq)
	{
		this.wzq=wzq;
		this.addMouseListener(
		new MouseAdapter()
		{	
			public void mousePressed(MouseEvent e)
			{
				if(over)
				{
					return;
				}
				x=(e.getX()-(30-jj/2))/jj;
				y=(e.getY()-(30-jj/2))/jj;
				if(gamedata[x][y]!=0)
				{
					return;
				}
				if(who)
				{
					gamedata[x][y]=1;
				}else
				{
					gamedata[x][y]=2;
				}
				who=!who;
				wzq.jmikj.setEnabled(true);
				wzq.jmihq.setEnabled(true);
				saoMiao();
				MJPanel.this.repaint();
			}		
		}			
		);
		
	}
	public void saoMiao()
	{
		int xq=x-4;
		int xz=x+4;
		if(xq<0)
		{
			xq=0;
		}
		if(xz>hl)
		{
			xz=hl;
		}
		int yq=y-4;
		int yz=y+4;
		if(yq<0)
		{
			yq=0;
		}
		if(yz>hl)
		{
			yz=hl;
		}
		saoMiaoH(xq,xz);
		sl=0;
		saoMiaoS(yq,yz);
		sl=0;
		saoMiaoFX(xq,xz,yq,yz);
		sl=0;
		saoMiaoZX(xq,xz,yq,yz);
		sl=0;
		
	}
	public boolean isOver()
	{
		if(sl>=5)
		{
			over=true;
			if(t==null)
			{
				t=new Timer();
			}
			if(mt==null)
			{
				mt=new MyTimerTask(this);
			}
			wzq.jmihq.setEnabled(false);
			t.scheduleAtFixedRate(mt,0,1000);
			return true;
		}else{
			return false;
		}
	}
	public void saoMiaoH(int xq,int xz)
	{
		for(int i=xq;i<=xz;i++)
		{
			if(gamedata[i][y]==gamedata[x][y])
			{
				sl++;
				if(isOver())
				{
					break;
				}
				
			}else{
				sl=0;
			}
		}

	}
	public void saoMiaoS(int yq,int yz)
	{
		for(int i=yq;i<=yz;i++)
		{
			if(gamedata[x][i]==gamedata[x][y])
			{
				sl++;
				if(isOver())
				{
					break;
				}
			}else{
				sl=0;
			}
		}

	}
	public void saoMiaoFX(int xq,int xz,int yq ,int yz)
	{
		for(int i=xq,j=yq;j<=yz&&i<=xz;i++,j++)
		{
			if(gamedata[i][j]==gamedata[x][y])
			{
				sl++;
				if(isOver())
				{
					break;
				}
			}else{
				sl=0;
			}
		}

	}
	public void saoMiaoZX(int xq,int xz,int yq ,int yz)
	{
		for(int i=xq,j=yz;j>=yq&&i<=xz;i++,j--)
		{
			if(gamedata[i][j]==gamedata[x][y])
			{
				sl++;
				System.out.println(sl);
				if(isOver())
				{
					break;
				}
			}else{
				sl=0;
				System.out.println(sl);
			}
		}
		System.out.println("--------");

	}
	public void paint(Graphics g)
	{
	//	g.drawLine(30,30,400,30);
	//	g.drawLine(30,30+20,400,30+20);
		if(who)
		{
			wzq.setTitle("我自己的五子棋  请黑方落子");	
		}else{
			wzq.setTitle("我自己的五子棋  请白方落子");
		}
		g.setColor(new Color(183,157,66));
		g.fillRect(0,0,hl*jj+60,hl*jj+60);
		g.setColor(Color.black);
		for(int i=0;i<=hl;i++)
		{
			g.drawLine(30,30+jj*i,30+hl*jj,30+jj*i); 
			g.drawLine(30+jj*i,30,30+jj*i,30+hl*jj);
			
		}
		//g.fillOval(30-d/2,30-d/2,d,d);
		for(int i=0;i<gamedata.length;i++)
		{
			for(int j=0;j<gamedata[i].length;j++)
			{
				if(gamedata[i][j]==1)
				{
					g.setColor(Color.black);
				}else if(gamedata[i][j]==2)
				{
					g.setColor(Color.white);	
				}else{
					continue;
				}
				Graphics2D g2d=(Graphics2D)g;
				//抗锯齿
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				g.fillOval(30-d/2+jj*i,30-d/2+jj*j,d,d);
			}
		}
		if(over)
		{
			g.setColor(Color.black);
			g.setFont(new Font("隶书",1,100));
			g.drawString(msg,(hl*jj)/4,(hl*jj+40)/2);
			g.setColor(Color.red);
			g.setFont(new Font("隶书",1,100));
			g.drawString(msg,(hl*jj)/4+3,(hl*jj+40)/2+3);
			if(who)
			{
				wzq.setTitle("我自己的五子棋  白方胜");	
			}else{
				wzq.setTitle("我自己的五子棋  黑方胜");
			}
		}
		
	
	}
}

class MyTimerTask extends TimerTask
{
	MJPanel mjp;
	boolean flag=true;
	public MyTimerTask(MJPanel mjp)
	{
		this.mjp=mjp;
	}
	public void run()
	{
		if(flag)
		{
			mjp.msg="游戏结束";
		}else{
			mjp.msg="";
		}
		flag=!flag;
		mjp.repaint();
	}
}

class GY extends JDialog
{
	Image img;
	JButton jok=new JButton("确定");
	MYWZQ wzq;
	public GY(MYWZQ wzq)
	{	
		super(wzq,"关于王晨最帅这件事",true);
		img=new ImageIcon("pyt.png").getImage();
		this.setLayout(null);
		this.wzq=wzq;
		this.add(jok);
		double x=wzq.getBounds().getX()+wzq.getBounds().getWidth()/4;
		double y=wzq.getBounds().getY()+wzq.getBounds().getHeight()/2;
		jok.setBounds(300,230,60,20);
		jok.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					GY.this.dispose();
				}
			}
			);
		this.setBounds((int)x,(int)y,673,345);
		this.setVisible(true);
	}
	public void paint(Graphics g)
	{
		g.drawImage(img,0,30,null);
	//	this.setVisible(true);
	}
}



			
			
			
			