package me.atomicstring.tracker.pages.components;

import static j2html.TagCreator.*;
import j2html.tags.ContainerTag;
import me.atomicstring.tracker.pages.dsl.Component;

public class NavSeparator implements Component {

	@Override
	public ContainerTag<?> build() {
		return span(" | ").withClasses("px-3");
	}

}
