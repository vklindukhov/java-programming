//--------------------------------------------------------------------------
//I M P A C T	  Version 2.1
//
//(C)  COPYRIGHT International Business Machines Corp. 1996 
//
//by John Henckel, henckel@vnet.ibm.com              June 1996
//
//This is a little java program that simulates the interactions of balls.
//You can grab the balls with your mouse and drag them or throw them.  You
//can control the forces of viscosity and gravity.  You can make the walls
//be bouncy, or wrap-around.  You can turn on trace and make interesting
//designs.  Have fun with it!
//
//The public class (Impact) can be executed as either an applet from html
//or as a standalone application using the java interpreter.
//
//If you have any comments about Impact, or if you make any modifications
//to the program, please send me a note.
//
//---------------------------------------------------------------------------
//Permission to use, copy, modify, distribute and sell this software
//and its documentation for any purpose is hereby granted without fee,
//provided that the above copyright notice appear in all copies and
//that both that copyright notice and this permission notice appear
//in supporting documentation.
//
//This program has not been thoroughly tested under all conditions.  IBM,
//therefore, cannot guarantee or imply reliability, serviceability, or
//function of this program.  The Program contained herein is provided
//'AS IS'.  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
//PARTICULAR PURPOSE ARE EXPRESSLY DISCLAIMED.
//IBM shall not be liable for any lost revenue, lost profits or other
//consequential damages caused by the Program even if advised of the
//possibility of such damages.  Furthermore, IBM shall not be liable for
//any delays, losses or any other damages which may result from
//furnishing the Program, even if advised of the possibility of such
//damages.

import java.awt.*;

//-------------------------------------------------------------------
//This is the main applet class

@SuppressWarnings("deprecation")
public class Impact extends java.applet.Applet {
	private static final long serialVersionUID = -7249193018220898138L;
	MainWindow b;

	public void init() {
		add(new Button("Start"));
	}

	public boolean action(Event e, Object arg) {
		if (e.target instanceof Button && ((String) arg).equals("Start")) {
			b = new MainWindow("Impact Simulator", true);
			b.resize(640, 480);
			b.show();
			b.start();
		}
		return true;
	}

	public void stop() {
		if (b != null)
			b.stop();
	}

	// These methods allow the applet to also run as an application.

	public static void main(String args[]) {
		new Impact().begin();
	}

	private void begin() {
		b = new MainWindow("Impact Simulator", false);
		b.resize(640, 480);
		b.show();
		b.start();
	}
}

// -------------------------------------------------------------------
// This class controls the main window

@SuppressWarnings("deprecation")
class MainWindow extends Frame {
	private static final long serialVersionUID = -5938331160250205967L;
	Animator anim;
	Ticker tick;
	Thread anim_thread;
	Thread tick_thread;
	Dialog db; // dialog box for new ball
	boolean is_applet;

	// This method creates layout and main objects.

	MainWindow(String title, boolean isapp) {
		super(title);
		is_applet = isapp;
		Panel p = new Panel(); // control panel
		Label n = new Label("Impact 2.1");
		p.setLayout(new GridLayout(0, 1)); // vertical layout
		p.setFont(new Font("Helvetica", Font.PLAIN, 10));
		n.setFont(new Font("TimesRoman", Font.BOLD, 16));
		p.add(n);
		p.add(new Button("add ball"));
		p.add(new Button("delete ball"));
		p.add(new Button("quit"));
		tick = new Ticker(p); // pace maker for the animator
		anim = new Animator(tick, p);
		setLayout(new BorderLayout(2, 2));
		add("Center", anim);
		add("West", p);

		TextField t;
		db = new Dialog(this, "New Ball", false);
		db.setLayout(new GridLayout(8, 2, 2, 0));
		db.add(new Label("Mass"));
		db.add(t = new TextField(13));
		t.setText("64");
		db.add(new Label("Radius"));
		db.add(t = new TextField(13));
		t.setText("0");
		db.add(new Label("Color"));
		db.add(t = new TextField(13));
		t.setText("any");
		db.add(new Label("X Loc"));
		db.add(t = new TextField(13));
		t.setText("100");
		db.add(new Label("Y Loc"));
		db.add(t = new TextField(13));
		t.setText("100");
		db.add(new Label("X Vel"));
		db.add(t = new TextField(13));
		t.setText("2");
		db.add(new Label("Y Vel"));
		db.add(t = new TextField(13));
		t.setText("1");
		db.add(new Button("add"));
		db.add(new Button("close"));
		db.resize(200, 300);
		db.setResizable(true);
	}

