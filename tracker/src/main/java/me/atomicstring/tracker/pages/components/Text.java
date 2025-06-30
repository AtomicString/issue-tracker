package me.atomicstring.tracker.pages.components;

import static j2html.TagCreator.*;
import j2html.tags.ContainerTag;
import me.atomicstring.tracker.pages.dsl.Component;

public class Text implements Component {
	String text;
	
	public Text(String text) {
		super();
		this.text = text;
	}

	@Override
	public ContainerTag<?> build() {
		return div(text);
	}

}
