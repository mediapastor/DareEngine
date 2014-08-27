public class RenderContext extends Bitmap
{
	public RenderContext(int width, int height)
	{
		super(width, height);
	}

	public void FillRect(float xCenter, float yCenter, 
			float width, float height,
			byte a, byte b, byte g, byte r)
	{
		float xStart = xCenter - width/2.0f;
		float yStart = yCenter - height/2.0f;
		float xEnd = xStart + width;
		float yEnd = yStart + height;

		float halfWidth   = GetWidth()/2.0f;
		float halfHeight  = GetHeight()/2.0f;

		float imageXStart = 0.0f;
		float imageYStart = 0.0f;
		float imageYStep  = 1.0f/(((yEnd * halfHeight) + halfHeight)
			   	-((yStart * halfHeight) + halfHeight));
		float imageXStep  = 1.0f/(((xEnd * halfWidth) + halfWidth)
			   	-((xStart * halfWidth) + halfWidth));

		if(xStart < -1.0f)
		{
			imageXStart = -((xStart + 1.0f)/(xEnd - xStart));
			xStart = -1.0f;
		}
		if(xStart > 1.0f)
		{
			imageXStart = -((xStart + 1.0f)/(xEnd - xStart));
			xStart = 1.0f;
		}
		if(yStart < -1.0f)
		{
			imageYStart = -((yStart + 1.0f)/(yEnd - yStart));
			yStart = -1.0f;
		}
		if(yStart > 1.0f)
		{
			imageYStart = -((yStart + 1.0f)/(yEnd - yStart));
			yStart = 1.0f;
		}

		xEnd = Util.Clamp(xEnd, -1.0f, 1.0f);
		yEnd = Util.Clamp(yEnd, -1.0f, 1.0f);
		
		xStart = (xStart * halfWidth) + halfWidth;
		yStart = (yStart * halfHeight) + halfHeight;
		xEnd   = (xEnd * halfWidth) + halfWidth;
		yEnd   = (yEnd * halfHeight) + halfHeight;

		FillRectInternal((int)xStart, (int)yStart, 
				(int)xEnd, (int)yEnd,
				a, b, g, r);
	}
	
	private void FillRectInternal(int xStart, int yStart, 
			int xEnd, int yEnd,
			byte a, byte b, byte g, byte r)
	{
		for(int j = yStart; j < yEnd; j++)
		{
			for(int i = xStart; i < xEnd; i++)
			{
				DrawPixel(i, j, a, b, g, r);
			}
		}
	}

//	public void DrawImage(Bitmap image, float xCenter, float yCenter, float width, float height)
//	{
//		//Begin clipping logic
//		float xStart = xCenter - width/2.0f;
//		float yStart = yCenter - height/2.0f;
//		float xEnd = xStart + width;
//		float yEnd = yStart + height;
//
//		float halfWidth   = GetWidth()/2.0f;
//		float halfHeight  = GetHeight()/2.0f;
//		float scaleFactor = halfWidth < halfHeight ? halfWidth : halfHeight;
//
//		float imageXStart = 0.0f;
//		float imageYStart = 0.0f;
//		float imageYStep  = 1.0f/(((yEnd * scaleFactor) + halfHeight)
//			   	-((yStart * scaleFactor) + halfHeight));
//		float imageXStep  = 1.0f/(((xEnd * scaleFactor) + halfWidth)
//			   	-((xStart * scaleFactor) + halfWidth));
//
//		if(xStart < -1.0f)
//		{
//			imageXStart = -((xStart + 1.0f)/(xEnd - xStart));
//			xStart = -1.0f;
//		}
//		if(xStart > 1.0f)
//		{
//			imageXStart = -((xStart + 1.0f)/(xEnd - xStart));
//			xStart = 1.0f;
//		}
//		if(yStart < -1.0f)
//		{
//			imageYStart = -((yStart + 1.0f)/(yEnd - yStart));
//			yStart = -1.0f;
//		}
//		if(yStart > 1.0f)
//		{
//			imageYStart = -((yStart + 1.0f)/(yEnd - yStart));
//			yStart = 1.0f;
//		}
//
//		xEnd = Util.Clamp(xEnd, -1.0f, 1.0f);
//		yEnd = Util.Clamp(yEnd, -1.0f, 1.0f);
//		
//		xStart = (xStart * scaleFactor) + halfWidth;
//		yStart = (yStart * scaleFactor) + halfHeight;
//		xEnd   = (xEnd * scaleFactor) + halfWidth;
//		yEnd   = (yEnd * scaleFactor) + halfHeight;
//		//End clipping logic
//
//		float imageY = imageYStart;
//
//		for(int y = (int)yStart; y < (int)yEnd; y++)
//		{
//			float imageX = imageXStart;
//			for(int x = (int)xStart; x < (int)xEnd; x++)
//			{
//				image.CopyNearest(this, x, y, imageX, imageY);
//				imageX += imageXStep;
//			}
//			imageY += imageYStep;
//		}
//	}

//	public void DrawLine(float xStart, float yStart, float xEnd, 
//			float yEnd, byte a, byte b, byte g, byte r)
//	{
//		float halfWidth   = GetWidth()/2.0f;
//		float halfHeight  = GetHeight()/2.0f;
//		float scaleFactor = halfWidth < halfHeight ? halfWidth : halfHeight;		
//		xStart = (xStart * scaleFactor) + halfWidth;
//		yStart = (yStart * scaleFactor) + halfHeight;
//		xEnd   = (xEnd * scaleFactor) + halfWidth;
//		yEnd   = (yEnd * scaleFactor) + halfHeight;
//
//		int x0 = (int)xStart;
//		int x1 = (int)xEnd;
//		int y0 = (int)yStart;
//		int y1 = (int)yEnd;
//
//		int deltaX = Math.abs(x1 - x0);
//		int deltaY = Math.abs(y1 - y0);
//		int error = deltaX - deltaY;
//		int xStep;
//		int yStep;
//		if(x0 < x1)
//			xStep = 1;
//		else
//			xStep = -1;
//		if(y0 < y1)
//			yStep = 1;
//		else
//			yStep = -1;
//		while(true)
//		{
//			SafeDrawPixel(x0, y0, a, b, g, r);
//			if(x0 == x1 && y0 == y1)
//				break;
//			int error2 = error * 2;
//			if(error2 > -deltaY)
//			{
//				error -= deltaY;
//				x0 += xStep;
//			}
//			if(x0 == x1 && y0 == y1)
//			{
//				SafeDrawPixel(x0, y0, a, b, g, r);
//				break;
//			}
//			if(error2 < deltaX)
//			{
//				error += deltaX;
//				y0 += yStep;
//			}
//		}
//	}
}