	// This starts the threads.

	public void start() {
		if (anim_thread == null) {
			anim_thread = new Thread(anim);
			anim_thread.start(); // start new thread
		}
		if (tick_thread == null) {
			tick_thread = new Thread(tick);
			tick_thread.start(); // start new thread
		}
	}

	// This stops the threads.

	public void stop() {
		if (anim_thread != null) {
			anim_thread.stop(); // kill the thread
			anim_thread = null; // release object
		}
		if (tick_thread != null) {
			tick_thread.stop(); // kill the thread
			tick_thread = null; // release object
		}
	}

	// This handles user input events.

	public boolean action(Event e, Object arg) {
		if (e.target instanceof Button) {
			if (((String) arg).equals("clean"))
				anim.clearAll = true;
			else if (((String) arg).equals("delete ball"))
				anim.delBall();
			else if (((String) arg).equals("close"))
				db.hide();
			else if (((String) arg).equals("add ball"))
				db.show();
			else if (((String) arg).equals("quit")) {
				stop();
				db.hide();
				hide(); // I don't know if all this is necessary.
				removeAll();
				dispose();
				if (!is_applet)
					System.exit(0);
			} else if (((String) arg).equals("add"))
				anim.addBall(new Ball(get_field(db, 1), get_field(db, 3),
						get_color(db, 5), get_field(db, 7), anim.ysize
								- get_field(db, 9), get_field(db, 11),
						-get_field(db, 13)));
			else if (((String) arg).equals("drift"))
				anim.zeroMomentum();
			else if (((String) arg).equals("center"))
				anim.centerMass();
			else if (((String) arg).equals("orbit"))
				anim.orbit();
			else
				return false;
			return true;
		}
		return false;
	}

	// These are little utility methods to get a dialog data

	private double get_field(Dialog d, int i) {
		TextComponent t = (TextComponent) d.getComponent(i);
		if (t instanceof TextComponent)
			return Double.valueOf(t.getText()).doubleValue();
		return 1;
	}

	private Color get_color(Dialog d, int i) {
		TextComponent t = (TextComponent) d.getComponent(i);
		if (!(t instanceof TextComponent))
			return Color.cyan;
		if (t.getText().equals("red"))
			return Color.red;
		if (t.getText().equals("orange"))
			return Color.orange;
		if (t.getText().equals("white"))
			return Color.white;
		if (t.getText().equals("gray"))
			return Color.gray;
		if (t.getText().equals("pink"))
			return Color.pink;
		if (t.getText().equals("black"))
			return Color.black;
		if (t.getText().equals("yellow"))
			return Color.yellow;
		if (t.getText().equals("green"))
			return Color.green;
		if (t.getText().equals("blue"))
			return Color.blue;
		if (t.getText().equals("magenta"))
			return Color.magenta;
		if (t.getText().equals("cyan"))
			return Color.cyan;
		return new Color(Color.HSBtoRGB((float) Math.random(), 1.0F, 1.0F));
	}
}

// -------------------------------------------------------------------
// This class performs the animation in the main canvas.

@SuppressWarnings("deprecation")
class Animator extends Canvas implements Runnable {
	private static final long serialVersionUID = -7650468743616950373L;
	final int max = 100;
	int num; // number of balls
	int cur, mx, my; // current ball and mouse x y
	Ball[] ball = new Ball[max]; // array of balls
	Ticker tick;
	boolean clearAll;

	// The following are some "physical" properties. Each property
	// has a value and a control. The values are updated once per
	// animation loop (this is for efficiency).

	public double g, mg, f, r;
	public boolean trc, col, mu, wr, sm;
	public int xsize, ysize;
	Scrollbar grav, mgrav, fric, rest;
	Checkbox trace, collide, mush, wrap, smooth;

	// The ctor method creates initial objects.

