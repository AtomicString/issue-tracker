package me.atomicstring.tracker.pages;

import static j2html.TagCreator.a;
import static j2html.TagCreator.div;
import static j2html.TagCreator.script;

import java.util.List;

import io.javalin.http.Context;
import j2html.tags.ContainerTag;
import j2html.tags.specialized.HtmlTag;
import me.atomicstring.tracker.dao.data.Comment;
import me.atomicstring.tracker.dao.data.Issue;
import me.atomicstring.tracker.pages.components.CommentSection;
import me.atomicstring.tracker.pages.components.Header;
import me.atomicstring.tracker.pages.components.LoginOption;
import me.atomicstring.tracker.pages.components.Logo;
import me.atomicstring.tracker.pages.components.LogoutMenu;
import me.atomicstring.tracker.pages.components.NavSeparator;
import me.atomicstring.tracker.pages.components.Text;
import me.atomicstring.tracker.pages.components.UserMenu;
import me.atomicstring.tracker.pages.dsl.Component;
import me.atomicstring.tracker.pages.dsl.Page;
import me.atomicstring.tracker.pages.dsl.PageBuilder;
import me.atomicstring.tracker.users.User;

public class IssuePage implements Page {

	Issue issue;
	User author;
	List<Comment> comments;

	public IssuePage(Issue issue, User author, List<Comment> comments) {
		this.issue = issue;
		this.author = author;
		this.comments = comments;
	}

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
				base.attachComponent(new LogoutMenu());
			} else {
				base.attachComponent(new LoginOption(), "flex items-center");
			}
		}
		base.nextStage("flex justify-between items-center py-5 px-5");
		// CONTENT
		base.attachComponent(new Header(issue.getTitle(), 3));
		base.attachComponent(new Text(author.getUsername()), "text-lg");
		base.attachComponent(new Text(issue.getBody()), "mt-5 border border-zinc-300 rounded-xl p-4");
		base.attachComponent(new Component() {
			
			@Override
			public ContainerTag<?> build() {
				return div(
						a("Comment")
						.attr("hx-get", "/new-comment?issue=" + issue.getId().toString())
						.attr("hx-swap", "outerHTML")
						.withClass("text-blue-600 cursor-pointer underline decoration-solid")
					).attr("hx-target", "this");
			}
		}, "mt-5");
		base.attachComponent(new Header("Comments", 2), "mt-5");
		base.attachComponent(new CommentSection(comments));
		base.nextStage("mx-auto w-10/12 border border-zinc-200 p-7 mt-4");
		// FOOTER
		base.skipStage();
		base.addRaw(script("lucide.createIcons();"));
		return base.build();
	}

}
