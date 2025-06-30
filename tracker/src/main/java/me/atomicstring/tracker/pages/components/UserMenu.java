package me.atomicstring.tracker.pages.components;

import static j2html.TagCreator.*;
import j2html.tags.ContainerTag;
import me.atomicstring.tracker.pages.dsl.Component;
import me.atomicstring.tracker.users.User;

public class UserMenu implements Component {

	User curUser;

	
	public UserMenu(User curUser) {
		this.curUser = curUser;
	}

	@Override
	public ContainerTag<?> build() {
		return a(img().withSrc("/users/" + curUser.getId() + "/image")).withHref("/profile/" + curUser.getId());
	}

}