	Animator(Ticker t, Panel p) {
		tick = t;
		setBackground(Color.black);
		grav = new Scrollbar(Scrollbar.HORIZONTAL, 0, 1, 0, 20);
		p.add(new Label("v-gravity"));
		p.add(grav);
		mgrav = new Scrollbar(Scrollbar.HORIZONTAL, 10, 1, 0, 20);
		p.add(new Label("m-gravity"));
		p.add(mgrav);
		fric = new Scrollbar(Scrollbar.HORIZONTAL, 0, 1, 0, 20);
		p.add(new Label("viscosity"));
		p.add(fric);
		rest = new Scrollbar(Scrollbar.HORIZONTAL, 17, 1, 0, 20);
		p.add(new Label("restitution"));
		p.add(rest);
		trace = new Checkbox("trace"); // initially false
		p.add(trace);
		collide = new Checkbox("collide");
		collide.setState(true);
		p.add(collide);
		mush = new Checkbox("mush");
		p.add(mush);
		wrap = new Checkbox("wrap");
		p.add(wrap);
		smooth = new Checkbox("flicker");
		p.add(smooth);
		p.add(new Button("orbit"));
		p.add(new Button("drift"));
		p.add(new Button("center"));
		p.add(new Button("clean"));

		// Add two balls

		addBall(new Ball(100, 10, Color.yellow, 100, 100, 0.48, 0));
		addBall(new Ball(16, 4, Color.cyan, 100, 50, -3, 0));
		my = -17; // mouse up
	}

	// The run method updates the locations of the balls.

	public void run() {
		while (true) {
			readControls();
			for (int i = 0; i < num - 1; ++i)
				for (int j = i + 1; j < num; ++j)
					if (ball[i].interact(ball[j], this)) { // true = delete b[j]
						ball[j] = ball[num - 1];
						ball[--num] = null;
						clearAll = true;
					}
			for (int i = 0; i < num; ++i)
				ball[i].update(this);
			if (my != -17) { // mouse is dragging
				ball[cur].vx = (mx - ball[cur].x) / 10;
				ball[cur].vy = (my - ball[cur].y) / 10;
			}
			repaint();
			tick.poll(); // wait for tick
		}
	}

	// Read user input and cache in local vars (for efficiency)

	private void readControls() {
		g = grav.getValue() / 100.0;
		mg = mgrav.getValue() / 100.0;
		f = fric.getValue() / 20.0;
		r = rest.getValue() / 20.0;
		trc = trace.getState();
		col = collide.getState();
		mu = mush.getState();
		wr = wrap.getState();
		sm = !smooth.getState();
		xsize = size().width;
		ysize = size().height;
	}

	// The paint method displays objects.

	public void paint(Graphics g) {
		for (int i = 0; i < num; ++i)
			ball[i].draw(g, sm && !trc);
	}

	// If trace is on, then do not erase before painting.

	public void update(Graphics g) {
		if (trc || sm) {
			if (clearAll)
				g.clearRect(0, 0, xsize, ysize);
			paint(g);
		} else
			super.update(g);
		clearAll = false;
	}

	// These add and delete balls.

	public void addBall(Ball b) {
		if (num < max)
			ball[num++] = b;
	}

	public void delBall() {
		if (num > 0) {
			if (cur < num)
				ball[cur] = ball[num - 1];
			ball[num--] = null;
		}
		clearAll = true;
	}

	// The following mouse methods allow you to drag balls.

	public boolean mouseDown(Event e, int x, int y) {
		cur = nearestBall(x, y, num);
		mx = x;
		my = y;
		return true;
	}

	public boolean mouseDrag(Event e, int x, int y) {
		mx = x;
		my = y;
		return true;
	}

	public boolean mouseUp(Event e, int x, int y) {
		my = -17; // this magic number means that the mouse is up
		return true;
	}

	// This returns the index of the ball nearest to x,y
	// excluding ex. (wrap is not taken into account).

	int nearestBall(int x, int y, int ex) {
		double d = 1e20, t;
		int j = 0;
		for (int i = 0; i < num; ++i) {
			t = Ball.hypot(x - ball[i].x, y - ball[i].y);
			if (t < d && i != ex) {
				d = t;
				j = i;
			}
		}
		return j;
	}

	// This causes the current ball to orbit the other ball nearest to it.

	void orbit() {
		int a = cur, b;
		if (a >= num)
			a = num - 1;
		if (a < 0)
			return;
		b = nearestBall((int) ball[a].x, (int) ball[a].y, a);
		if (b == a)
			return;

		double d, m, dx, dy, t;
		d = Ball.hypot(ball[a].x - ball[b].x, ball[a].y - ball[b].y);
		if (d < 1e-20)
			return; // too close
		m = ball[a].m + ball[b].m;
		if (m < 1e-100)
			return; // too small
		t = Math.sqrt(mg / m) / d;
		dy = t * (ball[a].x - ball[b].x); // perpedicular direction vector
		dx = t * (ball[b].y - ball[a].y);

		ball[a].vx = ball[b].vx - dx * ball[b].m;
		ball[a].vy = ball[b].vy - dy * ball[b].m;
		ball[b].vx += dx * ball[a].m;
		ball[b].vy += dy * ball[a].m;
	}

