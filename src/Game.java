import java.awt.event.KeyEvent;
import java.util.*;

public class Game
{
	private QuadTree m_scene;

	public Game()
	{
		m_scene = new QuadTree(
				new AABB(-100, -100, 100, 100)
				//new AABB(-0.1f, -0.1f, 0.1f, 0.1f)
				, 1);

		Entity test1 = new Entity(-0.1f, -0.1f, 0.1f, 0.1f);
		Entity test2 = new Entity(-0.3f, -0.3f, -0.2f, -0.2f);
		Entity test3 = new Entity(-1.1f, -1.1f, -0.9f, -0.9f);

		m_scene.Add(test1);
		m_scene.Add(test2);
		m_scene.Add(test3);
		//m_scene.Remove(test3);
	}

	public void AddEntity(Entity entity)
	{
		m_scene.Add(entity);
	}

	public void Update(Input input, float delta)
	{
		Set<Entity> entities = m_scene.GetAll();

		Iterator it = entities.iterator();
		while(it.hasNext())
		{
			((Entity)it.next()).Update(input, delta);
		}
	}

	public void Render(RenderContext target)
	{
		target.Clear((byte)0x00);

		Set<Entity> renderableEntities = 
			m_scene.QueryRange(new AABB(-1, -1, 1, 1));

		Iterator it = renderableEntities.iterator();
		while(it.hasNext())
		{
			((Entity)it.next()).Render(target);
		}
	}
}
