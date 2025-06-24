package me.atomicstring.tracker.pages;

import static j2html.TagCreator.*;
import j2html.tags.DomContent;
import me.atomicstring.tracker.pages.dsl.Component;
import me.atomicstring.tracker.pages.dsl.PageBuilder;

public class MainPage implements Component{

	@Override
	public DomContent build() {
		PageBuilder base = new PageBuilder();
		return base.build().with(body(h1("Hello World")));
	}


}
