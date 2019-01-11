package sample.GridSelection;

public class Vector2D
{
	public float Magnitude;
	public float x;
	public float y;
	
	public Vector2D normilized;
	
	public Vector2D()
	{
		this.x = 0.0f;
		this.y = 0.0f;
		this.Magnitude = (float)Math.sqrt((Math.pow((double)x, 2) + Math.pow((double)y, 2)));
	}
	
	public Vector2D(float x, float y)
	{
		this.x = x;
		this.y = y;
		this.Magnitude = (float)Math.sqrt((Math.pow((double)x, 2) + Math.pow((double)y, 2)));
	}
	
	public Vector2D(float x, float y, float magnitude)
	{
		this.x = x;
		this.y = y;
		this.Magnitude = magnitude;
	}
	
	public Vector2D Normalized()
	{
		this.normilized = new Vector2D((this.x / Magnitude), (this.y / Magnitude), 1.0f);
		return this.normilized;
	}
	
	public void Normalize()
	{
		this.x = (this.x / Magnitude);
		this.y = (this.y / Magnitude);
		this.Magnitude = 1.0f;
	}
	
	public static Vector2D Normalize(Vector2D vector2D)
	{
		vector2D.x = (vector2D.x / vector2D.Magnitude);
		vector2D.y = (vector2D.y / vector2D.Magnitude);
		vector2D.Magnitude = 1.0f;
		
		return vector2D;
	}
	
	public static float Dot(Vector2D a, Vector2D b)
	{
		return (a.x * b.x) + (a.y * b.y);
	}

	public static float Cross(Vector2D a, Vector2D b)
	{
		return (a.x * b.y) - (a.y * b.x);
	}
	
	public static float Distance(Vector2D a, Vector2D b)
	{
		return (float)Math.sqrt(Math.pow((double)(a.x - b.x), 2) + Math.pow((double)(a.y - b.y), 2));
	}

	public static Vector2D Direction(Vector2D a, Vector2D b)
	{
		return Vector2D.Normalize(Vector2D.Subtract(b, a));
	}
	
	public static Vector2D Up()
	{
		return new Vector2D(0.0f, 1.0f, 1.0f);
	}
	
	public static Vector2D Down()
	{
		return new Vector2D(0.0f, -1.0f, 1.0f);
	}
	
	public static Vector2D Right()
	{
		return new Vector2D(1.0f, 0.0f, 1.0f);
	}
	
	public static Vector2D Left()
	{
		return new Vector2D(-1.0f, 0.0f, 1.0f);
	}
	
	public static Vector2D Zero()
	{
		return new Vector2D(0.0f, 0.0f, 0.0f);
	}
	
	public static Vector2D Add(Vector2D a, Vector2D b)
	{
		return new Vector2D((a.x + b.x), (a.y + b.y));
	}

	public static Vector2D Add(Vector2D a, float b)
	{
		return new Vector2D((a.x + b), (a.y + b));
	}
	
	public static Vector2D Subtract(Vector2D a, Vector2D b)
	{
		return new Vector2D((a.x - b.x), (a.y - b.y));
	}
	
	public static Vector2D Divide(Vector2D a, Vector2D b)
	{
		return new Vector2D((a.x / b.x), (a.y / b.y));
	}
	
	public static Vector2D Divide(Vector2D a, float b)
	{
		return new Vector2D((a.x / b), (a.y / b));
	}
	
	public static Vector2D Divide(float a, Vector2D b)
	{
		return new Vector2D((a / b.x), (a / b.y));
	}
	
	public static Vector2D Multiply(Vector2D a, Vector2D b)
	{
		return new Vector2D((a.x * b.x), (a.y * b.y));
	}
	
	public static Vector2D Multiply(Vector2D a, float b)
	{
		return new Vector2D((a.x * b), (a.y * b));
	}
	
	public static Vector2D Multiply(float a, Vector2D b)
	{
		return new Vector2D((a * b.x), (a * b.y));
	}

	public boolean equals(Vector2D vector2D)
	{
		return (this.x == vector2D.x && this.y == vector2D.y);
	}
}
