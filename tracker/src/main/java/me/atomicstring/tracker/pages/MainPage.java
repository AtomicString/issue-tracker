package me.atomicstring.tracker.pages;

import io.javalin.http.Context;
import static j2html.TagCreator.script;
import j2html.tags.specialized.HtmlTag;
import me.atomicstring.tracker.pages.components.Header;
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
		base.addScript("https://unpkg.com/lucide@latest");
		base.addScript("https://cdn.tailwindcss.com");
		base.addTitle("Home");
		base.nextStage();
		// NAVBAR
		base.attachComponent(new Logo());
		if (ctx.attribute("user") != null) {
			if (ctx.attribute("user") instanceof User) {
				base.attachComponent(new UserMenu(ctx.attribute("user")), "float-right");
				base.attachComponent(new NavSeparator(), "float-right");
				base.attachComponent(new LogoutMenu(), "float-right");
			} else {
				base.attachComponent(new LoginOption(), "flex items-center");
			}
		}
		base.nextStage("flex justify-between items-center py-5 px-5");
		// CONTENT
		base.attachComponent(new Header("Issues", 3));
		base.attachComponent(, null)
		base.nextStage("mx-auto w-10/12 border border-zinc-200 p-5 mt-4");
		// FOOTER
		base.skipStage();
		base.addRaw(script("lucide.createIcons();"));
		return base.build();
	}

}
