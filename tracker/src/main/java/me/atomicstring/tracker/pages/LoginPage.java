package me.atomicstring.tracker.pages;

import io.javalin.http.Context;
import j2html.tags.specialized.HtmlTag;
import me.atomicstring.tracker.pages.components.Header;
import me.atomicstring.tracker.pages.components.Logo;
import me.atomicstring.tracker.pages.components.forms.LoginForm;
import me.atomicstring.tracker.pages.dsl.Page;
import me.atomicstring.tracker.pages.dsl.PageBuilder;

public class LoginPage implements Page {

	@Override
	public HtmlTag getPage(Context ctx) {
		PageBuilder base = new PageBuilder();
		//HEAD
		base.addScript("/webjars/htmx.org/2.0.5/dist/htmx.min.js");
		base.addScript("https://cdn.tailwindcss.com");
		base.addTitle("Login");
		base.nextStage();
		//NAVBAR
		base.attachComponent(new Logo());
		base.nextStage("py-5 px-5");
		//CONTENT
		base.attachComponent(new Header("Login", 3));
		base.attachComponent(new LoginForm(), "mt-6", "flex");
		base.nextStage("mx-auto w-7/12 mt-4");
		//FOOTER
		base.skipStage();
		return base.build();
	}

}
