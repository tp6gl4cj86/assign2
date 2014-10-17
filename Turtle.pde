class Turtle
{
	PImage imgTurtle_Partial;
	PImage imgTurtle_Half;
	PImage imgTurtle_Full;

	int state = 0;
	int state_speed;
	int state_timer;
	final int State_Partial = 0;
	final int State_Half    = 1;
	final int State_Full    = 2;
	final int State_Half2   = 3;

	int block;
	int speed;
	int turtle_w = 30;
	int turtle_h = 30;
	int center_x;
	int center_y;

	Turtle(int speed, int block, int state_speed, int x_offset)
	{
		initTurtle(speed, block, state_speed);
		center_x += x_offset;
	}

	Turtle(int speed, int block, int state_speed)
	{
		initTurtle(speed, block, state_speed);
	}

	void initTurtle(int speed, int block, int state_speed)
	{
		imgTurtle_Partial = loadImage("data/Turtle_Partial.png");
		imgTurtle_Half 	  = loadImage("data/Turtle_Half.png");
		imgTurtle_Full 	  = loadImage("data/Turtle_Full.png");

		this.state_speed = state_speed;
		state_timer = millis();

		this.speed = -speed;
		center_x = width + turtle_w/2;
		center_y = 60 + 55 + block * 45 + turtle_h/2;
	}

	void display()
	{
		if(state_timer + state_speed < millis())
		{
			state_timer = millis();
			goNextState();
		}

		center_x += speed;

		if(center_x + turtle_w/2 < 0)
		{
			center_x = width + turtle_w/2;
		}

		PImage img;
		switch (state)
		{
			case State_Partial : img = imgTurtle_Partial; break;
			case State_Half    :
			case State_Half2   : img = imgTurtle_Half;	  break;
			case State_Full    : img = imgTurtle_Full;	  break;
			default 		   : img = imgTurtle_Partial; break;
		}
		image(img, center_x - turtle_w/2, center_y - turtle_h/2, turtle_w, turtle_h);
	}

	void goNextState()
	{
		state++;
		if(state > State_Half2)
		{
			state = State_Partial;
		}
	}

}