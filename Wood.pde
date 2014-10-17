class Wood
{
	PImage img;
	public static final int MODE_Log_84x20  = 0; 
	public static final int MODE_Log_116x20 = 1; 
	public static final int MODE_Log_180x20 = 2; 
	int block;
	int speed;
	int wood_w;
	int wood_h = 20;
	int center_x;
	int center_y;

	Wood(int mode, int speed, int block, int x_offset)
	{
		initWood(mode, speed, block);
		center_x += x_offset;
	}

	Wood(int mode, int speed, int block)
	{
		initWood(mode, speed, block);
	}

	void initWood(int mode, int speed, int block)
	{
		switch (mode) 
		{
			case MODE_Log_84x20  : 
				img = loadImage("data/Log_84x20.png");  
				wood_w = 84;
				break;

			case MODE_Log_116x20 : 
				img = loadImage("data/Log_116x20.png"); 
				wood_w = 116;
				break;

			case MODE_Log_180x20  :
				 img = loadImage("data/Log_180x20.png");  
				 wood_w = 180;
				 break;
		}

		// this.speed = -speed;
		// center_x = width + wood_w/2;
		this.speed = speed;
		center_x = -wood_w/2;
		center_y = 60 + 55 + block * 45 + wood_h/2;
	}

	void display()
	{
		center_x += speed;

		if(center_x + wood_w/2 < 0)
		{
			center_x = width + wood_w/2;
		}
		else if (center_x - wood_w/2 > width)
		{
			center_x = -wood_w/2;
		}

		image(img, center_x - wood_w/2, center_y - wood_h/2, wood_w, wood_h);
	}
}