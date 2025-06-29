package me.atomicstring.tracker.pages.components;

import static j2html.TagCreator.*;
import j2html.tags.ContainerTag;
import me.atomicstring.tracker.pages.dsl.Component;

public class LogoutMenu implements Component {

	@Override
	public ContainerTag<?> build() {
		return a(span("Logout").withClasses("text-xl", "font-bold")).withHref("/logout");
	}

}
