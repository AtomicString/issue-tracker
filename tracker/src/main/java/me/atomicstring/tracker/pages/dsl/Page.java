package me.atomicstring.tracker.pages.dsl;

import io.javalin.http.Context;
import j2html.tags.specialized.HtmlTag;

public interface Page {
	
	public HtmlTag getPage(Context ctx);
	
}
