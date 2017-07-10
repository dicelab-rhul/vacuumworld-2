package uk.ac.rhul.cs.dice.vacuumworld.agent.user.avatar;

import java.util.Collection;

import uk.ac.rhul.cs.dice.starworlds.entities.avatar.AbstractSelfishAvatarMind;
import uk.ac.rhul.cs.dice.starworlds.perception.Perception;
import uk.ac.rhul.cs.dice.vacuumworld.actions.VacuumWorldAction;
import uk.ac.rhul.cs.dice.vacuumworld.agent.user.UserMindAnnotation;

@UserMindAnnotation
public class VacuumWorldSelfishAvatarMind extends
		AbstractSelfishAvatarMind<VacuumWorldAction> {

	public VacuumWorldSelfishAvatarMind() {
	}

	@Override
	public void showAvatarView(Collection<Perception<?>> perceptions) {

	}
}
