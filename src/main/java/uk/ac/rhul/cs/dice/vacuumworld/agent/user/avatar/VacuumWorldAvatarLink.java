package uk.ac.rhul.cs.dice.vacuumworld.agent.user.avatar;

import java.awt.event.KeyEvent;
import java.util.logging.Level;

import uk.ac.rhul.cs.dice.starworlds.entities.avatar.AbstractAvatarMind;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.link.AbstractAvatarLink;
import uk.ac.rhul.cs.dice.starworlds.entities.avatar.link.KeyboardAvatarLink;
import uk.ac.rhul.cs.dice.vacuumworld.VacuumWorld;
import uk.ac.rhul.cs.dice.vacuumworld.actions.MoveAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.PlaceDirtAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.TurnAction;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAction;
import uk.ac.rhul.cs.dice.vacuumworld.misc.BodyColor;
import uk.ac.rhul.cs.dice.vacuumworld.misc.TurnDirection;

/**
 * The {@link AbstractAvatarLink AvatarLink} that is used to link a user of
 * {@link VacuumWorld} to a {@link VacuumWorldSelflessAvatarMind}.
 * 
 * @author Ben Wilkins
 *
 */
public class VacuumWorldAvatarLink
		extends
		KeyboardAvatarLink<VacuumWorldAction, AbstractAvatarMind<VacuumWorldAction>> {

	public VacuumWorldAvatarLink(AbstractAvatarMind<VacuumWorldAction> mind) {
		super(mind);
		// set up action mapping
		try {
			this.addMapping(KeyEvent.VK_A, TurnAction.class,
					TurnDirection.class);
			this.mapDefaultArguments(KeyEvent.VK_A, TurnDirection.LEFT);
			this.addMapping(KeyEvent.VK_D, TurnAction.class,
					TurnDirection.class);
			this.mapDefaultArguments(KeyEvent.VK_D, TurnDirection.RIGHT);
			this.addMapping(KeyEvent.VK_W, MoveAction.class);
			this.addMapping(KeyEvent.VK_SPACE, PlaceDirtAction.class);
			this.addMapping(KeyEvent.VK_O, PlaceDirtAction.class,
					BodyColor.class);
			this.mapDefaultArguments(KeyEvent.VK_O, BodyColor.ORANGE);
			this.addMapping(KeyEvent.VK_G, PlaceDirtAction.class,
					BodyColor.class);
			this.mapDefaultArguments(KeyEvent.VK_G, BodyColor.GREEN);
		} catch (NoSuchMethodException | SecurityException e) {
			VacuumWorld.LOGGER
					.log(Level.SEVERE,
							"Failed to initialise avatar link, the Avatar cannot be used properly.",
							e);
			super.clearMappings();
		}
	}

	public void destroy() {
		this.getMind().destoryLink();
	}

	public void relink() {
		this.getMind().link();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (this.containsKey(e.getKeyCode())) {
			this.decide(e.getKeyCode());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// unused
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// unused
	}
}