	// This adjusts the frame of reference so that the total momentum becomes
	// zero.

	void zeroMomentum() {
		double mx = 0, my = 0, M = 0;
		for (int i = 0; i < num; ++i) {
			mx += ball[i].vx * ball[i].m;
			my += ball[i].vy * ball[i].m;
			M += ball[i].m;
		}
		if (M != 0)
			for (int i = 0; i < num; ++i) {
				ball[i].vx -= mx / M;
				ball[i].vy -= my / M;
			}
	}

	// This adjusts the centroid to the center of the canvas.
	// Note, the "while" loops here could be simply use %= but some
	// interpreters have bugs with %=.

	void centerMass() {
		double x, y, cx = 0, cy = 0, M = 0;
		for (int i = 0; i < num; ++i) {
			x = ball[i].x;
			y = ball[i].y;
			if (wr) { // if wrap, convert the top 1/4 to negative
				if (x > xsize * 0.75)
					x -= xsize;
				if (y > ysize * 0.75)
					y -= ysize;
			}
			cx += ball[i].x * ball[i].m;
			cy += ball[i].y * ball[i].m;
			M += ball[i].m;
		}
		if (M != 0)
			for (int i = 0; i < num; ++i) {
				ball[i].x += xsize / 2 - cx / M;
				ball[i].y += ysize / 2 - cy / M;
				while (ball[i].x < 0)
					ball[i].x += xsize;
				while (ball[i].x > xsize)
					ball[i].x -= xsize;
				while (ball[i].y < 0)
					ball[i].y += ysize;
				while (ball[i].y > ysize)
					ball[i].y -= ysize;
			}
	}
}

// -------------------------------------------------------------------
// The Ball class

class Ball {
	double x, y; // location
	double z; // radius
	double vx, vy; // velocity
	Color c; // color
	double m; // mass
	boolean hit; // scratch field
	double ox, oy; // old location (for smooth redraw)
	final double vmin = 1e-20; // a weak force to prevent overlapping

	Ball(double mass, double radius, Color color, double px, double py,
			double sx, double sy) {
		m = mass;
		z = radius - 0.5;
		c = color;
		if (z < 0.5)
			z = Math.min(Math.sqrt(Math.abs(m)), Math.min(px, py));
		if (z < 0.5)
			z = 0.5;
		x = px;
		y = py;
		vx = sx;
		vy = sy;
	}

	// This updates a ball according to the physical universe.
	// The reason I exempt a ball from gravity during a hit is
	// to simulate "at rest" equilibrium when the ball is resting
	// on the floor or on another ball.

	public void update(Animator a) {
		x += vx;
		if (x + z > a.xsize)
			if (a.wr)
				x -= a.xsize;
			else {
				if (vx > 0)
					vx *= a.r; // restitution
				vx = -Math.abs(vx) - vmin; // reverse velocity
				hit = true;
				// Check if location is completely off screen
				if (x - z > a.xsize)
					x = a.xsize + z;
			}
		if (x - z < 0)
			if (a.wr)
				x += a.xsize;
			else {
				if (vx < 0)
					vx *= a.r;
				vx = Math.abs(vx) + vmin;
				hit = true;
				if (x + z < 0)
					x = -z;
			}
		y += vy;
		if (y + z > a.ysize)
			if (a.wr)
				y -= a.ysize;
			else {
				if (vy > 0)
					vy *= a.r;
				vy = -Math.abs(vy) - vmin;
				hit = true;
				if (y - z > a.ysize)
					y = a.ysize + z;
			}
		if (y - z < 0)
			if (a.wr)
				y += a.ysize;
			else {
				if (vy < 0)
					vy *= a.r;
				vy = Math.abs(vy) + vmin;
				hit = true;
				if (y + z < 0)
					y = -z;
			}
		if (a.f > 0 && m != 0) { // viscosity
			double t = 100 / (100 + a.f * hypot(vx, vy) * z * z / m);
			vx *= t;
			vy *= t;
		}
		if (!hit)
			vy += a.g; // if not hit, exert gravity
		hit = false; // reset flag
	}

	// This computes the interaction of two balls, either collision
	// or gravitational force.
	// Returns TRUE if ball b should be deleted.

