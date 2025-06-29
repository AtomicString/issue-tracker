package me.atomicstring.tracker.pages.components;

import static j2html.TagCreator.*;
import j2html.tags.ContainerTag;
import me.atomicstring.tracker.pages.dsl.Component;

public class LoginOption implements Component {

	@Override
	public ContainerTag<?> build() {
		return div(a(span("Login").withClasses("text-xl")).withHref("/login"), new NavSeparator().build(), a(span("Signup").withClasses("text-xl")).withHref("/signup"));
	}

}
