package me.atomicstring.tracker.pages;

import static j2html.TagCreator.*;

import j2html.tags.ContainerTag;
import j2html.tags.specialized.HtmlTag;
import me.atomicstring.tracker.pages.dsl.Component;
import me.atomicstring.tracker.pages.dsl.Page;
import me.atomicstring.tracker.pages.dsl.PageBuilder;

public class MainPage implements Page{

	@Override
	public HtmlTag getPage() {
		PageBuilder base = new PageBuilder();
		base.addScript("/webjars/htmx.org/2.0.5/dist/htmx.min.js");
		base.addTitle("Main Page");
		base.nextStage();
		base.skipStage();
		base.attachComponent(new Component() {
			
			@Override
			public ContainerTag<?> build() {
				return h1("Hello World");
			}
		});
		base.nextStage();
		base.skipStage();
		return base.build();
	}


}