	public boolean interact(Ball b, Animator a) {
		double p = b.x - x;
		double q = b.y - y;
		if (a.wr) { // wrap around, use shortest distance
			if (p > a.xsize / 2)
				p -= a.xsize;
			else if (p < -a.xsize / 2)
				p += a.xsize;
			if (q > a.ysize / 2)
				q -= a.ysize;
			else if (q < -a.ysize / 2)
				q += a.ysize;
		}
		double h2 = p * p + q * q;
		if (a.col) { // collisions enabled
			double h = Math.sqrt(h2);
			if (h < z + b.z) { // HIT
				hit = b.hit = true;
				if (a.mu) { // mush together
					if (m < b.m)
						c = b.c; // color
					if (b.m + m != 0) {
						double t = b.m / (b.m + m);
						x += p * t;
						y += q * t;
						vx += (b.vx - vx) * t;
						vy += (b.vy - vy) * t;
						if (x > a.xsize)
							x -= a.xsize;
						if (x < 0)
							x += a.xsize;
						if (y > a.ysize)
							y -= a.ysize;
						if (y < 0)
							y += a.ysize;
					}
					m += b.m;
					z = hypot(b.z, z);
					return true; // delete b
				} else if (h > 1e-10) {
					// Compute the elastic collision of two balls.
					// The math involved here is not for the faint of heart!

					double v1, v2, r1, r2, s, t, v;
					p /= h;
					q /= h; // normalized impact direction
					v1 = vx * p + vy * q;
					v2 = b.vx * p + b.vy * q; // impact velocity
					r1 = vx * q - vy * p;
					r2 = b.vx * q - b.vy * p; // remainder velocity
					if (v1 < v2)
						return false;
					s = m + b.m; // total mass
					if (s == 0)
						return false;

					t = (v1 * m + v2 * b.m) / s;
					v = t + a.r * (v2 - v1) * b.m / s;
					vx = v * p + r1 * q;
					vy = v * q - r1 * p;
					v = t + a.r * (v1 - v2) * m / s;
					b.vx = v * p + r2 * q;
					b.vy = v * q - r2 * p;
				}
			}
		}
		if (a.mg != 0 && h2 > 1e-10 && !hit && !b.hit) { // gravity is enabled
			double dv;
			dv = a.mg * b.m / h2;
			vx += dv * p;
			vy += dv * q;
			dv = a.mg * m / h2;
			b.vx -= dv * p;
			b.vy -= dv * q;
		}
		return false;
	}

	public void draw(Graphics g, boolean sm) {
		if (!sm) { // if not smooth, always draw
			g.setColor(c);
			g.drawOval((int) (x - z), (int) (y - z), (int) (2 * z),
					(int) (2 * z));
			ox = x;
			oy = y; // save new location
		}
		// For smooth mode, only redraw if ball moved a pixel or more.

		else if (Math.abs(x - ox) > 1 || Math.abs(y - oy) > 1) {
			g.setColor(Color.black);
			g.drawOval((int) (ox - z), (int) (oy - z), (int) (2 * z),
					(int) (2 * z));
			ox = x;
			oy = y; // save new location
			g.setColor(c);
			g.drawOval((int) (x - z), (int) (y - z), (int) (2 * z),
					(int) (2 * z));
		}
	}

	static double hypot(double x, double y) {
		return Math.sqrt(x * x + y * y);
	}
}

// -------------------------------------------------------------------
// To use the Ticker class, create an object and create a thread
// to run it. Thereafter, any other thread can use it as a
// pacemaker.

class Ticker implements Runnable {
	int t; // ticks elapsed
	Checkbox pause; // pause ticker
	Scrollbar speed; // animation rate

	Ticker(Panel p) {
		speed = new Scrollbar(Scrollbar.HORIZONTAL, 6, 1, 0, 7);
		p.add(new Label("speed"));
		p.add(speed);
		pause = new Checkbox("pause"); // initially false
		p.add(pause);
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(dura());
			} catch (InterruptedException e) {
			}
			t += pause.getState() ? 0 : 1;
		}
	}

	void poll(int eat) { // poll for non-zero tick
		while (t == 0) {
			try {
				Thread.sleep(dura() / 10 + 1);
			} catch (InterruptedException e) {
			}
		}
		if (eat > t)
			t = 0;
		else
			t -= eat;
	}

	void poll() {
		poll(30000);
	}

	int dura() {
		return 1 << (10 - speed.getValue());
	}
}
