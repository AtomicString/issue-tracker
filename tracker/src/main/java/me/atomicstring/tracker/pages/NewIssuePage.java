package me.atomicstring.tracker.pages;

import static j2html.TagCreator.script;

import static j2html.TagCreator.*;
import io.javalin.http.Context;
import j2html.tags.ContainerTag;
import j2html.tags.specialized.HtmlTag;
import me.atomicstring.tracker.pages.components.Header;
import me.atomicstring.tracker.pages.components.LoginOption;
import me.atomicstring.tracker.pages.components.Logo;
import me.atomicstring.tracker.pages.components.LogoutMenu;
import me.atomicstring.tracker.pages.components.NavSeparator;
import me.atomicstring.tracker.pages.components.UserMenu;
import me.atomicstring.tracker.pages.components.forms.IssueForm;
import me.atomicstring.tracker.pages.dsl.Component;
import me.atomicstring.tracker.pages.dsl.Page;
import me.atomicstring.tracker.pages.dsl.PageBuilder;
import me.atomicstring.tracker.users.User;

public class NewIssuePage implements Page {

	@Override
	public HtmlTag getPage(Context ctx) {
		PageBuilder base = new PageBuilder();
		base.addScript("/webjars/htmx.org/2.0.5/dist/htmx.min.js");
		base.addScript("https://unpkg.com/lucide@latest");
		base.addScript("https://cdn.tailwindcss.com");
		base.addTitle("New Issue");
		base.nextStage();
		// NAVBAR
		base.attachComponent(new Logo());
		if (ctx.attribute("user") != null) {
			if (ctx.attribute("user") instanceof User) {
				base.attachComponent(new Component() {
					
					@Override
					public ContainerTag<?> build() {
						// TODO Auto-generated method stub
						return div(new UserMenu(ctx.attribute("user")).build(), new NavSeparator().build(), new LogoutMenu().build());
					}
				}, "flex items-center");
			} else {
				base.attachComponent(new LoginOption(), "flex items-center");
			}
		}
		base.nextStage("flex justify-between items-center py-5 px-5");
		// CONTENT
		base.attachComponent(new Header("Create a New Issue", 3));
		base.attachComponent(new IssueForm(), "mt-5");
		base.nextStage("mx-auto w-10/12 border border-zinc-200 p-7 mt-4");
		// FOOTER
		base.skipStage();
		base.addRaw(script("lucide.createIcons();"));
		return base.build();
	}

}
