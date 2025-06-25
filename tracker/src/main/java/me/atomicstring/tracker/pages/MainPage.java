package me.atomicstring.tracker.pages;

import static j2html.TagCreator.*;

import j2html.tags.specialized.HtmlTag;
import me.atomicstring.tracker.pages.dsl.Page;
import me.atomicstring.tracker.pages.dsl.PageBuilder;

public class MainPage implements Page{

	@Override
	public HtmlTag getPage() {
		PageBuilder base = new PageBuilder();
		return base.build().with(body(h1("Hello World")));
	}


}
