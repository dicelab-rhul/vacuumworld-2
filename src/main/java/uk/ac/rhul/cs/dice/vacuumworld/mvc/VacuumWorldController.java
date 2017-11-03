package uk.ac.rhul.cs.dice.vacuumworld.mvc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Observable;

import uk.ac.rhul.cs.dice.starworlds.MVC.AbstractViewController;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.AbstractAvatarMind;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorld;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorldUniverse;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAction;
import uk.ac.rhul.cs.dice.vacuumworld.agent.VacuumWorldAgent;
import uk.ac.rhul.cs.dice.vacuumworld.agent.user.avatar.VacuumWorldAvatar;
import uk.ac.rhul.cs.dice.vacuumworld.agent.user.avatar.VacuumWorldAvatarLink;
import uk.ac.rhul.cs.dice.vacuumworld.appearances.VacuumWorldAgentAppearance;
import uk.ac.rhul.cs.dice.vacuumworld.bodies.Dirt;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.Position;
import uk.ac.rhul.cs.dice.vacuumworld.mvc.view.VacuumWorldView;
import uk.ac.rhul.cs.dice.vacuumworld.utilities.AgentMindFinder;
import uk.ac.rhul.cs.dice.vacuumworld.utilities.LogUtils;
import uk.ac.rhul.cs.dice.vacuumworld.utilities.ClassFinder.ClassFinderException;
import uk.ac.rhul.cs.dice.vacuumworld.utilities.VacuumWorldInitialisationException;

public class VacuumWorldController extends AbstractViewController {
    public static final Integer MAXGRIDSIZE = 100;
    public static final Integer MINGRIDSIZE = 2;
    public static final Boolean SINGLEAVATAR = true;

    protected static final Collection<Class<?>> POSSIBLEAGENTMINDS;
    protected static final Collection<Class<?>> POSSIBLEMINDS;
    
    private VacuumWorldView view;
    private Collection<VacuumWorldAvatarLink> avatarlinks;
    private Thread universeThread;
    
    static {
	Collection<Class<?>> possibleminds = null;
	Collection<Class<?>> possibleagentminds = null;
	try {
	    possibleminds = AgentMindFinder.getAgentMinds();
	    possibleagentminds = Collections
		    .unmodifiableCollection(AgentMindFinder.getNonUserAgentMinds(possibleminds));
	} catch (ClassFinderException e) {
	    throw new VacuumWorldInitialisationException(e);
	}
	POSSIBLEAGENTMINDS = possibleagentminds;
	POSSIBLEMINDS = possibleminds;
	String pminfo = "possible minds: " + POSSIBLEMINDS;
	String paminfo = "possible agent minds: " + POSSIBLEAGENTMINDS;
	LogUtils.log(pminfo);
	LogUtils.log(paminfo);
    }

    public VacuumWorldController(VacuumWorldView view, VacuumWorldUniverse universe) {
	super(universe);
	this.view = view;
	this.view.setUniverseStart(new UniverseStart());
	this.view.setUniversePause(new UniversePause());
	this.view.setUniverseRestart(new UniverseRestart());
	this.view.setModel(universe.getAmbient());
	this.avatarlinks = new HashSet<>();
	universe.addObserver(view);
    }
    
    public static Collection<Class<?>> getPossibleAgentMinds() {
	return POSSIBLEAGENTMINDS;
    }

    public void pause() {
	this.getUniverse().setPaused(true);
    }

    public void unpause() {
	this.getUniverse().setPaused(false);
    }

    public void getAmbientState() {
	// wait for the universe to be safely paused
	while (!this.getUniverse().isPausedSafe()) {
	    // do nothing
	}
    }

    public void start(StartParameters params) throws InterruptedException {
	if (universeThread != null) {
	    avatarlinks.forEach(VacuumWorldAvatarLink::destroy);
	    avatarlinks.clear();
	    // wait for the thread to finish
	    universeThread.join();
	}
	Collection<VacuumWorldAgent> agents = new ArrayList<>();
	Collection<Dirt> dirts = new ArrayList<>();
	Collection<VacuumWorldAvatar> avatars = new ArrayList<>();

	params.getDirtapps().forEach((p, a) -> {
	    Dirt d = new Dirt(a.getColor());
	    d.getAppearance().setPosition(p);
	    dirts.add(d);
	});

	params.getAgentapps().forEach((p, a) -> {
	    if (a.getColor() == BodyColor.USER) {
		agents.add(setUpAgent(VacuumWorld.USERMIND, p, a));
	    } else if (a.getColor() == BodyColor.AVATAR) {
		avatars.add(setupAvatar(VacuumWorld.AVATARMIND, p, a));
	    } else {
		agents.add(setUpAgent(params.getMindmap().get(a.getColor()), p, a));
	    }
	});
	this.getUniverse().initialiseGrid(params.getDimension(), params.getSimulationRate(), agents, dirts, avatars);
	universeThread = new Thread(this.getUniverse());
	universeThread.start();
    }

    private VacuumWorldAgent setUpAgent(Class<?> mind, Position p, VacuumWorldAgentAppearance a) {
	VacuumWorldAgent agent = VacuumWorld.createVacuumWorldAgent(mind);
	agent.getAppearance().setColor(a.getColor());
	agent.getAppearance().setOrientation(a.getOrientation());
	agent.getAppearance().setPosition(p);
	return agent;
    }

    private VacuumWorldAvatar setupAvatar(Class<? extends AbstractAvatarMind<VacuumWorldAction>> mind, Position p,
	    VacuumWorldAgentAppearance a) {
	VacuumWorldAvatar avatar = VacuumWorld.createAvatar(mind);
	avatar.getAppearance().setColor(a.getColor());
	avatar.getAppearance().setOrientation(a.getOrientation());
	avatar.getAppearance().setPosition(p);
	// set up the avatar link
	VacuumWorldAvatarLink link = new VacuumWorldAvatarLink(avatar.getMind());
	avatarlinks.add(link);
	this.view.addKeyListenerToMainPanel(link);
	return avatar;
    }

    @Override
    public void update(Observable o, Object arg) {
	// unused
    }

    public class UniversePause {
	private boolean paused = false;

	public void pause() {
	    setPaused(true);
	    VacuumWorldController.this.pause();
	}

	public void unpause() {
	    setPaused(false);
	    VacuumWorldController.this.unpause();
	}

	public boolean isPaused() {
	    return paused;
	}

	private void setPaused(boolean paused) {
	    this.paused = paused;
	}
    }

    public class UniverseStart {
	public void start(StartParameters params) {
	    try {
		VacuumWorldController.this.start(params);
	    } catch (InterruptedException e) {
		LogUtils.log(e);
		Thread.currentThread().interrupt();
	    }
	}
    }

    public class UniverseRestart {
	public void restart() {
	    getUniverse().stop();
	}
    }

    @Override
    public VacuumWorldUniverse getUniverse() {
	return (VacuumWorldUniverse) super.getUniverse();
    }
}