package me.atomicstring.tracker.pages.components.forms;

import static j2html.TagCreator.*;
import j2html.tags.ContainerTag;
import me.atomicstring.tracker.pages.dsl.Component;

public class IssueForm implements Component {

	@Override
	public ContainerTag<?> build() {
		return div(
				form(
					div("Title")
					.withClasses("text-zinc-700"),
					
					input()
					.withType("text")
					.withName("title")
					.withPlaceholder("Title")
					.withClasses("border-slate-500", "p-2", "rounded-xl", "border-2", "w-full"),
					
					div("Description")
					.withClasses("text-zinc-700", "mt-5"),
					
					textarea()
					.withName("body")
					.withPlaceholder("Description")
					.withRows("10")
					.withClasses("border-slate-500", "p-2", "rounded-xl", "border-2", "w-full"),
					
					div().withClasses("border-b-2", "border-b-slate-700 mt-5"),
					
					div(
						input()
						.withType("submit")
						.withValue("Create")
						.withClasses("text-md border-green-500", "p-2", "rounded-xl", "border-2", "cursor-pointer", "hover:bg-green-500", "transition", "duration-200", "ease-in-out"),

						a("Cancel")
						.withHref("/")
						.withClasses("text-md", "border-zinc-500", "p-2", "rounded-xl", "border-2", "cursor-pointer", "hover:bg-zinc-500", "hover:text-white", "transition", "duration-200", "ease-in-out", "ml-3")
					).withClasses("mt-4")

				).withAction("/issues").withMethod("POST")
			);
	}

}
