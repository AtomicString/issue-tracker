package me.atomicstring.tracker.pages.dsl;

import j2html.tags.ContainerTag;

public interface Component {
	
	public ContainerTag<?> build();
	
}
