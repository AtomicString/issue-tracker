package me.atomicstring.tracker.pages.components;

import static j2html.TagCreator.*;
import j2html.tags.ContainerTag;
import me.atomicstring.tracker.pages.dsl.Component;

public class Header implements Component {
	
	String title;
	int size;

	public Header(String title, int size) {
		this.title = title;
		this.size = size;
	}

	@Override
	public ContainerTag<?> build() {
		return div(span(title).withClass("text-" + size + "xl" + " font-bold"));
	}

}
