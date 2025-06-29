package me.atomicstring.tracker.pages;

import io.javalin.http.Context;
import j2html.tags.specialized.HtmlTag;
import me.atomicstring.tracker.pages.components.LoginOption;
import me.atomicstring.tracker.pages.components.Logo;
import me.atomicstring.tracker.pages.components.LogoutMenu;
import me.atomicstring.tracker.pages.components.NavSeparator;
import me.atomicstring.tracker.pages.components.UserMenu;
import me.atomicstring.tracker.pages.dsl.Page;
import me.atomicstring.tracker.pages.dsl.PageBuilder;
import me.atomicstring.tracker.users.User;

public class MainPage implements Page {

	@Override
	public HtmlTag getPage(Context ctx) {
		// HEAD
		PageBuilder base = new PageBuilder();
		base.addScript("/webjars/htmx.org/2.0.5/dist/htmx.min.js");
		base.addScript("https://cdn.tailwindcss.com");
		base.addTitle("Home");
		base.nextStage();
		// NAVBAR
		base.attachComponent(new Logo());
		if (ctx.attribute("user") != null) {
			if (ctx.attribute("user") instanceof User) {
				base.attachComponent(new LogoutMenu());
				base.attachComponent(new NavSeparator());
				base.attachComponent(new UserMenu(ctx.attribute("user")));
			} else {
				base.attachComponent(new LoginOption(), "float-right");
			}
		}
		base.nextStage("py-5 px-5");
		// CONTENT
		base.nextStage("mx-auto w-4/12");
		// FOOTER
		base.skipStage();
		return base.build();
	}

}
