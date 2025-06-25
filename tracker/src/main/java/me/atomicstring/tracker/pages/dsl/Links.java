package me.atomicstring.tracker.pages.dsl;

import static j2html.TagCreator.*;

import j2html.tags.ContainerTag;

public class Links implements Component {
	
	String base;

	public Links(String base) {
		this.base = base;
	}
	
	@Override
	public ContainerTag<?> build() {
		return a().withHref(base);
	}

}
