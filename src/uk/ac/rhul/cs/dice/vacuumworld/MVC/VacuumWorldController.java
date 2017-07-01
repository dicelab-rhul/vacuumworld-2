package uk.ac.rhul.cs.dice.vacuumworld.MVC;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import uk.ac.rhul.cs.dice.starworlds.MVC.AbstractViewController;
import uk.ac.rhul.cs.dice.starworlds.utils.Pair;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorld;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldUniverse;
import uk.ac.rhul.cs.dice.vacuumworld.MVC.view.VacuumWorldView;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldAgent;
import uk.ac.rhul.cs.dice.vacuumworld.bodies.Dirt;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Orientation;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;

public class VacuumWorldController extends AbstractViewController {

	private VacuumWorldView view;

	public VacuumWorldController(VacuumWorldView view,
			VacuumWorldUniverse universe) {
		super(universe);
		this.view = view;
		this.view.setOnStart(new UniverseOnStart());
		universe.addObserver(view);
	}

	public void start(Integer dimension,
			Map<Position, List<Pair<BodyColor, Orientation>>> entitymap) {
		Collection<VacuumWorldAgent> agents = new ArrayList<>();
		Collection<Dirt> dirts = new ArrayList<>();
		entitymap.forEach((position, list) -> list.forEach((pair) -> {
			if (pair.getSecond() == null) {
				// do dirt
				Dirt d = new Dirt(pair.getFirst());
				d.getAppearance().setPosition(position);
				dirts.add(d);
			} else if (pair.getFirst() == BodyColor.USER) {
				// do user
				VacuumWorldAgent a = VacuumWorld
						.createVacuumWorldAgent(VacuumWorld.USERMIND);
				a.getAppearance().setColor(pair.getFirst());
				a.getAppearance().setOrientation(pair.getSecond());
				a.getAppearance().setPosition(position);
				agents.add(a);
			} else {
				VacuumWorldAgent a = VacuumWorld
						.createVacuumWorldAgent(VacuumWorld.DEFAULTMIND);
				a.getAppearance().setColor(pair.getFirst());
				a.getAppearance().setOrientation(pair.getSecond());
				a.getAppearance().setPosition(position);
				agents.add(a);
			}
		}));
		this.getUniverse().initialiseGrid(dimension, agents, dirts);
		view.doContent(this.getUniverse());
		Thread t = new Thread(this.getUniverse());
		t.start();
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("update");
	}

	public class UniverseOnStart {
		public void start(Integer dimension,
				Map<Position, List<Pair<BodyColor, Orientation>>> entitymap) {
			VacuumWorldController.this.start(dimension, entitymap);
		}
	}

	@Override
	public VacuumWorldUniverse getUniverse() {
		return (VacuumWorldUniverse) super.getUniverse();
	}

}
