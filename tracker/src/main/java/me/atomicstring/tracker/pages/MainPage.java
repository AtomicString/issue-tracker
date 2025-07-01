package me.atomicstring.tracker.pages;

import io.javalin.http.Context;
import static j2html.TagCreator.script;

import java.util.List;

import static j2html.TagCreator.*;
import j2html.tags.ContainerTag;
import j2html.tags.specialized.HtmlTag;
import me.atomicstring.tracker.dao.IssueDao;
import me.atomicstring.tracker.pages.components.Header;
import me.atomicstring.tracker.pages.components.IssueList;
import me.atomicstring.tracker.pages.components.LoginOption;
import me.atomicstring.tracker.pages.components.Logo;
import me.atomicstring.tracker.pages.components.LogoutMenu;
import me.atomicstring.tracker.pages.components.NavSeparator;
import me.atomicstring.tracker.pages.components.UserMenu;
import me.atomicstring.tracker.pages.dsl.Component;
import me.atomicstring.tracker.pages.dsl.Page;
import me.atomicstring.tracker.pages.dsl.PageBuilder;
import me.atomicstring.tracker.users.User;

public class MainPage implements Page {

	IssueDao issueDao;

	public MainPage(IssueDao issueDao) {
		this.issueDao = issueDao;
	}

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
				base.attachComponent(new LogoutMenu());
			} else {
				base.attachComponent(new LoginOption(), "flex items-center");
			}
		}
		base.nextStage("flex justify-between items-center py-5 px-5");
		// CONTENT
		base.attachComponent(new Component() {

			@Override
			public ContainerTag<?> build() {
				return div(
						new Header("Issue", 3)
						.build()
						.withClasses("inline-block")
					).condWith(
						ctx.attribute("user") instanceof User,
						
						a("New Issue")
						.withClasses(
							"border-green-400 border-2 hover:bg-green-400 transition duration-200 ease-in-out rounded-2xl float-right p-2"
						)
						.withHref("/issues"));
			}
		}, "w-full");
		base.attachComponent(new IssueList(issueDao.getLatestPage(10)));
		base.nextStage("mx-auto w-10/12 border border-zinc-200 rounded-xl p-5 mt-4");
		// FOOTER
		base.skipStage();
		base.addRaw(script("lucide.createIcons();"));
		return base.build();
	}

}
