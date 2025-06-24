package me.atomicstring.tracker.pages.dsl;

import static j2html.TagCreator.head;
import static j2html.TagCreator.html;
import static j2html.TagCreator.script;

import j2html.tags.ContainerTag;

public class PageBuilder {
	ContainerTag<?> working;

	public PageBuilder() {
		working = html(
				head(
						script().withSrc("/webjars/htmx.org/2.0.5/dist/htmx.min.js")
					)
				);
	}
	
	public PageBuilder addHeader() {
		return this;
	}
	
	public ContainerTag<?> build() {
		return working;
	}
}
